package hypothetical

import (
	"context"
	"encoding/csv"
	"io"
	"log"
	"os"
	"path/filepath"
	"regexp"
	"strings"

	"github.com/cespare/xxhash/v2"
)

// filteredSearchOfDirectoryTree Walks down a directory tree looking for
// files that match the pattern: re. If a file is found print it out and
// add it to the files list for later user.
func filteredSearchOfDirectoryTree(re *regexp.Regexp, dir string) ([]string, error) {
	// Just a demo, this is how we capture the files that match
	// the pattern.
	files := []string{}

	// Function variable that can be used to filter
	// files based on the pattern.
	// Note that it uses re internally to filter.
	// Also note that it populates the files variable with
	// the files that matches the pattern.
	walk := func(fn string, fi os.FileInfo, err error) error {
		if re.MatchString(fn) == false {
			return nil
		}
		if fi.IsDir() {
			// fmt.Println(fn + string(os.PathSeparator))
		} else {
			// fmt.Println(fn)
			files = append(files, fn)
		}
		return nil
	}
	filepath.Walk(dir, walk)
	// fmt.Printf("Found %[1]d files.\n", len(files))
	return files, nil
}

// Struct to store hash value and offset of each record
type Record struct {
	HashValue uint64
	Offset    int64
}

// Function to store the records in HashMap as Key-Value pairs
func getRecords(filePath string, separator string) map[string]Record {
	records := make(map[string]Record)
	file, err := os.Open(filePath)
	if err != nil {
		log.Fatal(err)
		return records
	}
	// Assumption: Windows - 2, Linux - 1
	newLineCharacterLength := int64(GetNewLineCharacterLength(filePath, separator))
	csvReader := csv.NewReader(file)
	csvReader.Comma = []rune(separator)[0]
	var offset int64 = 0
	for {
		columnValues, err := csvReader.Read()
		if err != nil {
			break
		}
		if err == io.EOF {
			break
		}
		// identifier is required to identify the record uniquely
		identifier := columnValues[0]
		row := strings.Join(columnValues[1:], separator)
		// Hash the non-key columns and store its values in the Record struct
		records[identifier] = Record{HashValue: xxhash.Sum64String(row), Offset: offset}
		offset += int64(len(identifier+separator+row)) + newLineCharacterLength
	}
	return records
}

// Actual function to compare two HashMaps of each files to find the differences.
func Compare(oldFilePath, newFilePath, separator string) ([]string, []string, []string) {
	var inserts []string
	var updates []string
	var deletes []string

	// offsets are used to get the data from the file
	insertsOffsets := []int64{}
	updatesOffsets := []int64{}
	deletesOffsets := []int64{}

	oldFile := getRecords(oldFilePath, separator)
	newFile := getRecords(newFilePath, separator)
	for oldFileKey := range oldFile {
		newFileRecord, ok := newFile[oldFileKey]
		// If the record is present in both the files, then compare the hash values
		if ok {
			if oldFile[oldFileKey].HashValue != newFileRecord.HashValue {
				updatesOffsets = append(updatesOffsets, newFileRecord.Offset)
			}
			// Delete the record from the map once it is compared
			delete(newFile, oldFileKey)
			delete(oldFile, oldFileKey)
			// If the record is not present in the new file, then it is deleted
		} else {
			deletesOffsets = append(deletesOffsets, oldFile[oldFileKey].Offset)
		}

	}
	// If the record is present in the new file, then it is inserted (left over records)
	for newFileKey := range newFile {
		newFileRecord := newFile[newFileKey]
		insertsOffsets = append(insertsOffsets, newFileRecord.Offset)
	}
	// Get the data from the offsets
	inserts = GetDataFromOffsets(newFilePath, insertsOffsets)
	updates = GetDataFromOffsets(newFilePath, updatesOffsets)
	deletes = GetDataFromOffsets(oldFilePath, deletesOffsets)
	return inserts, updates, deletes
}

// To cover the solution, create a map of arrays
type MapOfArrays struct {
	make (map[string][]string)
}

func Run(ctx context.Context, c Config) map[string][]string {

	x := make(map[string][]string)

	// Find al base outputs...
	reOut := regexp.MustCompile("/[0-9]{3}.csv")
	outFiles, error := filteredSearchOfDirectoryTree(reOut, c.Path)

	if error == nil {

		// Iterate posible outputs to find the algorithm results...
		for _, outFile := range outFiles {

			// Considering file hash this pattern /path/to/file/001.csv get the name of the file
			re := regexp.MustCompile("/[0-9]{3}.csv")
			outName := re.FindString(outFile)
			// Remove first charater and file extension to find the "number"
			outName = outName[1 : len(outName)-4]

			// Find the algorithm results...
			reOutAlg := regexp.MustCompile("/" + outName + "_ALG[0-9]{3}.csv")
			outSolutions, error := filteredSearchOfDirectoryTree(reOutAlg, c.Path)

			// If there is any solution...
			if error == nil {
				// Iterate all to find if fis...
				for _, solution := range outSolutions {
					// Compare the files
					inserts, updates, deletes := Compare(outFile, solution, ",")
					// if all arrays are empty then the files are equals
					if len(inserts) == 0 && len(updates) == 0 && len(deletes) == 0 {
						alg := reOutAlg.FindString(solution)
						// Remove first 7 characters, and the last 4
						alg = alg[8 : len(alg)-4]
						x[alg] = append(x[alg], outName)
					}
				}
			}

		}

	}
	return x
}
