package environment;

import car.Car;

import java.util.Set;

public interface SavingTrafficDataStrategy {
    void saveTrafficData(Set<Car> allCityCars, double currentPollutionValue);
}
