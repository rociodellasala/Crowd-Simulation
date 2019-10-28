package calculators;

import models.Particle;
import models.Universe;
import utils.Const;

import java.util.Map;
import java.util.Set;

public class VelocityCalculator {

    private NeighbourCalculator ncalculator;

    public VelocityCalculator(NeighbourCalculator ncalculator) {
        this.ncalculator = ncalculator;
    }

    public void calculateVelocity(Set<Particle> all, Set<Particle> particles) {
        Map<Particle, Set<Particle>> neighbours = ncalculator.getNeighbours(all);

        for (Particle p : particles) {
            if(!neighbours.get(p).isEmpty()) {
                p.setHasCrashed(true);
                calculateEscapeVelocity(p, neighbours.get(p));
            } else {
                calculateDesiredVelocity(p);
            }
        }
    }

    private void calculateEscapeVelocity(Particle particle, Set<Particle> particles) {
        double eijsum = 0;
        double eijabssum = 0;
        double directionandsense;

        for(Particle p : particles) {
            // Formula numero (7) del paper
            directionandsense = (particle.getPosition().subtract(p.getPosition())) /
                    Math.abs(particle.getPosition().subtract(p.getPosition());
            eijsum += directionandsense;
            eijabssum += Math.abs(directionandsense);
        }

        // Formula numero (6) y (9) del paper
        particle.setVelocity(Const.vdmax * (eijsum/eijabssum)); //TODO: Que onda dejamos Vector2D o double para speed?
    }

    private void calculateDesiredVelocity(Particle p) {
        double desiredWalkingSpeed;

        // Formula numero (1) del paper
        desiredWalkingSpeed
                = Const.vdmax * (Math.pow((p.getRadius() - Const.minRadius)/(Const.maxRadius - Const.minRadius),
                            Const.beta));

        // Formula numero (5) del paper //TODO: HAY QUE CALCULAR Etarget!
        p.setVelocity(desiredWalkingSpeed); //TODO: Que onda dejamos Vector2D o double para speed?
    }
}
