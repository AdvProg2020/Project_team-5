package controller.sortingAndFiltering;

import exception.categoryExceptions.CategoryNotFound;
import exception.categoryExceptions.FilteredCategoryAlreadyChosen;
import exception.categoryExceptions.HaveNotChosenCategoryFilter;
import exception.categoryExceptions.SubcategoryNotFoundInThisCategory;
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

    public List<Good> filterByCategory(String category, List<Good> allGoods) {
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

    private List<Good> filterByName(String name,List<Good> allGoods){
        return allGoods.stream().filter(good -> good.getName().startsWith(name) || good.getName().endsWith(name)).collect(Collectors.toList());
    }

    public void resetAll() {
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

    public void addBinaryFilter(String filterName, String startValue, String endValue) {
        binaryFilters.add(new BinaryFilters(filterName, startValue, endValue));
    }

    public void addUnaryFilter(String filterName, String value) {
        unaryFilters.put(filterName, value);
    }

    public ArrayList<String> getCurrentFilters() {
        ArrayList<String> currentFilters = new ArrayList<>();
        for (String filterName : unaryFilters.keySet()) {
            currentFilters.add(filterName + ": " + unaryFilters.get(filterName));
        }
        for (BinaryFilters filter : binaryFilters) {
            currentFilters.add(filter.toString());
        }
        return currentFilters;
    }

    public void addCategoryFilter(String categoryName) throws Exception {
        if (filteredCategory != null)
            throw new FilteredCategoryAlreadyChosen();
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
        if (unaryFilters.containsKey("subcategory"))
            unaryFilters.remove("subcategory");
        addUnaryFilter("subcategory", subcategoryName);
    }

    public void addBrandFiltering(String brandName){
        if (unaryFilters.containsKey("brand"))
            unaryFilters.remove("brand");
        addUnaryFilter("brand", brandName);
    }

    public void addPriceFiltering(String startValue, String endValue){
        for (BinaryFilters filter : binaryFilters) {
            filter.getFilterName().equals("price");
            binaryFilters.remove(filter);
            break;
        }
        addBinaryFilter("price", startValue, endValue);
    }

    public void addNameFiltering(String name){
        if (unaryFilters.containsKey("name"))
            unaryFilters.remove("name");
        unaryFilters.put("name" , name);
    }

    public void disableFilter(int chosenFilter){
        if (chosenFilter <= unaryFilters.size())
            disableUnaryFilter(chosenFilter);
        else
            binaryFilters.remove(chosenFilter - unaryFilters.size() - 1);
    }

    public void disableUnaryFilter(int chosenFilter){
        int i = 1;
        for (String filterName : unaryFilters.keySet()) {
            if (i == chosenFilter){
                if (filterName.equals("category"))
                    disableCategoryFilter();
                unaryFilters.remove(filterName);
                break;
            }
            i++;
        }
    }

    private void disableCategoryFilter(){
        filteredCategory = null;
        if (unaryFilters.containsKey("subcategory"))
            unaryFilters.remove("subcategory");
    }

    public List<Good> showProducts(){
        List<Good> goods = new ArrayList<Good>(goodList);
        for (String key : unaryFilters.keySet()) {
            if (key.equals("category"))
                goods = filterByCategory(unaryFilters.get("category"), goods);
            else if (key.equals("subcategory"))
                goods = filterBySubCategory(unaryFilters.get("subcategory"), goods);
            else if (key.equals("name"))
                goods = filterByName(unaryFilters.get("name"), goods);
            else if (key.equals("brand"))
                goods = filterByBrand(unaryFilters.get("brand"), goods);
        }
        for (BinaryFilters binaryFilter : binaryFilters) {
            if (binaryFilter.getFilterName().equals("price"))
                goods = filterByPrice(binaryFilter, goods);
        }
        return goods;
    }
}
