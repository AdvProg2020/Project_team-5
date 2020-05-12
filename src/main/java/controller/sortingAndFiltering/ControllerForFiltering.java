package controller.sortingAndFiltering;

import exception.CategoryNotFound;
import exception.HaveNotChosenCategoryFilter;
import exception.SubcategoryNotFoundInThisCategory;
import model.Shop;
import model.category.Category;
import model.category.SubCategory;
import model.productThings.Good;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class ControllerForFiltering {
    private HashMap<String, String> unaryFilters;
    private ArrayList<BinaryFilters> binaryFilters;
    private List<Good> goodList;
    private Category filteredCategory;

    public ControllerForFiltering() {
        this.unaryFilters = new HashMap<>();
        this.binaryFilters = new ArrayList<>();
        this.goodList = new ArrayList<>();
    }

    public List<Good> filterByCategory(String category, List<Good> allGoods){
        return allGoods.stream().filter(good -> good.getSubCategory().getParentCategory().getName().equals(category)).collect(Collectors.toList());
    }

    public List<Good> filterBySubCategory(String subcategoryName, List<Good> allGoods) {
        return allGoods.stream().filter(good -> good.getSubCategory().getName().equals(subcategoryName)).collect(Collectors.toList());
    }

    private List<Good> filterByBrand(String brandName, List<Good> allGoods) {
        return allGoods.stream().filter(good -> good.getBrand().equals(brandName)).collect(Collectors.toList());
    }

    private List<Good> filterByPrice(BinaryFilters priceRange, List<Good> allGoods) {
        return allGoods.stream().filter(good -> good.getMinimumPrice() >= Long.parseLong(priceRange.getStartValue()) &&
                good.getMinimumPrice() <= Long.parseLong(priceRange.getEndValue())).collect(Collectors.toList());
    }

    public void resetAll(){
        unaryFilters.clear();
        binaryFilters.clear();
        goodList.clear();
    }

    public void setGoodList(boolean isAllProducts) {
        if (isAllProducts)
            goodList = Shop.getInstance().getAllGoods();
        if (!isAllProducts)
            goodList = Shop.getInstance().getOffGoods();
    }

    public void addBinaryFilter(String filterName, String startValue, String endValue){
        binaryFilters.add(new BinaryFilters(filterName, startValue, endValue));
    }

    public void addUnaryFilter(String filterName, String value){
        unaryFilters.put(filterName,value);
    }

    public ArrayList<String> getCurrentFilters(){
        ArrayList<String> currentFilters = new ArrayList<>();
        for (String filterName : unaryFilters.keySet()) {
            currentFilters.add(filterName + ": " + unaryFilters.get(filterName));
        }
        for (BinaryFilters filter : binaryFilters) {
            currentFilters.add(filter.toString());
        }
        return currentFilters;
    }

    public void addCategoryFilter(String categoryName) throws CategoryNotFound {
        Category category = Shop.getInstance().findCategoryByName(categoryName);
        if (category == null)
            throw new CategoryNotFound();
        addUnaryFilter("category", categoryName);
        filteredCategory = category;
    }

    public void addSubCategoryFilter(String subcategoryName) throws Exception {
        if (filteredCategory == null)
            throw new HaveNotChosenCategoryFilter();
        SubCategory subCategory = filteredCategory.findSubCategoryByName(subcategoryName);
        if (subCategory == null)
            throw new SubcategoryNotFoundInThisCategory();
        addUnaryFilter("subcategory", subcategoryName);
    }
}
