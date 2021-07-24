package ambulancesimulation.fileElements;

import java.util.ArrayList;
import java.util.List;

public class Place {

    private final int placeId;
    private final String name;
    private int xCord;
    private int yCord;

    public Place(int placeId, String name, int xCord, int yCord) {
        this.placeId = placeId;
        this.name = name;
        this.xCord = xCord;
        this.yCord = yCord;
    }

    public Place(Place that) {
        this(that.getPlaceId(), that.getPlaceName(), that.getPlaceXCord(), that.getPlaceYCord());
    }

    public static List<Place> cloneList(List<Place> placesList) {
        List<Place> clonedList = new ArrayList<Place>(placesList.size());
        for (Place place : placesList) {
            clonedList.add(new Place(place));
        }
        return clonedList;
    }

    public int getPlaceId() {
        return this.placeId;
    }

    public String getPlaceName() {
        return this.name;
    }

    public int getPlaceXCord() {
        return this.xCord;
    }

    public int getPlaceYCord() {
        return this.yCord;
    }

    public int setPlaceXCord(int newXCord) {
        return this.xCord = newXCord;
    }

    public int setPlaceYCord(int newYCord) {
        return this.yCord = newYCord;
    }

}
