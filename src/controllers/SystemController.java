package controllers;

import bird.Bird;
import car.Car;
import car.wheel.StandardWheelSet;
import city.City;
import engines.*;
import environment.EnvironmentCenter;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SystemController {
    private final City city;
    private final EnvironmentCenter environmentCenter;
    private final Random random = new Random();

    public SystemController(City city, EnvironmentCenter environmentCenter) {
        this.city = city;
        this.environmentCenter = environmentCenter;

        initializeCars();
        initializeEnvironmentDataCenter();
        initializeBird();
    }

    private void initializeCars() {
        final int CARS_AMOUNT = 200;

        ExecutorService carsPool = Executors.newFixedThreadPool(CARS_AMOUNT);

        for (int i = 0; i < CARS_AMOUNT; i++) {
            Car car = new Car(city, environmentCenter);
            car.changeEngine(getRandomEngine(CARS_AMOUNT, i + 1));
            car.changeWheelSet(new StandardWheelSet());
            carsPool.submit(car);
        }

        carsPool.shutdown();
    }

    private Engine getRandomEngine(int carsTotal, int currentCar) {
        int randomNumber = random.nextInt(2);

        if (carsTotal - currentCar <= carsTotal * 0.1) {
            return randomNumber == 1 ? new LemonadeEngine() : new ElectricEngine();
        }

        return randomNumber == 1 ? new DieselEngine() : new PetrolEngine();
    }

    private void initializeEnvironmentDataCenter() {
        new Thread(environmentCenter).start();
    }

    private void initializeBird() {
        new Thread(new Bird(environmentCenter)).start();
    }
}
