package car.wheel;

import exceptions.WheelIsAlreadyBrokenException;

public interface WheelSet {
    void takeDamage() throws WheelIsAlreadyBrokenException;

    boolean wheelIsBroken();
}
