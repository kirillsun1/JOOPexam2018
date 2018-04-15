package engines;

public class PetrolEngine implements InternalCombustionEngine {
    @Override
    public double getPollutionValue() {
        return 2;
    }
}
