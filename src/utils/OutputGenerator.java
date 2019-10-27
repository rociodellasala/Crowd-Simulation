package utils;

import models.Particle;
import simulation.Simulation;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class OutputGenerator {
    private static FileWriter ovitoWriter;

    public static void initializeOvito() {
        File data = createFile("./ovito.txt");
        ovitoWriter = openWriter(data);
    }

    public static void closeFiles() {
        closeWriter(ovitoWriter);
    }

    public static void recopilateData(Simulation simulation) {
        List<double[]> data = new ArrayList<>();
        Set<Particle> walls = new HashSet<>();
        recopilateParticlesData(simulation.getUniverse().getParticles(), data);
        for (Particle p : simulation.getUniverse().getWalls())
            walls.add(p);
        recopilateParticlesData(walls, data);
        generateOvitoOutput(data);
    }


    public static void recopilateParticlesData(Set<Particle> particles, List<double[]> data) {
        double id;
        double x, y;
        double vx, vy;
        double ra;
        double pressure;

        for (Particle p : particles) {
            id = p.getID();
            x = p.getPosition().getX();
            y = p.getPosition().getY();
            vx = p.getSpeed().getX();
            vy = p.getSpeed().getY();
            ra = p.getRadius();
            pressure = p.getNormalForce()/(2 * Math.PI * ra); 

            double[] currentParticle = {id, x, y, vx, vy, ra, pressure};
            data.add(currentParticle);
        }
    }

    private static File createFile(String name) {
        File file = new File(name);
        try {
            file.createNewFile();
            System.out.println("El archivo de output " + name + " ha sido creado");
        } catch (IOException e) {
            System.out.println("El archivo de output " + name + " no se ha podido crear");
        }
        return file;
    }

    private static FileWriter openWriter(File file) {
        FileWriter writer = null;

        try {
            writer = new FileWriter(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return writer;
    }

    private static void closeWriter(FileWriter writer) {
        try {
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void generateOvitoOutput(List<double[]> list) {

        try {
        	ovitoWriter.write(list.size() + "\n");
            ovitoWriter.write("\\ID" + "\t" + "X" + "\t" + "Y" + "\t" + "Vx" + "\t" + "Vy" + "\t" +
                    "Radius" + "\t" + "Pressure" + "\n");

            for (double[] d : list) {
                ovitoWriter.write((int) d[0] + "\t" + d[1] + "\t" + d[2] + "\t" + d[3] + "\t" + d[4] +
                        "\t" + d[5] + "\t" + d[6] + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}