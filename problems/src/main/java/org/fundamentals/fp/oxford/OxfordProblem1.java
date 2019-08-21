package org.fundamentals.fp.oxford;

import io.vavr.control.Try;
import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OxfordProblem1 {

    /**
     * Trip ID
     * Taxi ID
     * Trip Start Timestamp
     * Trip End Timestamp
     * Trip Seconds
     * Trip Miles
     * Pickup Census Tract
     * Dropoff Census Tract
     * Pickup Community Area
     * Dropoff Community Area
     * Fare	Tips
     * Tolls
     * Extras
     * Trip Total
     * Payment Type
     * Company
     * Pickup Centroid Latitude
     * Pickup Centroid Longitude
     * Pickup Centroid Location
     * Dropoff Centroid Latitude
     * Dropoff Centroid Longitude
     * Dropoff Centroid  Location
     * Community Areas
     */
    @Data
    @AllArgsConstructor
    public static class TaxiRecord {
        private String tripId;
        private String taxiId;
        private String tripStartTimestamp;
        private String tripEndTimestamp;
        private long seconds;
        private double miles;
    }

    private List<TaxiRecord> loadData() {

        return Try.of(() -> {

            Path pathToCsv = Paths.get(getClass().getClassLoader()
                    .getResource("oxford/small.csv")
                    .toURI());

            List<TaxiRecord> list = new ArrayList<>();
            String row;
            BufferedReader csvReader = new BufferedReader(new FileReader(pathToCsv.toFile()));
            long counter = 0;
            while ((row = csvReader.readLine()) != null) {
                String[] data = row.split(",");

                counter++;

                if(counter == 1) {
                    continue;
                }

                long seconds;
                try {
                    seconds = Long.parseLong(data[4]);
                } catch (NumberFormatException ex) {
                    seconds = 0L;
                }

                double miles = Double.parseDouble(data[5]);

                list.add(new TaxiRecord(
                        data[0],
                        data[1],
                        data[2],
                        data[3],
                        seconds,
                        miles));
            }
            csvReader.close();

            LOGGER.info("{}", list.size());

            return list;
        }).getOrElseThrow(ex -> {
            LOGGER.error(ex.getLocalizedMessage(), ex);
            throw new IllegalArgumentException("It was impossible to load the data");
        });
    }

    Predicate<TaxiRecord> notVoidedTrip = t -> t.getSeconds() == 0;
    Predicate<OxfordProblem1.TaxiRecord> anomalousSpeed = t -> {
        double hours = t.getSeconds() / 3600;
        double speed = t.getMiles() / hours;

        if(speed == Double.NaN) {
            return true;
        }

        if(speed == Double.NEGATIVE_INFINITY) {
            return true;
        }

        return false;
    };

    /**
     * How many records would you classify as bad? We could consider a bad
     * record to be one where the trip is not voided, but also if there is an
     * anomalous speed, distance or cost. Justify your approach. Once you have
     * defined this, ensure that all further answers are based only on good data.
     */
    public long getSolution1() {

        return loadData().stream().parallel()
                .filter(notVoidedTrip.or(anomalousSpeed))
                .count();
    }

}
