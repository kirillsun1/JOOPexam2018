package car;

import car.wheel.WheelSet;
import city.City;
import city.Crossroad;
import city.StartingCrossroad;
import city.road.BadRoad;
import city.road.Road;
import city.service.CarService;
import engines.Engine;
import engines.InternalCombustionEngine;
import environment.EnvironmentCenter;
import exceptions.WheelIsAlreadyBrokenException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class Car implements Runnable {
    final EnvironmentCenter environmentCenter;
    private final City city;
    private final Random random = new Random();
    Crossroad currentCrossroad;
    int roadsChanged;
    private Engine engine;
    private Road currentRoad;
    private WheelSet wheelSet;
    private int waitingTimes;

    public Car(City city, EnvironmentCenter environmentCenter) {
        this.city = city;
        this.environmentCenter = environmentCenter;

        chooseRandomStartingCrossroad();
        chooseRandomRoadAtStartingCrossroad();

        roadsChanged = 0;
        waitingTimes = 0;
    }

    public synchronized Engine getEngine() {
        return engine;
    }

    public synchronized void changeEngine(Engine engine) {
        waitingTimes = 0;
        this.engine = engine;
    }

    public synchronized WheelSet getWheelSet() {
        return wheelSet;
    }

    public synchronized void changeWheelSet(WheelSet wheelSet) {
        this.wheelSet = wheelSet;
    }

    public void incrementWaitingTimes() {
        waitingTimes++;
        System.out.println(String.format("%s set to wait", this));
    }

    public boolean wantsToChangeEngine() {
        return engine instanceof InternalCombustionEngine
                && waitingTimes >= 2 && random.nextInt(7) == 1;
    }

    public Road getRoad() {
        return currentRoad;
    }

    public Crossroad getCrossroad() {
        return currentCrossroad;
    }

    @Override
    public String toString() {
        return String.format("Car[%d,%s,%s]", this.hashCode(),
                engine.getClass().getSimpleName(), wheelSet.getClass().getSimpleName());
    }

    @Override
    public void run() {
        while (!Thread.interrupted()) {
            if (wheelSet.wheelIsBroken()) {
                System.out.println(String.format("%s's wheels are broken. Asking for help!", this));
                environmentCenter.getEnvironmentHelpingCarController().askForHelp();
                continue;
            }

            if (needToSendDataToEnvironmentDatabase()) {
                environmentCenter.registerDrivingCarEngine(this);
            }

            if (needToAskPermission()) {
                environmentCenter.waitForPermission(this);
            }

            try {
                Thread.sleep(getTimeToSleep());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            arriveToAnotherCrossroad();
            goToServiceIfNecessary();
            moveToAnotherRoad();
            roadsChanged++;
        }
    }

    int getTimeToSleep() {
        return 3 + random.nextInt(18);
    }

    void moveToAnotherRoad() {
        currentRoad = chooseNewRoadAtNewCrossroad();
        if (currentRoad instanceof BadRoad) {
            try {
                wheelSet.takeDamage();
            } catch (WheelIsAlreadyBrokenException e) {
                e.printStackTrace();
            }
        }
        System.out.println(String.format("%s changed road to %s", this, currentCrossroad));
    }

    void arriveToAnotherCrossroad() {
        currentCrossroad.removeCar(this);
        currentCrossroad = currentRoad.getSecondCrossroad(currentCrossroad);
        currentCrossroad.addCar(this);
    }

    private void chooseRandomStartingCrossroad() {
        List<Crossroad> startingCrossroadList = city.getAllStartingCrossroads();
        StartingCrossroad startingCrossroad =
                (StartingCrossroad) startingCrossroadList.get(random.nextInt(startingCrossroadList.size()));
        this.currentCrossroad = startingCrossroad;
        startingCrossroad.addCar(this);
    }

    private void chooseRandomRoadAtStartingCrossroad() {
        List<Road> roads = new ArrayList<>(city.getRoadsOfCrossroad(currentCrossroad));
        currentRoad = roads.get(random.nextInt(roads.size()));
    }

    private boolean needToSendDataToEnvironmentDatabase() {
        return roadsChanged > 0 && roadsChanged % 5 == 0;
    }

    private boolean needToAskPermission() {
        return roadsChanged > 0 && roadsChanged % 7 == 0;
    }

    private void goToServiceIfNecessary() {
        Optional<CarService> carServiceOptional = currentCrossroad.getCarService();
        carServiceOptional.ifPresent(carService -> carService.fixCar(this));
    }

    private Road chooseNewRoadAtNewCrossroad() {
        List<Road> roads = new ArrayList<>(city.getRoadsOfCrossroad(currentCrossroad));
        roads.remove(currentRoad);
        return roads.get(random.nextInt(roads.size()));
    }
}
