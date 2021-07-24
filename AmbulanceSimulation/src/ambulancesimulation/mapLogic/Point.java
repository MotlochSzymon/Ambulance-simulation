package ambulancesimulation.mapLogic;

import java.awt.geom.Point2D;
import java.beans.Transient;

public class Point extends Point2D implements java.io.Serializable {

    private double x;
    private double y;

    public Point() {
        this(0, 0);
    }

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    @Transient
    public Point getLocation() {
        return new Point(x, y);
    }

    public void setLocation(Point p) {
        setLocation(p.x, p.y);
    }

    public void setLocation(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void setXCord(double x) {
        this.x = x;
    }

    public void setYcord(double y) {
        this.y = y;
    }

    public boolean equals(Object obj) {
        if (obj instanceof Point) {
            Point pt = (Point) obj;
            return (x == pt.x) && (y == pt.y);
        }
        return super.equals(obj);
    }

    public String toString() {
        return getClass().getName() + "[x=" + x + ",y=" + y + "]";
    }
}
