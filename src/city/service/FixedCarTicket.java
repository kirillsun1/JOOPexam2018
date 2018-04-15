package city.service;

import car.Car;

import java.time.LocalDateTime;

public class FixedCarTicket {
    private final Car fixedCar;
    private final LocalDateTime fixedTime;

    FixedCarTicket(Car fixedCar, LocalDateTime fixedTime) {
        this.fixedCar = fixedCar;
        this.fixedTime = fixedTime;
    }

    public Car getFixedCar() {
        return fixedCar;
    }

    public LocalDateTime getFixedTime() {
        return fixedTime;
    }
}
