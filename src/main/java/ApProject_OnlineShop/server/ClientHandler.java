package ApProject_OnlineShop.server;

import ApProject_OnlineShop.controller.MainController;
import ApProject_OnlineShop.exception.FileCantBeDeletedException;
import ApProject_OnlineShop.exception.FileCantBeSavedException;
import ApProject_OnlineShop.exception.PropertyNotFoundException;
import ApProject_OnlineShop.exception.RequestNotFoundException;
import ApProject_OnlineShop.exception.categoryExceptions.CategoryNotFoundException;
import ApProject_OnlineShop.exception.categoryExceptions.SubCategoryNotFoundException;
import ApProject_OnlineShop.exception.discountcodeExceptions.DiscountCodeCantBeEditedException;
import ApProject_OnlineShop.exception.discountcodeExceptions.DiscountCodeCantCreatedException;
import ApProject_OnlineShop.exception.discountcodeExceptions.DiscountCodeNotFoundException;
import ApProject_OnlineShop.exception.productExceptions.ProductNotFoundExceptionForSeller;
import ApProject_OnlineShop.exception.productExceptions.ProductWithThisIdNotExist;
import ApProject_OnlineShop.exception.productExceptions.YouRatedThisProductBefore;
import ApProject_OnlineShop.exception.userExceptions.*;
import ApProject_OnlineShop.model.Shop;
import ApProject_OnlineShop.model.persons.Customer;
import ApProject_OnlineShop.model.persons.Manager;
import ApProject_OnlineShop.model.persons.Person;
import ApProject_OnlineShop.model.persons.Seller;
import ApProject_OnlineShop.server.clientHandler.BankAccountsControllerHandler;
import ApProject_OnlineShop.server.clientHandler.BankTransactionControllerHandler;
import com.google.gson.Gson;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ClientHandler extends Thread {
    private Socket clientSocket;
    private DataOutputStream dataOutputStream;
    private DataInputStream dataInputStream;
    private ServerSocket serverSocket;
    private Person user;
    private Gson gson;

    public ClientHandler(Socket clientSocket, DataOutputStream dataOutputStream, DataInputStream dataInputStream, ServerSocket serverSocket) {
        this.clientSocket = clientSocket;
        this.dataOutputStream = dataOutputStream;
        this.dataInputStream = dataInputStream;
        this.serverSocket = serverSocket;
        gson = new Gson();
        user = null;
    }

    @Override
    public void run() {
        handelClient();
        try {
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handelClient() {
        String input = null;
        try {
            input = dataInputStream.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
        RequestForServer requestForServer = new Gson().fromJson(input, RequestForServer.class);
        try {
            handleRequest(requestForServer);
        } catch (IOException | FileCantBeSavedException e) {
            try {
                dataOutputStream.writeUTF(e.getMessage());
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            try {
                dataOutputStream.flush();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

    private void handleRequest(RequestForServer requestForServer) throws IOException, FileCantBeSavedException {
        if (requestForServer.getToken() != null)
            user = Server.getOnlineUsers().get(requestForServer.getToken());
        if (requestForServer.getController().equals("getCurrentPerson")) {
            getCurrentPerson();
        } else if (requestForServer.getController().equals("LoginRegisterController")) {
            loginRegisterControllerHandler(requestForServer);
        } else if (requestForServer.getController().equals("AccountAreaController")) {
            accountAreaControllerHandler(requestForServer);
        } else if (requestForServer.getController().equals("BankAccountsController")) {
            new BankAccountsControllerHandler(clientSocket, dataOutputStream, dataInputStream, serverSocket, user).
                    bankAccountsControllerHandler(requestForServer);
        } else if (requestForServer.getController().equals("AccountAreaForSellerController")) {
            accountAreaForSellerHandler(requestForServer);
        } else if (requestForServer.getController().equals("BankTransactionsController")) {
            new BankTransactionControllerHandler(clientSocket, dataOutputStream, dataInputStream, serverSocket, user).
                    bankTransactionControllerHandler(requestForServer);
        } else if (requestForServer.getController().equals("AccountAreaForManagerController")) {
            accountAreaForManagerHandler(requestForServer);
        } else if (requestForServer.getController().equals("AccountAreaForCustomerController")) {
            accountAreaForCustomer(requestForServer);
        }
    }

    private void accountAreaForCustomer(RequestForServer requestForServer) throws IOException, FileCantBeSavedException {
        if (requestForServer.getFunction().equals("viewDiscountCodes")) {
            if (user == null)
                return;
            dataOutputStream.writeUTF(convertListToString(MainController.getInstance().getAccountAreaForCustomerController().viewDiscountCodes(Integer.parseInt(requestForServer.getInputs().get(0)), user)));
            dataOutputStream.flush();
        } else if (requestForServer.getFunction().equals("viewGoodInCartById")) {
            dataOutputStream.writeUTF(convertListToString(MainController.getInstance().getAccountAreaForCustomerController().viewGoodInCartById(Long.parseLong(requestForServer.getInputs().get(0)))));
            dataOutputStream.flush();
        } else if (requestForServer.getFunction().equals("rateProduct")) {
            if (user == null)
                return;
            try {
                MainController.getInstance().getAccountAreaForCustomerController().rateProduct(Long.parseLong(requestForServer.getInputs().get(0)), Integer.parseInt(requestForServer.getInputs().get(1)), user);
                dataOutputStream.writeUTF("rate was successful");
                dataOutputStream.flush();
            } catch (YouRatedThisProductBefore e) {
                dataOutputStream.writeUTF(e.getMessage());
                dataOutputStream.flush();
            }
        } else if (requestForServer.getFunction().equals("getSortedCustomerOrders")) {
            if (user == null)
                return;
            dataOutputStream.writeUTF(convertListToString(MainController.getInstance().getAccountAreaForCustomerController().getSortedCustomerOrders(Integer.parseInt(requestForServer.getInputs().get(0)), user)));
            dataOutputStream.flush();
        } else if (requestForServer.getFunction().equals("checkValidDiscountCode")) {
            if (user == null)
                return;
            try {
                String output = MainController.getInstance().getAccountAreaForCustomerController().checkValidDiscountCode(requestForServer.getInputs().get(0), user) + "";
                dataOutputStream.writeUTF(output);
                dataOutputStream.flush();
            } catch (Exception e) {
                dataOutputStream.writeUTF(e.getMessage());
                dataOutputStream.flush();
            }
        } else if (requestForServer.getFunction().equals("useDiscountCode")) {
            if (user == null)
                return;
            try {
                String output = MainController.getInstance().getAccountAreaForCustomerController().useDiscountCode(requestForServer.getInputs().get(0), user) + "";
                dataOutputStream.writeUTF(output);
                dataOutputStream.flush();
            } catch (Exception e) {
                dataOutputStream.writeUTF("this discount code has expired");
                dataOutputStream.flush();
            }
        } else if (requestForServer.getFunction().equals("purchaseByWallet")) {
            if (user == null)
                return;
            Long totalPrice = Long.parseLong(requestForServer.getInputs().get(0));
            String usedDiscountCode = requestForServer.getInputs().get(requestForServer.getInputs().size() - 1);
            requestForServer.getInputs().remove(0);
            requestForServer.getInputs().remove(requestForServer.getInputs().size() - 1);
            try {
                MainController.getInstance().getAccountAreaForCustomerController().purchaseByWallet(totalPrice, requestForServer.getInputs(), usedDiscountCode, user);
                dataOutputStream.writeUTF("purchase was successful");
                dataOutputStream.flush();
            } catch (Exception exception) {
                dataOutputStream.writeUTF(exception.getMessage());
                dataOutputStream.flush();
            }
        } else if (requestForServer.getFunction().equals("purchaseByBankPortal")) {
            if (user == null)
                return;
            String bankUserName = requestForServer.getInputs().get(0);
            String password = requestForServer.getInputs().get(1);
            String money = requestForServer.getInputs().get(2);
            String usedDiscountCode = requestForServer.getInputs().get(3);
            requestForServer.getInputs().remove(3);
            requestForServer.getInputs().remove(2);
            requestForServer.getInputs().remove(1);
            requestForServer.getInputs().remove(0);
            try {
                MainController.getInstance().getAccountAreaForCustomerController().purchaseByBankPortal(bankUserName, password, money, usedDiscountCode, requestForServer.getInputs(), user);
                dataOutputStream.writeUTF("purchase was successful");
                dataOutputStream.flush();
            } catch (Exception exception) {
                dataOutputStream.writeUTF(exception.getMessage());
                dataOutputStream.flush();
            }
        } else if (requestForServer.getFunction().equals("getBoughtProducts")) {
            if (user == null)
                return;
            List<Long> ids = MainController.getInstance().getAccountAreaForCustomerController().getBoughtProducts(user);
            ArrayList<String> output = new ArrayList<>();
            for (Long id : ids) {
                output.add(id + "");
            }
            dataOutputStream.writeUTF(convertArrayListToString(output));
            dataOutputStream.flush();
        }
    }

    private void accountAreaForManagerHandler(RequestForServer requestForServer) throws IOException, FileCantBeSavedException {
        if (requestForServer.getFunction().equals("createNewDiscountCode")) {
            try {
                MainController.getInstance().getAccountAreaForManagerController().createNewDiscountCode(requestForServer.getInputs());
                dataOutputStream.writeUTF("discountCode created successfully");
                dataOutputStream.flush();
            } catch (DiscountCodeCantCreatedException e) {
                dataOutputStream.writeUTF(e.getMessage());
                dataOutputStream.flush();
            }
        } else if (requestForServer.getFunction().equals("addIncludedCustomerToDiscountCode")) {
            try {
                MainController.getInstance().getAccountAreaForManagerController().addIncludedCustomerToDiscountCode(requestForServer.getInputs().get(0), requestForServer.getInputs().get(1), requestForServer.getInputs().get(2));
                dataOutputStream.writeUTF("customer included successfully");
                dataOutputStream.flush();
            } catch (DiscountCodeCantCreatedException e) {
                dataOutputStream.writeUTF(e.getMessage());
                dataOutputStream.flush();
            } catch (UsernameNotFoundException e) {
                dataOutputStream.writeUTF(e.getMessage());
                dataOutputStream.flush();
            } catch (DiscountCodeNotFoundException e) {
                dataOutputStream.writeUTF(e.getMessage());
                dataOutputStream.flush();
            }
        } else if (requestForServer.getFunction().equals("editDiscountCode")) {
            try {
                MainController.getInstance().getAccountAreaForManagerController().editDiscountCode(requestForServer.getInputs().get(0), requestForServer.getInputs().get(1), requestForServer.getInputs().get(2));
                dataOutputStream.writeUTF("discountCode edited successfully");
                dataOutputStream.flush();
            } catch (DiscountCodeNotFoundException e) {
                dataOutputStream.writeUTF(e.getMessage());
                dataOutputStream.flush();
            } catch (DiscountCodeCantBeEditedException e) {
                dataOutputStream.writeUTF(e.getMessage());
                dataOutputStream.flush();
            }
        } else if (requestForServer.getFunction().equals("removeCustomerFromDiscount")) {
            try {
                MainController.getInstance().getAccountAreaForManagerController().removeCustomerFromDiscount(requestForServer.getInputs().get(0), requestForServer.getInputs().get(1));
                dataOutputStream.writeUTF("customers removed successfully");
                dataOutputStream.flush();
            } catch (Exception exception) {
                dataOutputStream.writeUTF(exception.getMessage());
                dataOutputStream.flush();
            }
        } else if (requestForServer.getFunction().equals("removeDiscountCode")) {
            try {
                MainController.getInstance().getAccountAreaForManagerController().removeDiscountCode(requestForServer.getInputs().get(0));
                dataOutputStream.writeUTF("discountCode removed successfully");
                dataOutputStream.flush();
            } catch (DiscountCodeNotFoundException e) {
                dataOutputStream.writeUTF(e.getMessage());
                dataOutputStream.flush();
            } catch (FileCantBeDeletedException e) {
                dataOutputStream.writeUTF(e.getMessage());
                dataOutputStream.flush();
            }
        } else if (requestForServer.getFunction().equals("viewRequestDetails")) {
            try {
                String output = MainController.getInstance().getAccountAreaForManagerController().viewRequestDetails(requestForServer.getInputs().get(0));
                dataOutputStream.writeUTF(output);
                dataOutputStream.flush();
            } catch (RequestNotFoundException e) {
                dataOutputStream.writeUTF("#error");
                dataOutputStream.flush();
            }
        } else if (requestForServer.getFunction().equals("acceptRequest")) {
            try {
                MainController.getInstance().getAccountAreaForManagerController().acceptRequest(requestForServer.getInputs().get(0));
                dataOutputStream.writeUTF("request accepted successfully");
                dataOutputStream.flush();
            } catch (RequestNotFoundException | FileCantBeDeletedException e) {
                dataOutputStream.writeUTF(e.getMessage());
                dataOutputStream.flush();
            }
        } else if (requestForServer.getFunction().equals("declineRequest")) {
            try {
                MainController.getInstance().getAccountAreaForManagerController().declineRequest(requestForServer.getInputs().get(0));
                dataOutputStream.writeUTF("request declined successfully");
                dataOutputStream.flush();
            } catch (RequestNotFoundException | FileCantBeDeletedException e) {
                dataOutputStream.writeUTF(e.getMessage());
                dataOutputStream.flush();
            }
        } else if (requestForServer.getFunction().equals("getCategorySubCatsNames")) {
            dataOutputStream.writeUTF(convertArrayListToString(MainController.getInstance().getAccountAreaForManagerController().getCategorySubCatsNames(requestForServer.getInputs().get(0))));
            dataOutputStream.flush();
        } else if (requestForServer.getFunction().equals("editCategory")) {
            try {
                MainController.getInstance().getAccountAreaForManagerController().editCategory(requestForServer.getInputs().get(0), requestForServer.getInputs().get(1), requestForServer.getInputs().get(2));
                dataOutputStream.writeUTF("category edited successfully");
                dataOutputStream.flush();
            } catch (PropertyNotFoundException e) {
                dataOutputStream.writeUTF(e.getMessage());
                dataOutputStream.flush();
            }
        } else if (requestForServer.getFunction().equals("isExistCategoryWithThisName")) {
            dataOutputStream.writeUTF("" + MainController.getInstance().getAccountAreaForManagerController().isExistCategoryWithThisName(requestForServer.getInputs().get(0)));
            dataOutputStream.flush();
        } else if (requestForServer.getFunction().equals("isExistSubcategoryWithThisName")) {
            dataOutputStream.writeUTF("" + MainController.getInstance().getAccountAreaForManagerController().isExistSubcategoryWithThisName(requestForServer.getInputs().get(0)));
            dataOutputStream.flush();
        } else if (requestForServer.getFunction().equals("addCategory")) {
            String name = requestForServer.getInputs().get(0);
            requestForServer.getInputs().remove(0);
            MainController.getInstance().getAccountAreaForManagerController().addCategory(name, requestForServer.getInputs());
            dataOutputStream.writeUTF("category added successfully");
            dataOutputStream.flush();
        } else if (requestForServer.getFunction().equals("removeCategory")) {
            try {
                MainController.getInstance().getAccountAreaForManagerController().removeCategory(requestForServer.getInputs().get(0));
                dataOutputStream.writeUTF("category removed successfully");
                dataOutputStream.flush();
            } catch (CategoryNotFoundException | FileCantBeDeletedException e) {
                dataOutputStream.writeUTF(e.getMessage());
                dataOutputStream.flush();
            }
        } else if (requestForServer.getFunction().equals("addSubcategory")) {
            String categoryName = requestForServer.getInputs().get(0);
            String subCategoryName = requestForServer.getInputs().get(1);
            requestForServer.getInputs().remove(1);
            requestForServer.getInputs().remove(0);
            MainController.getInstance().getAccountAreaForManagerController().addSubcategory(categoryName, subCategoryName, requestForServer.getInputs());
            dataOutputStream.writeUTF("subCategory added successfully");
            dataOutputStream.flush();
        } else if (requestForServer.getFunction().equals("removeSubCategory")) {
            try {
                MainController.getInstance().getAccountAreaForManagerController().removeSubCategory(requestForServer.getInputs().get(0), requestForServer.getInputs().get(1));
                dataOutputStream.writeUTF("subCategory removed successfully");
                dataOutputStream.flush();
            } catch (CategoryNotFoundException | FileCantBeDeletedException | SubCategoryNotFoundException e) {
                dataOutputStream.writeUTF(e.getMessage());
                dataOutputStream.flush();
            }
        } else if (requestForServer.getFunction().equals("editSubcategory")) {
            try {
                MainController.getInstance().getAccountAreaForManagerController().editSubcategory(requestForServer.getInputs().get(0), requestForServer.getInputs().get(1), requestForServer.getInputs().get(2));
                dataOutputStream.writeUTF("subcategory edited successfully");
                dataOutputStream.flush();
            } catch (PropertyNotFoundException e) {
                dataOutputStream.writeUTF(e.getMessage());
                dataOutputStream.flush();
            }
        } else if (requestForServer.getFunction().equals("removeUser")) {
            try {
                MainController.getInstance().getAccountAreaForManagerController().removeUser(requestForServer.getInputs().get(0));
                dataOutputStream.writeUTF("user removed successfully");
                dataOutputStream.flush();
            } catch (UsernameNotFoundException | UserCantBeRemovedException | FileCantBeDeletedException e) {
                dataOutputStream.writeUTF(e.getMessage());
                dataOutputStream.flush();
            }
        } else if (requestForServer.getFunction().equals("createManagerAccount")) {
            String name = requestForServer.getInputs().get(0);
            requestForServer.getInputs().remove(0);
            try {
                MainController.getInstance().getAccountAreaForManagerController().createManagerAccount(name, requestForServer.getInputs());
                dataOutputStream.writeUTF("user created successfully");
                dataOutputStream.flush();
            } catch (UsernameIsTakenAlreadyException e) {
                dataOutputStream.writeUTF(e.getMessage());
                dataOutputStream.flush();
            }
        } else if (requestForServer.getFunction().equals("removeProduct")) {
            try {
                MainController.getInstance().getAccountAreaForManagerController().removeProduct(requestForServer.getInputs().get(0));
                dataOutputStream.writeUTF("product removed successfully");
                dataOutputStream.flush();
            } catch (ProductWithThisIdNotExist | FileCantBeDeletedException e) {
                dataOutputStream.writeUTF(e.getMessage());
                dataOutputStream.flush();
            }
        } else if (requestForServer.getFunction().equals("getAllCategoriesName")) {
            dataOutputStream.writeUTF(convertListToString(MainController.getInstance().getAccountAreaForManagerController().getAllCategoriesName()));
            dataOutputStream.flush();
        } else if (requestForServer.getFunction().equals("getAllSubCategoriesNamesOfCategory")) {
            dataOutputStream.writeUTF(convertListToString(MainController.getInstance().getAccountAreaForManagerController().getAllSubCategoriesNamesOfCategory(requestForServer.getInputs().get(0))));
            dataOutputStream.flush();
        } else if (requestForServer.getFunction().equals("addPropertyToCategory")) {
            MainController.getInstance().getAccountAreaForManagerController().addPropertyToCategory(requestForServer.getInputs().get(0), requestForServer.getInputs().get(1));
            dataOutputStream.writeUTF("successfully property added");
            dataOutputStream.flush();
        } else if (requestForServer.getFunction().equals("addPropertyToSubCategory")) {
            MainController.getInstance().getAccountAreaForManagerController().addPropertyToSubCategory(requestForServer.getInputs().get(0), requestForServer.getInputs().get(1));
            dataOutputStream.writeUTF("successfully property added");
            dataOutputStream.flush();
        }else if(requestForServer.getFunction().equals("setBankingFeePercent")){
            MainController.getInstance().getAccountAreaForManagerController().setBankingFeePercent(requestForServer.getInputs().get(0));
            dataOutputStream.writeUTF("percent of banking fee successfully changed");
            dataOutputStream.flush();
        }else if(requestForServer.getFunction().equals("setMinimumAmountForWallet")){
            MainController.getInstance().getAccountAreaForManagerController().setMinimumAmountForWallet(requestForServer.getInputs().get(0));
            dataOutputStream.writeUTF("minimum amount successfully changed");
            dataOutputStream.flush();
        }
    }

    private void accountAreaForSellerHandler(RequestForServer requestForServer) throws IOException {
        Person person = user;
        if (person == null)
            return;
        if (requestForServer.getFunction().equals("removeProduct")) {
            try {
                MainController.getInstance().getAccountAreaForSellerController().removeProduct(Long.parseLong(requestForServer.getInputs().get(0)), person);
                dataOutputStream.writeUTF("product removed successfully");
                dataOutputStream.flush();
            } catch (ProductNotFoundExceptionForSeller productNotFoundExceptionForSeller) {
                dataOutputStream.writeUTF(productNotFoundExceptionForSeller.getMessage());
                dataOutputStream.flush();
            } catch (IOException e) {
                dataOutputStream.writeUTF(e.getMessage());
                dataOutputStream.flush();
            } catch (FileCantBeSavedException e) {
                dataOutputStream.writeUTF(e.getMessage());
                dataOutputStream.flush();
            } catch (FileCantBeDeletedException e) {
                dataOutputStream.writeUTF(e.getMessage());
                dataOutputStream.flush();
            }
        } else if (requestForServer.getFunction().equals("getCompanyInfo")) {
            ArrayList<String> data = MainController.getInstance().getAccountAreaForSellerController().getCompanyInfo(person);
            dataOutputStream.writeUTF(convertArrayListToString(data));
            dataOutputStream.flush();
        } else if (requestForServer.getFunction().equals("getSortedLogs")) {
            List<String> data = MainController.getInstance().getAccountAreaForSellerController().getSortedLogs(Integer.parseInt(requestForServer.getInputs().get(0)), person);
            dataOutputStream.writeUTF(convertListToString(data));
            dataOutputStream.flush();
        } else if (requestForServer.getFunction().equals("viewBalance")) {
            dataOutputStream.writeUTF("" + MainController.getInstance().getAccountAreaForSellerController().viewBalance(person));
            dataOutputStream.flush();
        } else if (requestForServer.getFunction().equals("buyersOfProduct")) {
            try {
                String output = convertArrayListToString(MainController.getInstance().getAccountAreaForSellerController().buyersOfProduct(Long.parseLong(requestForServer.getInputs().get(0)), person));
                dataOutputStream.writeUTF(output);
                dataOutputStream.flush();
            } catch (ProductNotFoundExceptionForSeller productNotFoundExceptionForSeller) {
                dataOutputStream.writeUTF(productNotFoundExceptionForSeller.getMessage());
                dataOutputStream.flush();
            }
        } else if (requestForServer.getFunction().equals("getAllOffs")) {
            List<String> data = MainController.getInstance().getAccountAreaForSellerController().getAllOffs(person);
            dataOutputStream.writeUTF(convertListToString(data));
            dataOutputStream.flush();
        } else if (requestForServer.getFunction().equals("getSortedOffs")) {
            List<String> data = MainController.getInstance().getAccountAreaForSellerController().getSortedOffs(Integer.parseInt(requestForServer.getInputs().get(0)), person);
            dataOutputStream.writeUTF(convertListToString(data));
            dataOutputStream.flush();
        } else if (requestForServer.getFunction().equals("viewOffGUI")) {
            String output = convertArrayListToString(MainController.getInstance().getAccountAreaForSellerController().viewOffGUI(Long.parseLong(requestForServer.getInputs().get(0))));
            dataOutputStream.writeUTF(output);
            dataOutputStream.flush();
        } else if (requestForServer.getFunction().equals("isSubCategoryCorrect")) {
            dataOutputStream.writeUTF("" + MainController.getInstance().getAccountAreaForSellerController().isSubCategoryCorrect(requestForServer.getInputs().get(0)));
            dataOutputStream.flush();
        } else if (requestForServer.getFunction().equals("getSubcategoryDetails")) {
            List<String> data = MainController.getInstance().getAccountAreaForSellerController().getSubcategoryDetails(requestForServer.getInputs().get(0));
            dataOutputStream.writeUTF(convertListToString(data));
            dataOutputStream.flush();
        } else if (requestForServer.getFunction().equals("addProduct")) {
            ArrayList<String> productInfo = new ArrayList<>();
            for (int i = 0; i < requestForServer.getInputs().indexOf("###"); i++) {
                productInfo.add(requestForServer.getInputs().get(i));
            }
            HashMap<String, String> categoryProperties = new HashMap<>();
            for (int i = requestForServer.getInputs().indexOf("###") + 1; i < requestForServer.getInputs().size(); i = i + 2) {
                categoryProperties.put(requestForServer.getInputs().get(i), requestForServer.getInputs().get(i + 1));
            }
            try {
                MainController.getInstance().getAccountAreaForSellerController().addProduct(productInfo, categoryProperties, person);
                dataOutputStream.writeUTF("successfully created!");
                dataOutputStream.flush();
            } catch (FileCantBeSavedException e) {
                e.printStackTrace();
            }
        } else if (requestForServer.getFunction().equals("checkValidProductId")) {
            dataOutputStream.writeUTF("" + MainController.getInstance().getAccountAreaForSellerController().checkValidProductId(Long.parseLong(requestForServer.getInputs().get(0)), person));
            dataOutputStream.flush();
        } else if (requestForServer.getFunction().equals("addOff")) {
            ArrayList<String> offDetails = new ArrayList<>();
            for (int i = 0; i < requestForServer.getInputs().indexOf("###"); i++) {
                offDetails.add(requestForServer.getInputs().get(i));
            }
            ArrayList<Long> offProducts = new ArrayList<>();
            for (int i = requestForServer.getInputs().indexOf("###") + 1; i < requestForServer.getInputs().size(); i++) {
                offProducts.add(Long.parseLong(requestForServer.getInputs().get(i)));
            }
            try {
                MainController.getInstance().getAccountAreaForSellerController().addOff(offDetails, offProducts, person);
            } catch (FileCantBeSavedException e) {
                e.printStackTrace();
            }
            dataOutputStream.writeUTF("off successfully added");
            dataOutputStream.flush();
        } else if (requestForServer.getFunction().equals("editOff")) {
            try {
                MainController.getInstance().getAccountAreaForSellerController().editOff(requestForServer.getInputs().get(0), requestForServer.getInputs().get(1), Long.parseLong(requestForServer.getInputs().get(2)));
                dataOutputStream.writeUTF("off edited successfully");
                dataOutputStream.flush();
            } catch (FileCantBeSavedException e) {
                dataOutputStream.writeUTF(e.getMessage());
                dataOutputStream.flush();
            }
        } else if (requestForServer.getFunction().equals("editProduct")) {
            try {
                MainController.getInstance().getAccountAreaForSellerController().editProduct(requestForServer.getInputs().get(0), requestForServer.getInputs().get(1), Long.parseLong(requestForServer.getInputs().get(2)), person);
                dataOutputStream.writeUTF("product edited successfully");
                dataOutputStream.flush();
            } catch (FileCantBeSavedException e) {
                dataOutputStream.writeUTF(e.getMessage());
                dataOutputStream.flush();
            }
        } else if (requestForServer.getFunction().equals("viewProducts")) {
            List<Long> products = MainController.getInstance().getAccountAreaForSellerController().viewProducts(Integer.parseInt(requestForServer.getInputs().get(0)));
            ArrayList<String> output = new ArrayList<>();
            for (Long product : products) {
                output.add(product + "");
            }
            dataOutputStream.writeUTF(convertArrayListToString(output));
            dataOutputStream.flush();
        } else if (requestForServer.getFunction().equals("isInOff")) {
            dataOutputStream.writeUTF("" + MainController.getInstance().getAccountAreaForSellerController().isInOff(Long.parseLong(requestForServer.getInputs().get(0)), person));
            dataOutputStream.flush();
        }
    }

    private void accountAreaControllerHandler(RequestForServer requestForServer) throws IOException {
        Person person = Server.getOnlineUsers().get(requestForServer.getToken());
        if (person == null)
            return;
        if (requestForServer.getFunction().equals("showCategories")) {
            ArrayList<String> data = null;
            if (person instanceof Customer) {
                data = MainController.getInstance().getAccountAreaForCustomerController().showCategories();
            } else if (person instanceof Seller) {
                data = MainController.getInstance().getAccountAreaForSellerController().showCategories();
            } else if (person instanceof Manager) {
                data = MainController.getInstance().getAccountAreaForManagerController().showCategories();
            }
            dataOutputStream.writeUTF(convertArrayListToString(data));
            dataOutputStream.flush();
        } else if (requestForServer.getFunction().equals("getUserPersonalInfo")) {
            ArrayList<String> personalInfo = MainController.getInstance().getAccountAreaForSellerController().getUserPersonalInfo(person);
            dataOutputStream.writeUTF(convertArrayListToString(personalInfo));
            dataOutputStream.flush();
        } else if (requestForServer.getFunction().equals("editField")) {
            try {
                MainController.getInstance().getAccountAreaForManagerController()
                        .editField(Integer.parseInt(requestForServer.getInputs().get(0)), requestForServer.getInputs().get(1), person);
                dataOutputStream.writeUTF("field edited successfully");
                dataOutputStream.flush();
            } catch (Exception exception) {
                dataOutputStream.writeUTF(exception.getMessage());
                dataOutputStream.flush();
            }
        }
    }

    private void getCurrentPerson() {
        if (user == null) {
            try {
                dataOutputStream.writeUTF("null");
                dataOutputStream.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (user instanceof Customer) {
            try {
                dataOutputStream.writeUTF("customer###" + gson.toJson(user, Customer.class));
                dataOutputStream.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (user instanceof Seller) {
            try {
                dataOutputStream.writeUTF("seller###" + gson.toJson(user, Seller.class));
                dataOutputStream.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (user instanceof Manager) {
            try {
                dataOutputStream.writeUTF("manager###" + gson.toJson(user, Manager.class));
                dataOutputStream.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void loginRegisterControllerHandler(RequestForServer requestForServer) throws IOException {
        if (requestForServer.getFunction().equals("createAccount")) {
            try {
                ArrayList<String> details = new ArrayList<>();
                for (int i = 2; i < requestForServer.getInputs().size(); i++) {
                    details.add(requestForServer.getInputs().get(i));
                }
                MainController.getInstance().getLoginRegisterController()
                        .createAccount(requestForServer.getInputs().get(0), requestForServer.getInputs().get(1), details);
                dataOutputStream.writeUTF("successfully account created.");
                dataOutputStream.flush();
            } catch (UsernameIsTakenAlreadyException e) {
                try {
                    dataOutputStream.writeUTF(e.getMessage());
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                try {
                    dataOutputStream.flush();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            } catch (MainManagerAlreadyRegistered mainManagerAlreadyRegistered) {
                try {
                    dataOutputStream.writeUTF(mainManagerAlreadyRegistered.getMessage());
                    dataOutputStream.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (FileCantBeSavedException e) {
                e.printStackTrace();
            }
        } else if (requestForServer.getFunction().equals("loginUser")) {
            try {
                Person person = MainController.getInstance().getLoginRegisterController().loginUser(requestForServer.getInputs().get(0), requestForServer.getInputs().get(1));
                String token = generateToken();
                Server.addOnlineUser(person, token);
                dataOutputStream.writeUTF("successfully login #" + token);
                dataOutputStream.flush();
            } catch (UsernameNotFoundException e) {
                try {
                    dataOutputStream.writeUTF(e.getMessage());
                    dataOutputStream.flush();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            } catch (PasswordIncorrectException e) {
                try {
                    dataOutputStream.writeUTF(e.getMessage());
                    dataOutputStream.flush();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (requestForServer.getFunction().equals("logoutUser")) {
            MainController.getInstance().getLoginRegisterController().logoutUser();
            Server.removeOnlineUser(requestForServer.getToken());
            dataOutputStream.writeUTF("logout successfully");
            dataOutputStream.flush();
        }
    }

    private String generateToken() {
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdefghijklmnopqrstuvxyz";
        StringBuilder randomCode = new StringBuilder(12);
        for (int i = 0; i < 12; i++) {
            int index = (int) (AlphaNumericString.length() * Math.random());
            randomCode.append(AlphaNumericString.charAt(index));
        }
        return randomCode.toString();
    }

    private String convertArrayListToString(ArrayList<String> data) {
        String output = "";
        for (String s : data) {
            output += s + "#";
        }
        String output2 = output;
        if (!data.isEmpty())
            output2 = output.substring(0, output.lastIndexOf("#"));
        return output2;
    }

    private String convertListToString(List<String> data) {
        String output = "";
        for (String s : data) {
            output += s + "#";
        }
        String output2 = output;
        if (!data.isEmpty())
            output2 = output.substring(0, output.lastIndexOf("#"));
        return output2;
    }
}
