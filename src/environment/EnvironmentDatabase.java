package environment;

import car.Car;
import engines.InternalCombustionEngine;

import java.util.HashSet;
import java.util.Set;

public class EnvironmentDatabase {
    final Set<Car> allCityCars;
    double currentPollutionValue;
    boolean needToResetPollutionValue;

    private double tempPollutionValue;

    EnvironmentDatabase() {
        currentPollutionValue = 0;
        needToResetPollutionValue = false;
        allCityCars = new HashSet<>();
    }

    public double getCurrentPollutionValue() {
        return currentPollutionValue;
    }

    synchronized void resetPollutionValue() {
        long internalCombustionEnginesInCity = countInternalCombustionEngines();

        currentPollutionValue = internalCombustionEnginesInCity < 70 ? 0 : tempPollutionValue * 0.4;
        needToResetPollutionValue = false;

        System.out.println(String.format("PV is now %.2f", currentPollutionValue));
    }

    synchronized void informThatNeedReset() {
        System.out.println(String.format("PV[%.2f] needs reset!", currentPollutionValue));
        needToResetPollutionValue = true;
        tempPollutionValue = currentPollutionValue;
    }

    private synchronized long countInternalCombustionEngines() {
        return allCityCars.parallelStream()
                .filter(engine -> engine instanceof InternalCombustionEngine)
                .count();
    }
}
