/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mapLogic;

import ambulancesimulation.mapHandler.BordersMapCheckingIntereface;
import ambulancesimulation.mapLogic.ConvexHullAlgorithm;
import ambulancesimulation.mapLogic.Point;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Dom
 */
public class ConvexHullAlgorithmTest {

    public ConvexHullAlgorithmTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void getBorderPointsTest() throws Exception {
        //given
        boolean hasCorrectResults = false;
        List<Point> allPoints = new ArrayList();

        Point a = new Point(0, 0);
        Point b = new Point(1, 1);
        Point c = new Point(2, 2);
        Point d = new Point(5, -10);
        Point e = new Point(10, 10);
        Point f = new Point(0, 10);
        Point g = new Point(2.5, 2);

        allPoints.add(a);
        allPoints.add(b);
        allPoints.add(c);
        allPoints.add(d);
        allPoints.add(e);
        allPoints.add(f);
        allPoints.add(g);

        //when
        BordersMapCheckingIntereface mapChecker = new ConvexHullAlgorithm();
        List<Point> borders = mapChecker.getBorderPoints(allPoints);

        if (borderContainsPoint(borders, a) && borderContainsPoint(borders, d) && borderContainsPoint(borders, e) && borderContainsPoint(borders, f)) {
            hasCorrectResults = true;
        }

        assertEquals(hasCorrectResults, true);
    }

    @Test
    public void checkIfPointIsInsideBordersTrueExample() throws Exception {
        //given
        boolean excpeted = true;
        List<Point> borders = new ArrayList();

        Point a = new Point(0, 2);
        Point b = new Point(2, 0);
        Point c = new Point(3, 3);
        Point d = new Point(0, 3);
        Point pointToCheck = new Point(1.75, 1.75);

        borders.add(a);
        borders.add(b);
        borders.add(c);
        borders.add(d);

        //when
        BordersMapCheckingIntereface mapChecker = new ConvexHullAlgorithm();
        boolean isPointInside = mapChecker.checkIfPointIsInsideBorders(borders, pointToCheck);

        assertEquals(isPointInside, excpeted);
    }

    @Test
    public void checkIfPointIsInsideBordersFalseExample() throws Exception {
        //given
        boolean excpeted = false;
        List<Point> borders = new ArrayList();

        Point a = new Point(0, 2);
        Point b = new Point(2, 0);
        Point c = new Point(3, 3);
        Point d = new Point(0, 3);
        Point pointToCheck = new Point(-1, -1);

        borders.add(a);
        borders.add(b);
        borders.add(c);
        borders.add(d);

        //when
        BordersMapCheckingIntereface mapChecker = new ConvexHullAlgorithm();
        boolean isPointInside = mapChecker.checkIfPointIsInsideBorders(borders, pointToCheck);

        assertEquals(isPointInside, excpeted);
    }

    public boolean borderContainsPoint(List<Point> borders, Point point) {
        for (int i = 0; i < borders.size(); i++) {
            if (borders.get(i).equals(point)) {
                return true;
            }
        }
        return false;
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
}
