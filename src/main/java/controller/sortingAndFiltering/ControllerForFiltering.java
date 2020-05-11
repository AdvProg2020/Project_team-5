package controller.sortingAndFiltering;

import java.util.ArrayList;
import java.util.HashMap;

public class ControllerForFiltering {
    private HashMap<String, String> unaryFilters;
    private ArrayList<BinaryFilters> binaryFilters;

    public ControllerForFiltering() {
        this.unaryFilters = new HashMap<>();
        this.binaryFilters = new ArrayList<>();
    }
}
