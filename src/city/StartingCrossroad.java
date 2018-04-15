package city;

import car.Car;
import environment.EnvironmentCenter;

public class StartingCrossroad extends Crossroad {
    private final EnvironmentCenter environmentCenter;

    public StartingCrossroad(String name, EnvironmentCenter environmentCenter) {
        super(name);
        this.environmentCenter = environmentCenter;
    }

    @Override
    public synchronized void addCar(Car car) {
        environmentCenter.registerNewCarInCity(car);
        super.addCar(car);
    }
}
