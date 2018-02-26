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
@Table(name = "food_items", schema = "rawdata")
public class FoodItem {
    private static final String CSV_SEPARATOR = ";";
    private static final String CSV_NEWLINE = "\n";

    @Id
    @SequenceGenerator(name = "FOOD_ITEMS_ID_SEQ", sequenceName = "rawdata.food_items_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FOOD_ITEMS_ID_SEQ")
    @Column(insertable = false, updatable = false)
    private int id;
    @Column(name="name")
    public String name;
    @Column(name="nutrients_csv")
    private String nutrientsCsv;
    @Transient
    private List<Nutrient> nutrients = new ArrayList<Nutrient>();

    public List<Nutrient> getNutrients() {
        return nutrients;
    }

    public void setNutrients(List<Nutrient> nutrients) {
        this.nutrients = nutrients;
    }

    public Nutrient getMeasurementByName(String elementName) {
        for (Nutrient m: nutrients) {
            if ( m.getElement().equals(elementName) ) {
                return m;
            }
        }
        return null;
    }

    @PrePersist
    @PreUpdate
    private void parseNutrientsToCsv() {
        StringBuilder csv = new StringBuilder();
        for (Nutrient m: nutrients) {
            csv.append(m.getElement() + CSV_SEPARATOR
                    + m.getUnit() + CSV_SEPARATOR
                    + m.getValue() + CSV_NEWLINE);
        }
        nutrientsCsv = csv.toString();
    }

    @PostLoad
    @PostConstruct
    private void parseCsvToNutrientsList() throws FoodItemEntityException {
        String[] lines = nutrientsCsv.split(CSV_NEWLINE);
        for (int i = 0; i < lines.length; i++) {
            String[] line = lines[i].split(CSV_SEPARATOR);
            if (line.length == 3) {
                if (line[2].matches("[0-9.]*")) {
                    String elementName = line[0].replaceAll("\"", "");
                    String unit = line[1];
                    String value = line[2];
                    Nutrient m = new Nutrient(elementName, unit, value);
                    nutrients.add(m);
                } else {
                    throw new FoodItemEntityException("Unexpected data in position 3, " +
                            "should have been a sequence of digits");
                }
            } else {
                throw new FoodItemEntityException("Line must have 3 elements, "
                        + line.length + " elements found instead");
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        FoodItem foodItem = (FoodItem) o;

        return new EqualsBuilder()
                .append(name, foodItem.name)
                .append(nutrients, foodItem.nutrients)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(name)
                .append(nutrients)
                .toHashCode();
    }
}
