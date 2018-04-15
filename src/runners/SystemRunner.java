package runners;

import city.City;
import city.Crossroad;
import city.StartingCrossroad;
import city.road.BadRoad;
import city.road.Road;
import city.service.CarService;
import controllers.SystemController;
import environment.EnvironmentCenter;

import java.util.ArrayList;
import java.util.List;

class SystemRunner {
    public static void main(String[] args) {
        City someCity = new City();

        EnvironmentCenter environmentCenter = new EnvironmentCenter(someCity);

        Crossroad crossroadK = new StartingCrossroad("K", environmentCenter);
        Crossroad crossroadI = new StartingCrossroad("I", environmentCenter);
        Crossroad crossroadC = new StartingCrossroad("C", environmentCenter);
        Crossroad crossroadG = new StartingCrossroad("G", environmentCenter);

        Crossroad crossroadF = new Crossroad("F");
        Crossroad crossroadB = new Crossroad("B");
        Crossroad crossroadH = new Crossroad("H");
        Crossroad crossroadE = new Crossroad("E");

        CarService carServiceF = new CarService("CSF");
        CarService carServiceB = new CarService("CSB");
        CarService carServiceH = new CarService("CSH");
        CarService carServiceE = new CarService("CSE");

        crossroadF.setCarService(carServiceF);
        crossroadB.setCarService(carServiceB);
        crossroadH.setCarService(carServiceH);
        crossroadE.setCarService(carServiceE);

        Crossroad crossroadJ = new Crossroad("J");
        Crossroad crossroadA = new Crossroad("A");
        Crossroad crossroadD = new Crossroad("D");
        Crossroad crossroadL = new Crossroad("L");

        List<Road> roadList = new ArrayList<>();

        roadList.add(new Road("a", crossroadK, crossroadE));
        roadList.add(new Road("b", crossroadE, crossroadC));
        roadList.add(new Road("c", crossroadC, crossroadH));
        roadList.add(new Road("d", crossroadC, crossroadB));
        roadList.add(new Road("e", crossroadD, crossroadL));
        roadList.add(new Road("f", crossroadA, crossroadB));
        roadList.add(new Road("g", crossroadA, crossroadC));
        roadList.add(new BadRoad("h", crossroadD, crossroadA));
        roadList.add(new Road("i", crossroadA, crossroadE));
        roadList.add(new Road("j", crossroadD, crossroadF));
        roadList.add(new Road("k", crossroadG, crossroadD));
        roadList.add(new Road("l", crossroadB, crossroadH));
        roadList.add(new Road("m", crossroadI, crossroadB));
        roadList.add(new Road("n", crossroadF, crossroadI));
        roadList.add(new Road("p", crossroadE, crossroadJ));
        roadList.add(new Road("q", crossroadJ, crossroadK));
        roadList.add(new BadRoad("r", crossroadK, crossroadG));
        roadList.add(new Road("s", crossroadG, crossroadJ));
        roadList.add(new Road("t", crossroadG, crossroadF));
        roadList.add(new Road("f1", crossroadL, crossroadA));
        roadList.add(new Road("g1", crossroadL, crossroadI));

        roadList.forEach(someCity::addRoad);

        new SystemController(someCity, environmentCenter);

    }
}
