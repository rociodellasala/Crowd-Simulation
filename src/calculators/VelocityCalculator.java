package calculators;

import models.Particle;
import utils.Const;

import java.util.Map;
import java.util.Set;

public class VelocityCalculator {

    private NeighbourCalculator ncalculator;
    private double deltaR;

    public VelocityCalculator(NeighbourCalculator ncalculator, double deltaT) {
        // Formula numero (8) del paper
        this.deltaR = Const.maxRadius / (Const.tau/deltaT);
        this.ncalculator = ncalculator;
    }

    public void calculateVelocity(Set<Particle> all, Set<Particle> particles) {
        Map<Particle, Set<Particle>> neighbours = ncalculator.getNeighbours(all);
        Set<Particle> myneighbours;

        for (Particle p : particles) {
            // TODO: Chequear la condicion del if!
                // no estoy segura si la calcu de vecinos devuelve vecinos o vecinos + la propia particula
                // dejo las dos condiciones, dsp hay q sacar y ver bien cual es la condicion para ver si la particula
                // esta "free of contact"
            myneighbours = neighbours.get(p);
            if (myneighbours.isEmpty() || (myneighbours.size() == 1 && myneighbours.contains(p))) {
                if (p.getRadius() < Const.maxRadius) {
                    p.setRadius(p.getRadius() + deltaR);
                }
                calculateDesiredVelocity(p);
            } else {
                p.setRadius(Const.minRadius);
                calculateEscapeVelocity(p, myneighbours);
            }
        }
    }


    private void calculateEscapeVelocity(Particle particle, Set<Particle> neighbours) {
        double eijsum = 0;
        double eijabssum = 0;
        double directionandsense;

        for(Particle neighbour : neighbours) {
            // Formula numero (7) del paper
            directionandsense = (particle.getPosition().subtract(neighbour.getPosition())) /
                    Math.abs(particle.getPosition().subtract(neighbour.getPosition()));
            eijsum += directionandsense;
            eijabssum += Math.abs(directionandsense);
        }

        // Formula numero (6) y (9) del paper
        particle.setVelocity(Const.vdmax * (eijsum/eijabssum));
        // TODO: Que onda dejamos Vector2D y descomponemos de alguna forma o usamos double para speed y position?
    }

    private void calculateDesiredVelocity(Particle p) {
        double desiredWalkingSpeed;


        // Formula numero (1) del paper
        desiredWalkingSpeed
                = Const.vdmax * (Math.pow((p.getRadius() - Const.minRadius)/(Const.maxRadius - Const.minRadius),
                            Const.beta));

        // Formula numero (5) del paper //TODO: HAY QUE CALCULAR etarget!
        p.setVelocity(desiredWalkingSpeed); //TODO: Que onda dejamos Vector2D o double para speed?
    }
}
