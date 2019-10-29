package simulation;

import calculators.VelocityCalculator;
import models.Universe;
import utils.Configuration;
import utils.Const;
import utils.OutputGenerator;

public class Main {

    public static void main(String[] args) {
        Configuration config;
        Simulation simulation;
        Universe universe;

        config = new Configuration();
        config.loadConfig();
        checkParameters(config);
        System.out.println("Todos los parametros de configuracion son correctos");

        new Const(config.getL());

        System.out.println("Creando el universo");
        universe = new Universe();

        double mediumL = (Const.L / 2);
        VelocityCalculator velocityCalculator = new VelocityCalculator(config.getDeltaT(), mediumL);

        simulation = new Simulation(universe, config.getTotalTime(), velocityCalculator);

        OutputGenerator.initializeOvito();

        System.out.println("Iniciando la simulación del medio granular");
        simulation.startUniverse(config.getQuantity());
        simulation.simulate(config.getDeltaT(), config.getDeltaT2());

        OutputGenerator.closeFiles();
    }

    private static void checkParameters(Configuration config) {
        if (config.getQuantity() < 1 || config.getQuantity() > 600) {
            throw new IllegalArgumentException("La cantidad de particulas debe ser como minimo 1");
        }

        if (config.getL() < 0) {
            throw new IllegalArgumentException("El alto L del recinto debe pertenecer al intervalo [1.0,1.5] m");
        }

    }

}