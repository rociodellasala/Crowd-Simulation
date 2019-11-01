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
    private static FileWriter timeWriter;
    private static FileWriter velocityWriter;

    public static void initializeOvito() {
        File data = createFile("./ovito.txt");
        File times = createFile("./times.txt");
        File velocity = createFile("./velocity.txt");
        ovitoWriter = openWriter(data);
        timeWriter = openWriter(times);
        velocityWriter = openWriter(velocity);
    }

    public static void closeFiles() {
        closeWriter(ovitoWriter);
        closeWriter(timeWriter);
        closeWriter(velocityWriter);
    }

    public static void recopilateData(Simulation simulation) {
        List<double[]> data = new ArrayList<>();
        Set<Particle> walls = new HashSet<>();
        recopilateParticlesData(simulation.getUniverse().getParticles(), data);
        generateVelocities(data);
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
            vx = p.getVelocity().getX();
            vy = p.getVelocity().getY();
            ra = p.getRadius();

            double[] currentParticle = {id, x, y, vx, vy, ra};
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
                    "Radius" + "\n");

            for (double[] d : list) {
                ovitoWriter.write((int) d[0] + "\t" + d[1] + "\t" + d[2] + "\t" + d[3] + "\t" + d[4] +
                        "\t" + d[5] + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void generateTimes(double time){
        try{
            timeWriter.write(time + ",");
        }catch (IOException e) {
        e.printStackTrace();
        }
    }

    private static void generateVelocities(List<double[]> list){
        try{
            double velocity;
            double totalvelocity = 0.0;
            double average;
            for (double[] d : list) {
                velocity = Math.sqrt( Math.pow(d[3],2) + Math.pow(d[4],2));
                totalvelocity += velocity;
            }
            average = totalvelocity / list.size();
            velocityWriter.write(average + ",");
        }catch (IOException e) {
            e.printStackTrace();
        }
    }


}