package com.accreativos.oncalltutti.main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class Main {

	private static void writeToFile(List<OnCallSchedule> items, String fileName) throws URISyntaxException {


 		try {
            Path path = Paths.get(fileName);
            Files.deleteIfExists(path);
            Files.createFile(path);
            try (BufferedWriter writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8)) {
				writer.write("startDate,endDate,person");
                    writer.newLine();
                for (OnCallSchedule item : items) {
                    writer.write(item.toCSV());
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
	}

    private static List<OnCallSchedule> readItems(String fileName) throws FileNotFoundException {
        List<OnCallSchedule> items = new ArrayList<>();
        InputStream is = new FileInputStream(fileName);
        // using try with resource, Java 7 feature to close resources
        try (BufferedReader br =  new BufferedReader(new InputStreamReader(is))) {

            // read the first line from the text file
            String line = br.readLine();

            line = br.readLine();

            // loop until all lines are read
            while (line != null) {

                // use string.split to load a string array with the values from
                // each line of
                // the file, using a comma as the delimiter
                String[] attributes = line.split(",");

                OnCallSchedule item = new OnCallSchedule();
				// Format datetime from YYYY-MM-DDTHH:MM:SSZ
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH.mm.ss'Z'");
                Date date = null;
                try {
                    date = formatter.parse(attributes[0]);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                item.setStartDate(date.getTime());
                // Format datetime from YYYY-MM-DDTHH:MM:SSZ
                try {
                    date = formatter.parse(attributes[1]);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                item.setEndDate(date.getTime());
				item.setWorkerId(Long.parseLong(attributes[2]));
                item.setPriority(Integer.parseInt(attributes[3]));

                // adding book into ArrayList
                items.add(item);

                // read next line before looping
                // if end of file reached, line would be null
                line = br.readLine();
            }

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        return items;
    }	

	
	public static void main(String[] args) {

		// Read environment variable
		String INPUT = System.getenv("INPUT");
        if (INPUT == null) {
            System.out.println("INPUT variable not set");
            System.exit(1);
        }
        System.out.println("INPUT: "+INPUT);

		String OUTPUT = System.getenv("OUTPUT");
        if (OUTPUT == null) {
            System.out.println("OUTPUT variable not set");
            System.exit(1);
        }
        System.out.println("OUTPUT: "+OUTPUT);


		// Listado composicion de precios
		List<OnCallSchedule> workers = new ArrayList<OnCallSchedule>();
		
		try {
			workers.addAll(readItems(INPUT));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		List<OnCallSchedule> workerListsProcessed = flatListWorkers(workers);

		try {
			writeToFile(workerListsProcessed, OUTPUT);
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private static List<OnCallSchedule> flatListWorkers(List<OnCallSchedule> workerLists) {
		List<OnCallSchedule> resultNewList = new ArrayList<OnCallSchedule>(); 
		
		for (OnCallSchedule interatedRange : workerLists) {

            List<OnCallSchedule> overlaped = findByStartDateLessThanEqualAndEndDateGreaterThan(resultNewList, interatedRange.getStartDate());
			if (overlaped.size()>0) {
				resultNewList.remove(overlaped.get(0)); 
			}

			if (overlaped.size() == 1) {
				OnCallSchedule newInTheMiddle = new OnCallSchedule();
				newInTheMiddle.setStartDate(overlaped.get(0).getStartDate());
				newInTheMiddle.setEndDate(overlaped.get(0).getEndDate());
				newInTheMiddle.setPriority(overlaped.get(0).getPriority());
				newInTheMiddle.setWorkerId(overlaped.get(0).getWorkerId());

				if (interatedRange.getEndDate().longValue()<newInTheMiddle.getEndDate().longValue()) {
					OnCallSchedule newToTheRight = new OnCallSchedule();
					newToTheRight.setStartDate(interatedRange.getEndDate());
					newToTheRight.setEndDate(overlaped.get(0).getEndDate());
					newToTheRight.setPriority(overlaped.get(0).getPriority());
					newToTheRight.setWorkerId(overlaped.get(0).getWorkerId());
					resultNewList.add(newToTheRight);
				}

				newInTheMiddle.setEndDate(interatedRange.getStartDate()-1000);
				newInTheMiddle.setWorkerId(overlaped.get(0).getWorkerId());
				resultNewList.add(newInTheMiddle);

			} 

			// newToTheLeft (-1000 to no overlap)
			interatedRange.setEndDate(interatedRange.getEndDate()-1000);
			if (overlaped.size()>0) {
				interatedRange.setWorkerId(interatedRange.getWorkerId());
			}
			resultNewList.add(interatedRange);
		}

		return resultNewList;
	}
	
	/**
	 * Utility method to sort a list by startDate
	 * 
	 * @param workerListsProcessed
	 */
	private static void sortListByStartDate(List<OnCallSchedule> workerListsProcessed) {
		workerListsProcessed.sort(new Comparator
		<OnCallSchedule>() {
			@Override
			public int compare(OnCallSchedule o1, OnCallSchedule o2) {
				return o1.getStartDate().compareTo(o2.getStartDate());
			}
		});
	}


	private static List<OnCallSchedule> findByStartDateLessThanEqualAndEndDateGreaterThan(List<OnCallSchedule> workerListsProcessed, Long startDate) {
		sortListByStartDate(workerListsProcessed);
		
		List<OnCallSchedule> result = new ArrayList<>();
        
		for (int i = 0; i < workerListsProcessed.size(); i++) {
			if (workerListsProcessed.get(i).getStartDate().longValue() <= startDate.longValue()) {
				for (int j = i; j < workerListsProcessed.size(); j++) {
					if (workerListsProcessed.get(j).getEndDate().longValue() >= startDate.longValue()) {
						result.add(workerListsProcessed.get(j));
						break;
					}
				}
				break;
			}
		}
		return result;
	}

}

