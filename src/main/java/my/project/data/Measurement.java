package my.project.data;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Created by michele on 2/5/17.
 */
public class Measurement {
    private String element;
    private String unit;
    private String value;

    public Measurement() {
    }

    public Measurement(String element, String unit, String value) {
        this.element = element;
        this.unit = unit;
        this.value = value;
    }

    public String getElement() {
        return element;
    }

    public void setElement(String element) {
        this.element = element;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Measurement that = (Measurement) o;

        return new EqualsBuilder()
                .append(element, that.element)
                .append(unit, that.unit)
                .append(value, that.value)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(element)
                .append(unit)
                .append(value)
                .toHashCode();
    }
}
