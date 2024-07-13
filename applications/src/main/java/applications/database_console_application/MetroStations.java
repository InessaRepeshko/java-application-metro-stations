package applications.database_console_application;

import java.util.ArrayList;
import java.util.List;


/**
 * Class for representing a list of Metro Stations.
 */
public class MetroStations {
    /**
     * The list of Metro Stations.
     */
    private List<MetroStationForDB> metroStationList = new ArrayList<>();

    /**
     * Gets the list of Metro Stations.
     * @return the list of Metro Stations.
     */
    public List<MetroStationForDB> getList() {
        return metroStationList;
    }

    /**
     * Sets the list of Metro Stations.
     * @param metroStationList the list of Metro Stations to set.
     */
    public void setList(List<MetroStationForDB> metroStationList) {
        this.metroStationList.clear();
        this.metroStationList.addAll(metroStationList);
    }

    /**
     * Adds a Metro Station to the list.
     * @param metroStation the Metro Station to add.
     */
    public void add(MetroStationForDB metroStation) {
        this.metroStationList.add(metroStation);
    }

    /**
     * Adds a list of Metro Stations to the existing list.
     * @param metroStations the list of Metro Stations to add.
     */
    public void addAll(List<MetroStationForDB> metroStations) {
        this.metroStationList.addAll(metroStations);
    }
}
