package ApProject_OnlineShop.controller.sortingAndFilteringForProducts;

import ApProject_OnlineShop.exception.categoryExceptions.CategoryNotFound;
import ApProject_OnlineShop.exception.categoryExceptions.FilteredCategoryAlreadyChosen;
import ApProject_OnlineShop.exception.categoryExceptions.HaveNotChosenCategoryFilter;
import ApProject_OnlineShop.exception.categoryExceptions.SubcategoryNotFoundInThisCategory;
import ApProject_OnlineShop.exception.userExceptions.SellerNotFound;
import ApProject_OnlineShop.model.Shop;
import ApProject_OnlineShop.model.category.Category;
import ApProject_OnlineShop.model.category.SubCategory;
import ApProject_OnlineShop.model.persons.Person;
import ApProject_OnlineShop.model.persons.Seller;
import ApProject_OnlineShop.model.productThings.Good;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class ControllerForFiltering {
    private HashMap<String, String> categoryProperties;
    private ArrayList<BinaryFilters> binaryFilters;
    private List<Good> goodList;
    private boolean availableProduct;
    private boolean offProductsFilter;
    private String category = "";
    private String subCategory = "";
    private String brand = "";
    private String seller = "";
    private String name = "";

    public ControllerForFiltering() {
        this.categoryProperties = new HashMap<>();
        this.binaryFilters = new ArrayList<>();
        this.goodList = new ArrayList<>();
    }

    public boolean isOffProductsFilter() {
        return offProductsFilter;
    }

    public void setOffProductsFilter() {
        offProductsFilter = true;
    }

    public void removeOffProductsFilter() {
        offProductsFilter = false;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }

    public String getSeller() {
        return seller;
    }

    public String getName() {
        return name;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getCategory() {
        return category;
    }

    public String getSubCategory() {
        return subCategory;
    }

    public String getBrand() {
        return brand;
    }

    public List<Good> getGoodList() {
        return goodList;
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
        return allGoods.stream().filter(good -> good.getCategoryProperties().get(property).equals(categoryProperties.get(property))).collect(Collectors.toList());
    }

    private List<Good> filterAvailableProducts(List<Good> allGoods) {
        return allGoods.stream().filter(good -> good.getGoodStatus() == Good.GoodStatus.CONFIRMED).collect(Collectors.toList());
    }

    private List<Good> filterBySeller(String sellerUserName, List<Good> allGoods) {
        return allGoods.stream().filter(good -> good.doesExistInSellerList((Seller) Shop.getInstance().findUser(sellerUserName))).collect(Collectors.toList());
    }

    private List<Good> filterByOff(List<Good> allGoods) {
        return allGoods.stream().filter(good -> good.getSellerThatPutsThisGoodOnOff() != null).collect(Collectors.toList());
    }

    public void addBinaryFilter(String filterName, String startValue, String endValue) {
        binaryFilters.add(new BinaryFilters(filterName, startValue, endValue));
    }

    public void addCategoryFilter(String categoryName) {
        disableCategoryFilter();
        this.category = categoryName;
    }

    public void addSubCategoryFilter(String subcategoryName) {
        this.subCategory = subcategoryName;
    }

    public void addBrandFiltering(String brandName) {
        this.brand = brandName;
    }

    public void disablePriceFiltering() {
        if (binaryFilters.size() > 0)
            for (BinaryFilters filter : binaryFilters) {
                if (filter.getFilterName().equals("price")) {
                    binaryFilters.remove(filter);
                    break;
                }
            }
    }

    public String getStartPrice(){
        if (binaryFilters.size() > 0)
            return binaryFilters.get(0).getStartValue();
        return "";
    }

    public String getEndPrice(){
        if (binaryFilters.size() > 0)
            return binaryFilters.get(0).getEndValue();
        return "";
    }

    public void addPriceFiltering(String startValue, String endValue) {
        disablePriceFiltering();
        addBinaryFilter("price", startValue, endValue);
    }

    public void addSellerFilter(String sellerUser) {
        this.seller = sellerUser;
    }

    public void addNameFiltering(String name) {
        this.name = name;
    }

    public void addAvailableProduct() {
        availableProduct = true;
    }

    public List<String> getProperties() throws Exception {
//        if (filteredCategory == null)
//            throw new HaveNotChosenCategoryFilter();
        return Shop.getInstance().findCategoryByName(getCategory()).getDetails();
    }

    public void disableCategoryFilter() {
        categoryProperties = new HashMap<>();
        subCategory = "";
        category = "";
    }

    public List<String> getSubcategories(){
        if (!getCategory().equals(""))
            return Shop.getInstance().findCategoryByName(getCategory()).getSubCategories().stream().map(subcategory -> subcategory.getName()).collect(Collectors.toList());
        return null;
    }

    public List<String> getCategoryProperties(){
        return Shop.getInstance().findCategoryByName(getCategory()).getDetails();
    }

    public List<Good> showProducts() {
        List<Good> goods = new ArrayList<>(getGoodList());
        if (isAvailableProduct())
            goods = filterAvailableProducts(goods);
        if (!getCategory().equals(""))
            goods = filterByCategory(getCategory(), goods);
        if (!getSubCategory().equals(""))
            goods = filterBySubCategory(getSubCategory(), goods);
        if (isOffProductsFilter())
            goods = filterByOff(goods);
        if (!getBrand().equals(""))
            goods = filterByBrand(getBrand(), goods);
        if (!getName().equals(""))
            goods = filterByName(getName(), goods);
        if (!getSeller().equals(""))
            goods = filterBySeller(getSeller(), goods);
        for (BinaryFilters binaryFilter : getBinaryFilters()) {
            if (binaryFilter.getFilterName().equals("price"))
                goods = filterByPrice(binaryFilter, goods);
        }
        return goods;
    }

    public List<String> getSubCategoryProperties(){
        if (!getSubCategory().equals(""))
            return Shop.getInstance().findSubCategoryByName(getSubCategory()).getDetails();
        return null;
    }

    public String getValueOfProperty(String property){
        if (categoryProperties.containsKey(property))
            return categoryProperties.get(property);
        return "";
    }

    public void addPropertiesFilter(String property,String value){
        if (categoryProperties.containsKey(property))
            categoryProperties.replace(property,value);
        else
            categoryProperties.put(property, value);
    }

    public void removeProperty(String property){
        if (categoryProperties.containsKey(property))
            categoryProperties.remove(property);
    }

    public void removeAvailableProductsFilter() {
        this.availableProduct = false;
    }
}
