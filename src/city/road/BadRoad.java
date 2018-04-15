package city.road;

import city.Crossroad;

public class BadRoad extends Road {
    public BadRoad(String name, Crossroad crossroad1, Crossroad crossroad2) {
        super(name, crossroad1, crossroad2);
    }

    @Override
    public String toString() {
        return String.format("BadRoad[%s]", name);
    }
}
