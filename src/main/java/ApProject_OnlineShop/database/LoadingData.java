package ApProject_OnlineShop.database;

import ApProject_OnlineShop.controller.MainController;
import ApProject_OnlineShop.model.Shop;
import ApProject_OnlineShop.model.ShopBankAccount;
import ApProject_OnlineShop.model.category.Category;
import ApProject_OnlineShop.model.category.SubCategory;
import ApProject_OnlineShop.model.orders.*;
import ApProject_OnlineShop.model.persons.*;
import ApProject_OnlineShop.model.productThings.*;
import ApProject_OnlineShop.model.requests.*;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class LoadingData {
    private Gson yaGson;

    public LoadingData() {
        yaGson = new Gson();
        File file = new File("Resources");
        if (!file.exists())
            file.mkdir();
        file = new File("Resources\\Users");
        if (!file.exists())
            file.mkdir();
        file = new File("Resources\\Orders");
        if (!file.exists())
            file.mkdir();
        file = new File("Resources\\UserImages");
        if (!file.exists())
            file.mkdir();
        file = new File("Resources\\productImages");
        if (!file.exists())
            file.mkdir();
        file = new File("Resources\\BankAccounts");
        if (!file.exists())
            file.mkdir();
        file = new File("Resources\\Auctions");
        if (!file.exists())
            file.mkdir();
        file = new File("Resources\\fileProductsInfo");
        if (!file.exists())
            file.mkdir();
        file = new File("Resources\\fileProducts");
        if (!file.exists())
            file.mkdir();
    }

    public void loadManager() throws IOException {
        File[] files = loadFolder("Resources\\Users\\Managers");
        if (files != null)
            for (File file : files) {
                Shop.getInstance().addPerson(yaGson.fromJson(readFile(file), Manager.class));
            }
    }

    public void loadCustomer() throws IOException {
        File[] files = loadFolder("Resources\\Users\\Customers");
        if (files != null)
            for (File file : files) {
                Shop.getInstance().addPerson(yaGson.fromJson(readFile(file), Customer.class));
            }
    }

    public void loadSeller() throws IOException {
        File[] files = loadFolder("Resources\\Users\\Sellers");
        if (files != null)
            for (File file : files) {
                Shop.getInstance().addPerson(yaGson.fromJson(readFile(file), Seller.class));
            }
    }

    public void loadSupporter() throws IOException {
        File[] files = loadFolder("Resources\\Users\\Supporters");
        if (files != null)
            for (File file : files) {
                Shop.getInstance().addPerson(yaGson.fromJson(readFile(file), Supporter.class));
            }
    }

    public void loadCompany() throws IOException {
        File[] files = loadFolder("Resources\\Companies");
        if (files != null) {
            for (File file : files) {
                Shop.getInstance().addCompany(yaGson.fromJson(readFile(file), Company.class));
            }
            if (Shop.getInstance().getAllCompanies().size() != 0) {
                List<Long> ids = new ArrayList<>(Shop.getInstance().getAllCompanies().keySet());
                Company.setCompanyIdCounter(getMaximumOfNumbers(ids) + 1);
            }
        }
    }

    public void loadShopBankAccount() throws IOException {
        File file = new File("Resources\\BankAccounts\\Shop.json");
        if (file.exists())
            Shop.getInstance().setShopBankAccount(yaGson.fromJson(readFile(file), ShopBankAccount.class));
        if (!file.exists()){
            String accountId = MainController.getInstance().getBankAccountsController().createBankAccount("Shop","Shop","Shop","Shop1","Shop1");
            Shop.getInstance().setShopBankAccount(new ShopBankAccount("Shop","Shop1", accountId));
        }
    }

    public void loadInfoAboutGood() throws IOException {
        File[] files = loadFolder("Resources\\ProductsInfo");
        if (files != null) {
            for (File file : files) {
                Shop.getInstance().addSellerRelatedInfoAboutGood(yaGson.fromJson(readFile(file), SellerRelatedInfoAboutGood.class));
            }
            if (Shop.getInstance().getAllSellerRelatedInfoAboutGood().size() != 0) {
                List<Long> ids = new ArrayList<>(Shop.getInstance().getAllSellerRelatedInfoAboutGood().keySet());
                SellerRelatedInfoAboutGood.setSellerRelatedInfoAboutGoodCount(getMaximumOfNumbers(ids) + 1);
            }
        }
    }

    public void loadProduct() throws IOException {
        File[] files = loadFolder("Resources\\Products");
        if (files != null) {
            for (File file : files) {
                Shop.getInstance().addGoodToAllGoods(yaGson.fromJson(readFile(file), Good.class));
            }
            if (Shop.getInstance().getAllGoods().size() != 0) {
                if (Shop.getInstance().getAllFileProductsList().size() != 0) {
                    if (Shop.getInstance().getAllRequest().size() != 0) {
                        Good.setGoodsCount(Math.max(getMaximumOfNumbers(Shop.getInstance().getAllGoods().stream().map(Good::getGoodId)
                                .collect(Collectors.toList())), getMaximumOfNumbers(Shop.getInstance().getAllFileProductsList().stream().map(FileProduct::getFileProductId).collect(Collectors.toList()))) + getMaximumOfNumbers(Shop.getInstance()
                                .getAllRequest().stream().map(Request::getRequestId).collect(Collectors.toList())) + 1);
                    } else {
                        Good.setGoodsCount(Math.max(getMaximumOfNumbers(Shop.getInstance().getAllGoods().stream().map(Good::getGoodId)
                                .collect(Collectors.toList())), getMaximumOfNumbers(Shop.getInstance().getAllFileProductsList().stream().map(FileProduct::getFileProductId).collect(Collectors.toList()))) + getMaximumOfNumbers(Shop.getInstance()
                                .getAllRequest().stream().map(Request::getRequestId).collect(Collectors.toList())) + 1);
                    }
                } else {
                    if (Shop.getInstance().getAllRequest().size() != 0) {
                        Good.setGoodsCount(getMaximumOfNumbers(Shop.getInstance().getAllGoods().stream().map(Good::getGoodId)
                                .collect(Collectors.toList())) + getMaximumOfNumbers(Shop.getInstance()
                                .getAllRequest().stream().map(Request::getRequestId).collect(Collectors.toList())) + 1);
                    } else {
                        Good.setGoodsCount(getMaximumOfNumbers(Shop.getInstance().getAllGoods().stream().map(Good::getGoodId)
                                .collect(Collectors.toList())) + 1);
                    }
                }
            }
        }
    }

    public void loadDiscount() throws IOException {
        File[] files = loadFolder("Resources\\Discounts");
        if (files != null) {
            for (File file : files) {
                Shop.getInstance().addDiscountCode(yaGson.fromJson(readFile(file), DiscountCode.class));
            }
            if (Shop.getInstance().getAllDiscountCodes().size() != 0)
                DiscountCode.setDiscountCodeCount(getMaximumOfNumbers(Shop.getInstance().getAllDiscountCodes()
                        .stream().map(DiscountCode::getId).collect(Collectors.toList())) + 1);
        }
    }

    public void loadGoodsInCarts() throws IOException {
        File[] files = loadFolder("Resources\\GoodsInCarts");
        if (files != null) {
            for (File file : files) {
                Shop.getInstance().addGoodInCart(yaGson.fromJson(readFile(file), GoodInCart.class));
            }
            if (Shop.getInstance().getAllGoodInCarts().size() != 0) {
                List<Long> ids = new ArrayList<>(Shop.getInstance().getAllGoodInCarts().keySet());
                GoodInCart.setGoodInCartCounter(getMaximumOfNumbers(ids) + 1);
            }
        }
    }

    public void loadComment() throws IOException {
        File[] files = loadFolder("Resources\\Comments");
        if (files != null) {
            for (File file : files) {
                Shop.getInstance().addAComment(yaGson.fromJson(readFile(file), Comment.class));
            }
            if (Shop.getInstance().getAllComments().size() != 0) {
                List<Long> ids = new ArrayList<>(Shop.getInstance().getAllComments().keySet());
                Comment.setCommentIdCounter(getMaximumOfNumbers(ids) + 1);
            }
        }
    }

    public void loadOff() throws IOException {
        File[] files = loadFolder("Resources\\Offs");
        if (files != null) {
            for (File file : files) {
                Shop.getInstance().addOff(yaGson.fromJson(readFile(file), Off.class));
            }
            if (Shop.getInstance().getOffs().size() != 0)
                Off.setOffsCount(getMaximumOfNumbers(Shop.getInstance().getOffs().stream().map(Off::getOffId)
                        .collect(Collectors.toList())) + 1);
        }
    }

    public void loadRate() throws IOException {
        File[] files = loadFolder("Resources\\Rates");
        if (files != null) {
            for (File file : files) {
                Shop.getInstance().addRate(yaGson.fromJson(readFile(file), Rate.class));
            }
        }
    }

    public void loadCategory() throws IOException {
        File[] files = loadFolder("Resources\\Categories");
        if (files != null)
            for (File file : files) {
                Shop.getInstance().addCategory(yaGson.fromJson(readFile(file), Category.class));
            }
    }

    public void loadSubCategory() throws IOException {
        File[] files = loadFolder("Resources\\SubCategories");
        if (files != null)
            for (File file : files) {
                Shop.getInstance().addSubCategory(yaGson.fromJson(readFile(file), SubCategory.class));
            }
    }

    public void loadOrderForSeller() throws IOException {
        File[] files = loadFolder("Resources\\Orders\\OrderForSellers");
        if (files != null) {
            for (File file : files) {
                Shop.getInstance().addOrder(yaGson.fromJson(readFile(file), OrderForSeller.class));
            }
        }
    }

    public void loadOrderForCustomer() throws IOException {
        File[] files = loadFolder("Resources\\Orders\\OrderForCustomers");
        if (files != null) {
            for (File file : files) {
                Shop.getInstance().addOrder(yaGson.fromJson(readFile(file), OrderForCustomer.class));
            }
            if (Shop.getInstance().getHasMapOfOrders().size() != 0) {
                List<Long> ids = new ArrayList<>(Shop.getInstance().getHasMapOfOrders().keySet());
                Order.setOrdersCount(getMaximumOfNumbers(ids) + 1);
            }
        }
    }

    public void loadOrderFileProductForSeller() throws IOException {
        File[] files = loadFolder("Resources\\Orders\\OrderFileProductForSellers");
        if (files != null) {
            for (File file : files) {
                Shop.getInstance().addOrder(yaGson.fromJson(readFile(file), OrderFileProductForSeller.class));
            }
        }
    }

    public void loadOrderFileProductForCustomer() throws IOException {
        File[] files = loadFolder("Resources\\Orders\\OrderFileProductForCustomers");
        if (files != null) {
            for (File file : files) {
                Shop.getInstance().addOrder(yaGson.fromJson(readFile(file), OrderFileProductForCustomer.class));
            }
        }
    }

    public void loadRequests() throws IOException {
        File[] files = loadFolder("Resources\\Requests");
        if (files != null) {
            for (File file : files) {
                loadRequestByType(file);
            }
            if (Shop.getInstance().getAllRequest().size() != 0)
                Request.setRequestCount(getMaximumOfNumbers(Shop.getInstance()
                        .getAllRequest().stream().map(Request::getRequestId).collect(Collectors.toList())) + 1);
        }
    }

    private void loadRequestByType(File file) throws IOException {
        String name = file.getName();
        if (name.startsWith("request_AddingCommentRequest")) {
            Shop.getInstance().addRequest(yaGson.fromJson(readFile(file), AddingCommentRequest.class));
        } else if (name.startsWith("request_AddingGoodRequest")) {
            Shop.getInstance().addRequest(yaGson.fromJson(readFile(file), AddingGoodRequest.class));
        } else if (name.startsWith("request_AddingOffRequest")) {
            Shop.getInstance().addRequest(yaGson.fromJson(readFile(file), AddingOffRequest.class));
        } else if (name.startsWith("request_EditingGoodRequest")) {
            Shop.getInstance().addRequest(yaGson.fromJson(readFile(file), EditingGoodRequest.class));
        } else if (name.startsWith("request_EditingOffRequest")) {
            Shop.getInstance().addRequest(yaGson.fromJson(readFile(file), EditingOffRequest.class));
        } else if (name.startsWith("request_RegisteringSellerRequest")) {
            Shop.getInstance().addRequest(yaGson.fromJson(readFile(file), RegisteringSellerRequest.class));
        } else throw new IOException();
    }

    public void loadAuctions() throws IOException {
        File[] files = loadFolder("Resources\\Auctions");
        if (files != null) {
            for (File file : files) {
                Shop.getInstance().addAuction(yaGson.fromJson(readFile(file), Auction.class));
            }
            if (Shop.getInstance().getAllAuctions().size() != 0) {
                List<Long> ids = new ArrayList<>();
                for (Integer id : Shop.getInstance().getAllAuctions().keySet()) {
                    ids.add(Long.valueOf(id));
                }
                Auction.setAuctionsCount((int)getMaximumOfNumbers(ids) + 1);
            }
        }
    }

    public void loadFileProduct() throws IOException {
        File[] files = loadFolder("Resources\\fileProductsInfo");
        if (files != null) {
            for (File file : files) {
                Shop.getInstance().addFileProduct(yaGson.fromJson(readFile(file), FileProduct.class));
            }
        }
    }

    private File[] loadFolder(String folderPath) {
        File file = new File(folderPath);
        if (!file.exists())
            file.mkdir();
        return file.listFiles();
    }

    private String readFile(File file) throws IOException {
        return Files.readString(file.toPath());
    }

    private long getMaximumOfNumbers(List<Long> numbers) {
        if (numbers != null)
            return Collections.max(numbers);
        return 0;
    }

}
