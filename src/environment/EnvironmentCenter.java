package environment;

import car.Car;
import car.HelpingCar;
import car.wheel.MarmaladeWheelSet;
import city.City;
import engines.DieselEngine;
import engines.ElectricEngine;
import engines.PetrolEngine;

import java.util.concurrent.CompletableFuture;

public class EnvironmentCenter implements Runnable {
    private final City city;
    private final EnvironmentDatabase environmentDatabase;
    private final EnvironmentHelpingCarController environmentHelpingCarController;

    private boolean isCleaning = false;

    public EnvironmentCenter(City city) {
        this.city = city;
        this.environmentDatabase = new EnvironmentDatabase();
        this.environmentHelpingCarController = new EnvironmentHelpingCarController();
    }

    public synchronized void registerNewCarInCity(Car car) {
        environmentDatabase.allCityCars.add(car);
    }

    public synchronized void registerDrivingCarEngine(Car car) {
        environmentDatabase.currentPollutionValue += car.getEngine().getPollutionValue();
    }

    public EnvironmentHelpingCarController getEnvironmentHelpingCarController() {
        return environmentHelpingCarController;
    }

    public EnvironmentDatabase getEnvironmentDatabase() {
        return environmentDatabase;
    }

    public synchronized void waitForPermission(Car car) {
        if (carShouldWait(car)) {
            environmentDatabase.informThatNeedReset();
            car.incrementWaitingTimes();
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized void saveTrafficData(SavingTrafficDataStrategy savingTrafficDataStrategy) {
        savingTrafficDataStrategy.saveTrafficData(environmentDatabase.allCityCars,
                environmentDatabase.currentPollutionValue);
    }

    @Override
    public void run() {
        while (!Thread.interrupted()) {
            if (environmentDatabase.needToResetPollutionValue && !isCleaning) {
                cleanEnvironment();
            }

            if (environmentHelpingCarController.needToSendHelpingCar()) {
                activateHelpingCar();
                environmentHelpingCarController.informThatHelpingCarHasBeenSent();
            }
        }
    }

    private void activateHelpingCar() {
        Car helpingCar = new HelpingCar(city, this);
        helpingCar.changeEngine(new ElectricEngine());
        helpingCar.changeWheelSet(new MarmaladeWheelSet());
        new Thread(helpingCar).start();
        System.out.println(helpingCar + " was sent!");
    }

    private boolean carShouldWait(Car car) {
        return (environmentDatabase.currentPollutionValue >= 400 && car.getEngine() instanceof DieselEngine)
                || (environmentDatabase.currentPollutionValue >= 500 && car.getEngine() instanceof PetrolEngine);
    }

    private synchronized void cleanEnvironment() {
        isCleaning = true;
        CompletableFuture
                .runAsync(() -> {
                    final int TIME_TO_CLEAN_ENVIRONMENT = 2000;

                    System.out.println("\n\n\nResetting pollution value\n\n\n");
                    try {
                        Thread.sleep(TIME_TO_CLEAN_ENVIRONMENT);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    environmentDatabase.resetPollutionValue();
                    isCleaning = false;
                    awakeWaitingCars();
                });
    }

    private synchronized void awakeWaitingCars() {
        notifyAll();
    }
}
