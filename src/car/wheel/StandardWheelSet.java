package car.wheel;

import exceptions.WheelIsAlreadyBrokenException;

public class StandardWheelSet implements WheelSet {
    private static final int MAX_STRENGTH = 3;

    private int currentStrength;

    public StandardWheelSet() {
        currentStrength = MAX_STRENGTH;
    }

    @Override
    public void takeDamage() throws WheelIsAlreadyBrokenException {
        if (wheelIsBroken()) {
            throw new WheelIsAlreadyBrokenException();
        }

        currentStrength--;
    }

    @Override
    public boolean wheelIsBroken() {
        return currentStrength == 0;
    }
}
