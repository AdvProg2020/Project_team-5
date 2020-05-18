package modelTest;

import controller.sortingAndFilteringForProducts.BinaryFilters;
import controller.sortingAndFilteringForProducts.ControllerForFiltering;
import exception.categoryExceptions.CategoryNotFound;
import model.category.SubCategory;
import model.productThings.Good;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.*;

public class FilteringTest {


    @Test
    public void PriceFilter() {
        ControllerForFiltering controller = new ControllerForFiltering() {
            @Override
            public ArrayList<Good> getGoodList() {
                SubCategory subCategory2 = new SubCategory("b", new ArrayList<>());
                SubCategory subCategory = new SubCategory("a", new ArrayList<>());
                ArrayList<Good> goods = new ArrayList<>();
                Good good1 = new Good("laptop", "app", subCategory, "", new HashMap<>(), null, 100, 2);
                Good good2 = new Good("phone", "app", subCategory, "", new HashMap<>(), null, 400, 2);
                Good good3 = new Good("headphone", "sam", subCategory, "", new HashMap<>(), null, 500, 4);
                Good good4 = new Good("laptop", "app", subCategory2, "", new HashMap<>(), null, 200, 3);
                goods.add(good1);
                goods.add(good2);
                goods.add(good3);
                goods.add(good4);
                return goods;
            }

            @Override
            public ArrayList<BinaryFilters> getBinaryFilters() {
                ArrayList<BinaryFilters> filters = new ArrayList<>();
                filters.add(new BinaryFilters("price", "150", "450"));
                return filters;
            }
        };
        assertEquals(2, controller.showProducts().size());
    }

    @Test
    public void categoryFilter() {
        ControllerForFiltering controller = new ControllerForFiltering() {
            @Override
            public ArrayList<Good> getGoodList() {
                SubCategory subCategory2 = new SubCategory("b", new ArrayList<>());
                SubCategory subCategory = new SubCategory("a", new ArrayList<>());
                ArrayList<Good> goods = new ArrayList<>();
                Good good1 = new Good("laptop", "app", subCategory, "", new HashMap<>(), null, 100, 2);
                Good good2 = new Good("phone", "app", subCategory, "", new HashMap<>(), null, 400, 2);
                Good good3 = new Good("headphone", "sam", subCategory, "", new HashMap<>(), null, 500, 4);
                Good good4 = new Good("laptop", "app", subCategory2, "", new HashMap<>(), null, 200, 3);
                goods.add(good1);
                goods.add(good2);
                goods.add(good3);
                goods.add(good4);
                return goods;
            }

            @Override
            public HashMap<String, String> getUnaryFilters() {
                HashMap<String, String> unaryFilters = new HashMap<>();
                unaryFilters.put("subcategory", "a");
                unaryFilters.put("brand", "app");
                unaryFilters.put("name", "laptop");
                return unaryFilters;
            }
        };
        assertEquals(1, controller.showProducts().size());
    }

    @Test
    public void filterByAvailable() {
        ControllerForFiltering controller = new ControllerForFiltering() {
            @Override
            public ArrayList<Good> getGoodList() {
                SubCategory subCategory2 = new SubCategory("b", new ArrayList<>());
                SubCategory subCategory = new SubCategory("a", new ArrayList<>());
                ArrayList<Good> goods = new ArrayList<>();
                Good good1 = new Good("laptop", "app", subCategory, "", new HashMap<>(), null, 100, 2);
                good1.setGoodStatus(Good.GoodStatus.CONFIRMED);
                Good good2 = new Good("phone", "app", subCategory, "", new HashMap<>(), null, 400, 2);
                Good good3 = new Good("headphone", "sam", subCategory, "", new HashMap<>(), null, 500, 4);
                Good good4 = new Good("laptop", "app", subCategory2, "", new HashMap<>(), null, 200, 3);
                good4.setGoodStatus(Good.GoodStatus.CONFIRMED);
                goods.add(good1);
                goods.add(good2);
                goods.add(good3);
                goods.add(good4);
                return goods;
            }

            @Override
            public boolean isAvailableProduct() {
                return true;
            }
        };
        assertEquals(2, controller.showProducts().size());
    }

    @Test
    public void filterAFilter() {
        ControllerForFiltering controller = new ControllerForFiltering();
        controller.addBrandFiltering("app");
        controller.addNameFiltering("laptop");
        controller.addPriceFiltering("100", "200");
        assertTrue(controller.getUnaryFilters().containsKey("brand") && controller.getUnaryFilters().containsKey("name"));
        assertTrue(controller.getBinaryFilters().get(0).getFilterName().equals("price"));
        assertEquals(3,controller.getCurrentFilters().size());
        controller.disableFilter(1);
        controller.disableFilter(1);
        assertEquals(1,controller.getCurrentFilters().size());
    }

    @Test
    public void filterCategory() {
        ControllerForFiltering controller = new ControllerForFiltering();
        try {
            controller.addCategoryFilter("aa");
        } catch (CategoryNotFound e) {
            assertTrue(true);
        } catch (Exception e) {
        }
    }

    @Test
    public void filterSubCategory(){
        ControllerForFiltering controller = new ControllerForFiltering();
        try {
            controller.addSubCategoryFilter("aa");
        } catch (CategoryNotFound e) {
            assertTrue(true);
        } catch (Exception e) {
        }
    }

}
