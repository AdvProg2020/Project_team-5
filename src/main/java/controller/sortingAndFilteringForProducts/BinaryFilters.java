package controller.sortingAndFilteringForProducts;

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


    @Override
    public String toString(){
        return filterName + ": from " + startValue + " to " + endValue ;
    }
}
