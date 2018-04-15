package engines;

public class DieselEngine implements InternalCombustionEngine {
    @Override
    public double getPollutionValue() {
        return 3;
    }
}
