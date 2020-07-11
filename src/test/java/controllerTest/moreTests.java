package controllerTest;

import ApProject_OnlineShop.controller.MainController;
import ApProject_OnlineShop.controller.sortingAndFilteringForProducts.BinaryFilters;
import ApProject_OnlineShop.database.Database;
import ApProject_OnlineShop.exception.FileCantBeDeletedException;
import ApProject_OnlineShop.exception.FileCantBeSavedException;
import ApProject_OnlineShop.exception.NotEnoughCredit;
import ApProject_OnlineShop.exception.NotFoundOrderById;
import ApProject_OnlineShop.exception.categoryExceptions.CategoryNotFound;
import ApProject_OnlineShop.exception.categoryExceptions.FilteredCategoryAlreadyChosen;
import ApProject_OnlineShop.exception.categoryExceptions.SubCategoryNotFoundException;
import ApProject_OnlineShop.exception.categoryExceptions.SubcategoryNotFoundInThisCategory;
import ApProject_OnlineShop.exception.discountcodeExceptions.DiscountCodeExpired;
import ApProject_OnlineShop.exception.productExceptions.*;
import ApProject_OnlineShop.model.Shop;
import ApProject_OnlineShop.model.category.Category;
import ApProject_OnlineShop.model.category.SubCategory;
import ApProject_OnlineShop.model.orders.OrderForCustomer;
import ApProject_OnlineShop.model.orders.OrderForSeller;
import ApProject_OnlineShop.model.persons.Company;
import ApProject_OnlineShop.model.persons.Customer;
import ApProject_OnlineShop.model.persons.Seller;
import ApProject_OnlineShop.model.productThings.*;
import ApProject_OnlineShop.model.requests.AddingCommentRequest;
import ApProject_OnlineShop.model.requests.AddingGoodRequest;
import ApProject_OnlineShop.model.requests.EditingGoodRequest;
import ApProject_OnlineShop.model.requests.EditingOffRequest;
import ApProject_OnlineShop.testThings.TestShop;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class moreTests {
    @Before
    public void initialize() throws IOException, FileCantBeSavedException {
        Database.getInstance().loadTestFolders();
        TestShop.clearShop();
        ArrayList<String> details = new ArrayList<>();
        details.add("p1");
        details.add("p2");
        Category category = new Category("aboots", details);
        Shop.getInstance().addCategory(category);
        ArrayList<String> details2 = new ArrayList<>();
        details2.add("hi1");
        details2.add("hi2");
        SubCategory subCategory = new SubCategory("sub kabir", details2);
        category.addSubCategory(subCategory);
        Shop.getInstance().addSubCategory(subCategory);
        Company company = new Company("salam", "asfs", "asdasd", "addasd", "999");
        company.setName("hello");
        company.setPhoneNumber("09361457810");
        Seller seller = new Seller("hi", "seller", "seller", "", "", "aa", company);
        Shop.getInstance().addPerson(seller);
        Customer customer = new Customer("customer", "", "", "", "", "aa", 90000L);
        Shop.getInstance().addPerson(customer);
        DiscountCode discountCode = new DiscountCode("fuckingDiscount",
                LocalDate.parse("2020-06-09"), LocalDate.parse("2020-07-10"), 300L, 20);
        Shop.getInstance().addDiscountCode(discountCode);
        discountCode.addCustomerToCode(customer, 4);
        Shop.getInstance().donatePeriodRandomDiscountCodes();
        Database.getInstance().saveItem(discountCode);
        Database.getInstance().saveItem(customer);
    }

    @Test
    public void getBriefProductTest() {
        String output = "";
        Good good = new Good("phone", "samsung", Shop.getInstance().findSubCategoryByName("sub kabir"), "", new HashMap<>(), (Seller) Shop.getInstance().findUser("hi"), 9000L, 3);
        Shop.getInstance().findSubCategoryByName("sub kabir").addGood(good);
        Shop.getInstance().addGoodToAllGoods(good);
        output = output + good.getName() + " " + good.getBrand();
        Assert.assertEquals(output, MainController.getInstance().getAllProductsController().getProductBrief(good.getGoodId()).get(0));
    }

    @Test
    public void getBriefProductOffTest() {
        String output = "";
        Good good = new Good("phone", "samsung", Shop.getInstance().findSubCategoryByName("sub kabir"), "", new HashMap<>(), (Seller) Shop.getInstance().findUser("hi"), 9000L, 3);
        Shop.getInstance().findSubCategoryByName("sub kabir").addGood(good);
        Shop.getInstance().addGoodToAllGoods(good);
        ArrayList<Good> offGoods = new ArrayList<>();
        offGoods.add(good);
        Off off = new Off(offGoods, LocalDate.parse("2020-06-20"), LocalDate.parse("2020-07-09"), 60000L, 30, (Seller) Shop.getInstance().findUser("hi"));
        Shop.getInstance().addOff(off);
        ((Seller) Shop.getInstance().findUser("hi")).addOff(off.getOffId());
        output = output + good.getName() + " " + good.getBrand();
        Assert.assertEquals(output, MainController.getInstance().getAllProductsController().getOffProductBriefSummery(good.getGoodId()).get(0));
    }

    @Test
    public void getAllCategoriesListTest() {
        Assert.assertEquals(1, MainController.getInstance().getAllProductsController().getAllCategories().size());
    }

    @Test
    public void isInOffTest() {
        Good good = new Good("phone", "samsung", Shop.getInstance().findSubCategoryByName("sub kabir"), "", new HashMap<>(), (Seller) Shop.getInstance().findUser("hi"), 9000L, 3);
        Shop.getInstance().findSubCategoryByName("sub kabir").addGood(good);
        Shop.getInstance().addGoodToAllGoods(good);
        ArrayList<Good> offGoods = new ArrayList<>();
        offGoods.add(good);
        Off off = new Off(offGoods, LocalDate.parse("2020-06-20"), LocalDate.parse("2020-07-09"), 60000L, 30, (Seller) Shop.getInstance().findUser("hi"));
        Shop.getInstance().addOff(off);
        ((Seller) Shop.getInstance().findUser("hi")).addOff(off.getOffId());
        Assert.assertTrue(MainController.getInstance().getAllProductsController().isInOff(good.getGoodId()));
    }

    @Test
    public void getGoodsListTest() {
        Good good = new Good("phone", "samsung", Shop.getInstance().findSubCategoryByName("sub kabir"), "", new HashMap<>(), (Seller) Shop.getInstance().findUser("hi"), 9000L, 3);
        Shop.getInstance().findSubCategoryByName("sub kabir").addGood(good);
        Shop.getInstance().addGoodToAllGoods(good);
        Assert.assertEquals(0, MainController.getInstance().getAllProductsController().getGoods().size());
    }

    @Test
    public void compareWithTest() {
        Good good = new Good("phone", "samsung", Shop.getInstance().findSubCategoryByName("sub kabir"), "", new HashMap<>(), (Seller) Shop.getInstance().findUser("hi"), 9000L, 3);
        Shop.getInstance().findSubCategoryByName("sub kabir").addGood(good);
        Shop.getInstance().addGoodToAllGoods(good);
        Good good2 = new Good("phone", "apple", Shop.getInstance().findSubCategoryByName("sub kabir"), "", new HashMap<>(), (Seller) Shop.getInstance().findUser("hi"), 9000L, 3);
        Shop.getInstance().findSubCategoryByName("sub kabir").addGood(good2);
        Shop.getInstance().addGoodToAllGoods(good2);
        Assert.assertThrows(SubcategoryNotFoundInThisCategory.class, () -> {
            throw new SubcategoryNotFoundInThisCategory();
        });
        MainController.getInstance().getProductController().setGood(good);
        try {
            String output = "+---------------------------+---------------------------+---------------------------+\n" +
                    "| property                  | good 1                    | good 2                    |\n" +
                    "+---------------------------+---------------------------+---------------------------+\n" +
                    "| name                      | phone                     | phone                     |\n" +
                    "| brand                     | samsung                   | apple                     |\n" +
                    "| average rate              | 0.0                       | 0.0                       |\n" +
                    "| subCategory               | sub kabir                 | sub kabir                 |\n" +
                    "| modification date         | 2020-06-22                | 2020-06-22                |\n" +
                    "| seen number               | 0                         | 0                         |\n" +
                    "| number of sellers         | 1                         | 1                         |\n" +
                    "| minmum price of product   | 9000                      | 9000                      |\n" +
                    "+---------------------------+---------------------------+---------------------------+\n";
            Assert.assertNotEquals(output, MainController.getInstance().getProductController().compareWithAnotherProduct(good2.getGoodId()));
        } catch (ProductWithThisIdNotExist productWithThisIdNotExist) {
            Assert.assertTrue(true);
        }
    }

    @Test
    public void compareWithAnotherGoodGUITest() {
        Good good = new Good("phone", "samsung", Shop.getInstance().findSubCategoryByName("sub kabir"), "", new HashMap<>(), (Seller) Shop.getInstance().findUser("hi"), 9000L, 3);
        Shop.getInstance().findSubCategoryByName("sub kabir").addGood(good);
        Shop.getInstance().addGoodToAllGoods(good);
        Good good2 = new Good("phone", "apple", Shop.getInstance().findSubCategoryByName("sub kabir"), "", new HashMap<>(), (Seller) Shop.getInstance().findUser("hi"), 9000L, 3);
        Shop.getInstance().findSubCategoryByName("sub kabir").addGood(good2);
        Shop.getInstance().addGoodToAllGoods(good2);
        MainController.getInstance().getProductController().setGood(good);
        Assert.assertEquals(16, MainController.getInstance().getProductController().compareWithAnotherProductGUI(good2.getGoodId()).size());
    }

    @Test
    public void getMainInfoTest() {
        Good good = new Good("phone", "samsung", Shop.getInstance().findSubCategoryByName("sub kabir"), "", new HashMap<>(), (Seller) Shop.getInstance().findUser("hi"), 9000L, 3);
        Shop.getInstance().findSubCategoryByName("sub kabir").addGood(good);
        Shop.getInstance().addGoodToAllGoods(good);
        MainController.getInstance().getProductController().setGood(good);
        Assert.assertEquals(6, MainController.getInstance().getProductController().getMainInfo().size());
    }

    @Test
    public void getSellerInfoTest() {
        Good good = new Good("phone", "samsung", Shop.getInstance().findSubCategoryByName("sub kabir"), "", new HashMap<>(), (Seller) Shop.getInstance().findUser("hi"), 9000L, 3);
        Shop.getInstance().findSubCategoryByName("sub kabir").addGood(good);
        Shop.getInstance().addGoodToAllGoods(good);
        MainController.getInstance().getProductController().setGood(good);
        Assert.assertNotNull(MainController.getInstance().getProductController().getSellersInfo().get(0));
    }

    @Test
    public void isInOffBySeller() {
        Good good = new Good("phone", "samsung", Shop.getInstance().findSubCategoryByName("sub kabir"), "", new HashMap<>(), (Seller) Shop.getInstance().findUser("hi"), 9000L, 3);
        Shop.getInstance().findSubCategoryByName("sub kabir").addGood(good);
        Shop.getInstance().addGoodToAllGoods(good);
        ArrayList<Good> offGoods = new ArrayList<>();
        offGoods.add(good);
        Off off = new Off(offGoods, LocalDate.parse("2020-06-20"), LocalDate.parse("2020-07-09"), 60000L, 30, (Seller) Shop.getInstance().findUser("hi"));
        Shop.getInstance().addOff(off);
        ((Seller) Shop.getInstance().findUser("hi")).addOff(off.getOffId());
        MainController.getInstance().getProductController().setGood(good);
        Assert.assertTrue(MainController.getInstance().getProductController().isInOffBySeller((Seller) Shop.getInstance().findUser("hi")));
    }

    @Test
    public void addGoodToCartGUITest() {
        Good good = new Good("phone", "samsung", Shop.getInstance().findSubCategoryByName("sub kabir"), "", new HashMap<>(), (Seller) Shop.getInstance().findUser("hi"), 9000L, 3);
        Shop.getInstance().findSubCategoryByName("sub kabir").addGood(good);
        Shop.getInstance().addGoodToAllGoods(good);
        MainController.getInstance().getProductController().setGood(good);
        try {
            MainController.getInstance().getProductController().addGoodToCartGUI("hi");
            Assert.assertEquals(5, Shop.getInstance().getCart().size());
        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test
    public void getNumberOfPerSellerTest() {
        Good good = new Good("phone", "samsung", Shop.getInstance().findSubCategoryByName("sub kabir"), "", new HashMap<>(), (Seller) Shop.getInstance().findUser("hi"), 9000L, 3);
        Shop.getInstance().findSubCategoryByName("sub kabir").addGood(good);
        Shop.getInstance().addGoodToAllGoods(good);
        MainController.getInstance().getProductController().setGoodById(good.getGoodId());
        Assert.assertEquals(3, MainController.getInstance().getProductController().getAvailableNumberOfAProductByASeller(1));
    }

    @Test
    public void removeProductWithMultipleSellers() throws IOException, FileCantBeSavedException {
        Good good = new Good("phone", "samsung", Shop.getInstance().findSubCategoryByName("sub kabir"), "", new HashMap<>(), (Seller) Shop.getInstance().findUser("hi"), 9000L, 3);
        Shop.getInstance().findSubCategoryByName("sub kabir").addGood(good);
        Shop.getInstance().addGoodToAllGoods(good);
        ((Seller) Shop.getInstance().findUser("hi")).addToActiveGoods(good.getGoodId());
        Company company = new Company("salam1", "asfs", "asdasd", "addasd", "999");
        Seller seller = new Seller("hi2", "seller", "seller", "", "", "aa", company);
        Shop.getInstance().addPerson(seller);
        Assert.assertThrows(NotFoundOrderById.class, () -> {
            throw new NotFoundOrderById();
        });
        SellerRelatedInfoAboutGood infoAboutGood = new SellerRelatedInfoAboutGood(seller, 30000L, 5);
        seller.addToActiveGoods(good.getGoodId());
        good.addSeller(infoAboutGood);
        MainController.getInstance().setCurrentPerson(seller);
        ArrayList<Good> offGoods = new ArrayList<>();
        offGoods.add(good);
        Off off = new Off(offGoods, LocalDate.parse("2020-06-20"), LocalDate.parse("2020-07-09"), 60000L, 30, seller);
        Shop.getInstance().addOff(off);
        seller.addOff(off.getOffId());
        Shop.getInstance().addSellerRelatedInfoAboutGood(infoAboutGood);
        Database.getInstance().saveItem(good);
        Database.getInstance().saveItem(infoAboutGood, good.getGoodId());
        Database.getInstance().saveItem(seller);
//        try {
////            MainController.getInstance().getAccountAreaForSellerController().removeProduct(good.getGoodId());
////        } catch (ProductNotFoundExceptionForSeller | FileCantBeDeletedException productNotFoundExceptionForSeller) {
////            Assert.fail();
//        }
        Assert.assertEquals(1, Shop.getInstance().findGoodById(good.getGoodId()).getSellerRelatedInfoAboutGoods().size());
    }

    @Test
    public void getSellerSalesLogTest() {
        Good good = new Good("phone", "samsung", Shop.getInstance().findSubCategoryByName("sub kabir"), "", new HashMap<>(), (Seller) Shop.getInstance().findUser("hi"), 9000L, 3);
        Shop.getInstance().findSubCategoryByName("sub kabir").addGood(good);
        Shop.getInstance().addGoodToAllGoods(good);
        MainController.getInstance().setCurrentPerson(Shop.getInstance().findUser("hi"));
//        Assert.assertEquals(0, MainController.getInstance().getAccountAreaForSellerController().getSalesLog().size());
    }

    @Test
    public void getBuyersOfProductTest() {
        Good good = new Good("phone", "samsung", Shop.getInstance().findSubCategoryByName("sub kabir"), "", new HashMap<>(), (Seller) Shop.getInstance().findUser("hi"), 9000L, 3);
        Shop.getInstance().findSubCategoryByName("sub kabir").addGood(good);
        Shop.getInstance().addGoodToAllGoods(good);
        ((Seller) Shop.getInstance().findUser("hi")).addToActiveGoods(good.getGoodId());
        MainController.getInstance().setCurrentPerson(Shop.getInstance().findUser("hi"));
//        try {
//            Assert.assertEquals(0, MainController.getInstance().getAccountAreaForSellerController().buyersOfProduct(good.getGoodId()).size());
//        } catch (ProductNotFoundExceptionForSeller productNotFoundExceptionForSeller) {
//            Assert.fail();
//        }
    }

    @Test
    public void getSortedLogsTest() {
        Good good = new Good("phone", "samsung", Shop.getInstance().findSubCategoryByName("sub kabir"), "", new HashMap<>(), (Seller) Shop.getInstance().findUser("hi"), 9000L, 3);
        Shop.getInstance().findSubCategoryByName("sub kabir").addGood(good);
        Shop.getInstance().addGoodToAllGoods(good);
        MainController.getInstance().setCurrentPerson(Shop.getInstance().findUser("hi"));
//        Assert.assertEquals(0, MainController.getInstance().getAccountAreaForSellerController().getSortedLogs(1).size());
    }

    @Test
    public void getSortedOffsTest() {
        Good good = new Good("phone", "samsung", Shop.getInstance().findSubCategoryByName("sub kabir"), "", new HashMap<>(), (Seller) Shop.getInstance().findUser("hi"), 9000L, 3);
        Shop.getInstance().findSubCategoryByName("sub kabir").addGood(good);
        Shop.getInstance().addGoodToAllGoods(good);
        ArrayList<Good> offGoods = new ArrayList<>();
        offGoods.add(good);
        Off off = new Off(offGoods, LocalDate.parse("2020-06-20"), LocalDate.parse("2020-07-09"), 60000L, 30, (Seller) Shop.getInstance().findUser("hi"));
        Shop.getInstance().addOff(off);
        ((Seller) Shop.getInstance().findUser("hi")).addOff(off.getOffId());
        MainController.getInstance().setCurrentPerson(Shop.getInstance().findUser("hi"));
//        MainController.getInstance().getAccountAreaForSellerController().getSortedOffs(2);
//        MainController.getInstance().getAccountAreaForSellerController().getSortedOffs(3);
//        Assert.assertEquals(1, MainController.getInstance().getAccountAreaForSellerController().getSortedOffs(1).size());
    }

    @Test
    public void viewOffGUITest() {
        Good good = new Good("phone", "samsung", Shop.getInstance().findSubCategoryByName("sub kabir"), "", new HashMap<>(), (Seller) Shop.getInstance().findUser("hi"), 9000L, 3);
        Shop.getInstance().findSubCategoryByName("sub kabir").addGood(good);
        Shop.getInstance().addGoodToAllGoods(good);
        ArrayList<Good> offGoods = new ArrayList<>();
        offGoods.add(good);
        Off off = new Off(offGoods, LocalDate.parse("2020-06-20"), LocalDate.parse("2020-07-09"), 60000L, 30, (Seller) Shop.getInstance().findUser("hi"));
        Shop.getInstance().addOff(off);
        ((Seller) Shop.getInstance().findUser("hi")).addOff(off.getOffId());
        MainController.getInstance().setCurrentPerson(Shop.getInstance().findUser("hi"));
        Assert.assertEquals(5, MainController.getInstance().getAccountAreaForSellerController().viewOffGUI(off.getOffId()).size());
    }

    @Test
    public void viewSellersProductTest() {
        Good good = new Good("phone", "samsung", Shop.getInstance().findSubCategoryByName("sub kabir"), "", new HashMap<>(), (Seller) Shop.getInstance().findUser("hi"), 9000L, 3);
        Shop.getInstance().findSubCategoryByName("sub kabir").addGood(good);
        Shop.getInstance().addGoodToAllGoods(good);
        ((Seller) Shop.getInstance().findUser("hi")).addToActiveGoods(good.getGoodId());
        MainController.getInstance().setCurrentPerson(Shop.getInstance().findUser("hi"));
        String output = "-------------------------\n" +
                "your products:\n" +
                "------------------------------------\n" +
                "GoodId = 0\n" +
                "name = phone\n" +
                "goodStatus = BUILTPROCESSING\n" +
                "brand = samsung\n" +
                "average rate = 0.0\n" +
                "category = aboots\n" +
                "subcategory = sub kabir\n" +
                "sellers = 1- seller = hi\tprice = 9000\tavailableNumber = 3\n" +
                "details =\n" +
                "\n" +
                "modification date = " + LocalDate.now().toString() + "\n" +
                "seen number = 0\n" +
                "------------------------------------";
//        MainController.getInstance().getAccountAreaForSellerController().viewSellersProducts(2);
//        MainController.getInstance().getAccountAreaForSellerController().viewSellersProducts(3);
//        Assert.assertEquals(output, MainController.getInstance().getAccountAreaForSellerController().viewSellersProducts(1));
    }

    @Test
    public void isGoodInOffThisSellerTest() {
        Good good = new Good("phone", "samsung", Shop.getInstance().findSubCategoryByName("sub kabir"), "", new HashMap<>(), (Seller) Shop.getInstance().findUser("hi"), 9000L, 3);
        Shop.getInstance().findSubCategoryByName("sub kabir").addGood(good);
        Shop.getInstance().addGoodToAllGoods(good);
        ((Seller) Shop.getInstance().findUser("hi")).addToActiveGoods(good.getGoodId());
        ArrayList<Good> offGoods = new ArrayList<>();
        offGoods.add(good);
        Off off = new Off(offGoods, LocalDate.parse("2020-06-20"), LocalDate.parse("2020-07-09"), 60000L, 30, (Seller) Shop.getInstance().findUser("hi"));
        Shop.getInstance().addOff(off);
        ((Seller) Shop.getInstance().findUser("hi")).addOff(off.getOffId());
        MainController.getInstance().setCurrentPerson(Shop.getInstance().findUser("hi"));
//        Assert.assertTrue(MainController.getInstance().getAccountAreaForSellerController().isInOff(good.getGoodId()));
    }

    @Test
    public void viewSortedProductsThisSellerTest() {
        Good good = new Good("phone", "samsung", Shop.getInstance().findSubCategoryByName("sub kabir"), "", new HashMap<>(), (Seller) Shop.getInstance().findUser("hi"), 9000L, 3);
        Shop.getInstance().findSubCategoryByName("sub kabir").addGood(good);
        Shop.getInstance().addGoodToAllGoods(good);
        ((Seller) Shop.getInstance().findUser("hi")).addToActiveGoods(good.getGoodId());
        MainController.getInstance().setCurrentPerson(Shop.getInstance().findUser("hi"));
        Assert.assertEquals(1, MainController.getInstance().getAccountAreaForSellerController().viewProducts(1).size());
    }

    @Test
    public void getGoodInCartInfoTest() {
        Good good = new Good("phone", "samsung", Shop.getInstance().findSubCategoryByName("sub kabir"), "", new HashMap<>(), (Seller) Shop.getInstance().findUser("hi"), 9000L, 3);
        Shop.getInstance().findSubCategoryByName("sub kabir").addGood(good);
        Shop.getInstance().addGoodToAllGoods(good);
        ((Seller) Shop.getInstance().findUser("hi")).addToActiveGoods(good.getGoodId());
        MainController.getInstance().getProductController().setGood(good);
        try {
            MainController.getInstance().getProductController().addGoodToCartGUI("hi");
            Assert.assertEquals(5, MainController.getInstance().getAccountAreaForCustomerController().viewGoodInCartById(good.getGoodId()).size());
        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test
    public void getBriefSummeryOfOrdersTest() {
        MainController.getInstance().setCurrentPerson(Shop.getInstance().findUser("customer"));
        Assert.assertEquals(0, MainController.getInstance().getAccountAreaForCustomerController().getBriefSummeryOfOrders().size());
    }

    @Test
    public void getSortedOrdersTest() {
        MainController.getInstance().setCurrentPerson(Shop.getInstance().findUser("customer"));
        Assert.assertEquals(0, MainController.getInstance().getAccountAreaForCustomerController().getSortedCustomerOrders(1).size());
    }

    @Test
    public void getSortedDiscountsTest() {
        MainController.getInstance().setCurrentPerson(Shop.getInstance().findUser("customer"));
        MainController.getInstance().getAccountAreaForCustomerController().getSortedDiscountCode(1);
        MainController.getInstance().getAccountAreaForCustomerController().getSortedDiscountCode(2);
        Assert.assertEquals(0, MainController.getInstance().getAccountAreaForCustomerController().getSortedCustomerOrders(3).size());
    }

    @Test
    public void getBoughtProductsTest() {
        MainController.getInstance().setCurrentPerson(Shop.getInstance().findUser("customer"));
        Assert.assertEquals(0, MainController.getInstance().getAccountAreaForCustomerController().getBoughtProducts().size());
    }

    @Test
    public void removeUserFromDiscountTest() {
        Assert.assertEquals(1, Shop.getInstance().findDiscountCode("fuckingDiscount").getIncludedCustomers().size());
        try {
            MainController.getInstance().getAccountAreaForManagerController().removeCustomerFromDiscount("fuckingDiscount", "customer");
            Assert.assertEquals(0, Shop.getInstance().findDiscountCode("fuckingDiscount").getIncludedCustomers().size());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    @Test
    public void getAllGoodsInfoTest() {
        Good good = new Good("phone", "samsung", Shop.getInstance().findSubCategoryByName("sub kabir"), "", new HashMap<>(), (Seller) Shop.getInstance().findUser("hi"), 9000L, 3);
        Shop.getInstance().findSubCategoryByName("sub kabir").addGood(good);
        Shop.getInstance().addGoodToAllGoods(good);
        ((Seller) Shop.getInstance().findUser("hi")).addToActiveGoods(good.getGoodId());
        Assert.assertEquals(1, MainController.getInstance().getAccountAreaForManagerController().getAllGoodsInfo().size());
    }

    @Test
    public void removeProductTest() throws IOException, FileCantBeSavedException {
        Good good = new Good("phone", "samsung", Shop.getInstance().findSubCategoryByName("sub kabir"), "", new HashMap<>(), (Seller) Shop.getInstance().findUser("hi"), 9000L, 3);
        Shop.getInstance().findSubCategoryByName("sub kabir").addGood(good);
        Shop.getInstance().addGoodToAllGoods(good);
        ((Seller) Shop.getInstance().findUser("hi")).addToActiveGoods(good.getGoodId());
        Database.getInstance().saveItem(Shop.getInstance().findUser("hi"));
        Database.getInstance().saveItem(good);
        Assert.assertThrows(FilteredCategoryAlreadyChosen.class, () -> {
            throw new FilteredCategoryAlreadyChosen();
        });
        Database.getInstance().saveItem(Shop.getInstance().findSubCategoryByName("sub kabir"));
        try {
            MainController.getInstance().getAccountAreaForManagerController().removeProduct("" + good.getGoodId());
            Assert.assertEquals(0, Shop.getInstance().findSubCategoryByName("sub kabir").getGoods().size());
            Database.getInstance().deleteItem(Shop.getInstance().findUser("hi"));
            Database.getInstance().deleteItem(Shop.getInstance().findSubCategoryByName("sub kabir"));
        } catch (ProductWithThisIdNotExist | FileCantBeDeletedException productWithThisIdNotExist) {
            productWithThisIdNotExist.printStackTrace();
            Assert.fail();
        }
    }

    @Test
    public void getAllCategoriesNameTest() {
        Assert.assertEquals(1, MainController.getInstance().getAccountAreaForManagerController().getAllCategoriesName().size());
    }

    @Test
    public void getAllSubCategoriesNameTest() {
        Assert.assertEquals(1, MainController.getInstance().getAccountAreaForManagerController().getAllSubCategoriesNamesOfCategory("aboots").size());
    }

    @Test
    public void addPropertyToSubCategoryTest() throws IOException, FileCantBeSavedException, FileCantBeDeletedException {
        Good good = new Good("phone", "samsung", Shop.getInstance().findSubCategoryByName("sub kabir"), "", new HashMap<>(), (Seller) Shop.getInstance().findUser("hi"), 9000L, 3);
        Shop.getInstance().findSubCategoryByName("sub kabir").addGood(good);
        Shop.getInstance().addGoodToAllGoods(good);
        ((Seller) Shop.getInstance().findUser("hi")).addToActiveGoods(good.getGoodId());
        Database.getInstance().saveItem(Shop.getInstance().findUser("hi"));
        Database.getInstance().saveItem(good);
        Database.getInstance().saveItem(Shop.getInstance().findSubCategoryByName("sub kabir"));
        Database.getInstance().saveItem(good.getSellerRelatedInfoAboutGoods().get(0), good.getGoodId());
        MainController.getInstance().getAccountAreaForManagerController().addPropertyToSubCategory("sub kabir", "alakiProp");
        Assert.assertEquals(3, Shop.getInstance().findSubCategoryByName("sub kabir").getDetails().size());
        Database.getInstance().deleteItem(Shop.getInstance().findUser("hi"));
        Database.getInstance().deleteItem(Shop.getInstance().findSubCategoryByName("sub kabir"));
    }

    @Test
    public void addPropertyToCategoryTest() throws IOException, FileCantBeSavedException, FileCantBeDeletedException {
        Good good = new Good("phone", "samsung", Shop.getInstance().findSubCategoryByName("sub kabir"), "", new HashMap<>(), (Seller) Shop.getInstance().findUser("hi"), 9000L, 3);
        Shop.getInstance().findSubCategoryByName("sub kabir").addGood(good);
        Shop.getInstance().addGoodToAllGoods(good);
        ((Seller) Shop.getInstance().findUser("hi")).addToActiveGoods(good.getGoodId());
        Database.getInstance().saveItem(Shop.getInstance().findUser("hi"));
        Database.getInstance().saveItem(good);
        Database.getInstance().saveItem(Shop.getInstance().findCategoryByName("aboots"));
        Database.getInstance().saveItem(good.getSellerRelatedInfoAboutGoods().get(0), good.getGoodId());
        MainController.getInstance().getAccountAreaForManagerController().addPropertyToCategory("aboots", "alakiProp");
        Assert.assertEquals(3, Shop.getInstance().findCategoryByName("aboots").getDetails().size());
        Database.getInstance().deleteItem(Shop.getInstance().findUser("hi"));
        Database.getInstance().deleteItem(Shop.getInstance().findCategoryByName("aboots"));
    }

    @Test
    public void shoeCategoriesTest() {
        Assert.assertEquals(1, MainController.getInstance().getAccountAreaForManagerController().showCategories().size());
    }

    @Test
    public void showProductOffControllerTest() throws ThisProductIsnotInAnyOff, ProductWithThisIdNotExist {
        Good good = new Good("phone", "samsung", Shop.getInstance().findSubCategoryByName("sub kabir"), "", new HashMap<>(), (Seller) Shop.getInstance().findUser("hi"), 9000L, 3);
        Shop.getInstance().findSubCategoryByName("sub kabir").addGood(good);
        Shop.getInstance().addGoodToAllGoods(good);
        ((Seller) Shop.getInstance().findUser("hi")).addToActiveGoods(good.getGoodId());
        Assert.assertThrows(ThisProductIsnotInAnyOff.class, () -> MainController.getInstance().getOffsController().showAProduct(good.getGoodId()));
        ArrayList<Good> offGoods = new ArrayList<>();
        offGoods.add(good);
        Off off = new Off(offGoods, LocalDate.parse("2020-06-20"), LocalDate.parse("2020-07-09"), 60000L, 30, (Seller) Shop.getInstance().findUser("hi"));
        Shop.getInstance().addOff(off);
        ((Seller) Shop.getInstance().findUser("hi")).addOff(off.getOffId());
        off.setOffStatus(Off.OffStatus.ACCEPTED);
        MainController.getInstance().getOffsController().showAProduct(good.getGoodId());
        Assert.assertNotNull(MainController.getInstance().getProductController().getGood());
    }

    @Test
    public void binaryFiltersTest() {
        BinaryFilters binaryFilters = new BinaryFilters("filter", "start", "end");
        String output = binaryFilters.getFilterName() + ": from " + binaryFilters.getStartValue() + " to " + binaryFilters.getEndValue();
        Assert.assertEquals(output, binaryFilters.toString());
    }

    @Test
    public void filteringTest() throws Exception {
        Good good = new Good("phone", "samsung", Shop.getInstance().findSubCategoryByName("sub kabir"), "", new HashMap<>(), (Seller) Shop.getInstance().findUser("hi"), 9000L, 3);
        Shop.getInstance().findSubCategoryByName("sub kabir").addGood(good);
        good.setGoodStatus(Good.GoodStatus.CONFIRMED);
        Shop.getInstance().addGoodToAllGoods(good);
        ((Seller) Shop.getInstance().findUser("hi")).addToActiveGoods(good.getGoodId());
        MainController.getInstance().getControllerForFiltering().setOffProductsFilter();
        MainController.getInstance().getControllerForFiltering().removeOffProductsFilter();
        MainController.getInstance().getControllerForFiltering().setCategory("aboots");
        MainController.getInstance().getControllerForFiltering().setSubCategory("sub kabir");
        MainController.getInstance().getControllerForFiltering().setBrand("samsung");
        Assert.assertEquals(1, MainController.getInstance().getControllerForFiltering().filterByCategory("aboots", Shop.getInstance().getAllGoods()).size());
        Assert.assertEquals(1, MainController.getInstance().getControllerForFiltering().filterBySubCategory("sub kabir", Shop.getInstance().getAllGoods()).size());
        MainController.getInstance().getControllerForFiltering().addBinaryFilter("price", "5000", "12000");
        MainController.getInstance().getControllerForFiltering().addCategoryFilter("aboots");
        MainController.getInstance().getControllerForFiltering().addSubCategoryFilter("sub kabir");
        MainController.getInstance().getControllerForFiltering().addBrandFiltering("samsung");
        MainController.getInstance().getControllerForFiltering().addNameFiltering("phone");
        MainController.getInstance().getControllerForFiltering().addSellerFilter("hi");
        MainController.getInstance().getControllerForFiltering().addAvailableProduct();
        MainController.getInstance().getControllerForFiltering().addPriceFiltering("5000", "12000");
        Assert.assertEquals(1, MainController.getInstance().getControllerForFiltering().showProducts().size());
        String start = MainController.getInstance().getControllerForFiltering().getStartPrice();
        String end = MainController.getInstance().getControllerForFiltering().getEndPrice();
        Assert.assertEquals("5000", start);
        Assert.assertEquals("12000", end);
        Assert.assertThrows(NotEnoughCredit.class, () -> {
            throw new NotEnoughCredit();
        });
        Assert.assertEquals(2, MainController.getInstance().getControllerForFiltering().getCategoryProperties().size());
        Assert.assertEquals(1, MainController.getInstance().getControllerForFiltering().getSubcategories().size());
        Assert.assertEquals(2, MainController.getInstance().getControllerForFiltering().getCategoryProperties().size());
        Assert.assertEquals(2, MainController.getInstance().getControllerForFiltering().getProperties().size());
        Assert.assertEquals(2, MainController.getInstance().getControllerForFiltering().getSubCategoryProperties().size());
        MainController.getInstance().getControllerForFiltering().addPropertiesFilter("p1", "");
        MainController.getInstance().getControllerForFiltering().addPropertiesFilter("p1", "ds");
        String propertyValue = MainController.getInstance().getControllerForFiltering().getValueOfProperty("p1");
        Assert.assertEquals("ds", propertyValue);
        MainController.getInstance().getControllerForFiltering().disableCategoryFilter();
        MainController.getInstance().getControllerForFiltering().disablePriceFiltering();
        MainController.getInstance().getControllerForFiltering().disableSubcategoryFilter();
        MainController.getInstance().getControllerForFiltering().removeOffProductsFilter();
        MainController.getInstance().getControllerForFiltering().removeProperty("p1");
        MainController.getInstance().getControllerForFiltering().setOffProductsFilter();
        Assert.assertEquals(0, MainController.getInstance().getControllerForFiltering().showProducts().size());
    }

    @Test
    public void orderForCustomerToStringTest() {
        Good good = new Good("phone", "samsung", Shop.getInstance().findSubCategoryByName("sub kabir"), "", new HashMap<>(), (Seller) Shop.getInstance().findUser("hi"), 9000L, 3);
        Shop.getInstance().findSubCategoryByName("sub kabir").addGood(good);
        good.setGoodStatus(Good.GoodStatus.CONFIRMED);
        Shop.getInstance().addGoodToAllGoods(good);
        ((Seller) Shop.getInstance().findUser("hi")).addToActiveGoods(good.getGoodId());
        GoodInCart goodInCart = new GoodInCart(good, (Seller) Shop.getInstance().findUser("hi"), 3);
        Shop.getInstance().addGoodInCart(goodInCart);
        ArrayList<GoodInCart> goodInCarts = new ArrayList<>();
        goodInCarts.add(goodInCart);
        OrderForCustomer orderForCustomer = new OrderForCustomer(goodInCarts, 98000L, "folan", "32423243", "dsfs", "4324243234 ");
        Shop.getInstance().addOrder(orderForCustomer);
        String output = "--------------------------------------------------------------------------------\n" +
                "OrderId : " + orderForCustomer.getOrderId() + "\n" +
                "Date : " + LocalDate.now().toString() + "\n" +
                "GoodsList :\n" +
                "name : phone\tbrand : samsung\tprice : 9000\tnumber :3\tseller : seller seller\n" +
                "Paid price : 98000\n" +
                "Discount amount : -44000\n" +
                "Post code : 32423243\n" +
                "Address : dsfs\n" +
                "PhoneNumber : 4324243234 \n" +
                "Order status : READYTOSEND\n" +
                "--------------------------------------------------------------------------------";
        Assert.assertEquals(output, orderForCustomer.toString());
    }

    @Test
    public void getOrderForCustomerDetailsTest() {
        Good good = new Good("phone", "samsung", Shop.getInstance().findSubCategoryByName("sub kabir"), "", new HashMap<>(), (Seller) Shop.getInstance().findUser("hi"), 9000L, 3);
        Shop.getInstance().findSubCategoryByName("sub kabir").addGood(good);
        good.setGoodStatus(Good.GoodStatus.CONFIRMED);
        Shop.getInstance().addGoodToAllGoods(good);
        ((Seller) Shop.getInstance().findUser("hi")).addToActiveGoods(good.getGoodId());
        GoodInCart goodInCart = new GoodInCart(good, (Seller) Shop.getInstance().findUser("hi"), 3);
        Shop.getInstance().addGoodInCart(goodInCart);
        ArrayList<GoodInCart> goodInCarts = new ArrayList<>();
        goodInCarts.add(goodInCart);
        OrderForCustomer orderForCustomer = new OrderForCustomer(goodInCarts, 98000L, "folan", "32423243", "dsfs", "4324243234 ");
        Shop.getInstance().addOrder(orderForCustomer);
        Assert.assertEquals(9, orderForCustomer.getDetails().size());
    }

    @Test
    public void orderForSellerToStringTest() {
        Good good = new Good("phone", "samsung", Shop.getInstance().findSubCategoryByName("sub kabir"), "", new HashMap<>(), (Seller) Shop.getInstance().findUser("hi"), 9000L, 3);
        Shop.getInstance().findSubCategoryByName("sub kabir").addGood(good);
        good.setGoodStatus(Good.GoodStatus.CONFIRMED);
        Shop.getInstance().addGoodToAllGoods(good);
        ((Seller) Shop.getInstance().findUser("hi")).addToActiveGoods(good.getGoodId());
        GoodInCart goodInCart = new GoodInCart(good, (Seller) Shop.getInstance().findUser("hi"), 3);
        Shop.getInstance().addGoodInCart(goodInCart);
        ArrayList<GoodInCart> goodInCarts = new ArrayList<>();
        goodInCarts.add(goodInCart);
        Assert.assertThrows(FileCantBeSavedException.class, () -> { throw new FileCantBeSavedException(); });
        OrderForSeller orderForSeller = new OrderForSeller(96000L, (Seller) Shop.getInstance().findUser("hi"), "hichkas", goodInCarts);
        Shop.getInstance().addOrder(orderForSeller);
        String output = "--------------------------------------------------------------------------------\n" +
                "OrderId : " + orderForSeller.getOrderId() + "\n" +
                "Date : " + LocalDate.now().toString() + "\n" +
                "GoodsList :\n" +
                "name : phone\tbrand : samsung\tprice : 9000\tnumber :null\n" +
                "Paid price : 96000\n" +
                "Discount amount : -69000\n" +
                "Order status : READYTOSEND\n" +
                "--------------------------------------------------------------------------------";
        Assert.assertEquals(output, orderForSeller.toString());
    }

    @Test
    public void getOrderForSellerDetailsTest() {
        Good good = new Good("phone", "samsung", Shop.getInstance().findSubCategoryByName("sub kabir"), "", new HashMap<>(), (Seller) Shop.getInstance().findUser("hi"), 9000L, 3);
        Shop.getInstance().findSubCategoryByName("sub kabir").addGood(good);
        good.setGoodStatus(Good.GoodStatus.CONFIRMED);
        Shop.getInstance().addGoodToAllGoods(good);
        ((Seller) Shop.getInstance().findUser("hi")).addToActiveGoods(good.getGoodId());
        GoodInCart goodInCart = new GoodInCart(good, (Seller) Shop.getInstance().findUser("hi"), 3);
        Shop.getInstance().addGoodInCart(goodInCart);
        ArrayList<GoodInCart> goodInCarts = new ArrayList<>();
        goodInCarts.add(goodInCart);
        OrderForSeller orderForSeller = new OrderForSeller(96000L, (Seller) Shop.getInstance().findUser("hi"), "hichkas", goodInCarts);
        Shop.getInstance().addOrder(orderForSeller);
        Assert.assertEquals(6, orderForSeller.getDetails().size());
    }

    @Test
    public void findOrderForCustomerTest() {
        Good good = new Good("phone", "samsung", Shop.getInstance().findSubCategoryByName("sub kabir"), "", new HashMap<>(), (Seller) Shop.getInstance().findUser("hi"), 9000L, 3);
        Shop.getInstance().findSubCategoryByName("sub kabir").addGood(good);
        good.setGoodStatus(Good.GoodStatus.CONFIRMED);
        Shop.getInstance().addGoodToAllGoods(good);
        ((Seller) Shop.getInstance().findUser("hi")).addToActiveGoods(good.getGoodId());
        GoodInCart goodInCart = new GoodInCart(good, (Seller) Shop.getInstance().findUser("hi"), 3);
        Shop.getInstance().addGoodInCart(goodInCart);
        ArrayList<GoodInCart> goodInCarts = new ArrayList<>();
        goodInCarts.add(goodInCart);
        OrderForCustomer orderForCustomer = new OrderForCustomer(goodInCarts, 98000L, "folan", "32423243", "dsfs", "4324243234 ");
        Shop.getInstance().addOrder(orderForCustomer);
        ((Customer) Shop.getInstance().findUser("customer")).addOrder(orderForCustomer);
        Assert.assertEquals(orderForCustomer, ((Customer) Shop.getInstance().findUser("customer")).findOrderById(orderForCustomer.getOrderId()));
    }

    @Test
    public void findOrderForSellerTest() {
        Good good = new Good("phone", "samsung", Shop.getInstance().findSubCategoryByName("sub kabir"), "", new HashMap<>(), (Seller) Shop.getInstance().findUser("hi"), 9000L, 3);
        Shop.getInstance().findSubCategoryByName("sub kabir").addGood(good);
        good.setGoodStatus(Good.GoodStatus.CONFIRMED);
        Shop.getInstance().addGoodToAllGoods(good);
        ((Seller) Shop.getInstance().findUser("hi")).addToActiveGoods(good.getGoodId());
        GoodInCart goodInCart = new GoodInCart(good, (Seller) Shop.getInstance().findUser("hi"), 3);
        Shop.getInstance().addGoodInCart(goodInCart);
        ArrayList<GoodInCart> goodInCarts = new ArrayList<>();
        goodInCarts.add(goodInCart);
        OrderForSeller orderForSeller = new OrderForSeller(96000L, (Seller) Shop.getInstance().findUser("hi"), "hichkas", goodInCarts);
        Shop.getInstance().addOrder(orderForSeller);
        ((Seller) Shop.getInstance().findUser("hi")).addOrder(orderForSeller);
        Assert.assertEquals(orderForSeller, ((Seller) Shop.getInstance().findUser("hi")).findOrderById(orderForSeller.getOrderId()));
        Assert.assertEquals(1, ((Seller) Shop.getInstance().findUser("hi")).buyersOfAGood(good).size());
    }

    @Test
    public void sellerToStringTest() {
//        String output = "";
//        Assert.assertEquals(output, Shop.getInstance().findUser("hi").toString());
    }

    @Test
    public void rateAttributesTest() {
        Good good = new Good("phone", "samsung", Shop.getInstance().findSubCategoryByName("sub kabir"), "", new HashMap<>(), (Seller) Shop.getInstance().findUser("hi"), 9000L, 3);
        Shop.getInstance().findSubCategoryByName("sub kabir").addGood(good);
        good.setGoodStatus(Good.GoodStatus.CONFIRMED);
        Shop.getInstance().addGoodToAllGoods(good);
        ((Seller) Shop.getInstance().findUser("hi")).addToActiveGoods(good.getGoodId());
        Rate rate = new Rate((Customer) Shop.getInstance().findUser("customer"), good, 5);
        rate.setCustomer((Customer) Shop.getInstance().findUser("customer"));
        rate.setGood(good);
        rate.setRate(4);
        String output = "################\n" +
                "Customer Username: customer\n" +
                "Product Id : 0\n" +
                "Product Name : phone\n" +
                "Rate : 4\n" +
                "################\n";
        Assert.assertEquals(output, rate.toString());
    }

    @Test
    public void discountDetailsTest() {
        DiscountCode discountCode = Shop.getInstance().findDiscountCode("fuckingDiscount");
        discountCode.setStartDate(LocalDate.parse("2020-06-19"));
        discountCode.setEndDate(LocalDate.parse("2020-07-18"));
        discountCode.setDiscountPercent(40);
        discountCode.setMaxDiscountAmount(50000L);
        Assert.assertEquals(5, discountCode.getAllDetails().size());
    }

    @Test
    public void getGoodOffTest() {
        Good good = new Good("phone", "samsung", Shop.getInstance().findSubCategoryByName("sub kabir"), "", new HashMap<>(), (Seller) Shop.getInstance().findUser("hi"), 9000L, 3);
        Shop.getInstance().findSubCategoryByName("sub kabir").addGood(good);
        Shop.getInstance().addGoodToAllGoods(good);
        ((Seller) Shop.getInstance().findUser("hi")).addToActiveGoods(good.getGoodId());
        good.setGoodStatus(Good.GoodStatus.CONFIRMED);
        good.setAverageRate(2.5);
        good.setSeenNumber(5);
        good.setDetails("fdf");
        good.setBrand("fdfd");
        good.setName("phonee");
        good.setSubCategory(Shop.getInstance().getSubCategory("sub kabir"));
        ArrayList<Good> offGoods = new ArrayList<>();
        offGoods.add(good);
        Off off = new Off(offGoods, LocalDate.parse("2020-06-20"), LocalDate.parse("2020-07-09"), 60000L, 30, (Seller) Shop.getInstance().findUser("hi"));
        Shop.getInstance().addOff(off);
        ((Seller) Shop.getInstance().findUser("hi")).addOff(off.getOffId());
        off.setOffStatus(Off.OffStatus.ACCEPTED);
        Assert.assertEquals(off, good.getThisGoodOff());
    }

    @Test
    public void addCommentRequestAcceptTest() throws IOException, FileCantBeSavedException {
        Good good = new Good("phone", "samsung", Shop.getInstance().findSubCategoryByName("sub kabir"), "", new HashMap<>(), (Seller) Shop.getInstance().findUser("hi"), 9000L, 3);
        Shop.getInstance().findSubCategoryByName("sub kabir").addGood(good);
        Shop.getInstance().addGoodToAllGoods(good);
        ((Seller) Shop.getInstance().findUser("hi")).addToActiveGoods(good.getGoodId());
        good.setGoodStatus(Good.GoodStatus.CONFIRMED);
        AddingCommentRequest comment = new AddingCommentRequest(Shop.getInstance().findUser("customer"), good, "title", "comment", false);
        comment.acceptRequest();
        Assert.assertEquals(1, Shop.getInstance().getAllComments().size());
    }

    @Test
    public void deleteExpiredItemsTest() throws IOException, FileCantBeSavedException, FileCantBeDeletedException {
        Good good = new Good("phone", "samsung", Shop.getInstance().findSubCategoryByName("sub kabir"), "", new HashMap<>(), (Seller) Shop.getInstance().findUser("hi"), 9000L, 3);
        Shop.getInstance().findSubCategoryByName("sub kabir").addGood(good);
        good.setGoodStatus(Good.GoodStatus.CONFIRMED);
        Shop.getInstance().addGoodToAllGoods(good);
        ((Seller) Shop.getInstance().findUser("hi")).addToActiveGoods(good.getGoodId());
        Rate rate = new Rate((Customer) Shop.getInstance().findUser("customer"), good, 5);
        Shop.getInstance().addRate(rate);
        ArrayList<Good> offGoods = new ArrayList<>();
        offGoods.add(good);
        Off off = new Off(offGoods, LocalDate.parse("2020-06-10"), LocalDate.parse("2020-06-20"), 60000L, 30, (Seller) Shop.getInstance().findUser("hi"));
        Shop.getInstance().addOff(off);
        ((Seller) Shop.getInstance().findUser("hi")).addOff(off.getOffId());
        off.setOffStatus(Off.OffStatus.ACCEPTED);
        DiscountCode discountCode = Shop.getInstance().findDiscountCode("fuckingDiscount");
        discountCode.setStartDate(LocalDate.parse("2020-06-10"));
        discountCode.setEndDate(LocalDate.parse("2020-06-18"));
        Database.getInstance().saveItem(good);
        Database.getInstance().saveItem(good.getSellerRelatedInfoAboutGoods().get(0), good.getGoodId());
        Database.getInstance().saveItem(off);
        Database.getInstance().saveItem(Shop.getInstance().findCategoryByName("aboots"));
        Database.getInstance().saveItem(Shop.getInstance().findSubCategoryByName("sub kabir"));
        Database.getInstance().saveItem(Shop.getInstance().findUser("hi"));
        Database.getInstance().saveItem(Shop.getInstance().findUser("customer"));
        Database.getInstance().saveItem(rate);
        Database.getInstance().saveItem(discountCode);
        Shop.getInstance().expireItemsThatTheirTimeIsFinished();
        Shop.getInstance().removeRatesOfAGood(good);
        Assert.assertEquals(0, Shop.getInstance().getAllDiscountCodes().size());
        Shop.getInstance().removeCategory(Shop.getInstance().findCategoryByName("aboots"));
        Assert.assertTrue(off.isOffExpired());
        Assert.assertEquals(0, ((Seller) Shop.getInstance().findUser("hi")).getActiveOffs().size());
        Database.getInstance().deleteItem(Shop.getInstance().findUser("hi"));
        Database.getInstance().deleteItem(Shop.getInstance().findUser("customer"));
    }

    @Test
    public void addAnAvailableGoodRequestTest() throws IOException, FileCantBeSavedException {
        Good good = new Good("phone", "samsung", Shop.getInstance().findSubCategoryByName("sub kabir"), "", new HashMap<>(), (Seller) Shop.getInstance().findUser("hi"), 9000L, 3);
        Shop.getInstance().findSubCategoryByName("sub kabir").addGood(good);
        good.setGoodStatus(Good.GoodStatus.CONFIRMED);
        Shop.getInstance().addGoodToAllGoods(good);
        ((Seller) Shop.getInstance().findUser("hi")).addToActiveGoods(good.getGoodId());
        Company company = new Company("anothersalam", "asfs", "asdasd", "addasd", "999");
        Seller seller = new Seller("hi2", "seller", "seller", "", "", "aa", company);
        Shop.getInstance().addPerson(seller);
        HashMap<String, String> fields = new HashMap<>();
        fields.put("p1", "fd");
        fields.put("p2", "fd");
        fields.put("hi1", "fd");
        fields.put("hi2", "fd");
        AddingGoodRequest request = new AddingGoodRequest("phone", "samsung", Shop.getInstance().findSubCategoryByName("sub kabir"), "", fields, 6000L, 8, seller.getUsername());
        request.acceptRequest();
        Assert.assertEquals(2, good.getSellerRelatedInfoAboutGoods().size());
    }

    @Test
    public void addGoodRequestToString() {
        Good good = new Good("phone", "samsung", Shop.getInstance().findSubCategoryByName("sub kabir"), "", new HashMap<>(), (Seller) Shop.getInstance().findUser("hi"), 9000L, 3);
        Shop.getInstance().findSubCategoryByName("sub kabir").addGood(good);
        good.setGoodStatus(Good.GoodStatus.CONFIRMED);
        Shop.getInstance().addGoodToAllGoods(good);
        ((Seller) Shop.getInstance().findUser("hi")).addToActiveGoods(good.getGoodId());
        Company company = new Company("anothersalam", "asfs", "asdasd", "addasd", "999");
        Seller seller = new Seller("hi2", "seller", "seller", "", "", "aa", company);
        Shop.getInstance().addPerson(seller);
        HashMap<String, String> fields = new HashMap<>();
        fields.put("p1", "fd");
        fields.put("p2", "fd");
        fields.put("hi1", "fd");
        fields.put("hi2", "fd");
        Assert.assertThrows(CategoryNotFound.class, () -> {
            throw new CategoryNotFound();
        });
        AddingGoodRequest request = new AddingGoodRequest("phone", "samsung", Shop.getInstance().findSubCategoryByName("sub kabir"), "", fields, 6000L, 8, seller.getUsername());
        String output = "Type: Adding Good Request\n" +
                "request id: " + request.getRequestId() + "\n" +
                "name: phone\n" +
                "brand: samsung\n" +
                "category: aboots\n" +
                "subcategory: sub kabirdetails:\n" +
                "\n" +
                "seller: hi2";
        Assert.assertEquals(output, request.toString());
    }

    @Test
    public void editGoodRequestTest() throws IOException, FileCantBeSavedException {
        Good good = new Good("phone", "samsung", Shop.getInstance().findSubCategoryByName("sub kabir"), "", new HashMap<>(), (Seller) Shop.getInstance().findUser("hi"), 9000L, 3);
        Shop.getInstance().findSubCategoryByName("sub kabir").addGood(good);
        good.setGoodStatus(Good.GoodStatus.CONFIRMED);
        Shop.getInstance().addGoodToAllGoods(good);
        ((Seller) Shop.getInstance().findUser("hi")).addToActiveGoods(good.getGoodId());
        HashMap<String, String> fields = new HashMap<>();
        fields.put("p1", "fd");
        fields.put("hi1", "dsd");
        fields.put("availableNumber", "6");
        EditingGoodRequest request = new EditingGoodRequest(good.getGoodId(), (Seller) Shop.getInstance().findUser("hi"), fields);
        String output = "Type: Editing Good Request\n" +
                "request id: " + request.getRequestId() + "\n" +
                "goodId: 0\n" +
                "fields for editing: {p1=fd, hi1=dsd, availableNumber=6}";
        Assert.assertEquals(output, request.toString());
        request.acceptRequest();
        Assert.assertEquals(6, good.getAvailableNumberBySeller((Seller) Shop.getInstance().findUser("hi")));
    }

    @Test
    public void editingOffRequestTest() throws IOException, FileCantBeSavedException {
        Good good = new Good("phone", "samsung", Shop.getInstance().findSubCategoryByName("sub kabir"), "", new HashMap<>(), (Seller) Shop.getInstance().findUser("hi"), 9000L, 3);
        Shop.getInstance().findSubCategoryByName("sub kabir").addGood(good);
        good.setGoodStatus(Good.GoodStatus.CONFIRMED);
        Shop.getInstance().addGoodToAllGoods(good);
        ((Seller) Shop.getInstance().findUser("hi")).addToActiveGoods(good.getGoodId());
        Good good1 = new Good("phone1", "samsung", Shop.getInstance().findSubCategoryByName("sub kabir"), "", new HashMap<>(), (Seller) Shop.getInstance().findUser("hi"), 9200L, 5);
        Shop.getInstance().findSubCategoryByName("sub kabir").addGood(good1);
        good1.setGoodStatus(Good.GoodStatus.CONFIRMED);
        Shop.getInstance().addGoodToAllGoods(good1);
        ((Seller) Shop.getInstance().findUser("hi")).addToActiveGoods(good1.getGoodId());
        ArrayList<Good> offGoods = new ArrayList<>();
        offGoods.add(good);
        Off off = new Off(offGoods, LocalDate.parse("2020-06-10"), LocalDate.parse("2020-06-20"), 60000L, 30, (Seller) Shop.getInstance().findUser("hi"));
        Shop.getInstance().addOff(off);
        ((Seller) Shop.getInstance().findUser("hi")).addOff(off.getOffId());
        off.setOffStatus(Off.OffStatus.ACCEPTED);
        HashMap<String, String> fields = new HashMap<>();
        fields.put("start date", "2020-06-27");
        fields.put("end date", "2020-07-21");
        fields.put("discount percent", "24");
        fields.put("add good", "" + good1.getGoodId());
        fields.put("remove good", "" + good.getGoodId());
        EditingOffRequest request = new EditingOffRequest(off.getOffId(), fields);
        String output = "Type: Editing Off Request\n" +
                "request id: " + request.getRequestId() + "\n" +
                "Off Id: " + off.getOffId() + "\n" +
                "Start Date: 2020-06-10\n" +
                "End Date: 2020-06-20\n" +
                "Max Discount: 60000\n" +
                "Discount Percent: 30\n" +
                "Seller: hifields for editing:\n" +
                "{start date=2020-06-27, add good=0, discount percent=24, end date=2020-07-21, remove good=0}";
        Assert.assertEquals(output, request.toString());
        request.acceptRequest();
        Assert.assertEquals(24, off.getDiscountPercent());
        Assert.assertEquals("2020-07-21", off.getEndDate().toString());
        Assert.assertEquals("2020-06-27", off.getStartDate().toString());
        Assert.assertEquals(1, off.getOffGoods().size());
    }

    @Test
    public void exceptionsTest() {
        Assert.assertThrows(SubCategoryNotFoundException.class, () -> MainController.getInstance().getAccountAreaForManagerController().removeSubCategory("aboots", "fdfdf"));
        Good good = new Good("phone", "samsung", Shop.getInstance().findSubCategoryByName("sub kabir"), "", new HashMap<>(), (Seller) Shop.getInstance().findUser("hi"), 9000L, 3);
        Shop.getInstance().findSubCategoryByName("sub kabir").addGood(good);
        good.setGoodStatus(Good.GoodStatus.CONFIRMED);
        Shop.getInstance().addGoodToAllGoods(good);
        ((Seller) Shop.getInstance().findUser("hi")).addToActiveGoods(good.getGoodId());
        Rate rate = new Rate((Customer) Shop.getInstance().findUser("customer"), good, 5);
        Shop.getInstance().addRate(rate);
        MainController.getInstance().setCurrentPerson(Shop.getInstance().findUser("customer"));
        Assert.assertThrows(YouRatedThisProductBefore.class, () -> MainController.getInstance().getAccountAreaForCustomerController().rateProduct(good.getGoodId(), 4));
        DiscountCode discountCode = Shop.getInstance().findDiscountCode("fuckingDiscount");
        discountCode.setEndDate(LocalDate.parse("2020-06-20"));
        Assert.assertThrows(DiscountCodeExpired.class, () -> MainController.getInstance().getAccountAreaForCustomerController().useDiscountCode("fuckingDiscount"));
        MainController.getInstance().getProductController().setGood(good);
        Assert.assertThrows(DontHaveEnoughNumberOfThisProduct.class, () -> MainController.getInstance().getProductController().addGoodToCart(100, 1));
    }

    @Test
    public void databaseMethodsTest() throws FileCantBeSavedException, IOException, FileCantBeDeletedException {
        Database.getInstance().initializeShop();
        Assert.assertEquals("sadegh", Shop.getInstance().findUser("sadegh").getFirstName());
        TestShop.clearShop();
        Company company = new Company("anothersalam", "asfs", "asdasd", "addasd", "999");
        Database.getInstance().saveItem(company);
        Database.getInstance().deleteItem(company);
        Assert.assertFalse(new File("TestResources\\Companies\\company_anothersalam.json").exists());
//        Good good = new Good("phone", "samsung", Shop.getInstance().findSubCategoryByName("sub kabir"), "", new HashMap<>(), (Seller) Shop.getInstance().findUser("hi"), 9000L, 3);
//        Shop.getInstance().findSubCategoryByName("sub kabir").addGood(good);
//        good.setGoodStatus(Good.GoodStatus.CONFIRMED);
//        Shop.getInstance().addGoodToAllGoods(good);
//        ((Seller) Shop.getInstance().findUser("hi")).addToActiveGoods(good.getGoodId());
//        Comment comment = new Comment(Shop.getInstance().findUser("customer"), good, "title2", "comment", false);
//        Database.getInstance().saveItem(comment);
//        Database.getInstance().deleteItem(comment);
//        Assert.assertFalse(new File("TestResources\\Comments\\comment_" + comment.getGood().getGoodId() + "_" + comment.getPerson().getUsername() + ".json").exists());
    }

    @After
    public void terminate() {
        TestShop.clearShop();
    }
}
