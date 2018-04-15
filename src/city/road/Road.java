package city.road;

import city.Crossroad;

public class Road {
    final String name;
    private final Crossroad crossroad1;
    private final Crossroad crossroad2;

    public Road(String name, Crossroad crossroad1, Crossroad crossroad2) {
        this.name = name;

        this.crossroad1 = crossroad1;
        this.crossroad2 = crossroad2;
    }

    public Crossroad getCrossroad1() {
        return crossroad1;
    }

    public Crossroad getCrossroad2() {
        return crossroad2;
    }

    public Crossroad getSecondCrossroad(Crossroad firstCrossroad) {
        return firstCrossroad == crossroad1 ? crossroad2 : crossroad1;
    }

    @Override
    public String toString() {
        return String.format("Road[%s]", name);
    }
}
