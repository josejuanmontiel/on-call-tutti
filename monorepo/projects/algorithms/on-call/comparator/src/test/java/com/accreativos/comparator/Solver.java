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

public class Solver {

	
	private static void examplesWorkersRandom(List<OnCallSchedule> workers) {

		Random random = new Random();

		for (long i = 0; i < 5; i++) {
			LongStream intStream = random.longs(0, 1000);
			LongStream lengthStream = random.longs(0, 50);
			IntStream prioStream = random.ints(0, 10);
			LongStream workerIdStream = random.longs(0, 3);
				
			OnCallSchedule worker1C = new OnCallSchedule();
			worker1C.setWorkerId(workerIdStream.findFirst().getAsLong());

			long start = intStream.findFirst().getAsLong();
			long end = start + lengthStream.findFirst().getAsLong();
			int prio = prioStream.findFirst().getAsInt();
			
			worker1C.setStartDate(start);
			worker1C.setEndDate(end);
			worker1C.setPriority(prio);
			
			if (!checkIfWorkerOverlap(worker1C,workers)) {
				workers.add(worker1C);
			}
			
		}
	}
	
	private static boolean checkIfWorkerOverlap(OnCallSchedule worker, List<OnCallSchedule> workers) {

		boolean overlap = false;
		for (Iterator iterator = workers.iterator(); iterator.hasNext() && !overlap;) {
			OnCallSchedule workerIter = (OnCallSchedule) iterator.next();
			overlap = ((worker.getPriority().equals(workerIter.getPriority())) &&
					    ((workerIter.getStartDate()<=worker.getStartDate() && worker.getStartDate()<=workerIter.getEndDate()) || 
					     (workerIter.getStartDate()<=worker.getEndDate() && worker.getEndDate()<=workerIter.getEndDate()) ||
					     (workerIter.getStartDate()>=worker.getStartDate() && workerIter.getEndDate()<=worker.getEndDate()) ||
					     (workerIter.getStartDate()<=worker.getStartDate() && workerIter.getEndDate()>=worker.getEndDate()) ||
					     (worker.getStartDate()==worker.getEndDate())
					    )
					   );
		}
		
		return overlap;
	}

	private static void examplesWorkers1(List<OnCallSchedule> workers) {
		// ti=1 tf=8 prio=1
		OnCallSchedule worker1C = new OnCallSchedule();
		worker1C.setWorkerId(1l);
		worker1C.setStartDate(1l);
		worker1C.setEndDate(8l);
		worker1C.setPriority(1);

		// ti=2 tf=4 prio=2
		OnCallSchedule worker2C = new OnCallSchedule();
		worker2C.setWorkerId(2l);
		worker2C.setStartDate(2l);
		worker2C.setEndDate(4l);
		worker2C.setPriority(2);
		
		// ti=3 tf=5 prio=3
		OnCallSchedule worker3C = new OnCallSchedule();
		worker3C.setWorkerId(3l);
		worker3C.setStartDate(3l);
		worker3C.setEndDate(5l);
		worker3C.setPriority(3);

		// ti=2 tf=4 prio=2
		OnCallSchedule worker4C = new OnCallSchedule();
		worker4C.setWorkerId(4l);
		worker4C.setStartDate(4l);
		worker4C.setEndDate(6l);
		worker4C.setPriority(2);
		
		
		// Desordenados por prioridad...
		workers.add(worker3C);
		workers.add(worker4C);
		workers.add(worker2C);
		workers.add(worker1C);
	}
	
	private static void examplesWorkers2(List<OnCallSchedule> workers) {
		// ti=1 tf=8 prio=1
		OnCallSchedule worker1C = new OnCallSchedule();
		worker1C.setWorkerId(1l);
		worker1C.setStartDate(2l);
		worker1C.setEndDate(8l);
		worker1C.setPriority(1);

		// ti=2 tf=4 prio=2
		OnCallSchedule worker2C = new OnCallSchedule();
		worker2C.setWorkerId(2l);
		worker2C.setStartDate(1l);
		worker2C.setEndDate(3l);
		worker2C.setPriority(2);
		
		// ti=3 tf=5 prio=3
		OnCallSchedule worker3C = new OnCallSchedule();
		worker3C.setWorkerId(3l);
		worker3C.setStartDate(4l);
		worker3C.setEndDate(7l);
		worker3C.setPriority(2);

		// Desordenados por prioridad...
		workers.add(worker3C);
		workers.add(worker2C);
		workers.add(worker1C);
	}	
	
	private static void examplesWorkers3(List<OnCallSchedule> workers) {
		// ti=1 tf=8 prio=1
		OnCallSchedule worker1C = new OnCallSchedule();
		worker1C.setWorkerId(1l);
		worker1C.setStartDate(6l);
		worker1C.setEndDate(15l);
		worker1C.setPriority(0);

		// ti=2 tf=4 prio=2
		OnCallSchedule worker2C = new OnCallSchedule();
		worker2C.setWorkerId(2l);
		worker2C.setStartDate(16l);
		worker2C.setEndDate(21l);
		worker2C.setPriority(0);
		
		// ti=3 tf=5 prio=3
		OnCallSchedule worker3C = new OnCallSchedule();
		worker3C.setWorkerId(3l);
		worker3C.setStartDate(12l);
		worker3C.setEndDate(21l);
		worker3C.setPriority(1);

		OnCallSchedule worker4C = new OnCallSchedule();
		worker4C.setWorkerId(4l);
		worker4C.setStartDate(8l);
		worker4C.setEndDate(11l);
		worker4C.setPriority(2);

		OnCallSchedule worker5C = new OnCallSchedule();
		worker5C.setWorkerId(5l);
		worker5C.setStartDate(13l);
		worker5C.setEndDate(20l);
		worker5C.setPriority(2);

		// Desordenados por prioridad...
		workers.add(worker5C);
		workers.add(worker4C);
		workers.add(worker3C);
		workers.add(worker2C);
		workers.add(worker1C);
	}	

	private static void examplesWorkers4(List<OnCallSchedule> workers) {
		// ti=1 tf=8 prio=1
		OnCallSchedule worker1C = new OnCallSchedule();
		worker1C.setWorkerId(1l);
		worker1C.setStartDate(4l);
		worker1C.setEndDate(19l);
		worker1C.setPriority(0);

		// ti=2 tf=4 prio=2
		OnCallSchedule worker2C = new OnCallSchedule();
		worker2C.setWorkerId(2l);
		worker2C.setStartDate(2l);
		worker2C.setEndDate(10l);
		worker2C.setPriority(1);
		
		// ti=3 tf=5 prio=3
		OnCallSchedule worker3C = new OnCallSchedule();
		worker3C.setWorkerId(3l);
		worker3C.setStartDate(3l);
		worker3C.setEndDate(34l);
		worker3C.setPriority(2);

		workers.add(worker3C);
		workers.add(worker2C);
		workers.add(worker1C);
	}	
	
	private static void examplesWorkers5(List<OnCallSchedule> workers) {
		// ti=1 tf=8 prio=1
		OnCallSchedule worker1C = new OnCallSchedule();
		worker1C.setWorkerId(1l);
		worker1C.setStartDate(2l);
		worker1C.setEndDate(5l);
		worker1C.setPriority(0);

		// ti=2 tf=4 prio=2
		OnCallSchedule worker2C = new OnCallSchedule();
		worker2C.setWorkerId(2l);
		worker2C.setStartDate(6l);
		worker2C.setEndDate(33l);
		worker2C.setPriority(0);
		
		// ti=3 tf=5 prio=3
		OnCallSchedule worker3C = new OnCallSchedule();
		worker3C.setWorkerId(3l);
		worker3C.setStartDate(2l);
		worker3C.setEndDate(48l);
		worker3C.setPriority(1);

		OnCallSchedule worker4C = new OnCallSchedule();
		worker4C.setWorkerId(4l);
		worker4C.setStartDate(2l);
		worker4C.setEndDate(3l);
		worker4C.setPriority(2);

		OnCallSchedule worker5C = new OnCallSchedule();
		worker5C.setWorkerId(5l);
		worker5C.setStartDate(7l);
		worker5C.setEndDate(37l);
		worker5C.setPriority(2);

		// Desordenados por prioridad...
		workers.add(worker5C);
		workers.add(worker4C);
		workers.add(worker3C);
		workers.add(worker2C);
		workers.add(worker1C);
	}		
	
	private static void examplesWorkers6(List<OnCallSchedule> workers) {
		// ti=1 tf=8 prio=1
		OnCallSchedule worker1C = new OnCallSchedule();
		worker1C.setWorkerId(1l);
		worker1C.setStartDate(15l);
		worker1C.setEndDate(49l);
		worker1C.setPriority(0);

		// ti=2 tf=4 prio=2
		OnCallSchedule worker2C = new OnCallSchedule();
		worker2C.setWorkerId(2l);
		worker2C.setStartDate(52l);
		worker2C.setEndDate(62l);
		worker2C.setPriority(0);
		
		// ti=3 tf=5 prio=3
		OnCallSchedule worker3C = new OnCallSchedule();
		worker3C.setWorkerId(3l);
		worker3C.setStartDate(38l);
		worker3C.setEndDate(43l);
		worker3C.setPriority(1);

		OnCallSchedule worker4C = new OnCallSchedule();
		worker4C.setWorkerId(4l);
		worker4C.setStartDate(45l);
		worker4C.setEndDate(49l);
		worker4C.setPriority(1);

		OnCallSchedule worker5C = new OnCallSchedule();
		worker5C.setWorkerId(5l);
		worker5C.setStartDate(53l);
		worker5C.setEndDate(70l);
		worker5C.setPriority(1);

		// Desordenados por prioridad...
		workers.add(worker5C);
		workers.add(worker4C);
		workers.add(worker3C);
		workers.add(worker2C);
		workers.add(worker1C);
	}		
	
	private static void examplesWorkers7(List<OnCallSchedule> workers) {
		// ti=1 tf=8 prio=1
		OnCallSchedule worker1C = new OnCallSchedule();
		worker1C.setWorkerId(1l);
		worker1C.setStartDate(82l);
		worker1C.setEndDate(95l);
		worker1C.setPriority(0);

		// ti=2 tf=4 prio=2
		OnCallSchedule worker2C = new OnCallSchedule();
		worker2C.setWorkerId(2l);
		worker2C.setStartDate(97l);
		worker2C.setEndDate(116l);
		worker2C.setPriority(0);

		OnCallSchedule worker3C= new OnCallSchedule();
		worker3C.setWorkerId(3l);
		worker3C.setStartDate(85l);
		worker3C.setEndDate(93l);
		worker3C.setPriority(1);

		
		// ti=3 tf=5 prio=3
		OnCallSchedule worker4C = new OnCallSchedule();
		worker4C.setWorkerId(4l);
		worker4C.setStartDate(98l);
		worker4C.setEndDate(119l);
		worker4C.setPriority(1);

		workers.add(worker4C);
		workers.add(worker3C);
		workers.add(worker2C);
		workers.add(worker1C);
	}			

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
        
//		for (int i = 0; i < 10; i++) {

			// Listado composicion de precios
			List<OnCallSchedule> workers = new ArrayList<OnCallSchedule>();
			
			try {
				workers.addAll(readItems(MYRUN));
			} catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// examplesWorkers1(workers);
			// examplesWorkersRandom(workers);
			
			// Sort and print to show the results...
//			---------------------------------------------------------------------------------------------------------------
			sortListByStartDate(workers);
			sortByPriority(workers);
			for (OnCallSchedule workerCompositionFlatVO : workers) {
				System.out.println(workerCompositionFlatVO.toString());
			}
			System.out.println();
//			---------------------------------------------------------------------------------------------------------------
				
			List<OnCallSchedule> workerListsProcessed = flatListWorkers(workers);
			
			boolean flatted = checkIfListItsFlatted(workerListsProcessed);
			System.out.println("Flatted? "+flatted);
			
			// Sort and print to show the results...
//				---------------------------------------------------------------------------------------------------------------
			sortListByStartDate(workerListsProcessed);
			for (OnCallSchedule workerCompositionFlatVO : workerListsProcessed) {
				System.out.println(workerCompositionFlatVO.toString());
			}
			System.out.println();
			
//		}
		
	}

	private static boolean checkIfListItsFlatted(List<OnCallSchedule> workerListsProcessed) {
		
		sortListByStartDate(workerListsProcessed);
		
		Iterator iterator = workerListsProcessed.iterator();
		
		OnCallSchedule last = null;
		if (iterator.hasNext()) {
			last = (OnCallSchedule) iterator.next();
		}
		
		boolean flatted = true;
		
		for (;iterator.hasNext() && flatted;) {
			OnCallSchedule actual = (OnCallSchedule) iterator.next();
			
			flatted = last.getEndDate()<=actual.getStartDate();
			last = actual;
		}
		
		return flatted;
	}

	private static List<OnCallSchedule> flatListWorkers(List<OnCallSchedule> workerLists) {
		List<OnCallSchedule> workerListsProcessed = new ArrayList<OnCallSchedule>(); 
		
		// Ordenamos la lista
		
		sortByPriority(workerLists);
		
		for (OnCallSchedule workerC : workerLists) {

			Optional<OnCallSchedule> opiFinded3 = workerListsProcessed.stream()
					.filter(pj-> ((workerC.getPriority() < pj.getPriority()) &&
								  (workerC.getStartDate() <= pj.getStartDate() && pj.getEndDate() <= workerC.getEndDate()))).findFirst();
			
			if (opiFinded3.isPresent()) {
				OnCallSchedule piFinded = opiFinded3.get();
				OnCallSchedule piFcloned = new OnCallSchedule();
				
//						(overlap type 3) El elemnento iterado de la lista original, tiene fecha de de inicio y fin, mayores que UNO de los ya procesados
				// Por estar ordenado, podemos usar el caso generico de revisar como trocear desde fecha de inicio a fin.
				piFcloned.setWorkerId(workerC.getWorkerId());
				piFcloned.setStartDate(workerC.getStartDate());
				piFcloned.setEndDate(workerC.getEndDate());
				piFcloned.setPriority(workerC.getPriority());
				// TODO Copiar el resto de campos...
				
				// Split if need before add
				workerListsProcessed.addAll(slitWorkerInEmptySpaces(piFcloned, workerListsProcessed));
				
			} else {
				Optional<OnCallSchedule> opiFinded2 = workerListsProcessed.stream()
						.filter(pj-> ((workerC.getPriority() < pj.getPriority()) &&
									  (pj.getStartDate() <= workerC.getStartDate() && workerC.getStartDate() <= pj.getEndDate()))).findFirst();
				
				if (opiFinded2.isPresent()) {
					OnCallSchedule piFinded = opiFinded2.get();
					OnCallSchedule piFcloned = new OnCallSchedule();

//					(overlap type 2) El elemento iterado de la lista original, tiene fecha de inicio entre alguna de las fechas ya puestas.
					// Por estar ordenada, debemos revisar los posibles huecos, segun sea la fecha fin del elemento de la lista original
					piFcloned.setWorkerId(workerC.getWorkerId());
					piFcloned.setStartDate(piFinded.getEndDate());
					piFcloned.setEndDate(workerC.getEndDate());
					piFcloned.setPriority(workerC.getPriority());
					// TODO Copiar el resto de campos...
					
					// Split if need before add
					workerListsProcessed.addAll(slitWorkerInEmptySpaces(piFcloned, workerListsProcessed));
					
				} else {
					Optional<OnCallSchedule> opiFinded1 = workerListsProcessed.stream()
							.filter(pi-> (
									(workerC.getPriority() < pi.getPriority()) &&
									(pi.getStartDate() <= workerC.getEndDate()  && workerC.getEndDate() <= pi.getEndDate()))).findFirst();
					
					if (opiFinded1.isPresent()) {
						OnCallSchedule piFinded = opiFinded1.get();
						OnCallSchedule piFcloned = new OnCallSchedule();

//						(overlap type 1) El elemnento iterado de la lista original, tiene fecha de fin, entre alguna de las fechas ya puestas
						// Por estar ordenada, es la primera coincidencia, con lo que la superposicion es simple...
						piFcloned.setWorkerId(workerC.getWorkerId());
						piFcloned.setStartDate(workerC.getStartDate());
						piFcloned.setEndDate(piFinded.getStartDate());
						piFcloned.setPriority(workerC.getPriority());
						// TODO Copiar el resto de campos...
						
						workerListsProcessed.add(piFcloned);
						
					} else {
						// Initial insert, or not overlaped period..
						workerListsProcessed.add(workerC);
					}
				}
			}
			
			// Mantenemos la lista resultante, ordenada por fecha...
			sortListByStartDate(workerListsProcessed);
			
		}
		return workerListsProcessed;
	}

	private static Collection<? extends OnCallSchedule> slitWorkerInEmptySpaces(OnCallSchedule iteratedWorker,
			List<OnCallSchedule> workerListsProcessed) {

		// Odenamos la lista resultado por fechas de inicio para la busqueda...
		// Se presupone, se han ido insertando sin solapamiento, 
		sortListByStartDate(workerListsProcessed);
		
		List<OnCallSchedule> result = new ArrayList<>();
		
		Iterator<OnCallSchedule> it = workerListsProcessed.iterator();
		OnCallSchedule worker = null;
		OnCallSchedule last = null;
		
		boolean startItemFinded = false;

		while(it.hasNext() && !startItemFinded) {
			worker = (OnCallSchedule) it.next();
			startItemFinded = iteratedWorker.getStartDate() <= worker.getEndDate();
		}
	
		if (startItemFinded) {
			
			OnCallSchedule piFcloned = new OnCallSchedule();
			piFcloned.setWorkerId(iteratedWorker.getWorkerId());
			piFcloned.setStartDate(iteratedWorker.getStartDate());
			piFcloned.setEndDate(worker.getStartDate());
			piFcloned.setPriority(iteratedWorker.getPriority());
			// TODO Copiar el resto de campos...
			if (piFcloned.getStartDate()<piFcloned.getEndDate()) {
				result.add(piFcloned);
			}
			
			last = worker;

			while (it.hasNext() && worker!=null && iteratedWorker.getEndDate()>worker.getEndDate())  { // StartDate
				last = worker;
				worker = it.next();
				
				piFcloned = new OnCallSchedule();
				piFcloned.setWorkerId(iteratedWorker.getWorkerId());
				piFcloned.setStartDate(last.getEndDate());
				piFcloned.setEndDate(worker.getStartDate());
				piFcloned.setPriority(iteratedWorker.getPriority());
				// TODO Copiar el resto de campos...
				if (piFcloned.getStartDate()<piFcloned.getEndDate() &&
					piFcloned.getEndDate()<worker.getStartDate()) {
					result.add(piFcloned);
				}
			}
		
			piFcloned = new OnCallSchedule();
			piFcloned.setWorkerId(iteratedWorker.getWorkerId());
			piFcloned.setStartDate(last.getEndDate());
			piFcloned.setEndDate(iteratedWorker.getEndDate());
			piFcloned.setPriority(iteratedWorker.getPriority());
			// TODO Copiar el resto de campos...
			if (piFcloned.getStartDate()<piFcloned.getEndDate() &&
				checkIfWorkerOverlap(piFcloned, workerListsProcessed)) {
				result.add(piFcloned);
			}
			
		}
		
		return result;
	}

	/**
	 * Utility method to sort by priority
	 * 
	 * @param workerLists
	 */
	private static void sortByPriority(List<OnCallSchedule> workerLists) {
		workerLists.sort(new Comparator
		<OnCallSchedule>() {
			@Override
			public int compare(OnCallSchedule o1, OnCallSchedule o2) {
				return o2.getPriority().compareTo(o1.getPriority());
			}
		});
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


}
