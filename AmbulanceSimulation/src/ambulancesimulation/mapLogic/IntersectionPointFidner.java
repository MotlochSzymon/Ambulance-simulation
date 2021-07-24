package ambulancesimulation.mapLogic;

import ambulancesimulation.mapHandler.InteresectionLinesInterface;

public class IntersectionPointFidner implements InteresectionLinesInterface {

    @Override
    public Point findIntersectionPointOfTwoLines(Point firstPointFirstLine, Point secondPointFirstLine, Point firstPointSecondLine, Point secondPointSecondLine) {
        double a1 = (secondPointFirstLine.getY() - firstPointFirstLine.getY()) / (secondPointFirstLine.getX() - firstPointFirstLine.getX());
        double b1 = firstPointFirstLine.getY() - a1 * firstPointFirstLine.getX();

        double a2 = (secondPointSecondLine.getY() - firstPointSecondLine.getY()) / (secondPointSecondLine.getX() - firstPointSecondLine.getX());
        double b2 = firstPointSecondLine.getY() - a2 * firstPointSecondLine.getX();

        Point intersectionPoint;

        if (a1 == a2) {
            return null;
        } else if (Double.isInfinite(a1)) {
            intersectionPoint = new Point(firstPointFirstLine.getX(), a2 * firstPointFirstLine.getX() + b2);
        } else if (Double.isInfinite(a2)) {
            intersectionPoint = new Point(firstPointSecondLine.getX(), a1 * firstPointSecondLine.getX() + b1);
        } else {
            intersectionPoint = calculateIntersectionPoint(a1, b1, a2, b2);
        }

        if (intersectionPoint != null && isPointInSectionRange(intersectionPoint, firstPointFirstLine, secondPointFirstLine)
                && isPointInSectionRange(intersectionPoint, firstPointSecondLine, secondPointSecondLine)) {

            return intersectionPoint;
        }
        return null;
    }

    public Point calculateIntersectionPoint(double a1, double b1, double a2, double b2) {

        if (a1 == a2) {
            return null;
        }

        double x = (b2 - b1) / (a1 - a2);
        double y = a1 * x + b1;

        Point point = new Point();
        point.setLocation(x, y);
        return point;
    }

    public boolean isPointInSectionRange(Point interesectionPoint, Point firstLinePoint, Point secondLinePoint) {
        double xMin = Math.min(firstLinePoint.getX(), secondLinePoint.getX());
        double xMax = Math.max(firstLinePoint.getX(), secondLinePoint.getX());

        double yMin = Math.min(firstLinePoint.getY(), secondLinePoint.getY());
        double yMax = Math.max(firstLinePoint.getY(), secondLinePoint.getY());

        if ((interesectionPoint.getX() > xMin && interesectionPoint.getX() < xMax)
                && (interesectionPoint.getY() > yMin && interesectionPoint.getY() < yMax)) {
            return true;
        } else {
            return false;
        }
    }
}
