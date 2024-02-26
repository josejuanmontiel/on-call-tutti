package main

import (
	"context"
	"strings"

	"github.com/olekukonko/tablewriter"
	githubactions "github.com/sethvargo/go-githubactions"

	"github.com/josejuanmontiel/on-call-tutti/monorepo/projects/github/actions/code/sample01/pkg/hypothetical"
)

func run() (map[string][]string, *githubactions.Action) {
	ctx := context.Background()
	action := githubactions.New()

	cfg, err := hypothetical.NewFromInputs(action)
	if err != nil {
		return nil, action
	}

	return hypothetical.Run(ctx, *cfg), action
}

func main() {
	data, action := run()

	// data := [][]string{
	// 	[]string{"A", "The Good", "500"},
	// 	[]string{"B", "The Very very Bad Man", "288"},
	// 	[]string{"C", "The Ugly", "120"},
	// 	[]string{"D", "The Gopher", "800"},
	// }

	// print data
	b := new(strings.Builder)
	table := tablewriter.NewWriter(b)
	table.SetHeader([]string{"Algorithm", "Output"})
	for alg, outputs := range data {
		for _, output := range outputs {
			table.Append([]string{alg, output})
		}
	}
	table.Render()

	action.SetOutput("result", b.String())
	// if err != nil {
	// 	action.Fatalf("%v", err)
	// }
}
