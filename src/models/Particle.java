package models;


public class Particle {
    private static int ID = 0;
    private static int WALL_ID = -1;
    private int id;

    private Vector2D previousPosition;
    private Vector2D position;

    private Vector2D velocity;

    private double mass;
    private double radius;

    private boolean hasCrashed;

    public Particle(Vector2D position, double mass, double radius, boolean isWall) {
        this.setPosition(position);
        this.setVelocity(Vector2D.ZERO);
        this.setRadius(radius);
        this.setMass(mass);
        this.setPosition(Vector2D.ZERO);
        this.setHasCrashed(false);
        if (isWall)
            this.setId(WALL_ID--);
        else
            this.setId(ID++);
    }


    public void setPosition(Vector2D position) {
        this.position = position;
    }

    public void setVelocity(Vector2D velocity) {
        this.velocity = velocity;
    }

    public void setId(int i) {
        this.id = i;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    private void setMass(double mass) {
        this.mass = mass;
    }

    public Vector2D getPreviousPosition() {
        return previousPosition;
    }

    public Vector2D getPosition() {
        return position;
    }

    public Vector2D getVelocity() {
        return velocity;
    }

    public int getID() {
        return id;
    }

    public double getRadius() {
        return radius;
    }

    public double getMass() {
        return mass;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Particle particle = (Particle) o;
        return id == particle.id;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public String toString() {
        return position.getX() + " "
                + position.getY() + " "
                + id;
    }

    public double getDistance(Particle p) {
        return this.getPosition().distance(p.getPosition()).getModule();
    }

    public boolean isHasCrashed() {
        return hasCrashed;
    }

    public void setHasCrashed(boolean hasCrashed) {
        this.hasCrashed = hasCrashed;
    }

    public void setPreviousPosition(Vector2D previousPosition) {
        this.previousPosition = previousPosition;
    }
}