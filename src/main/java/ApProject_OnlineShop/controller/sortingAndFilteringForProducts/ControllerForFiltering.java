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
    private Category filteredCategory;
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

    public HashMap<String, String> getCategoryProperties() {
        return categoryProperties;
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

    public void setGoodList(boolean isAllProducts) {
        if (isAllProducts)
            goodList = Shop.getInstance().getAllGoods();
        if (!isAllProducts)
            goodList = Shop.getInstance().getOffGoods();
    }

    public void addBinaryFilter(String filterName, String startValue, String endValue) {
        binaryFilters.add(new BinaryFilters(filterName, startValue, endValue));
    }

    public void addCategoryFilter(String categoryName){
        disableCategoryFilter();
        this.category = categoryName;
    }

    public void addSubCategoryFilter(String subcategoryName) {
        this.subCategory = subcategoryName;
    }

    public void addBrandFiltering(String brandName) {
        this.brand = brandName;
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

    public void addSellerFilter(String sellerUser) {
        this.seller = sellerUser;
    }

    public void addNameFiltering(String name) {
        this.name = name;
    }

    public void addAvailableProduct() {
        availableProduct = true;
    }

    public void removeAvailableProductsFilter() {

    }

    public List<String> getProperties() throws Exception {
        if (filteredCategory == null)
            throw new HaveNotChosenCategoryFilter();
        return Shop.getInstance().findCategoryByName(getCategory()).getDetails();
    }

    public void addPropertiesFilter(String property, String value) {
        categoryProperties.put(property, value);
    }

    public void disableCategoryFilter() {
        categoryProperties = new HashMap<>();
        subCategory = "";
        category = "";
    }

    public List<Good> showProducts() {
        List<Good> goods = new ArrayList<>(getGoodList());
        if (isAvailableProduct())
            goods = filterAvailableProducts(goods);
        if (!getCategory().equals(""))
            goods = filterByCategory(getCategory(), goods);
        if (!getSubCategory().equals(""))
            goods = filterBySubCategory(getSubCategory(), goods);
        if (isOffProductsFilter()) {
            //ToDo
        }
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
}
