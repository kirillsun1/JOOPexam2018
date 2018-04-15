package environment;

import car.Car;
import org.json.simple.JSONObject;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Set;

public class SavingTrafficDataAsJsonStrategy implements SavingTrafficDataStrategy {
    @Override
    public void saveTrafficData(Set<Car> allCityCars, double currentPollutionValue) {
        try {
            saveToFile(makeString(allCityCars, currentPollutionValue));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String makeString(Set<Car> allCityCars, double currentPollutionValue) {
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("pollutionValue", currentPollutionValue);

        JSONObject carsArray = new JSONObject();

        allCityCars.forEach(car -> {
            JSONObject carObject = new JSONObject();

            carObject.put("engine", car.getEngine().getClass().getSimpleName());
            carObject.put("wheels", car.getWheelSet().getClass().getSimpleName());
            carObject.put("road", car.getRoad());
            carObject.put("crossroad", car.getCrossroad());

            carsArray.put("car" + car.hashCode(), carObject);
        });

        jsonObject.put("cars", carsArray);

        return jsonObject.toJSONString();
    }

    private void saveToFile(String content) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("traffic.json"));
        bufferedWriter.write(content);
        bufferedWriter.close();
    }
}
