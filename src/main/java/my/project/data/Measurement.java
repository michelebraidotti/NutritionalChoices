package my.project.data;

/**
 * Created by michele on 2/5/17.
 */
public class Measurement {
    private String element;
    private String unit;
    private String value;

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
}
