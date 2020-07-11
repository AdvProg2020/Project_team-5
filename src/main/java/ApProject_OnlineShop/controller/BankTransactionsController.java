package ApProject_OnlineShop.controller;

import ApProject_OnlineShop.model.Shop;
import ApProject_OnlineShop.model.persons.Customer;

import java.io.IOException;
import java.net.Socket;
import java.util.regex.Pattern;

public class BankTransactionsController {
    private int bankPort;

    public BankTransactionsController(int bankPort) {
        this.bankPort = bankPort;
    }

    public String increaseCustomerCredit(String username, String password, String money) throws IOException {
        String token = MainController.getInstance().getBankAccountsController().getToken(username, password);
        if (token.startsWith("invalid"))
            return token;
        if (Pattern.matches("[\\d+]", money)) {
            String balance = MainController.getInstance().getBankAccountsController().getBalance(token);
            if (Integer.parseInt(balance) - Integer.parseInt(money) < Shop.getInstance().getShopBankAccount().getMinimumAmount())
                return "not enough credit in account";
        }
        Customer user = (Customer) Shop.getInstance().findUser(username);
        String receiptId = MainController.getInstance().getBankAccountsController().createReceipt(token, "move", money, user.getBankAccountId(), Shop.getInstance().getShopBankId(), "");
        if (!Pattern.matches("[\\d+]", receiptId))
            return receiptId;
        String finalResponse = MainController.getInstance().getBankAccountsController().pay(receiptId);
        if (finalResponse.equals("done successfully"))
            user.setCredit(user.getCredit() + Long.parseLong(money));
        return finalResponse;
    }
}
