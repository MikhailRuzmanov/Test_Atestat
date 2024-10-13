package pro.sky.java.testatestat;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Flight> flights = FlightBuilder.createFlights();
        flights.forEach(System.out::println);

        System.out.println("\n Показывает полеты,исключая полеты до текущего момента времени: ");
        flightTimeAfterNow1(flights);
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


    public static void flightTimeAfterNow1(List<Flight> flightList) {

        for (Flight flight : flightList) {
            if (checkFlight(flight)) {
                System.out.println(flight);
            }
        }

    }

    private static boolean checkFlight(Flight flight) {
        LocalDateTime timeNow1 = LocalDateTime.now();
        for (Segment segment : flight.getSegments()) {
            if (segment.getDepartureDate().isBefore(timeNow1)) {
                return false;
            }
        }
        return true;
    }


    public static void flightsAfterDepartureDate(List<Flight> flightList) {
        for (Flight flight : flightList) {
            if (isDepartureEarlierArrive(flight)) {
                System.out.println(flight);
            }
        }
//flightList.stream()
//       .filter(flight -> isDepartureEarlierArrive(flight))
//       .forEach(flight -> System.out.println(flight));
    }

    private static boolean isDepartureEarlierArrive(Flight flight) {
        for (Segment segment : flight.getSegments()) {
            if (segment.getDepartureDate().isAfter(segment.getArrivalDate())) {
                return false;
            }
        }
        return true;

    }

    public static void flightsTimeOnEarthLessTwoHours(List<Flight> flightList) {
        for (Flight flight : flightList) {
            if (checkFlight2_v2(flight)) {
                System.out.println(flight);
        }
            else {
                System.out.println("Нет, не работает" + flight);
            }
    }

}

    private static boolean checkFlight2(Flight flight) {
        double sum = 0;
        for (int i = 0; i < flight.getSegments().size() - 1; i++) {
            Long a = flight.getSegments().get(i).getArrivalDate().toInstant(ZoneOffset.UTC).getEpochSecond();
            Long b = flight.getSegments().get(i + 1).getDepartureDate().toInstant(ZoneOffset.UTC).getEpochSecond();
            sum += b - a;
            System.out.println(b-a);
        }
        System.out.println(sum/3600);
        return sum / 3600 < 2;
    }

    private static boolean checkFlight2_v2(Flight flight) {
        double sum = 0;
        for (int i = 0; i < flight.getSegments().size() - 1; i++) {
            sum += Duration.between(flight.getSegments().get(i).getArrivalDate(),
                    flight.getSegments().get(i + 1).getDepartureDate()).getSeconds();
        }
        System.out.println(sum/3600);
        return sum / 3600 < 2;
    }

    private static boolean checkFlight2_v3(Flight flight) {
        double sum = 0;
        for (int i = 0; i < flight.getSegments().size() - 1; i++) {
            sum += Duration.between(flight.getSegments().get(i).getArrivalDate(),
                    flight.getSegments().get(i + 1).getDepartureDate()).getSeconds();
        }
        System.out.println(sum/3600);
        return sum / 3600 < 2;
    }


}