package controller.sortingAndFilteringForProducts;

import exception.categoryExceptions.CategoryNotFound;
import exception.categoryExceptions.FilteredCategoryAlreadyChosen;
import exception.categoryExceptions.HaveNotChosenCategoryFilter;
import exception.categoryExceptions.SubcategoryNotFoundInThisCategory;
import exception.userExceptions.SellerNotFound;
import model.Shop;
import model.category.Category;
import model.category.SubCategory;
import model.persons.Person;
import model.persons.Seller;
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
    private boolean availableProduct;

    public ControllerForFiltering() {
        this.unaryFilters = new HashMap<>();
        this.binaryFilters = new ArrayList<>();
        this.goodList = new ArrayList<>();
    }

    public List<Good> getGoodList() {
        return goodList;
    }

    public HashMap<String, String> getUnaryFilters() {
        return unaryFilters;
    }

    public ArrayList<BinaryFilters> getBinaryFilters() {
        return binaryFilters;
    }

    public boolean isAvailableProduct() {
        return availableProduct;
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

    private List<Good> filterByName(String name, List<Good> allGoods) {
        return allGoods.stream().filter(good -> good.getName().startsWith(name) || good.getName().endsWith(name)).collect(Collectors.toList());
    }

    private List<Good> filterByProperty(String property, List<Good> allGoods) {
        return allGoods.stream().filter(good -> good.getCategoryProperties().get(property).equals(unaryFilters.get(property))).collect(Collectors.toList());
    }

    private List<Good> filterAvailableProducts(List<Good> allGoods) {
        return allGoods.stream().filter(good -> good.getGoodStatus() == Good.GoodStatus.CONFIRMED).collect(Collectors.toList());
    }

    private List<Good> filterBySeller(String sellerUserName, List<Good> allGoods){
        return allGoods.stream().filter(good -> good.doesExistInSellerList((Seller)Shop.getInstance().findUser(sellerUserName))).collect(Collectors.toList());
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
        if (availableProduct)
            currentFilters.add("availableFilters");
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

    public void addBrandFiltering(String brandName) {
        if (unaryFilters.containsKey("brand"))
            unaryFilters.remove("brand");
        addUnaryFilter("brand", brandName);
    }

    public void addPriceFiltering(String startValue, String endValue) {
        for (BinaryFilters filter : binaryFilters) {
            if (filter.getFilterName().equals("price")) {
                binaryFilters.remove(filter);
                break;
            }
        }
        addBinaryFilter("price", startValue, endValue);
    }

    public void addSellerFilter(String sellerUser) throws Exception{
        Person seller = Shop.getInstance().findUser(sellerUser);
        if (seller == null)
            throw new SellerNotFound();
        if (!(seller instanceof Seller))
            throw new SellerNotFound();
        if (unaryFilters.containsKey("seller"))
            unaryFilters.remove("seller");
        unaryFilters.put("seller", sellerUser);
    }

    public void addNameFiltering(String name) {
        if (unaryFilters.containsKey("name"))
            unaryFilters.remove("name");
        unaryFilters.put("name", name);
    }

    public void addAvailableProduct() {
        availableProduct = true;
    }

    public List<String> getProperties() throws Exception {
        if (filteredCategory == null)
            throw new HaveNotChosenCategoryFilter();
        return Shop.getInstance().findCategoryByName(unaryFilters.get("category")).getDetails();
    }

    public void addPropertiesFilter(String property, String value) {
        unaryFilters.put(property, value);
    }

    public void disableFilter(int chosenFilter) {
        if (chosenFilter <= unaryFilters.size())
            disableUnaryFilter(chosenFilter);
        else if (chosenFilter == unaryFilters.size() + binaryFilters.size() + 1)
            availableProduct = false;
        else
            binaryFilters.remove(chosenFilter - unaryFilters.size() - 1);
    }

    public void disableUnaryFilter(int chosenFilter) {
        int i = 1;
        for (String filterName : unaryFilters.keySet()) {
            if (i == chosenFilter) {
                if (filterName.equals("category"))
                    disableCategoryFilter();
                unaryFilters.remove(filterName);
                break;
            }
            i++;
        }
    }

    private void disableCategoryFilter() {
        filteredCategory = null;
        HashMap<String, String> tempFilters = new HashMap<>();
        for (String filterName : unaryFilters.keySet()) {
            if (filterName.equals("name") || filterName.equals("brand") || filterName.equals("seller"))
                tempFilters.put(filterName, unaryFilters.get(filterName));
        }
        this.unaryFilters = tempFilters;
    }

    public List<Good> showProducts() {
        List<Good> goods = new ArrayList<>(getGoodList());
        if (isAvailableProduct())
            goods = filterAvailableProducts(goods);
        for (String key : getUnaryFilters().keySet()) {
            if (key.equals("category"))
                goods = filterByCategory(getUnaryFilters().get("category"), goods);
            else if (key.equals("subcategory"))
                goods = filterBySubCategory(getUnaryFilters().get("subcategory"), goods);
            else if (key.equals("name"))
                goods = filterByName(getUnaryFilters().get("name"), goods);
            else if (key.equals("brand"))
                goods = filterByBrand(getUnaryFilters().get("brand"), goods);
            else if (key.equals("seller"))
                goods = filterBySeller(getUnaryFilters().get("seller"), goods);
            else
                goods = filterByProperty(key, goods);
        }
        for (BinaryFilters binaryFilter : getBinaryFilters()) {
            if (binaryFilter.getFilterName().equals("price"))
                goods = filterByPrice(binaryFilter, goods);
        }
        return goods;
    }
}
