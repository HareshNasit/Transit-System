package transitNetwork;
import java.util.ArrayList;
public class RouteManager {

  private final ArrayList<Route> routes;
  private final ArrayList<Stop> stops;
  private final ArrayList<Station> stations;

  RouteManager(){
    this.routes = new ArrayList<Route>();
    this.stops = new ArrayList<Stop>();
    this.stations = new ArrayList<Station>();

  }


}
