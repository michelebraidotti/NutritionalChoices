package my.project.data.entities;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.annotation.PostConstruct;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by michele on 2/5/17.
 */
@Entity
@Table(name = "nutrients", schema = "rawdata")
public class Nutrient {
    private static final String CSV_SEPARATOR = ",";
    private static final String CSV_NEWLINE = "\n";

    @Id
    @SequenceGenerator(name = "NUTRIENTS_ID_SEQ", sequenceName = "rawdata.nutrients_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "NUTRIENTS_ID_SEQ")
    @Column(insertable = false, updatable = false)
    private int id;
    @Column(name="name")
    public String name;
    @Column(name="measurements_csv")
    private String measurementsCsv;
    @Transient
    private List<Measurement> measurements = new ArrayList<Measurement>();

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

    @PrePersist
    @PreUpdate
    private void parseMesurementsToCsv() {
        StringBuilder csv = new StringBuilder();
        for (Measurement m:measurements) {
            csv.append(m.getElement() + CSV_SEPARATOR
                    + m.getUnit() + CSV_SEPARATOR
                    + m.getValue() + CSV_NEWLINE);
        }
        measurementsCsv = csv.toString();
    }

    @PostLoad
    @PostConstruct
    private void parseCsvToMeasurements() throws NutrientEntityException {
        String[] lines = measurementsCsv.split(CSV_NEWLINE);
        for (int i = 0; i < lines.length; i++) {
            String[] line = lines[i].split(CSV_SEPARATOR);
            if (line.length == 3) {
                if (line[2].matches("[0-9.]*")) {
                    String elementName = line[0].replaceAll("\"", "");
                    String unit = line[1];
                    String value = line[2];
                    Measurement m = new Measurement(elementName, unit, value);
                    measurements.add(m);
                } else {
                    throw new NutrientEntityException("Unexpected data in position 3, " +
                            "should have been a sequence of digits");
                }
            } else {
                throw new NutrientEntityException("Line must have 3 elements, "
                        + line.length + " elements found instead");
            }
        }
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
