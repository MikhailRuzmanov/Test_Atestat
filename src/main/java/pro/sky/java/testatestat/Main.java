package pro.sky.java.testatestat;

import java.time.LocalDateTime;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Flight> flights = FlightBuilder.createFlights();

        System.out.println("\n Показывает полеты,исключая полеты до текущего момента времени: ");
        flightTimeAfterNow(flights);
        System.out.println("\n Показывает  полеты без сегментов с датой прилёта раньше даты вылета : ");
        flightsAfterDepartureDate(flights);
        System.out.println("\n Показывает полеты, где общее время, проведённое на земле, не превышает два часа : ");
        flightsTimeOnEarthLessTwoHours(flights);
    }



    public static void flightTimeAfterNow(List<Flight> flightList) {
        for (Flight flight : flightList) {
            for (Segment segment : flight.getSegments()) {
                if (isTime(segment)) {
                    System.out.println(flight);
                    break;

                }
            }
        }

    }

    private static boolean isTime(LocalDateTime localDateTime) {
        LocalDateTime timeNow = LocalDateTime.now();
        return localDateTime.isAfter(timeNow);

    }

    private static boolean isTime(Segment segment) {
        return isTime(segment.getDepartureDate());

    }


    public static void flightsAfterDepartureDate(List<Flight> flightList) {
        for (Flight flight : flightList) {
            for (Segment segment : flight.getSegments()) {
                if (isDepartureEarlierArrive(segment)) {
                    System.out.println(flight);
                    break;

                }

            }
        }

    }

    private static boolean isDepartureEarlierArrive(Segment segment) {
        return (segment.getDepartureDate().getHour() < segment.getArrivalDate().getHour()
                || segment.getDepartureDate().getDayOfMonth() < segment.getArrivalDate().getDayOfMonth()
                || segment.getDepartureDate().getDayOfYear() < segment.getArrivalDate().getDayOfYear());

    }

    public static void flightsTimeOnEarthLessTwoHours(List<Flight> flightList) {
        for (Flight flight : flightList) {
            int count = 0;
            for (Segment segment : flight.getSegments()) {
                if (segment.getDepartureDate().getHour()-count <= 2) {
                    count = segment.getArrivalDate().getHour();
                    System.out.println(flight);
                    break;

                }

            }
        }

    }

}