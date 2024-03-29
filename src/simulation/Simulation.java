package simulation;

import calculators.VelocityCalculator;
import models.Particle;
import models.Universe;
import models.Vector2D;
import utils.Const;
import utils.OutputGenerator;

import java.util.*;

public class Simulation {

    private Universe universe;
    private double time;
    private double elapsedTime;
    private VelocityCalculator vcalculator;

    Simulation(Universe universe, double totalTime, VelocityCalculator vcalculator) {
        this.universe = universe;
        this.time = totalTime;
        this.elapsedTime = 0.0;
        this.vcalculator = vcalculator;
    }

    void startUniverse(int quantity) {
        double particleRadius = 0.007;

        System.out.println("Creando " + quantity + " particulas");
        initializeParticlesWithQuantity(quantity);

        System.out.println("Creando paredes");
        initializeCornerWalls(particleRadius);
    }

    public Universe getUniverse() {
        return universe;
    }

    private void initializeParticlesWithQuantity(int quantity) {
        double mediumL = (Const.L / 2);
        Particle particle;
        double positionX;
        double positionY;
        double angle;
        double radius;

        boolean check;

        for (int i = 0; i < quantity; i++) {
            do {
                angle = getRandomDouble(-4,4);
                radius = Const.minRadius;
                positionX = mediumL +
                        getRandomDouble(Const.innerRadius + radius, Const.outerRadius - radius) * Math.sin(angle);
                positionY = mediumL +
                        getRandomDouble(Const.innerRadius + radius, Const.outerRadius - radius) * Math.cos(angle);
                check = checkSuperposition(positionX, positionY, radius);
            } while (!check);
            particle = new Particle(new Vector2D(positionX, positionY), radius, false);
            this.universe.getParticles().add(particle);
        }
    }

    private void initializeCornerWalls(double pradius) {
        double mediumL = (Const.L / 2);
        initializeCorners(pradius, Const.outerRadius, mediumL);
        initializeCorners(pradius, Const.innerRadius, mediumL);
    }

    private void initializeCorners(double pradius, double wallrad, double mediumL) {
        for (double i = -2; i < 2; i +=  0.005) {
            double positionX = wallrad * Math.sin(i);
            double positionY = wallrad * Math.cos(i);
            this.universe.getWalls().add(new Particle(new Vector2D(mediumL + positionX, mediumL + positionY)
                    , pradius, true));
            this.universe.getWalls().add(new Particle(new Vector2D(mediumL - positionX, mediumL - positionY)
                    , pradius, true));
        }
    }

    private boolean checkSuperposition(double potentialPositionX, double potentialPositionY,
                                       double potentialRadio) {

        for (Particle particle : this.universe.getParticles()) {
            if (Math.pow(potentialPositionX - particle.getPosition().getX(), 2) +
                    Math.pow(potentialPositionY - particle.getPosition().getY(), 2) <=
                    Math.pow(potentialRadio + particle.getRadius(), 2)) {
                return false;
            }
        }

        for (Particle wall : this.universe.getWalls()) {
            if (Math.pow(potentialPositionX - wall.getPosition().getX(), 2) +
                    Math.pow(potentialPositionY - wall.getPosition().getY(), 2) <=
                    Math.pow(potentialRadio + wall.getRadius(), 2)) {
                return false;
            }
        }

        return true;
    }

    private double getRandomDouble(double min, double max) {
        Random rand = new Random();
        return min + (max - min) * rand.nextDouble();
    }

    void simulate(double deltaT, double deltaT2) {
        System.out.println("Comenzando la simulacion");
        double elapsedDeltaT2 = deltaT2;
        do {
            if (elapsedTime == deltaT || elapsedTime > elapsedDeltaT2) {
                OutputGenerator.recopilateData(this);
                OutputGenerator.generateTimes(elapsedDeltaT2);
                elapsedDeltaT2 += deltaT2;
                elapsedDeltaT2 = Math.round( elapsedDeltaT2 * 100.0 ) / 100.0;
                System.out.println("Elapsed time: " + elapsedTime);
            }

            vcalculator.calculateVelocity(this.universe.getAllParticles(), this.universe.getParticles());
            updateParticlesPositions(this.universe.getParticles(), deltaT);

            elapsedTime += deltaT;
        } while (isConditionNotComplete(elapsedTime));
        OutputGenerator.generateTimes(elapsedDeltaT2);
        OutputGenerator.recopilateData(this);
    }

    private boolean isConditionNotComplete(double elapsedTime) {
        return (elapsedTime <= time);
    }

    private void updateParticlesPositions(Set<Particle> particles, double deltaT) {
        for (Particle p : particles) {
            p.setPosition(p.getPosition().add(p.getVelocity().multiplyByScalar(deltaT)));
        }
    }
}