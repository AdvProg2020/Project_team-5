package ApProject_OnlineShop.server;

import ApProject_OnlineShop.controller.MainController;
import ApProject_OnlineShop.controller.sortingAndFilteringForProducts.ControllerForFiltering;
import ApProject_OnlineShop.controller.sortingAndFilteringForProducts.ControllerForSorting;
import ApProject_OnlineShop.exception.FileCantBeDeletedException;
import ApProject_OnlineShop.exception.FileCantBeSavedException;
import ApProject_OnlineShop.exception.PropertyNotFoundException;
import ApProject_OnlineShop.exception.RequestNotFoundException;
import ApProject_OnlineShop.exception.*;
import ApProject_OnlineShop.exception.categoryExceptions.CategoryNotFoundException;
import ApProject_OnlineShop.exception.categoryExceptions.SubCategoryNotFoundException;
import ApProject_OnlineShop.exception.discountcodeExceptions.DiscountCodeCantBeEditedException;
import ApProject_OnlineShop.exception.discountcodeExceptions.DiscountCodeCantCreatedException;
import ApProject_OnlineShop.exception.discountcodeExceptions.DiscountCodeNotFoundException;
import ApProject_OnlineShop.exception.productExceptions.*;
import ApProject_OnlineShop.exception.userExceptions.*;
import ApProject_OnlineShop.model.Massage;
import ApProject_OnlineShop.model.RequestForServer;
import ApProject_OnlineShop.model.Shop;
import ApProject_OnlineShop.model.persons.*;
import ApProject_OnlineShop.model.productThings.Auction;
import ApProject_OnlineShop.model.productThings.FileProduct;
import ApProject_OnlineShop.model.productThings.Good;
import ApProject_OnlineShop.model.productThings.GoodInCart;
import ApProject_OnlineShop.server.clientHandlerForBank.BankAccountsControllerHandler;
import ApProject_OnlineShop.server.clientHandlerForBank.BankTransactionControllerHandler;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.scene.image.Image;

import javax.imageio.ImageIO;
import javax.swing.text.html.ImageView;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

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
        if (!validSizeString(input)) {
            try {
                dataOutputStream.writeUTF("not formatted input");
                dataOutputStream.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }
        RequestForServer requestForServer = null;
        try {
            requestForServer = new Gson().fromJson(input, RequestForServer.class);
        } catch (Exception exception) {
            try {
                dataOutputStream.writeUTF("not formatted input");
                dataOutputStream.flush();
                return;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (requestForServer == null)
            return;
        try {
            handleRequest(requestForServer);
        } catch (Exception e) {
            try {
                dataOutputStream.writeUTF("exception occured in server");
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

    private boolean validSizeString(String input) {
        if (input == null)
            return false;
        if (input.length() > 10000)
            return false;
        return true;
    }

    private void handleRequest(RequestForServer requestForServer) throws Exception {
        if (requestForServer.getController().equals("###cart")) {
            cartHandler();
            return;
        }
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
        } else if (requestForServer.getController().equals("AllProductsController")) {
            allProductsHandler(requestForServer);
        } else if (requestForServer.getController().equals("ProductController")) {
            productControllerHandler(requestForServer);
        } else if (requestForServer.getController().equals("Shop")) {
            ShopHandler(requestForServer);
        } else if (requestForServer.getController().equals("Others")) {
            othersHandler(requestForServer);
        } else if (requestForServer.getController().equals("Good")) {
            goodHandler(requestForServer);
        } else if (requestForServer.getController().equals("FilteringController")) {
            filteringControllerHandler(requestForServer);
        } else if (requestForServer.getController().equals("SortingController")) {
            sortingHandler(requestForServer);
        } else if (requestForServer.getController().equals("AccountAreaForSupporterController")) {
            accountAreaForSupporterHandler(requestForServer);
        } else if (requestForServer.getController().equals("AuctionsController")) {
            auctionControllerHandler(requestForServer);
        } else {
            dataOutputStream.writeUTF("not a proper response");
            dataOutputStream.flush();
        }
    }

    private void sortingHandler(RequestForServer requestForServer) throws Exception {
        if (requestForServer.getFunction().equals("sortASort")) {
            Server.getControllerForSortingHashMap().get(Long.parseLong(requestForServer.getInputs().get(0))).sortASort(Integer.parseInt(requestForServer.getInputs().get(1)));
            dataOutputStream.writeUTF("done");
            dataOutputStream.flush();
        } else if (requestForServer.getFunction().equals("getCurrentSort")) {
            dataOutputStream.writeUTF(Server.getControllerForSortingHashMap().get(Long.parseLong(requestForServer.getInputs().get(0))).getCurrentSort());
            dataOutputStream.flush();
        } else {
            dataOutputStream.writeUTF("not a proper response");
            dataOutputStream.flush();
        }
    }

    private void filteringControllerHandler(RequestForServer requestForServer) throws Exception {
        ControllerForFiltering controllerForFiltering = Server.getControllerForFilteringHashMap().get(Long.parseLong(requestForServer.getInputs().get(0)));
        if (requestForServer.getFunction().equals("isOffProductsFilter")) {
            dataOutputStream.writeUTF(controllerForFiltering.isOffProductsFilter() + "");
            dataOutputStream.flush();
        } else if (requestForServer.getFunction().equals("setOffProductsFilter")) {
            controllerForFiltering.setOffProductsFilter();
            dataOutputStream.writeUTF("done");
            dataOutputStream.flush();
        } else if (requestForServer.getFunction().equals("removeOffProductsFilter")) {
            controllerForFiltering.removeOffProductsFilter();
            dataOutputStream.writeUTF("done");
            dataOutputStream.flush();
        } else if (requestForServer.getFunction().equals("getSeller")) {
            dataOutputStream.writeUTF(controllerForFiltering.getSeller());
            dataOutputStream.flush();
        } else if (requestForServer.getFunction().equals("getName")) {
            dataOutputStream.writeUTF(controllerForFiltering.getName());
            dataOutputStream.flush();
        } else if (requestForServer.getFunction().equals("getCategory")) {
            dataOutputStream.writeUTF(controllerForFiltering.getCategory());
            dataOutputStream.flush();
        } else if (requestForServer.getFunction().equals("getSubCategory")) {
            dataOutputStream.writeUTF(controllerForFiltering.getSubCategory());
            dataOutputStream.flush();
        } else if (requestForServer.getFunction().equals("getBrand")) {
            dataOutputStream.writeUTF(controllerForFiltering.getBrand());
            dataOutputStream.flush();
        } else if (requestForServer.getFunction().equals("isAvailableProduct")) {
            dataOutputStream.writeUTF(controllerForFiltering.isAvailableProduct() + "");
            dataOutputStream.flush();
        } else if (requestForServer.getFunction().equals("addBrandFiltering")) {
            controllerForFiltering.addBrandFiltering(requestForServer.getInputs().get(1));
            dataOutputStream.writeUTF("done");
            dataOutputStream.flush();
        } else if (requestForServer.getFunction().equals("disablePriceFiltering")) {
            controllerForFiltering.disablePriceFiltering();
            dataOutputStream.writeUTF("done");
            dataOutputStream.flush();
        } else if (requestForServer.getFunction().equals("getStartPrice")) {
            dataOutputStream.writeUTF(controllerForFiltering.getStartPrice());
            dataOutputStream.flush();
        } else if (requestForServer.getFunction().equals("getEndPrice")) {
            dataOutputStream.writeUTF(controllerForFiltering.getEndPrice());
            dataOutputStream.flush();
        } else if (requestForServer.getFunction().equals("addPriceFiltering")) {
            controllerForFiltering.addPriceFiltering(requestForServer.getInputs().get(1), requestForServer.getInputs().get(2));
            dataOutputStream.writeUTF("done");
            dataOutputStream.flush();
        } else if (requestForServer.getFunction().equals("addSellerFilter")) {
            controllerForFiltering.addSellerFilter(requestForServer.getInputs().get(1));
            dataOutputStream.writeUTF("done");
            dataOutputStream.flush();
        } else if (requestForServer.getFunction().equals("addNameFiltering")) {
            controllerForFiltering.addNameFiltering(requestForServer.getInputs().get(1));
            dataOutputStream.writeUTF("done");
            dataOutputStream.flush();
        } else if (requestForServer.getFunction().equals("addAvailableProduct")) {
            controllerForFiltering.addAvailableProduct();
            dataOutputStream.writeUTF("done");
            dataOutputStream.flush();
        } else if (requestForServer.getFunction().equals("disableCategoryFilter")) {
            controllerForFiltering.disableCategoryFilter();
            dataOutputStream.writeUTF("done");
            dataOutputStream.flush();
        } else if (requestForServer.getFunction().equals("getSubcategories")) {
            dataOutputStream.writeUTF(convertListToString(controllerForFiltering.getSubcategories()));
            dataOutputStream.flush();
        } else if (requestForServer.getFunction().equals("disableSubcategoryFilter")) {
            controllerForFiltering.disableSubcategoryFilter();
            dataOutputStream.writeUTF("done");
            dataOutputStream.flush();
        } else if (requestForServer.getFunction().equals("getCategoryProperties")) {
            dataOutputStream.writeUTF(convertListToString(controllerForFiltering.getCategoryProperties()));
            dataOutputStream.flush();
        } else if (requestForServer.getFunction().equals("getSubCategoryProperties")) {
            dataOutputStream.writeUTF(convertListToString(controllerForFiltering.getSubCategoryProperties()));
            dataOutputStream.flush();
        } else if (requestForServer.getFunction().equals("getValueOfProperty")) {
            dataOutputStream.writeUTF(controllerForFiltering.getValueOfProperty(requestForServer.getInputs().get(1)));
            dataOutputStream.flush();
        } else if (requestForServer.getFunction().equals("addPropertiesFilter")) {
            controllerForFiltering.addPropertiesFilter(requestForServer.getInputs().get(1), requestForServer.getInputs().get(2));
            dataOutputStream.writeUTF("done");
            dataOutputStream.flush();
        } else if (requestForServer.getFunction().equals("removeAvailableProductsFilter")) {
            controllerForFiltering.removeAvailableProductsFilter();
            dataOutputStream.writeUTF("done");
            dataOutputStream.flush();
        } else if (requestForServer.getFunction().equals("removeProperty")) {
            controllerForFiltering.removeProperty(requestForServer.getInputs().get(1));
            dataOutputStream.writeUTF("done");
            dataOutputStream.flush();
        } else if (requestForServer.getFunction().equals("addSubCategoryFilter")) {
            controllerForFiltering.addSubCategoryFilter(requestForServer.getInputs().get(1));
            dataOutputStream.writeUTF("done");
            dataOutputStream.flush();
        } else if (requestForServer.getFunction().equals("addCategoryFilter")) {
            controllerForFiltering.addCategoryFilter(requestForServer.getInputs().get(1));
            dataOutputStream.writeUTF("done");
            dataOutputStream.flush();
        } else {
            dataOutputStream.writeUTF("not a proper response");
            dataOutputStream.flush();
        }
    }

    private void goodHandler(RequestForServer requestForServer) throws Exception {
        if (requestForServer.getFunction().equals("getPriceBySeller")) {
            Seller seller = (Seller) Shop.getInstance().findUser(requestForServer.getInputs().get(1));
            Good good = Shop.getInstance().findGoodById(Long.parseLong(requestForServer.getInputs().get(0)));
            dataOutputStream.writeUTF(good.getPriceBySeller(seller) + "");
            dataOutputStream.flush();
        } else if (requestForServer.getFunction().equals("getAvailableNumberBySeller")) {
            Seller seller = (Seller) Shop.getInstance().findUser(requestForServer.getInputs().get(1));
            Good good = Shop.getInstance().findGoodById(Long.parseLong(requestForServer.getInputs().get(0)));
            dataOutputStream.writeUTF(good.getAvailableNumberBySeller(seller) + "");
            dataOutputStream.flush();
        } else if (requestForServer.getFunction().equals("getComments")) {
            Good good = Shop.getInstance().findGoodById(Long.parseLong(requestForServer.getInputs().get(0)));
            dataOutputStream.writeUTF(new Gson().toJson(good.getComments()));
            dataOutputStream.flush();
        } else if (requestForServer.getFunction().equals("getThisGoodOff")) {
            Good good = Shop.getInstance().findGoodById(Long.parseLong(requestForServer.getInputs().get(0)));
            dataOutputStream.writeUTF(new Gson().toJson(good.getThisGoodOff()));
            dataOutputStream.flush();
        } else {
            dataOutputStream.writeUTF("not a proper response");
            dataOutputStream.flush();
        }
    }

    private void othersHandler(RequestForServer requestForServer) throws Exception {
        if (requestForServer.getFunction().equals("Good.getGoodsCount")) {
            dataOutputStream.writeUTF(Good.getGoodsCount() + "");
            dataOutputStream.flush();
        } else if (requestForServer.getFunction().equals("Good.setGoodsCount")) {
            Good.setGoodsCount(Long.parseLong(requestForServer.getInputs().get(0)));
            dataOutputStream.writeUTF("successfully set");
            dataOutputStream.flush();
        } else if (requestForServer.getFunction().equals("photo")) {
            File file2 = new File("Resources\\productImages");
            if (!file2.exists())
                file2.mkdir();
            File file1 = new File("Resources\\UserImages");
            if (!file1.exists())
                file1.mkdir();
            String path = requestForServer.getInputs().get(0);
            dataOutputStream.writeUTF("ready to receive");
            dataOutputStream.flush();
            File file = new File(path);
            try {
                file.createNewFile();
                OutputStream outputStream = new FileOutputStream(path);
                byte[] bytes = new byte[16 * 2048 * 4];
                int count;
                while ((count = dataInputStream.read(bytes)) > 0) {
                    outputStream.write(bytes, 0, count);
                }
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
//            Image file = new Image(new ByteArrayInputStream(dataInputStream.readAllBytes()));
//            File file = new File(path);
//            System.out.println("hi1");
//            FileOutputStream os = new FileOutputStream(file);
//            System.out.println("hi2");
//            byte[] fileBytes = dataInputStream.readAllBytes();
//            System.out.println("hi3");
//            System.out.println(fileBytes.length);
//            os.write(fileBytes);
//            System.out.println("hi4");
//            os.close();
//            System.out.println("hi server");
//            BufferedImage bi = null;
//            try {
//                bi = ImageIO.read(file.toURL());
//            } catch (MalformedURLException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            try {
//                ImageIO.write(bi, "jpg", new File(requestForServer.getInputs().get(2)));
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
        } else {
            dataOutputStream.writeUTF("not a proper response");
            dataOutputStream.flush();
        }
    }

    private void ShopHandler(RequestForServer requestForServer) throws Exception {
        if (requestForServer.getFunction().equals("getAllPersons")) {
            dataOutputStream.writeUTF(new Gson().toJson(Shop.getInstance().getAllPersons()));
            dataOutputStream.flush();
        } else if (requestForServer.getFunction().equals("getAllDiscountCodes")) {
            dataOutputStream.writeUTF(new Gson().toJson(Shop.getInstance().getAllDiscountCodes()));
            dataOutputStream.flush();
        } else if (requestForServer.getFunction().equals("getAllRequest")) {
            dataOutputStream.writeUTF(new Gson().toJson(Shop.getInstance().getAllRequest()));
            dataOutputStream.flush();
        } else if (requestForServer.getFunction().equals("findGoodById")) {
            dataOutputStream.writeUTF(new Gson().toJson(Shop.getInstance().findGoodById(Long.parseLong(requestForServer.getInputs().get(0)))));
            dataOutputStream.flush();
        } else if (requestForServer.getFunction().equals("findDiscountCode")) {
            dataOutputStream.writeUTF(new Gson().toJson(Shop.getInstance().findDiscountCode((requestForServer.getInputs().get(0)))));
            dataOutputStream.flush();
        } else if (requestForServer.getFunction().equals("findOffById")) {
            dataOutputStream.writeUTF(new Gson().toJson(Shop.getInstance().findOffById(Long.parseLong(requestForServer.getInputs().get(0)))));
            dataOutputStream.flush();
        } else if (requestForServer.getFunction().equals("findFileProductById")) {
            dataOutputStream.writeUTF(new Gson().toJson(Shop.getInstance().findFileProductById(Long.parseLong(requestForServer.getInputs().get(0))), FileProduct.class));
            dataOutputStream.flush();
        } else if (requestForServer.getFunction().equals("findSubCategoryByName")) {
            dataOutputStream.writeUTF(new Gson().toJson(Shop.getInstance().findSubCategoryByName((requestForServer.getInputs().get(0)))));
            dataOutputStream.flush();
        } else if (requestForServer.getFunction().equals("findCategoryByName")) {
            dataOutputStream.writeUTF(new Gson().toJson(Shop.getInstance().findCategoryByName((requestForServer.getInputs().get(0)))));
            dataOutputStream.flush();
        } else if (requestForServer.getFunction().equals("getFinalPriceOfAGood")) {
            dataOutputStream.writeUTF("" + Shop.getInstance().getFinalPriceOfAGood(Long.parseLong(requestForServer.getInputs().get(0)), requestForServer.getInputs().get(1)));
            dataOutputStream.flush();
        } else {
            dataOutputStream.writeUTF("not a proper response");
            dataOutputStream.flush();
        }
    }

    private void cartHandler() throws Exception {
        Server.getCarts().put(Server.getIdForCarts(), new ArrayList<>());
        Server.getControllerForFilteringHashMap().put(Server.getIdForCarts(), new ControllerForFiltering());
        Server.getControllerForSortingHashMap().put(Server.getIdForCarts(), new ControllerForSorting());
        dataOutputStream.writeUTF(Server.getIdForCarts() + "");
        dataOutputStream.flush();
        Server.setIdForCarts(Server.getIdForCarts() + 1);
    }

    private void productControllerHandler(RequestForServer requestForServer) throws Exception {
        if (requestForServer.getFunction().equals("compareWithAnotherProductGUI")) {
            dataOutputStream.writeUTF(convertArrayListToString(MainController.getInstance().getProductController()
                    .compareWithAnotherProductGUI(Long.parseLong(requestForServer.getInputs().get(0)), Long.parseLong(requestForServer.getInputs().get(1)))));
            dataOutputStream.flush();
        } else if (requestForServer.getFunction().equals("getMainInfo")) {
            dataOutputStream.writeUTF(convertListToString(MainController.getInstance().getProductController()
                    .getMainInfo(Long.parseLong(requestForServer.getInputs().get(0)))));
            dataOutputStream.flush();
        } else if (requestForServer.getFunction().equals("getSellersInfo")) {
            dataOutputStream.writeUTF(new Gson().toJson(MainController.getInstance().getProductController().getSellersInfo(Long.parseLong(requestForServer.getInputs().get(0)))));
            dataOutputStream.flush();
        } else if (requestForServer.getFunction().equals("isInOffBySeller")) {
            Seller seller = (Seller) Shop.getInstance().findUser(requestForServer.getInputs().get(0));
            dataOutputStream.writeUTF("" + MainController.getInstance().getProductController().isInOffBySeller(seller, Long.parseLong(requestForServer.getInputs().get(1))));
            dataOutputStream.flush();
        } else if (requestForServer.getFunction().equals("addComment")) {
            if (user == null)
                return;
            MainController.getInstance().getProductController().addComment(requestForServer.getInputs().get(0), requestForServer.getInputs().get(1), user, Long.parseLong(requestForServer.getInputs().get(2)));
            dataOutputStream.writeUTF("comment request created successfully");
            dataOutputStream.flush();
        } else if (requestForServer.getFunction().equals("getAllGoodsIds")) {
            List<String> ids = new ArrayList<>();
            for (Long goodsId : MainController.getInstance().getProductController().getAllGoodsIds()) {
                ids.add(goodsId + "");
            }
            dataOutputStream.writeUTF(convertListToString(ids));
            dataOutputStream.flush();
        } else if (requestForServer.getFunction().equals("getOffGoods")) {
            List<String> ids = new ArrayList<>();
            for (Long goodsId : MainController.getInstance().getProductController().getOffGoods()) {
                ids.add(goodsId + "");
            }
            dataOutputStream.writeUTF(convertListToString(ids));
            dataOutputStream.flush();
        } else if (requestForServer.getFunction().equals("isSubCategoryEquals")) {
            dataOutputStream.writeUTF("" + MainController.getInstance().getProductController().isSubCategoryEquals(Long.parseLong(requestForServer.getInputs().get(0)), Long.parseLong(requestForServer.getInputs().get(1))));
            dataOutputStream.flush();
        } else if (requestForServer.getFunction().equals("addGoodToCartGUI")) {
            try {
                MainController.getInstance().getProductController().addGoodToCartGUI(requestForServer.getInputs().get(0), Long.parseLong(requestForServer.getInputs().get(1)), Long.parseLong(requestForServer.getInputs().get(2)));
                dataOutputStream.writeUTF("successfully added to cart");
                dataOutputStream.flush();
            } catch (Exception exception) {
                dataOutputStream.writeUTF(exception.getMessage());
                dataOutputStream.flush();
            }
        } else if (requestForServer.getFunction().equals("getProductImage")) {
            byte[] imageBytes = MainController.getInstance().getProductController().getProductImage(Long.parseLong(requestForServer.getInputs().get(0)));
            dataOutputStream.write(imageBytes);
            dataOutputStream.flush();
        } else if (requestForServer.getFunction().equals("increaseSeenNumber")) {
            Good good = Shop.getInstance().findGoodById(Long.parseLong(requestForServer.getInputs().get(0)));
            good.setSeenNumber(good.getSeenNumber() + 1);
            dataOutputStream.writeUTF("done");
            dataOutputStream.flush();
        } else {
            dataOutputStream.writeUTF("not a proper response");
            dataOutputStream.flush();
        }
    }

    private void allProductsHandler(RequestForServer requestForServer) throws Exception {
        if (requestForServer.getFunction().equals("getProductBrief")) {
            dataOutputStream.writeUTF(convertListToString(MainController.getInstance().getAllProductsController().getProductBrief(Long.parseLong(requestForServer.getInputs().get(0)))));
            dataOutputStream.flush();
        } else if (requestForServer.getFunction().equals("getOffProductBriefSummery")) {
            dataOutputStream.writeUTF(convertListToString(MainController.getInstance().getAllProductsController().getOffProductBriefSummery(Long.parseLong(requestForServer.getInputs().get(0)))));
            dataOutputStream.flush();
        } else if (requestForServer.getFunction().equals("getFileProductBrief")) {
            dataOutputStream.writeUTF(convertListToString(MainController.getInstance().getAllProductsController().getFileProductBrief(Long.parseLong(requestForServer.getInputs().get(0)))));
            dataOutputStream.flush();
        } else if (requestForServer.getFunction().equals("getAllCategories")) {
            dataOutputStream.writeUTF(convertListToString(MainController.getInstance().getAllProductsController().getAllCategories()));
            dataOutputStream.flush();
        } else if (requestForServer.getFunction().equals("getGoods")) {
            List<Long> longIds = MainController.getInstance().getAllProductsController().getGoods(Long.parseLong(requestForServer.getInputs().get(0)));
            ArrayList<String> idsString = new ArrayList<>();
            for (Long id : longIds) {
                idsString.add(id + "");
            }
            dataOutputStream.writeUTF(convertListToString(idsString));
            dataOutputStream.flush();
        } else if (requestForServer.getFunction().equals("getFileProducts")) {
            List<Long> longIds = MainController.getInstance().getAllProductsController().getFileProducts();
            ArrayList<String> idsString = new ArrayList<>();
            for (Long id : longIds) {
                idsString.add(id + "");
            }
            dataOutputStream.writeUTF(convertListToString(idsString));
            dataOutputStream.flush();
        } else if (requestForServer.getFunction().equals("isInOff")) {
            dataOutputStream.writeUTF("" + MainController.getInstance().getAllProductsController().isInOff(Long.parseLong(requestForServer.getInputs().get(0))));
            dataOutputStream.flush();
        } else {
            dataOutputStream.writeUTF("not a proper response");
            dataOutputStream.flush();
        }
    }

    private void accountAreaForCustomer(RequestForServer requestForServer) throws Exception {
        if (requestForServer.getFunction().equals("viewDiscountCodes")) {
            if (user == null)
                return;
            dataOutputStream.writeUTF(convertListToString(MainController.getInstance().getAccountAreaForCustomerController().viewDiscountCodes(Integer.parseInt(requestForServer.getInputs().get(0)), user)));
            dataOutputStream.flush();
        } else if (requestForServer.getFunction().equals("viewGoodInCartById")) {
            dataOutputStream.writeUTF(convertListToString(MainController.getInstance().getAccountAreaForCustomerController().viewGoodInCartById(Long.parseLong(requestForServer.getInputs().get(0)), Long.parseLong(requestForServer.getInputs().get(1)))));
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
                String output = MainController.getInstance().getAccountAreaForCustomerController().useDiscountCode(requestForServer.getInputs().get(0), user, Long.parseLong(requestForServer.getInputs().get(1))) + "";
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
            String usedDiscountCode = requestForServer.getInputs().get(requestForServer.getInputs().size() - 2);
            Long id = Long.parseLong(requestForServer.getInputs().get(requestForServer.getInputs().size() - 1));
            requestForServer.getInputs().remove(0);
            requestForServer.getInputs().remove(requestForServer.getInputs().size() - 2);
            requestForServer.getInputs().remove(requestForServer.getInputs().size() - 1);
            try {
                MainController.getInstance().getAccountAreaForCustomerController().purchaseByWallet(totalPrice, requestForServer.getInputs(), usedDiscountCode, user, id);
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
            Long id = Long.parseLong(requestForServer.getInputs().get(4));
            requestForServer.getInputs().remove(4);
            requestForServer.getInputs().remove(3);
            requestForServer.getInputs().remove(2);
            requestForServer.getInputs().remove(1);
            requestForServer.getInputs().remove(0);
            try {
                MainController.getInstance().getAccountAreaForCustomerController().purchaseByBankPortal(bankUserName, password, money, usedDiscountCode, requestForServer.getInputs(), user, id);
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
        } else if (requestForServer.getFunction().equals("viewInCartProducts")) {
            List<Long> ids = MainController.getInstance().getAccountAreaForCustomerController().viewInCartProducts(Long.parseLong(requestForServer.getInputs().get(0)));
            dataOutputStream.writeUTF(convertArrayListToString(convertArrayListLongToString(ids)));
            dataOutputStream.flush();
        } else if (requestForServer.getFunction().equals("increaseInCartProduct")) {
            try {
                MainController.getInstance().getAccountAreaForCustomerController().increaseInCartProduct(Long.parseLong(requestForServer.getInputs().get(0)), Long.parseLong(requestForServer.getInputs().get(1)));
                dataOutputStream.writeUTF("Successfully increased");
                dataOutputStream.flush();
            } catch (Exception exception) {
                dataOutputStream.writeUTF(exception.getMessage());
                dataOutputStream.flush();
            }
        } else if (requestForServer.getFunction().equals("decreaseInCartProduct")) {
            MainController.getInstance().getAccountAreaForCustomerController().decreaseInCartProduct(Long.parseLong(requestForServer.getInputs().get(0)), Long.parseLong(requestForServer.getInputs().get(1)));
            dataOutputStream.writeUTF("Successfully decreased");
            dataOutputStream.flush();
        } else if (requestForServer.getFunction().equals("getCart")) {
            dataOutputStream.writeUTF(new Gson().toJson(MainController.getInstance().getAccountAreaForCustomerController().getCart(Long.parseLong(requestForServer.getInputs().get(0)))));
            dataOutputStream.flush();
        } else if (requestForServer.getFunction().equals("finalPriceOfAList")) {
            dataOutputStream.writeUTF("" + MainController.getInstance().getAccountAreaForCustomerController().finalPriceOfAList(new Gson().fromJson(requestForServer.getInputs().get(0), new TypeToken<List<GoodInCart>>() {
            }.getType())));
            dataOutputStream.flush();
        } else if (requestForServer.getFunction().equals("clearCart")) {
            MainController.getInstance().getAccountAreaForCustomerController().clearCart(Long.parseLong(requestForServer.getInputs().get(0)));
            dataOutputStream.writeUTF("successfully cleared");
            dataOutputStream.flush();
        } else if (requestForServer.getFunction().equals("getOnlineSupporters")) {
            List<String> data = MainController.getInstance().getAccountAreaForCustomerController().getOnlineSupporters();
            dataOutputStream.writeUTF(convertListToString(data));
            dataOutputStream.flush();
        } else if (requestForServer.getFunction().equals("getAllAuctionsId")) {
            List<String> auctions = Shop.getInstance().getAllAuctionsList().stream().map(Auction::getAuctionId).map(integer -> "" + integer).collect(Collectors.toList());
            dataOutputStream.writeUTF(convertArrayListToString(new ArrayList<>(auctions)));
            dataOutputStream.flush();
        } else if (requestForServer.getFunction().equals("getAllAuctionsTitle")) {
            List<String> auctions = Shop.getInstance().getAllAuctionsList().stream().map(Auction::getTitle).collect(Collectors.toList());
            dataOutputStream.writeUTF(convertArrayListToString(new ArrayList<>(auctions)));
            dataOutputStream.flush();
        } else if (requestForServer.getFunction().equals("getLastOfferedPriceOfCustomer")) {
            Auction auction = Shop.getInstance().findAuctionById(Integer.parseInt(requestForServer.getInputs().get(0)));
            try {
                dataOutputStream.writeUTF("" + MainController.getInstance().getAccountAreaForCustomerController().getLastOfferedPriceOfCustomer(auction, (Customer) user));
            } catch (CustomerNotFoundInAuctionException e) {
                e.printStackTrace();
                dataOutputStream.writeUTF(e.getMessage());
            } finally {
                dataOutputStream.flush();
            }
        } else if (requestForServer.getFunction().equals("offerNewPrice")) {
            Auction auction = Shop.getInstance().findAuctionById(Integer.parseInt(requestForServer.getInputs().get(0)));
            Customer customer = (Customer) user;
            long offeredPrice = Long.parseLong(requestForServer.getInputs().get(1));
            if (customer.getCredit() >= offeredPrice) {
                if (offeredPrice > auction.getGood().getPriceBySeller(auction.getSeller())) {
                    if (auction.getAllCustomersOffers().containsKey(customer)) {
                        if (auction.getAllCustomersOffers().get(customer) < offeredPrice) {
                            auction.removeOffer(customer);
                            auction.addOffer(customer, offeredPrice);
                            dataOutputStream.writeUTF("your price offered successfully");
                        } else {
                            dataOutputStream.writeUTF("your price offered should be more than previous one.");
                        }
                    } else {
                        auction.addOffer(customer, offeredPrice);
                        dataOutputStream.writeUTF("your price offered successfully");
                    }
                } else {
                    dataOutputStream.writeUTF("you must offer a price more than base price.");
                }
            } else {
                dataOutputStream.writeUTF("you do not have enough credit to offer this price.");
            }
            dataOutputStream.flush();
        } else if (requestForServer.getFunction().equals("getMassages")) {
            dataOutputStream.writeUTF(new Gson().toJson(MainController.getInstance().getAccountAreaForCustomerController().getMassages(requestForServer.getInputs().get(0), requestForServer.getInputs().get(1))));
            dataOutputStream.flush();
        } else if (requestForServer.getFunction().equals("purchaseFileProductByWallet")) {
            try {
                MainController.getInstance().getAccountAreaForCustomerController().purchaseFileProductByWallet(
                        Long.parseLong(requestForServer.getInputs().get(0)), requestForServer.getInputs().get(1), requestForServer.getInputs().get(2),
                        Long.parseLong(requestForServer.getInputs().get(3)), user
                );
                dataOutputStream.writeUTF("purchase successful");
            } catch (Exception e) {
                e.printStackTrace();
                dataOutputStream.writeUTF(e.getMessage());
            } finally {
                dataOutputStream.flush();
            }
        } else if (requestForServer.getFunction().equals("downloadFile")) {
            File file = Shop.getInstance().findFileProductById(Long.parseLong(requestForServer.getInputs().get(0))).getFile();
            byte[] bytes = new byte[16 * 2048 * 4];
            InputStream inputStream = new FileInputStream(file);
            int count;
            while ((count = inputStream.read(bytes)) > 0) {
                dataOutputStream.write(bytes, 0, count);
            }
            inputStream.close();
        } else if (requestForServer.getFunction().equals("getFileOrdersOfCustomer")) {
            dataOutputStream.writeUTF(convertListToString(MainController.getInstance().getAccountAreaForCustomerController().getFileOrdersOfCustomer(user)));
            dataOutputStream.flush();
        } else {
            dataOutputStream.writeUTF("not a proper response");
            dataOutputStream.flush();
        }
    }

    private void accountAreaForManagerHandler(RequestForServer requestForServer) throws Exception {
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
        } else if (requestForServer.getFunction().equals("createSupporterAccount")) {
            String name = requestForServer.getInputs().get(0);
            requestForServer.getInputs().remove(0);
            try {
                MainController.getInstance().getAccountAreaForManagerController().createSupporter(name, requestForServer.getInputs());
                dataOutputStream.writeUTF("user created successfully");
                dataOutputStream.flush();
            } catch (Exception e) {
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
        } else if (requestForServer.getFunction().equals("setBankingFeePercent")) {
            MainController.getInstance().getAccountAreaForManagerController().setBankingFeePercent(requestForServer.getInputs().get(0));
            dataOutputStream.writeUTF("percent of banking fee successfully changed");
            dataOutputStream.flush();
        } else if (requestForServer.getFunction().equals("setMinimumAmountForWallet")) {
            MainController.getInstance().getAccountAreaForManagerController().setMinimumAmountForWallet(requestForServer.getInputs().get(0));
            dataOutputStream.writeUTF("minimum amount successfully changed");
            dataOutputStream.flush();
        } else if (requestForServer.getFunction().equals("getCustomersOrders")) {
            List<String> data = MainController.getInstance().getAccountAreaForManagerController().getCustomersOrders();
            dataOutputStream.writeUTF(convertListToString(data));
            dataOutputStream.flush();
        } else if (requestForServer.getFunction().equals("viewOrderGUI")) {
            List<String> data = MainController.getInstance().getAccountAreaForManagerController().viewOrderGUI(requestForServer.getInputs().get(0));
            dataOutputStream.writeUTF(convertListToString(data));
            dataOutputStream.flush();
        } else if (requestForServer.getFunction().equals("changeOrderStatus")) {
            MainController.getInstance().getAccountAreaForManagerController().changeOrderStatus(requestForServer.getInputs().get(0), requestForServer.getInputs().get(1));
            dataOutputStream.writeUTF("done successfully");
            dataOutputStream.flush();
        } else if (requestForServer.getFunction().equals("getCategoryProperties")) {
            dataOutputStream.writeUTF(convertArrayListToString(MainController.getInstance().getAccountAreaForManagerController().getCategoryProperties(requestForServer.getInputs().get(0))));
            dataOutputStream.flush();
        } else if (requestForServer.getFunction().equals("getSubCategoryProperties")) {
            dataOutputStream.writeUTF(convertArrayListToString(MainController.getInstance().getAccountAreaForManagerController().getSubCategoryProperties(requestForServer.getInputs().get(0))));
            dataOutputStream.flush();
        } else if (requestForServer.getFunction().equals("getOnlineUsers")) {
            dataOutputStream.writeUTF(convertArrayListToString(MainController.getInstance().getAccountAreaForManagerController().getOnlineCustomers()));
            dataOutputStream.flush();
        } else if (requestForServer.getFunction().equals("isOrderForFileProduct")) {
            dataOutputStream.writeUTF(MainController.getInstance().getAccountAreaForManagerController().isOrderForFileProduct(Long.parseLong(requestForServer.getInputs().get(0))) + "");
            dataOutputStream.flush();
        } else if (requestForServer.getFunction().equals("getFileOrderInfoGUI")) {
            dataOutputStream.writeUTF(convertListToString(MainController.getInstance().getAccountAreaForManagerController().getFileOrderInfoGUI(Long.parseLong(requestForServer.getInputs().get(0)))));
            dataOutputStream.flush();
        } else {
            dataOutputStream.writeUTF("not a proper response");
            dataOutputStream.flush();
        }
    }

    private void accountAreaForSellerHandler(RequestForServer requestForServer) throws Exception {
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
            List<Long> products = MainController.getInstance().getAccountAreaForSellerController().viewProducts(Integer.parseInt(requestForServer.getInputs().get(0)), person);
            ArrayList<String> output = new ArrayList<>();
            for (Long product : products) {
                output.add(product + "");
            }
            dataOutputStream.writeUTF(convertArrayListToString(output));
            dataOutputStream.flush();
        } else if (requestForServer.getFunction().equals("isInOff")) {
            dataOutputStream.writeUTF("" + MainController.getInstance().getAccountAreaForSellerController().isInOff(Long.parseLong(requestForServer.getInputs().get(0)), person));
            dataOutputStream.flush();
        } else if (requestForServer.getFunction().equals("createAuction")) {
            int goodId = Integer.parseInt(requestForServer.getInputs().get(0));
            ArrayList<String> fields = new ArrayList<>(requestForServer.getInputs().subList(1, 5));
            try {
                MainController.getInstance().getAccountAreaForSellerController().createAuction(fields, goodId, person);
                dataOutputStream.writeUTF("auction successfully created");
            } catch (FileCantBeSavedException | ProductNotFoundExceptionForSeller | ProductIsAlreadyInAuctionException e) {
                e.printStackTrace();
                dataOutputStream.writeUTF(e.getMessage());
            } finally {
                dataOutputStream.flush();
            }
        } else if (requestForServer.getFunction().equals("getSellerAllAuctionsTitle")) {
            dataOutputStream.writeUTF(convertArrayListToString(MainController.getInstance().getAccountAreaForSellerController().getAllAuctionsTitle(person)));
            dataOutputStream.flush();
        } else if (requestForServer.getFunction().equals("getAuctionProperties")) {
            Auction auction = Shop.getInstance().findAuctionById(Integer.parseInt(requestForServer.getInputs().get(0)));
            ArrayList<String> auctionProperties = new ArrayList<>();
            auctionProperties.add("auction id: " + auction.getAuctionId());
            auctionProperties.add("title: " + auction.getTitle());
            auctionProperties.add("description: " + auction.getDescription());
            auctionProperties.add("start date: " + auction.getStartDate().toString());
            auctionProperties.add("end date: " + auction.getEndDate().toString());
            auctionProperties.add("seller: " + auction.getSeller().getUsername());
            auctionProperties.add("good id: " + auction.getGood().getGoodId());
            auctionProperties.add("good name: " + auction.getGood().getName());
            dataOutputStream.writeUTF(convertArrayListToString(auctionProperties));
            dataOutputStream.flush();
        } else if (requestForServer.getFunction().equals("getSellerAllAuctionsId")) {
            List<String> auctions = Shop.getInstance().getAllAuctionsList().stream().filter(auction -> auction.getSeller().equals(person)).map(Auction::getAuctionId).map(integer -> "" + integer).collect(Collectors.toList());
            dataOutputStream.writeUTF(convertArrayListToString(new ArrayList<>(auctions)));
            dataOutputStream.flush();
        } else if (requestForServer.getFunction().equals("removeAuction")) {
            int auctionId = Integer.parseInt(requestForServer.getInputs().get(0));
            try {
                MainController.getInstance().getAccountAreaForSellerController().removeAuction(auctionId);
                dataOutputStream.writeUTF("auction successfully removed");
            } catch (Exception e) {
                e.printStackTrace();
                dataOutputStream.writeUTF(e.getMessage());
            } finally {
                dataOutputStream.flush();
            }
        } else if (requestForServer.getFunction().equals("endAuction")) {
            int auctionId = Integer.parseInt(requestForServer.getInputs().get(0));
            try {
                MainController.getInstance().getAccountAreaForSellerController().endAuction(auctionId);
                dataOutputStream.writeUTF("auction successfully ended");
            } catch (Exception e) {
                e.printStackTrace();
                dataOutputStream.writeUTF(e.getMessage());
            } finally {
                dataOutputStream.flush();
            }
        } else if (requestForServer.getFunction().equals("addFileProduct")) {
            try {
                MainController.getInstance().getAccountAreaForSellerController().addFileProduct(requestForServer.getInputs(), person);
                dataOutputStream.writeUTF("file product successfully added");
            } catch (FileCantBeSavedException | FileIsAlreadyAddedToActiveProductsException e) {
                e.printStackTrace();
                dataOutputStream.writeUTF(e.getMessage());
            } finally {
                dataOutputStream.flush();
            }
        } else if (requestForServer.getFunction().equals("getFileOrdersOfSeller")) {
            dataOutputStream.writeUTF(convertListToString(MainController.getInstance().getAccountAreaForSellerController().getFileOrdersOfSeller(user)));
            dataOutputStream.flush();
        } else if (requestForServer.getFunction().equals("getFileOrderInfoGUI")) {
            dataOutputStream.writeUTF(convertListToString(MainController.getInstance().getAccountAreaForSellerController().getFileOrderInfoGUI(Long.parseLong(requestForServer.getInputs().get(0)))));
            dataOutputStream.flush();
        } else {
            dataOutputStream.writeUTF("not a proper response");
            dataOutputStream.flush();
        }
    }

    private void accountAreaControllerHandler(RequestForServer requestForServer) throws Exception {
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
        } else if (requestForServer.getFunction().equals("getOrderDetails")) {
            dataOutputStream.writeUTF(convertListToString(MainController.getInstance().getAccountAreaForCustomerController().getOrderDetails(Long.parseLong(requestForServer.getInputs().get(0)), requestForServer.getInputs().get(1), requestForServer.getInputs().get(2))));
            dataOutputStream.flush();
        } else if (requestForServer.getFunction().equals("sendMassage")) {
            MainController.getInstance().getAccountAreaController().sendMassage(new Gson().fromJson(requestForServer.getInputs().get(0), Massage.class));
            dataOutputStream.writeUTF("done successfully");
            dataOutputStream.flush();
        } else if (requestForServer.getFunction().equals("getUserPhoto")) {
            File file = new File("Resources\\UserImages\\" + person.getUsername() + ".jpg");
            byte[] imageBytes;
            if (file.exists()) {
                imageBytes = Files.readAllBytes(Paths.get("Resources/UserImages/" + person.getUsername() + ".jpg"));
            } else {
                imageBytes = Files.readAllBytes(Paths.get("Resources/UserImages/default1.jpg"));
            }
            dataOutputStream.write(imageBytes);
            dataOutputStream.flush();
        } else {
            dataOutputStream.writeUTF("not a proper response");
            dataOutputStream.flush();
        }
    }

    private void auctionControllerHandler(RequestForServer requestForServer) throws Exception {
        if (requestForServer.getFunction().equals("getMassages")) {
            dataOutputStream.writeUTF(new Gson().toJson(MainController.getInstance().getAuctionsController().getMassages(requestForServer.getInputs().get(0))));
            dataOutputStream.flush();
        } else if (requestForServer.getFunction().equals("isCustomerOfferedAPriceInAuction")) {
            int auctionId = Integer.parseInt(requestForServer.getInputs().get(0));
            dataOutputStream.writeUTF(MainController.getInstance().getAuctionsController().isCustomerOfferedAPriceInAuction(user, auctionId) ? "true" : "false");
            dataOutputStream.flush();
        } else if (requestForServer.getFunction().equals("getBestPriceOfAuction")) {
            int auctionId = Integer.parseInt(requestForServer.getInputs().get(0));
            dataOutputStream.writeUTF(MainController.getInstance().getAuctionsController().getBestPriceOfAuction(auctionId) + "");
            dataOutputStream.flush();
        } else {
            dataOutputStream.writeUTF("not a proper response");
            dataOutputStream.flush();
        }
    }

    private void getCurrentPerson() throws Exception {
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
        } else if (user instanceof Supporter) {
            try {
                dataOutputStream.writeUTF("supporter###" + gson.toJson(user, Supporter.class));
                dataOutputStream.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            dataOutputStream.writeUTF("not a proper response");
            dataOutputStream.flush();
        }
    }

    private void loginRegisterControllerHandler(RequestForServer requestForServer) throws Exception {
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
            } catch (IOException | FileCantBeSavedException e) {
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
            MainController.getInstance().getLoginRegisterController().logoutUser(Long.parseLong(requestForServer.getInputs().get(0)));
            Server.removeOnlineUser(requestForServer.getToken());
            dataOutputStream.writeUTF("logout successfully");
            dataOutputStream.flush();
        } else {
            dataOutputStream.writeUTF("not a proper response");
            dataOutputStream.flush();
        }
    }

    private void accountAreaForSupporterHandler(RequestForServer requestForServer) throws Exception {
        if (requestForServer.getFunction().equals("getCustomersChat")) {
            dataOutputStream.writeUTF(convertListToString(MainController.getInstance().getAccountAreaForSupporter().getCustomersChat(requestForServer.getInputs().get(0))));
            dataOutputStream.flush();
        } else {
            dataOutputStream.writeUTF("not a proper response");
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

    private ArrayList<String> convertArrayListLongToString(List<Long> inputs) {
        ArrayList<String> output = new ArrayList<>();
        for (Long id : inputs) {
            output.add(id + "");
        }
        return output;
    }
}
