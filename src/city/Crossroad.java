package city;

import car.Car;
import city.service.CarService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Crossroad {
    private final String name;
    private final List<Car> carsOnCrossroad;
    private CarService carService;

    public Crossroad(String name) {
        this.name = name;
        carsOnCrossroad = new ArrayList<>();
    }

    public Optional<CarService> getCarService() {
        return Optional.ofNullable(carService);
    }

    public void setCarService(CarService carService) {
        this.carService = carService;
    }

    public synchronized void removeCar(Car car) {
        carsOnCrossroad.remove(car);
    }

    public synchronized void addCar(Car car) {
        carsOnCrossroad.add(car);
    }

    public synchronized List<Car> getCarsWithBrokenWheels() {
        return carsOnCrossroad.parallelStream()
                .filter(car -> car.getWheelSet().wheelIsBroken())
                .collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return String.format("Crossroad[%s]", name);
    }
}
