package com.kumuluz.ee.samples.jpa;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

@ApplicationScoped
public class Configuration {
 
    @Inject
    private TimeRangeService service;

    public void init(@Observes @Initialized(RequestScoped.class) Object init) {
     	// Read environment variable
		String INPUT = System.getenv("INPUT");
        if (INPUT == null) {
            System.out.println("INPUT variable not set");
            System.exit(1);
        }
        System.out.println("INPUT: "+INPUT);
        
        // Listado composicion de precios
        List<TimeRange> workers = new ArrayList<TimeRange>();
        try {
            workers.addAll(readItems(INPUT));
        } catch (URISyntaxException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        for (TimeRange worker : workers) {
            service.saveTimeRange(worker);
        }
    }    

    private static List<TimeRange> readItems(String fileName) throws URISyntaxException {
        List<TimeRange> items = new ArrayList<>();
        InputStream is = Configuration.class.getResourceAsStream(fileName);
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

                TimeRange item = new TimeRange();
				// Format datetime from YYYY-MM-DDTHH:MM:SSZ
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd'T'HH.mm.ss'Z'");
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
				item.setPerson(Long.parseLong(attributes[2]));
                item.setLevel(Integer.parseInt(attributes[3]));

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
            
}
