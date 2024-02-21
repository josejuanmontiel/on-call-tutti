package com.accreativos.comparator;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class RangoFechas {

    class Rango {

        private LocalDateTime startDate;
        private LocalDateTime endDate;
        private int person;
        private int level;
    
        public Rango(LocalDateTime startDate, LocalDateTime endDate, int person, int level) {
            this.startDate = startDate;
            this.endDate = endDate;
            this.person = person;
            this.level = level;
        }

        public Integer getLevel() {
            return level;
        }

        public LocalDateTime getStartDate() {
            return startDate;
        }

        public Integer getPerson() {
            return person;
        }

        public LocalDateTime getEndDate() {
            return endDate;
        }
    
        // Getters and setters...
    }

    public static void main(String[] args) {
        testDividirRangos();

        String[] entrada = {
            "2024-06-21T00.00.00Z,2025-01-01T00.00.00Z,1,0",
            "2024-06-21T15.00.00Z,2024-06-21T18.30.00Z,2,1",
            "2024-06-22T00.00.00Z,2024-06-22T11.00.00Z,3,1",
            "2024-06-22T16.00.00Z,2025-01-01T00.00.00Z,4,1"
        };

        String[] salida = dividirRangos(entrada);

        for (String linea : salida) {
            System.out.println(linea);
        }
    }

    private static String[] dividirRangos(String[] entrada) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH.mm.ss'Z'");

        List<Rango> rangos = new ArrayList<>();
        for (int i = 0; i < entrada.length; i++) {
            String[] partes = entrada[i].split(",");

            LocalDateTime startDate = LocalDateTime.parse(partes[0], formatter);
            LocalDateTime endDate = LocalDateTime.parse(partes[1], formatter);
            int person = Integer.parseInt(partes[2]);
            int level = Integer.parseInt(partes[3]);

            // Si la fecha de inicio es igual a la fecha de fin, solo se crea un rango
            RangoFechas rf = new RangoFechas();
            if (startDate.isEqual(endDate)) {
                rangos.add(rf.new Rango(startDate, endDate, person, level));
            } else {
                // Se crea un rango para la fecha de inicio
                rangos.add(rf.new Rango(startDate, startDate.plusSeconds(-1), person, level));

                // Se crea un rango para cada día entre la fecha de inicio y la fecha de fin
                while (startDate.isBefore(endDate)) {
                    startDate = startDate.plusDays(1);
                    rangos.add(rf.new Rango(startDate, startDate.plusSeconds(-1), person, level));
                }

                // Se crea un rango para la fecha de fin
                rangos.add(rf.new Rango(endDate, endDate, person, level));
            }
        }

        // // Filtrar rangos por nivel
        // List<Rango> rangosFiltrados = new ArrayList<>();
        // for (Rango rango : rangos) {
        //     if (rango.getLevel() >= 1) {
        //         rangosFiltrados.add(rango);
        //     }
        // }

        // // Filtrar rangos por nivel y por superposición
        // List<Rango> rangosFiltrados = new ArrayList<>();
        // for (Rango rango : rangos) {
        //     if (rango.getLevel() >= 1) {
        //         boolean superpuesto = false;
        //         for (Rango rangoFiltrado : rangosFiltrados) {
        //             if (rango.getStartDate().isBefore(rangoFiltrado.getEndDate()) && rango.getEndDate().isAfter(rangoFiltrado.getStartDate())) {
        //                 superpuesto = true;
        //                 break;
        //             }
        //         }
        //         if (!superpuesto) {
        //             rangosFiltrados.add(rango);
        //         }
        //     }
        // }

        // Filtrar rangos por nivel y por superposición
        List<Rango> rangosFiltrados = new ArrayList<>();
        for (Rango rango : rangos) {
            if (rango.getLevel() >= 1) {
                boolean superpuesto = false;
                for (Rango rangoFiltrado : rangosFiltrados) {
                    if (rango.getStartDate().isBefore(rangoFiltrado.getEndDate()) && rango.getEndDate().isAfter(rangoFiltrado.getStartDate())) {
                        superpuesto = true;
                        break;
                    } else if (rango.getStartDate().isEqual(rangoFiltrado.getStartDate()) || rango.getEndDate().isEqual(rangoFiltrado.getEndDate())) {
                        superpuesto = true;
                        break;
                    }
                }
                if (!superpuesto) {
                    rangosFiltrados.add(rango);
                }
            }
        }        

        // for (Rango rango1 : rangos) {
        //     for (Rango rango2 : rangos) {
        //         if (rango1 != rango2) {
        //             if (rango1.getStartDate().isAfter(rango2.getEndDate()) && rango1.getEndDate().isBefore(rango2.getStartDate())) {
        //                 // Eliminar rango1 o rango2
        //             }
        //         }
        //     }
        // }

        // Convertir rangos a strings
        String[] salida = new String[rangosFiltrados.size()];
        for (int i = 0; i < rangosFiltrados.size(); i++) {
            Rango rango = rangosFiltrados.get(i);
            salida[i] = String.format("%s,%s,%d", rango.getStartDate(), rango.getStartDate(), rango.getPerson());
        }

        return salida;
    }

    public static void testDividirRangos() {
        String[] entrada = {
            "2024-06-21T00.00.00Z,2025-01-01T00.00.00Z,1,0",
            "2024-06-21T15.00.00Z,2024-06-21T18.30.00Z,2,1",
            "2024-06-22T00.00.00Z,2024-06-22T11.00.00Z,3,1",
            "2024-06-22T16.00.00Z,2025-01-01T00.00.00Z,4,1"
        };

        String[] salida = dividirRangos(entrada);

        // Assert para la primera línea
        System.out.println("2024-06-21T00:00:00Z,2024-06-21T14:59:59Z,1".equals(salida[0]));

        // Assert para la segunda línea
        System.out.println("2024-06-21T15:00:00Z,2024-06-21T18:29:59Z,2".equals(salida[1]));

        // Assert para la tercera línea
        System.out.println("2024-06-21T18:30:00Z,2024-06-21T23:59:59Z,1".equals(salida[2]));

        // Assert para la cuarta línea
        System.out.println("2024-06-22T00:00:00Z,2024-06-22T10:59:59Z,3".equals(salida[3]));

        // Assert para la quinta línea
        System.out.println("2024-06-22T11:00:00Z,2024-06-22T15:59:59Z,1".equals(salida[4]));

        // Assert para la sexta línea
        System.out.println("2024-06-22T16:00:00Z,2024-12-31T23:59:59Z,4".equals(salida[5]));
    }

}
