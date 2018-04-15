package environment;

import car.Car;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Set;

public class SavingTrafficDataAsPlainTextStrategy implements SavingTrafficDataStrategy {
    @Override
    public void saveTrafficData(Set<Car> allCityCars, double currentPollutionValue) {
        try {
            saveToFile(makeString(allCityCars, currentPollutionValue));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String makeString(Set<Car> allCityCars, double currentPollutionValue) {
        StringBuilder builder = new StringBuilder();

        builder.append("Pollution value: ").append(currentPollutionValue).append("\n");

        allCityCars.forEach(car -> {
            builder.append(String.format("%s: %s, %s\n", car, car.getRoad(), car.getCrossroad()));
        });

        return builder.toString();
    }

    private void saveToFile(String content) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("traffic.txt"));
        bufferedWriter.write(content);
        bufferedWriter.close();
    }
}
