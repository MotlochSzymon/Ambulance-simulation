package ambulancesimulation.mapHandler;

import ambulancesimulation.mapLogic.Point;

public interface InteresectionLinesInterface {

    public Point findIntersectionPointOfTwoLines(Point firstPointFirstLine, Point secondPointFirstLine, Point firstPointSecondLine, Point secondPointSecondLine);
}
