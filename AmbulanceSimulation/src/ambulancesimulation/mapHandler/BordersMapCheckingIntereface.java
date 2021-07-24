package ambulancesimulation.mapHandler;

import ambulancesimulation.mapLogic.Point;
import java.util.List;

public interface BordersMapCheckingIntereface {

    public List<Point> getBorderPoints(List<Point> allMapPoints);

    public boolean checkIfPointIsInsideBorders(List<Point> polygonPoints, Point point);
}
