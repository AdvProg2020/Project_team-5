package ApProject_OnlineShop.controller.products;

import ApProject_OnlineShop.controller.MainController;
import ApProject_OnlineShop.database.Database;
import ApProject_OnlineShop.exception.FileCantBeSavedException;
import ApProject_OnlineShop.exception.productExceptions.DontHaveEnoughNumberOfThisProduct;
import ApProject_OnlineShop.exception.productExceptions.ProductWithThisIdNotExist;
import ApProject_OnlineShop.model.Shop;
import ApProject_OnlineShop.model.persons.Customer;
import ApProject_OnlineShop.model.persons.Person;
import ApProject_OnlineShop.model.persons.Seller;
import ApProject_OnlineShop.model.productThings.Comment;
import ApProject_OnlineShop.model.productThings.Good;
import ApProject_OnlineShop.model.productThings.GoodInCart;
import ApProject_OnlineShop.model.productThings.SellerRelatedInfoAboutGood;
import ApProject_OnlineShop.model.requests.AddingCommentRequest;
import javafx.scene.image.Image;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class ProductController {
//    private Good good;
//
//    public Good getGood() {
//        return good;
//    }
//
//    public void setGood(Good good) {
//        this.good = good;
//        if (good != null) {
//            if (MainController.getInstance().getCurrentPerson() instanceof Customer || MainController.getInstance().getCurrentPerson() == null) {
//                good.setSeenNumber(good.getSeenNumber() + 1);
//            }
//            try {
//                Database.getInstance().saveItem(good);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }

//    public void setGoodById(long goodId) {
//        setGood(Shop.getInstance().findGoodById(goodId));
//    }

//    public String digest() {
//        return good.toString();
//    }

//    public String getSellersOfAGood() {
//        String output = "";
//        int i = 1;
//        for (SellerRelatedInfoAboutGood infoAboutGood : good.getSellerRelatedInfoAboutGoods()) {
//            if (i < good.getSellerRelatedInfoAboutGoods().size())
//                output += ((i++) + "-" + infoAboutGood.getSeller().getUsername() + "\n");
//            else
//                output += ((i++) + "-" + infoAboutGood.getSeller().getUsername());
//        }
//        return output;
//    }

    public int numbersOfSellers(Good product) {
        return product.getSellerRelatedInfoAboutGoods().size();
    }

//    public void addGoodToCart(int number, int sellerNumber) throws DontHaveEnoughNumberOfThisProduct {
//        SellerRelatedInfoAboutGood sellerRelatedInfoAboutGood = good.getSellerRelatedInfoAboutGoods().get(sellerNumber - 1);
//        if (sellerRelatedInfoAboutGood.getAvailableNumber() < number)
//            throw new DontHaveEnoughNumberOfThisProduct();
//        Shop.getInstance().addGoodToCart(good, sellerRelatedInfoAboutGood.getSeller(), number);
//    }

    public void addGoodToCartGUI(String seller, long productId, long cartId) throws Exception {
        Good good = Shop.getInstance().findGoodById(productId);
        SellerRelatedInfoAboutGood sellerRelatedInfoAboutGood = null;
        for (SellerRelatedInfoAboutGood relatedInfoAboutGood : good.getSellerRelatedInfoAboutGoods()) {
            if (relatedInfoAboutGood.getSeller().getUsername().equals(seller)) {
                sellerRelatedInfoAboutGood = relatedInfoAboutGood;
                break;
            }
        }
        if (sellerRelatedInfoAboutGood.getAvailableNumber() < 1)
            throw new DontHaveEnoughNumberOfThisProduct();
        boolean flag = false;
        for (GoodInCart goodInCart : Shop.getInstance().getCart(cartId)) {
            if (goodInCart.getGood().equals(good) && !goodInCart.getSeller().getUsername().equals(seller)) {
                throw new Exception("you can't buy a same product from two different seller!");
            }
        }
        for (GoodInCart goodInCart : Shop.getInstance().getCart(cartId)) {
            if (goodInCart.getGood().equals(good) && goodInCart.getSeller().getUsername().equals(seller)) {
                Shop.getInstance().increaseGoodInCartNumber(good.getGoodId(), cartId);
                flag = true;
                return;
            }
        }
        if (!flag)
            Shop.getInstance().addGoodToCart(good, (Seller) Shop.getInstance().findUser(seller), 1, cartId);
    }

//    public int getAvailableNumberOfAProductByASeller(int sellerNumber) {
//        SellerRelatedInfoAboutGood sellerRelatedInfoAboutGood = good.getSellerRelatedInfoAboutGoods().get(sellerNumber - 1);
//        return sellerRelatedInfoAboutGood.getAvailableNumber();
//    }

//    public String attributes() {
//        String output = good.getDetails();
//        HashMap<String, String> categoryProperties = good.getCategoryProperties();
//        for (String s : categoryProperties.keySet()) {
//            output += ("\n" + s + " : " + categoryProperties.get(s).toString());
//        }
//        return output;
//    }

//    public String compareWithAnotherProduct(long id) throws ProductWithThisIdNotExist {
//        if (Shop.getInstance().findGoodById(id) == null)
//            throw new ProductWithThisIdNotExist();
//        Good good2 = Shop.getInstance().findGoodById(id);
//        String output = "+---------------------------+---------------------------+---------------------------+\n";
//        output += "| property                  | good 1                    | good 2                    |\n";
//        output += "+---------------------------+---------------------------+---------------------------+\n";
//        output += String.format("| %-25s | %-25s | %-25s |%n", "name", good.getName(), good2.getName());
//        output += String.format("| %-25s | %-25s | %-25s |%n", "brand", good.getBrand(), good2.getBrand());
//        output += String.format("| %-25s | %-25s | %-25s |%n", "average rate", good.getAverageRate(), good2.getAverageRate());
//        output += String.format("| %-25s | %-25s | %-25s |%n", "subCategory", good.getSubCategory().getName(), good2.getSubCategory().getName());
//        output += String.format("| %-25s | %-25s | %-25s |%n", "modification date", good.getModificationDate(), good2.getModificationDate());
//        output += String.format("| %-25s | %-25s | %-25s |%n", "seen number", good.getSeenNumber(), good2.getSeenNumber());
//        output += String.format("| %-25s | %-25s | %-25s |%n", "number of sellers", numbersOfSellers(good), numbersOfSellers(good2));
//        output += String.format("| %-25s | %-25s | %-25s |%n", "minmum price of product", good.getMinimumPrice(), good2.getMinimumPrice());
//        output += "+---------------------------+---------------------------+---------------------------+\n";
//        return output;
//    }

    public ArrayList<String> compareWithAnotherProductGUI(long id1, long id) {
        Good good = Shop.getInstance().findGoodById(id1);
        Good good2 = Shop.getInstance().findGoodById(id);
        ArrayList<String> output = new ArrayList<>();
        output.add(good2.getName());
        output.add(good.getName());
        output.add(good2.getBrand());
        output.add(good.getBrand());
        output.add(good2.getAverageRate() + "");
        output.add(good.getAverageRate() + "");
        output.add(good2.getSubCategory().getName());
        output.add(good.getSubCategory().getName());
        output.add(good2.getModificationDate().toString());
        output.add(good.getModificationDate().toString());
        output.add(good2.getSeenNumber() + "");
        output.add(good.getSeenNumber() + "");
        output.add(numbersOfSellers(good2) + "");
        output.add(numbersOfSellers(good) + "");
        output.add(good2.getMinimumPrice() + "");
        output.add(good.getMinimumPrice() + "");
        return output;
    }

//    public String showComments() {
//        String output = "--------------------------------------------\n";
//        output += ("average rate of this product is = " + good.getAverageRate());
//        output += "\n--------------------------------------------";
//        for (Comment comment : good.getComments()) {
//            output += ("\n" + comment.toString() + "--------------------------------------------");
//        }
//        return output;
//    }

    public List<String> getMainInfo(long id) {
        Good good = Shop.getInstance().findGoodById(id);
        List<String> goodInfo = new ArrayList<>();
        goodInfo.add(good.getName());
        goodInfo.add(good.getBrand());
        goodInfo.add(good.getSubCategory().getParentCategory().getName());
        goodInfo.add(good.getSubCategory().getName());
        goodInfo.add("" + good.getAverageRate() / 2);
        goodInfo.add("" + good.getSeenNumber());
        return goodInfo;
    }

    public List<SellerRelatedInfoAboutGood> getSellersInfo(long id) {
        Good good = Shop.getInstance().findGoodById(id);
        return good.getSellerRelatedInfoAboutGoods();
    }

    public boolean isInOffBySeller(Seller seller, long id) {
        Good good = Shop.getInstance().findGoodById(id);
        if (good.getPriceBySeller(seller) == Shop.getInstance().getFinalPriceOfAGood(good, seller))
            return false;
        return true;
    }

    public void addComment(String title, String content, Person person, long id) throws IOException, FileCantBeSavedException {
        boolean didCommenterBoughtThisProduct = false;
        Good good = Shop.getInstance().findGoodById(id);
        if (person instanceof Customer) {
            if (((Customer) person).hasBuyProduct(id))
                didCommenterBoughtThisProduct = true;
        }
        AddingCommentRequest addingCommentRequest = new AddingCommentRequest(person
                , good, title, content, didCommenterBoughtThisProduct);
        Shop.getInstance().addRequest(addingCommentRequest);
        Database.getInstance().saveItem(addingCommentRequest);
    }

    public List<Long> getAllGoodsIds() {
        return Shop.getInstance().getAllGoods().stream().map(Good::getGoodId).collect(Collectors.toList());
    }

    public List<Long> getOffGoods() {
        return Shop.getInstance().getOffGoods().stream().map(Good::getGoodId).collect(Collectors.toList());
    }

    public boolean isSubCategoryEquals(long productId1, long productId2) {
        return Shop.getInstance().findGoodById(productId1).getSubCategory().equals(Shop.getInstance().findGoodById(productId2).getSubCategory());
    }

    public byte[] getProductImage(long productId) throws IOException {
        return Files.readAllBytes(Paths.get("Resources/productImages/" + productId + ".jpg"));
    }
}
