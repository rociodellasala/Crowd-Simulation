package utils;

public class Const {

    public final static double minRadius = 0.10;
    public final static double maxRadius = 0.37;

    public final static double beta = 0.9;
    public final static double vdmax = 0.95;
    public final static double tau = 0.5;

    public final static double interactionRadio = 0.000001;

    public final static double outerRadius = 4;
    public final static double innerRadius = 2;

    public static double L;

    public Const(double L) {
        Const.L = L;
    }
}
