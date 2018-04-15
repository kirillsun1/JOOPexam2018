package city.service;

import car.Car;
import engines.ElectricEngine;
import engines.Engine;
import engines.LemonadeEngine;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;

public class CarService {
    private final List<FixedCarTicket> fixedCarTicketList = new ArrayList<>();
    private final Random random = new Random();
    private final String name;

    public CarService(String name) {
        this.name = name;
    }

    public synchronized void fixCar(Car car) {
        if (car.wantsToChangeEngine()) {
            changeEngine(car);
        }

        FixedCarTicket fixedCarTicket = new FixedCarTicket(car, LocalDateTime.now());
        fixedCarTicketList.add(fixedCarTicket);

        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(String.format("%s fixed %s", this, car));
    }

    private void changeEngine(Car car) {
        Engine oldEngine = car.getEngine();
        Engine newEngine = getRandomEnvironmentFriendlyEngine();
        car.changeEngine(newEngine);

        System.out.println(String.format("%s changed %s's engine from %s to %s", this, car, oldEngine, newEngine));
    }

    private Engine getRandomEnvironmentFriendlyEngine() {
        return random.nextInt(2) == 0 ? new LemonadeEngine() : new ElectricEngine();
    }

    public void processFixedCarsData(Consumer<List<FixedCarTicket>> action) {
        action.accept(fixedCarTicketList);
    }

    @Override
    public String toString() {
        return String.format("Service[%s]", name);
    }
}
