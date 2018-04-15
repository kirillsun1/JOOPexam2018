package car;

import car.wheel.MarmaladeWheelSet;
import car.wheel.StandardWheelSet;
import city.City;
import engines.ElectricEngine;
import engines.Engine;
import engines.LemonadeEngine;
import environment.EnvironmentCenter;

import java.util.List;

public class HelpingCar extends Car {
    private boolean hasHelped = false;

    public HelpingCar(City city, EnvironmentCenter environmentCenter) {
        super(city, environmentCenter);
    }

    @Override
    public void run() {
        while (!hasHelped) {
            try {
                Thread.sleep(getTimeToSleep());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            arriveToAnotherCrossroad();
            fixAllCarsOnCurrentCrossroad();
            moveToAnotherRoad();

            roadsChanged++;
        }
        System.out.println(this + " finished!");
    }

    @Override
    public String toString() {
        return String.format("HelpingCar[%d]", this.hashCode());
    }

    private void fixAllCarsOnCurrentCrossroad() {
        List<Car> carsToHelp = currentCrossroad.getCarsWithBrokenWheels();

        if (carsToHelp.size() > 0) {
            carsToHelp.forEach(this::changeWheelSetForCar);
            environmentCenter.getEnvironmentHelpingCarController().informThatHelpingCarHasFixedSomeCar();
            hasHelped = true;
        }
    }

    private void changeWheelSetForCar(Car car) {
        Engine carEngine = car.getEngine();

        boolean giveEliteWheelSet = carEngine instanceof ElectricEngine || carEngine instanceof LemonadeEngine;

        car.changeWheelSet(giveEliteWheelSet ? new MarmaladeWheelSet() : new StandardWheelSet());
        System.out.println(String.format("%s helped %s by changing its wheels!", this, car));
    }
}
