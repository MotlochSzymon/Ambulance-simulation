package ambulancesimulation.mapLogic;

import java.util.*;
import ambulancesimulation.mapHandler.BordersMapCheckingIntereface;

public class ConvexHullAlgorithm implements BordersMapCheckingIntereface {

    @Override
    public List<Point> getBorderPoints(List<Point> allMapPoints) {
        return runConvexHullAlgorithm(allMapPoints);
    }

    @Override
    public boolean checkIfPointIsInsideBorders(List<Point> polygonPoints, Point point) {
        return isInside(polygonPoints, point);
    }

    public List<Point> runConvexHullAlgorithm(List<Point> points) {
        int pointsAmount = points.size();
        if (pointsAmount < 3) {
            return null;
        }

        List<Point> borderPoints = new ArrayList<Point>();

        int firstPointIndex = 0;
        for (int i = 1; i < points.size(); i++) {
            if (points.get(i).getX() < points.get(firstPointIndex).getX()) {
                firstPointIndex = i;
            }
        }

        int secondPointIndex = firstPointIndex;
        int thirdPoint;
        do {
            borderPoints.add(points.get(secondPointIndex));

            thirdPoint = (secondPointIndex + 1) % pointsAmount;

            for (int i = 0; i < pointsAmount; i++) {

                if (checkOrientation(points.get(secondPointIndex), points.get(i), points.get(thirdPoint)) == 2) {
                    thirdPoint = i;
                }
            }

            secondPointIndex = thirdPoint;

        } while (secondPointIndex != firstPointIndex);

        for (Point temp : borderPoints) {
            System.out.println("(" + temp.getX() + ", "
                    + temp.getY() + ")");
        }

        return borderPoints;
    }

    public boolean isInside(List<Point> polygonPoints, Point point) {
        int amountOfPoints = polygonPoints.size();

        if (amountOfPoints < 3) {
            return false;
        }

        Point extreme = new Point(Double.MAX_VALUE, point.getY());

        int count = 0;
        int i = 0;
        do {
            int next = (i + 1) % amountOfPoints;

            if (checkIntersection(polygonPoints.get(i), polygonPoints.get(next), point, extreme)) {
                if (checkOrientation(polygonPoints.get(i), point, polygonPoints.get(next)) == 0) {
                    return isMiddlePointOnSegment(polygonPoints.get(i), point,
                            polygonPoints.get(next));
                }

                count++;
            }
            i = next;
        } while (i != 0);

        return (count % 2 == 1);
    }

    public double checkOrientation(Point point1, Point point2, Point point3) {
        double orientation = (point2.getY() - point1.getY()) * (point3.getX() - point2.getX())
                - (point2.getX() - point1.getX()) * (point3.getY() - point2.getY());

        if (orientation == 0) {
            return 0;
        }
        return (orientation > 0) ? 1 : 2;
    }

    boolean isMiddlePointOnSegment(Point point1, Point point2, Point point3) {
        if (point2.getX() <= Math.max(point1.getX(), point3.getX())
                && point2.getX() >= Math.min(point1.getX(), point3.getX())
                && point2.getY() <= Math.max(point1.getY(), point3.getY())
                && point2.getY() >= Math.min(point1.getY(), point3.getY())) {
            return true;
        }
        return false;
    }

    boolean checkIntersection(Point firstVectorPoint1, Point firstVectorPoint2, Point secondVectorPoint1, Point secondVectorPoint2) {
        double orientation1 = checkOrientation(firstVectorPoint1, firstVectorPoint2, secondVectorPoint1);
        double orientation2 = checkOrientation(firstVectorPoint1, firstVectorPoint2, secondVectorPoint2);
        double orientation3 = checkOrientation(secondVectorPoint1, secondVectorPoint2, firstVectorPoint1);
        double orientation4 = checkOrientation(secondVectorPoint1, secondVectorPoint2, firstVectorPoint2);

        if (orientation1 != orientation2 && orientation3 != orientation4) {
            return true;
        }

        if (orientation1 == 0 && isMiddlePointOnSegment(firstVectorPoint1, secondVectorPoint1, firstVectorPoint2)) {
            return true;
        }

        if (orientation2 == 0 && isMiddlePointOnSegment(firstVectorPoint1, secondVectorPoint2, firstVectorPoint2)) {
            return true;
        }

        if (orientation3 == 0 && isMiddlePointOnSegment(secondVectorPoint1, firstVectorPoint1, secondVectorPoint2)) {
            return true;
        }

        if (orientation4 == 0 && isMiddlePointOnSegment(secondVectorPoint1, firstVectorPoint2, secondVectorPoint2)) {
            return true;
        }

        return false;
    }

}
