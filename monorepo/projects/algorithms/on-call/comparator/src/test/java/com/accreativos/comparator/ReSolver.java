package com.accreativos.comparator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

public class ReSolver {
		

    private static List<OnCallSchedule> readItems(String fileName) throws URISyntaxException {
        List<OnCallSchedule> items = new ArrayList<>();
        InputStream is = Solver.class.getResourceAsStream(fileName);
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

//		MYRUN=value

		// Read environment variable
		String MYRUN = System.getenv("MYRUN");
        if (MYRUN == null) {
            System.out.println("MYRUN variable not set");
            System.exit(1);
        }
        System.out.println("MYRUN: "+MYRUN);

		// Listado composicion de precios
		List<OnCallSchedule> workers = new ArrayList<OnCallSchedule>();
		
		try {
			workers.addAll(readItems(MYRUN));
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (OnCallSchedule workerCompositionFlatVO : workers) {
			System.out.println(workerCompositionFlatVO.toString());
		}
		System.out.println();

		List<OnCallSchedule> workerListsProcessed = flatListWorkers(workers);
		
		for (OnCallSchedule workerCompositionFlatVO : workerListsProcessed) {
			System.out.println(workerCompositionFlatVO.toString());
		}
		System.out.println();
		
	}

	private static List<OnCallSchedule> flatListWorkers(List<OnCallSchedule> workerLists) {
		List<OnCallSchedule> resultNewList = new ArrayList<OnCallSchedule>(); 
		
		for (OnCallSchedule interatedRange : workerLists) {

			// System.out.println("*** *** *** ---interatedRange--->"+interatedRange.toString());

			// System.out.println("--- RESULT LIST ---");
			// for (OnCallSchedule workerCompositionFlatVO : resultNewList) {
			// 	System.out.println(workerCompositionFlatVO.toString());
			// }
			// System.out.println();


			List<OnCallSchedule> overlaped = findByStartDateLessThanEqualAndEndDateGreaterThan(resultNewList, interatedRange.getStartDate());
			if (overlaped.size()>0) {
				resultNewList.remove(overlaped.get(0)); 
				// System.out.println("---overlaped--->"+overlaped.get(0).toString());
			}

			if (overlaped.size() == 1) {
				OnCallSchedule newInTheMiddle = new OnCallSchedule();
				newInTheMiddle.setStartDate(overlaped.get(0).getStartDate());
				newInTheMiddle.setEndDate(overlaped.get(0).getEndDate());
				newInTheMiddle.setPriority(overlaped.get(0).getPriority());
				newInTheMiddle.setWorkerId(overlaped.get(0).getWorkerId());
				;

				if (interatedRange.getEndDate().longValue()<newInTheMiddle.getEndDate().longValue()) {
					OnCallSchedule newToTheRight = new OnCallSchedule();
					newToTheRight.setStartDate(interatedRange.getEndDate());
					newToTheRight.setEndDate(overlaped.get(0).getEndDate());
					newToTheRight.setPriority(overlaped.get(0).getPriority());
					newToTheRight.setWorkerId(overlaped.get(0).getWorkerId());
					// System.out.println("---newToTheRight--->"+newToTheRight.toString());
					resultNewList.add(newToTheRight);
				}

				newInTheMiddle.setEndDate(interatedRange.getStartDate()-1000);
				newInTheMiddle.setWorkerId(interatedRange.getWorkerId());
				// System.out.println("---newInTheMiddle--->"+newInTheMiddle.toString());
				resultNewList.add(newInTheMiddle);

			} 

			// newToTheLeft (-1000 to no overlap)
			interatedRange.setEndDate(interatedRange.getEndDate()-1000);
			if (overlaped.size()>0) {
				interatedRange.setWorkerId(overlaped.get(0).getWorkerId());
			}
			// System.out.println("---interatedRange--->"+interatedRange.toString());
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
				// result.add(workerListsProcessed.get(i));
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
