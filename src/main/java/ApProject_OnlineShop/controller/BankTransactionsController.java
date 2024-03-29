package ApProject_OnlineShop.controller;

import ApProject_OnlineShop.model.Shop;
import ApProject_OnlineShop.model.persons.Customer;
import ApProject_OnlineShop.model.persons.Person;
import ApProject_OnlineShop.model.persons.Seller;

import java.io.IOException;
import java.util.regex.Pattern;

public class BankTransactionsController {


    public String moveMoneyFromUserToShop(String username, String password, String money) throws IOException {
        String token = MainController.getInstance().getBankAccountsController().getToken(username, password);
        if (token.startsWith("invalid"))
            return token;
        String receiptId = "";
        if (Shop.getInstance().findUser(username) instanceof Customer) {
            Customer user = (Customer) Shop.getInstance().findUser(username);
            receiptId = MainController.getInstance().getBankAccountsController().createReceipt(token, "move", money, user.getBankAccountId(), Shop.getInstance().getShopBankId(), "");
        }else if (Shop.getInstance().findUser(username) instanceof Seller) {
            Seller user = (Seller) Shop.getInstance().findUser(username);
            receiptId = MainController.getInstance().getBankAccountsController().createReceipt(token, "move", money, user.getBankAccountId(), Shop.getInstance().getShopBankId(), "");
        }
        if (!Pattern.matches("[\\d]+", receiptId))
            return receiptId;
        String finalResponse = MainController.getInstance().getBankAccountsController().pay(receiptId);
        return finalResponse;
    }

    public String increaseCustomerCredit(String username, String password, String money) throws IOException {
        String finalResponse = moveMoneyFromUserToShop(username, password, money);
        if (finalResponse.equals("done successfully")) {
            Customer user = (Customer) Shop.getInstance().findUser(username);
            user.setCredit(user.getCredit() + Long.parseLong(money));
        }
        return finalResponse;
    }

    public String withdrawMoneySeller(String username, String password, String money) throws IOException {
        String token = MainController.getInstance().getBankAccountsController().getToken("Shop", Shop.getInstance().getShopBankAccount().getPassword());
        if (token.startsWith("invalid"))
            return token;
        if (Pattern.matches("[\\d]+", money)) {
            long balance = ((Seller) Shop.getInstance().findUser(username)).getBalance();
            if (balance - Long.parseLong(money) < Shop.getInstance().getShopBankAccount().getMinimumAmount())
                return "not enough money in account";
        }
        Seller user = (Seller) Shop.getInstance().findUser(username);
        String receiptId = MainController.getInstance().getBankAccountsController().createReceipt(token, "move", money, Shop.getInstance().getShopBankId(), user.getBankAccountId(), "");
        if (!Pattern.matches("[\\d]+", receiptId))
            return receiptId;
        String finalResponse = MainController.getInstance().getBankAccountsController().pay(receiptId);
        if (finalResponse.equals("done successfully"))
            user.setBalance(user.getBalance() - Long.parseLong(money));
        return finalResponse;
    }

    public String depositMoneySeller(String username, String password, String money) throws IOException {
        String finalResponse = moveMoneyFromUserToShop(username, password, money);
        if (finalResponse.equals("done successfully")) {
            Seller user = (Seller) Shop.getInstance().findUser(username);
            user.setBalance(user.getBalance() + Long.parseLong(money));
        }
        return finalResponse;
    }

    public void payMoneyToSellerAfterPurchaseByWallet(String money, String username) {
        Seller seller = (Seller) Shop.getInstance().findUser(username);
        seller.setBalance(seller.getBalance() + (Long.parseLong(money) * (100 - Shop.getInstance().getShopBankAccount().getBankingFeePercent()) / 100));
    }

    public String depositFirstAfterCreating(String username, String password, String money) throws IOException {
        String token = MainController.getInstance().getBankAccountsController().getToken(username, password);
        if (token.startsWith("invalid"))
            return token;
        Person user = Shop.getInstance().findUser(username);
        String receiptId = "";
        if (user instanceof Customer) {
            Customer customer = (Customer) user;
            receiptId = MainController.getInstance().getBankAccountsController().createReceipt(token, "deposit", money, "-1", customer.getBankAccountId(), "");
        } else if (user instanceof Seller) {
            Seller seller = (Seller) user;
            receiptId = MainController.getInstance().getBankAccountsController().createReceipt(token, "deposit", money, "-1", seller.getBankAccountId(), "");
        }
        if (!Pattern.matches("[\\d]+", receiptId))
            return receiptId;
        return MainController.getInstance().getBankAccountsController().pay(receiptId);
    }
}
