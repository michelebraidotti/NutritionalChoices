package my.project.data;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by michele on 2/5/17.
 */
public class Nutrient {
    private String name;
    @JsonDeserialize(contentAs=Measurement.class)
    private List<Measurement> measurements = new ArrayList<Measurement>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Measurement> getMeasurements() {
        return measurements;
    }

    public void setMeasurements(List<Measurement> measurements) {
        this.measurements = measurements;
    }

    public Measurement getMeasurementByName(String elementName) {
        for (Measurement m:measurements) {
            if ( m.getElement().equals(elementName) ) {
                return m;
            }
        }
        return null;
    }
}
