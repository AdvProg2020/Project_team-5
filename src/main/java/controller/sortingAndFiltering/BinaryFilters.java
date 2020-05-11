package controller.sortingAndFiltering;

public class BinaryFilters  {
    private String filterName;
    private String startValue;
    private String endValue;

    public BinaryFilters(String filterName, String startValue, String endValue) {
        this.filterName = filterName;
        this.startValue = startValue;
        this.endValue = endValue;
    }

    public String getFilterName() {
        return filterName;
    }

    public String getStartValue() {
        return startValue;
    }

    public String getEndValue() {
        return endValue;
    }

    public void setStartValue(String startValue) {
        this.startValue = startValue;
    }

    public void setEndValue(String endValue) {
        this.endValue = endValue;
    }
}
