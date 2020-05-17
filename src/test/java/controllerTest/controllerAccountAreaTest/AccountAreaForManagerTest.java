package controllerTest.controllerAccountAreaTest;

import controller.MainController;
import exception.FileCantBeDeletedException;
import exception.FileCantBeSavedException;
import exception.PropertyNotFoundException;
import exception.RequestNotFoundException;
import exception.productExceptions.FieldCantBeEditedException;
import exception.userExceptions.UsernameNotFoundException;
import exception.discountcodeExceptions.DiscountCodeCantBeEditedException;
import exception.discountcodeExceptions.DiscountCodeCantCreatedException;
import exception.discountcodeExceptions.DiscountCodeNotFoundException;
import model.Shop;
import model.category.Category;
import model.category.SubCategory;
import model.database.Database;
import model.persons.Customer;
import model.persons.Manager;
import model.persons.Seller;
import model.productThings.DiscountCode;
import model.requests.RegisteringSellerRequest;
import org.junit.*;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

public class AccountAreaForManagerTest {
    static ArrayList<String> fields;

    @BeforeClass
    public static void initializeVariables() {
        fields = new ArrayList<>();
        fields.add("RandomDiscount");
        fields.add("2020-07-01");
        fields.add("2020-08-01");
        fields.add("9000000");
        fields.add("30");
        Shop.getInstance().getAllPersons().add(new Customer("sadegh", "sadegh", "majidi", "sadegh0211380@gmail.com", "09361457810", "pass", 1500L));
        Shop.getInstance().getAllPersons().add(new Manager("XxXxXx", "aboots", "zzzzz", "aboot@gmail.com", "06065656060", "pass2"));
        Shop.getInstance().addRequest(new RegisteringSellerRequest(new Seller("fgf", "fgfg", "fgfg", "gfgg", "fgfg", "fgfgf",null)));
        ArrayList<String> details = new ArrayList<>();
        details.add("hich1");
        details.add("hich2");
        Category category = new Category("ashghal", details);
        SubCategory subCategory = new SubCategory("subAshghal", details);
        category.addSubCategory(subCategory);
        Shop.getInstance().addCategory(category);
    }

    @Test
    public void CreateDiscountCodeExceptionTest()
            throws DiscountCodeCantCreatedException, IOException, FileCantBeSavedException {
        MainController.getInstance().getAccountAreaForManagerController().createNewDiscountCode(fields);
        DiscountCode discountCode = Shop.getInstance().findDiscountCode("RandomDiscount");
        Assert.assertNotNull(discountCode);
        fields.add(0, "asdfghjklasdfghjk");
        Assert.assertThrows("can not create discount code because code length is incorrect.", DiscountCodeCantCreatedException.class,
                () -> MainController.getInstance().getAccountAreaForManagerController().createNewDiscountCode(fields));
        fields.add(0, "RandomDiscount");
        fields.add(1, "2019-08-01");
        Assert.assertThrows("can not create discount code because start date is incorrect.", DiscountCodeCantCreatedException.class,
                () -> MainController.getInstance().getAccountAreaForManagerController().createNewDiscountCode(fields));
        fields.add(1, "2020-07-01");
        fields.add(2, "2020-06-20");
        Assert.assertThrows("can not create discount code because end date is incorrect.", DiscountCodeCantCreatedException.class,
                () -> MainController.getInstance().getAccountAreaForManagerController().createNewDiscountCode(fields));
        fields.add(2, "2020-08-01");
        fields.add(4, "150");
        Assert.assertThrows("can not create discount code because discount percent is incorrect.", DiscountCodeCantCreatedException.class,
                () -> MainController.getInstance().getAccountAreaForManagerController().createNewDiscountCode(fields));
    }

    @Test
    public void addCustomerToDiscountCodeTest()
            throws DiscountCodeCantCreatedException, IOException, FileCantBeSavedException, DiscountCodeNotFoundException, UsernameNotFoundException {
        MainController.getInstance().getAccountAreaForManagerController().createNewDiscountCode(fields);
        Assert.assertThrows("discount code not found.", DiscountCodeNotFoundException.class,
                () -> MainController.getInstance().getAccountAreaForManagerController().addIncludedCustomerToDiscountCode("mdkedknkede", "sadegh", "4"));
        Assert.assertThrows("username not found!", UsernameNotFoundException.class,
                () -> MainController.getInstance().getAccountAreaForManagerController().addIncludedCustomerToDiscountCode("RandomDiscount", "amooo", "4"));
        Assert.assertThrows("can not create discount code because customer is incorrect.", DiscountCodeCantCreatedException.class,
                () -> MainController.getInstance().getAccountAreaForManagerController().addIncludedCustomerToDiscountCode("RandomDiscount", "XxXxXx", "4"));
        Assert.assertThrows("can not create discount code because number of use is incorrect.", DiscountCodeCantCreatedException.class,
                () -> MainController.getInstance().getAccountAreaForManagerController().addIncludedCustomerToDiscountCode("RandomDiscount", "sadegh", "44444444444444444444444444444444"));
        MainController.getInstance().getAccountAreaForManagerController().addIncludedCustomerToDiscountCode("RandomDiscount", "sadegh", "4");
    }

    @Test
    public void removeDiscountCodeTest()
            throws DiscountCodeCantCreatedException, DiscountCodeNotFoundException, IOException, FileCantBeSavedException, FileCantBeDeletedException {
        if(Shop.getInstance().findDiscountCode("RandomDiscount") == null)
            MainController.getInstance().getAccountAreaForManagerController().createNewDiscountCode(fields);
        Assert.assertThrows("discount code not found.", DiscountCodeNotFoundException.class,
                () -> MainController.getInstance().getAccountAreaForManagerController().removeDiscountCode("alalalalalal"));
    }

    @Test
    public void editDiscountRequestTest()
            throws DiscountCodeCantCreatedException, DiscountCodeNotFoundException, DiscountCodeCantBeEditedException, IOException, FileCantBeSavedException {
        fields.add(0,"RandomDiscount1");
        fields.add(1,"2020-07-01");
        fields.add(2, "2020-08-01");
        fields.add(3, "9000000");
        fields.add(4, "30");
        MainController.getInstance().getAccountAreaForManagerController().createNewDiscountCode(fields);
        Assert.assertThrows("discount code not found.", DiscountCodeNotFoundException.class,
                () -> MainController.getInstance().getAccountAreaForManagerController().editDiscountCode("kfmkff", "startDate", "2020-07-03"));
        Assert.assertThrows("can not edit discount code because new start date value is incorrect.", DiscountCodeCantBeEditedException.class,
                () -> MainController.getInstance().getAccountAreaForManagerController().editDiscountCode("RandomDiscount", "startDate", "2021-07-05"));
        Assert.assertThrows("can not edit discount code because new end date value is incorrect.", DiscountCodeCantBeEditedException.class,
                () -> MainController.getInstance().getAccountAreaForManagerController().editDiscountCode("RandomDiscount", "endDate", "2020-05-01"));
        Assert.assertThrows("can not edit discount code because new discount code amount value is incorrect.", DiscountCodeCantBeEditedException.class,
                () -> MainController.getInstance().getAccountAreaForManagerController().editDiscountCode("RandomDiscount", "maxDiscountAmount", "3333333333333333333333333333333333333333"));
        Assert.assertThrows("can not edit discount code because new discount percent value is incorrect.", DiscountCodeCantBeEditedException.class,
                () -> MainController.getInstance().getAccountAreaForManagerController().editDiscountCode("RandomDiscount", "discountPercent", "321"));
        Assert.assertThrows("can not edit discount code because field name for edit is incorrect.", DiscountCodeCantBeEditedException.class,
                () -> MainController.getInstance().getAccountAreaForManagerController().editDiscountCode("RandomDiscount", "fieldalaki", "100"));
        MainController.getInstance().getAccountAreaForManagerController().editDiscountCode("RandomDiscount", "startDate", "2020-07-10");
        LocalDate newDate = Shop.getInstance().findDiscountCode("RandomDiscount").getStartDate();
        LocalDate expectedDate = LocalDate.parse("2020-07-10");
        Assert.assertEquals(newDate.toString(), expectedDate.toString());
    }

    @Test
    public void getAllDiscountCodesInfoTest() {
        Assert.assertEquals(2, MainController.getInstance().getAccountAreaForManagerController().getAllDiscountCodesInfo(Shop.getInstance().getAllDiscountCodes()).size());
    }

    @Test
    public void viewDiscountCodeTest() {
        Assert.assertThrows(DiscountCodeNotFoundException.class,
                () ->MainController.getInstance().getAccountAreaForManagerController().viewDiscountCode("bullshitCode"));
    }

    @Test
    public void viewRequestInfoTest() throws RequestNotFoundException {
        Assert.assertThrows(RequestNotFoundException.class,
                () -> MainController.getInstance().getAccountAreaForManagerController().viewRequestDetails("5"));
        Assert.assertNotNull(MainController.getInstance().getAccountAreaForManagerController().viewRequestDetails("1"));
    }

    @Test
    public void getAllRequestsInfoTest() {
        System.out.println(MainController.getInstance().getAccountAreaForManagerController().getAllRequestsInfo());
        Assert.assertEquals(0, MainController.getInstance().getAccountAreaForManagerController().getAllRequestsInfo().size());
    }

    @Test
    public void acceptRequestTest() throws RequestNotFoundException, FileCantBeSavedException, IOException, FileCantBeDeletedException {
        Shop.getInstance().addRequest(new RegisteringSellerRequest(new Seller("fgf", "fgfg", "fgfg", "gfgg", "fgfg", "fgfgf",null)));
        Assert.assertThrows(RequestNotFoundException.class,
                () -> MainController.getInstance().getAccountAreaForManagerController().acceptRequest("10"));
        Assert.assertThrows(FileCantBeDeletedException.class , () -> MainController.getInstance().getAccountAreaForManagerController().acceptRequest("2"));
    }

    @Test
    public void declineRequestTest() throws RequestNotFoundException, IOException, FileCantBeDeletedException, FileCantBeSavedException {
        Database.getInstance().saveItem(Shop.getInstance().findRequestById(1));
        Assert.assertThrows(RequestNotFoundException.class,
                () -> MainController.getInstance().getAccountAreaForManagerController().declineRequest("5"));
        MainController.getInstance().getAccountAreaForManagerController().declineRequest("1");
        Assert.assertNull(Shop.getInstance().findRequestById(1));
        Assert.assertFalse(new File("Resources\\Requests\\request_RegisteringSellerRequest_1.json").exists());
    }

    @Test
    public void getAllCategoriesTest() {
        Assert.assertEquals(2, MainController.getInstance().getAccountAreaForManagerController().getAllCategories().size());
    }

    @Test
    public void getCategoryPropertiesTest() {
        Assert.assertEquals(2, MainController.getInstance().getAccountAreaForManagerController().getCategoryProperties("ashghal").size());
    }

    @Test
    public void getCategorySubCatsNamesTest() {
        Assert.assertEquals(1, MainController.getInstance().getAccountAreaForManagerController().getCategorySubCatsNames("ashghal").size());
    }

    @Test
    public void editCategoryTest() throws PropertyNotFoundException, IOException, FileCantBeSavedException {
        Assert.assertThrows(PropertyNotFoundException.class, () -> MainController.getInstance().getAccountAreaForManagerController().editCategory("ashghal", "om", "99"));
        MainController.getInstance().getAccountAreaForManagerController().editCategory("ashghal", "hich1", "fuck");
        Assert.assertEquals("fuck", Shop.getInstance().findCategoryByName("ashghal").getDetails().get(0));
    }

    @Test
    public void checkExistingOfCategoryTest() {
        Assert.assertTrue(MainController.getInstance().getAccountAreaForManagerController().isExistCategoryWithThisName("ashghal"));
    }

    @Test
    public void checkExistenceOfSubCategoryTest() {
        Assert.assertTrue(MainController.getInstance().getAccountAreaForManagerController().isExistSubcategoryWithThisName("subAshghal"));
    }

    @Test
    public void addCategoryTest() throws IOException, FileCantBeSavedException {
        MainController.getInstance().getAccountAreaForManagerController().addCategory("ashghal-2", fields);
        Assert.assertNotNull(Shop.getInstance().findCategoryByName("ashghal-2"));
    }

}

