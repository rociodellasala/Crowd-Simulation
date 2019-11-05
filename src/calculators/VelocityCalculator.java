package calculators;

import models.Particle;
import models.Vector2D;
import utils.Const;

import java.awt.geom.Point2D;
import java.util.HashSet;
import java.util.Set;

public class VelocityCalculator {

    private double deltaR;
    private double mediumL;

    public VelocityCalculator(double deltaT, double mediumL) {
        // Formula numero (8) del paper
        this.deltaR = Const.maxRadius / (Const.tau/deltaT);
        this.mediumL = mediumL;
    }

    public void calculateVelocity(Set<Particle> all, Set<Particle> particles) {
        Set<Particle> close;

        for (Particle p : particles) {
            close = new HashSet<>();
            for (Particle neighbour : all) {
                if (p.getDistance(neighbour) - p.getRadius() - neighbour.getRadius() < Const.interactionRadio) {
                    if(!p.equals(neighbour)) {
                        close.add(neighbour);
                    }
                }
                
            }
            if (close.size() > 0) {
                p.setRadius(Const.minRadius);
                calculateEscapeVelocity(p, close);
            } else {
                if (p.getRadius() < Const.maxRadius) {
                    p.setRadius(p.getRadius() + deltaR);
                }
                
                if(p.getRadius() >= Const.maxRadius) {
                	p.setRadius(Const.maxRadius);
                }
                calculateDesiredVelocity(p);
            }
        }
    }

    private void calculateEscapeVelocity(Particle particle, Set<Particle> neighbours) {
        double eijsumx = 0;
        double eijsumy = 0;
        double eijabssum = 0;
        double directionandsensex;
        double directionandsensey;
        double distancex;
        double distancey;
        double distance;
        double tangencialx;
        double tangencialy;
        double tangencial;

        for(Particle neighbour : neighbours) {
            // Formula numero (7) del paper
            distancex = particle.getPosition().getX() - neighbour.getPosition().getX();
            distancey = particle.getPosition().getY() - neighbour.getPosition().getY();
            distance = Math.sqrt( Math.pow(distancex,2) + Math.pow(distancey,2));
            directionandsensex = distancex /  distance;
            directionandsensey = distancey /  distance;
            eijsumx += directionandsensex;
            eijsumy += directionandsensey;
            eijabssum += Math.sqrt(Math.pow(directionandsensex,2) + Math.pow(directionandsensey,2));
        }
        // Formula numero (6) y (9) del paper
        
        particle.setVelocity(new Vector2D(Const.vdmax * (eijsumx/eijabssum), Const.vdmax * (eijsumy/eijabssum)));

        distancex = particle.getPosition().getX() - mediumL;
        distancey = particle.getPosition().getY() - mediumL;
        distance = Math.sqrt( Math.pow(distancex,2) + Math.pow(distancey,2));
        tangencialx = - (distancey / distance);
        tangencialy = distancex / distance;

        particle.setTangencial(new Vector2D(particle.getVelocity().getX() * tangencialx, particle.getVelocity().getY() * tangencialy));
       

    }

    private void calculateDesiredVelocity(Particle p) {
        double desiredWalkingSpeed;
        double distancex;
        double distancey;
        double distance;
        double tangencialx;
        double tangencialy;

        distancex = p.getPosition().getX() - mediumL;
        distancey = p.getPosition().getY() - mediumL;
        distance = Math.sqrt( Math.pow(distancex,2) + Math.pow(distancey,2));
        tangencialx = - (distancey / distance);
        tangencialy = distancex / distance;
        // Formula numero (1) del paper
        desiredWalkingSpeed
                = Const.vdmax * (Math.pow((p.getRadius() - Const.minRadius)/(Const.maxRadius - Const.minRadius),
                            Const.beta));

        // Formula numero (5) del paper
        p.setVelocity(new Vector2D(tangencialx * desiredWalkingSpeed, tangencialy * desiredWalkingSpeed ));
        p.setTangencial(new Vector2D(desiredWalkingSpeed, 0));
    }
}
