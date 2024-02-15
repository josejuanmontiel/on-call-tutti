package com.accreativos.comparator;

import java.time.LocalDate;
import java.util.*;

// Gemmini generated code
public class AplanarRangosFechas {

    public static List<LocalDate> aplanarRangosFechas(List<RangoFecha> rangosFechas, Map<RangoFecha, Integer> prioridades) {
        // Convertir los rangos de fechas a un formato más fácil de trabajar.
        List<Tuple<LocalDate, LocalDate>> fechas = new ArrayList<>();
        for (RangoFecha rangoFecha : rangosFechas) {
            fechas.add(new Tuple<>(rangoFecha.fechaInicio, rangoFecha.fechaFin));
        }

        // Calcular la intersección de cada par de rangos de fechas.
        List<Tuple<LocalDate, LocalDate>> intersecciones = new ArrayList<>();
        for (int i = 0; i < fechas.size(); i++) {
            for (int j = i + 1; j < fechas.size(); j++) {
                Tuple<LocalDate, LocalDate> interseccion = calcularInterseccion(fechas.get(i), fechas.get(j));
                if (interseccion != null) {
                    intersecciones.add(interseccion);
                }
            }
        }

        // Añadir las fechas de cada rango de fechas a una lista.
        List<LocalDate> fechasAplanadas = new ArrayList<>();
        for (Tuple<LocalDate, LocalDate> fecha : fechas) {
            Integer prioridad = prioridades.get(new RangoFecha(fecha.getPrimera(), fecha.getSegunda()));
            if (prioridad!=null && prioridad > 0) {
                fechasAplanadas.add(fecha.getPrimera());
            }
            if (!fechasAplanadas.contains(fecha.getSegunda())) {
                fechasAplanadas.add(fecha.getSegunda());
            }
        }

        // Ordenar la lista de fechas por prioridad.
        fechasAplanadas.sort((fecha1, fecha2) -> {
            Integer prioridad1 = prioridades.get(new RangoFecha(fecha1, fecha1));
            Integer prioridad2 = prioridades.get(new RangoFecha(fecha2, fecha2));
            if (prioridad1!=null && prioridad1 > 0 && prioridad2!=null && prioridad2 > 0) {
                return Integer.compare(prioridad2, prioridad1);
            } else {
                return 0;
            }
        });

        return fechasAplanadas;
    }

    private static Tuple<LocalDate, LocalDate> calcularInterseccion(Tuple<LocalDate, LocalDate> fecha1, Tuple<LocalDate, LocalDate> fecha2) {
        if (fecha1.getSegunda().isBefore(fecha2.getPrimera())) {
            return null;
        }
        if (fecha2.getSegunda().isBefore(fecha1.getPrimera())) {
            return null;
        }
        return new Tuple<>(
                LocalDate.ofEpochDay(Math.max(fecha1.getPrimera().toEpochDay(), fecha2.getPrimera().toEpochDay())), 
                LocalDate.ofEpochDay(Math.min(fecha1.getSegunda().toEpochDay(), fecha2.getSegunda().toEpochDay())));
    }

    public static class RangoFecha {

        private final LocalDate fechaInicio;
        private final LocalDate fechaFin;

        public RangoFecha(LocalDate fechaInicio, LocalDate fechaFin) {
            this.fechaInicio = fechaInicio;
            this.fechaFin = fechaFin;
        }

        public LocalDate getFechaInicio() {
            return fechaInicio;
        }

        public LocalDate getFechaFin() {
            return fechaFin;
        }
    }

    public static class Tuple<T, U> {

        private final T primera;
        private final U segunda;

        public Tuple(T primera, U segunda) {
            this.primera = primera;
            this.segunda = segunda;
        }

        public T getPrimera() {
            return primera;
        }

        public U getSegunda() {
            return segunda;
        }
    }

    public static void main(String[] args) {
        List<RangoFecha> rangosFechas = Arrays.asList(
            new RangoFecha(LocalDate.of(2024, 2, 1), LocalDate.of(2024, 2, 10)),
            new RangoFecha(LocalDate.of(2024, 2, 5), LocalDate.of(2024, 2, 15)),
            new RangoFecha(LocalDate.of(2024, 2, 10), LocalDate.of(2024, 2, 20))
        );

        Map<RangoFecha, Integer> prioridades = new HashMap<>();
        prioridades.put(rangosFechas.get(0), 1);
        prioridades.put(rangosFechas.get(1), 2);
        prioridades.put(rangosFechas.get(2), 3);
        
        List<LocalDate> fechasAplanadas = aplanarRangosFechas(rangosFechas, prioridades);
        
        for (LocalDate fecha : fechasAplanadas) {
            System.out.println(fecha);
        }
    }
}