package Utilities;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;


public class TimeHelper {

    private static ObservableList<LocalTime> startTimes = FXCollections.observableArrayList();
    private static ObservableList<LocalTime> endTimes = FXCollections.observableArrayList();

    private final static ZonedDateTime STARTEST = ZonedDateTime.of(LocalDate.now(), LocalTime.of(8, 0), ZoneId.of("America/New_York"));

    private static void generateTimeLists() {
        ZonedDateTime startLocal = STARTEST.withZoneSameInstant(ZoneId.systemDefault());
        ZonedDateTime endLocal = startLocal.plusHours(14);

        while (startLocal.isBefore(endLocal)) {
            startTimes.add(startLocal.toLocalTime());
            startLocal = startLocal.plusMinutes(15);
            endTimes.add(startLocal.toLocalTime());
        }
    }

    public static ObservableList<LocalTime> getStartTimes() {
        if (startTimes.isEmpty()) {
            generateTimeLists();

        }
        return startTimes;
    }

    public static ObservableList<LocalTime> getEndTimes() {
        if (endTimes.isEmpty()) {
            generateTimeLists();

        }
        return endTimes;
    }
}
