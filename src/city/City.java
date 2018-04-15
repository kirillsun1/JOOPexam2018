package city;

import city.road.Road;

import java.util.*;
import java.util.stream.Collectors;

public class City {
    private final Map<Crossroad, Set<Road>> connectedRoads;

    public City() {
        connectedRoads = new HashMap<>();
    }

    public void addRoad(Road road) {
        connectedRoads.putIfAbsent(road.getCrossroad1(), new HashSet<>());
        connectedRoads.putIfAbsent(road.getCrossroad2(), new HashSet<>());

        connectedRoads.get(road.getCrossroad1()).add(road);
        connectedRoads.get(road.getCrossroad2()).add(road);
    }

    public Set<Road> getRoadsOfCrossroad(Crossroad crossroad) {
        return connectedRoads.get(crossroad);
    }

    public List<Crossroad> getAllStartingCrossroads() {
        return connectedRoads.keySet().parallelStream()
                .filter(c -> c instanceof StartingCrossroad)
                .collect(Collectors.toList());
    }
}
