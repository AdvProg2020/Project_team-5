package controller.products;

import exception.DontHaveEnoughNumberOfThisProduct;
import exception.ProductWithThisIdNotExist;
import model.Shop;
import model.productThings.Comment;
import model.productThings.Good;
import model.productThings.SellerRelatedInfoAboutGood;

import java.util.HashMap;

public class ProductController {
    private Good good;

    public Good getGood() {
        return good;
    }

    public void setGood(Good good) {
        this.good = good;
        good.setSeenNumber(good.getSeenNumber() + 1);
    }

    public String digest() {
        return good.toString();
    }

    public String getSellersOfAGood() {
        String output = "";
        int i = 1;
        for (SellerRelatedInfoAboutGood infoAboutGood : good.getSellerRelatedInfoAboutGoods()) {
            if (i < good.getSellerRelatedInfoAboutGoods().size())
                output += ((i++) + "-" + infoAboutGood.getSeller().getUsername() + "\n");
            else
                output += ((i++) + "-" + infoAboutGood.getSeller().getUsername());
        }
        return output;
    }

    public int numbersOfSellers(Good product) {
        return product.getSellerRelatedInfoAboutGoods().size();
    }

    public void addGoodToCart(int number, int sellerNumber) throws DontHaveEnoughNumberOfThisProduct {
        SellerRelatedInfoAboutGood sellerRelatedInfoAboutGood = good.getSellerRelatedInfoAboutGoods().get(sellerNumber - 1);
        if (sellerRelatedInfoAboutGood.getAvailableNumber() < number)
            throw new DontHaveEnoughNumberOfThisProduct();
        Shop.getInstance().addGoodToCart(good, sellerRelatedInfoAboutGood.getSeller(), number);
    }

    public int getAvailableNumberOfAProductByASeller(int sellerNumber) {
        SellerRelatedInfoAboutGood sellerRelatedInfoAboutGood = good.getSellerRelatedInfoAboutGoods().get(sellerNumber - 1);
        return sellerRelatedInfoAboutGood.getAvailableNumber();
    }

    public String attributes() {
        String output = good.getDetails();
        HashMap<String, Object> categoryPropeties = good.getCategoryProperties();
        for (String s : categoryPropeties.keySet()) {
            output += ("\n" + s + " : " + categoryPropeties.get(s).toString());
        }
        return output;
    }

    public String compareWithAnotherProduct(long id) throws ProductWithThisIdNotExist {
        if (Shop.getInstance().findGoodById(id) == null)
            throw new ProductWithThisIdNotExist();
        Good good2 = Shop.getInstance().findGoodById(id);
        String output = "+-----------------+-----------------+-----------------+\n";
        output += "| property        | good 1          | good 2          |\n";
        output += "+-----------------+-----------------+-----------------+\n";
        output += String.format("| %-15s | %-15s | %-15s |%n", "name", good.getName(), good2.getName());
        output += String.format("| %-15s | %-15s | %-15s |%n", "brand", good.getBrand(), good2.getBrand());
        output += String.format("| %-15s | %-15s | %-15s |%n", "average rate", good.getAverageRate(), good2.getAverageRate());
        output += String.format("| %-15s | %-15s | %-15s |%n", "subCategory", good.getSubCategory().getName(), good2.getSubCategory().getName());
        output += String.format("| %-15s | %-15s | %-15s |%n", "modification date", good.getModificationDate(), good2.getModificationDate());
        output += String.format("| %-15s | %-15s | %-15s |%n", "seen number", good.getSeenNumber(), good2.getSeenNumber());
        output += String.format("| %-15s | %-15s | %-15s |%n", "number of sellers", numbersOfSellers(good), numbersOfSellers(good2));
        output += String.format("| %-15s | %-15s | %-15s |%n", "minmum price of product", good.getMinimumPrice(), good2.getMinimumPrice());
        output += "+-----------------+-----------------+-----------------+\n";
        return output;
    }

    public String showComments() {
        String output = "--------------------------------------------";
        output += ("average rate of this product is = " + good.getAverageRate());
        output += "--------------------------------------------";
        for (Comment comment : good.getComments()) {
            output += ("\n" + comment.toString() + "--------------------------------------------");
        }
        return output;
    }
}
