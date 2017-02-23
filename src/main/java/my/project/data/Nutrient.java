package my.project.data;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.data.annotation.Id;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by michele on 2/5/17.
 */
public class Nutrient extends MongoId {

    @Id
    public String id;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Nutrient nutrient = (Nutrient) o;

        return new EqualsBuilder()
                .append(name, nutrient.name)
                .append(measurements, nutrient.measurements)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(name)
                .append(measurements)
                .toHashCode();
    }
}
