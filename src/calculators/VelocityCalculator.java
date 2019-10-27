package calculators;

import models.Particle;
import models.Universe;

import java.util.Map;
import java.util.Set;

public class VelocityCalculator {

    private NeighbourCalculator ncalculator;

    public VelocityCalculator(NeighbourCalculator ncalculator) {
        this.ncalculator = ncalculator;
    }

    public void calculateEscapeVelocity(Set<Particle> all, Set<Particle> particles) {
        Map<Particle, Set<Particle>> neighbours = ncalculator.getNeighbours(all);

        for (Particle p : particles) {

        }
    }

    public void calculateDesiredVelocity() {
    }
}
