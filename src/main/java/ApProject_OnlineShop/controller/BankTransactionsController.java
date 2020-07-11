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
        String response = MainController.getInstance().getBankAccountsController().getToken(username, password);
        if (response.startsWith("invalid"))
            return response;
        Customer user = (Customer) Shop.getInstance().findUser(username);
        String receiptId = MainController.getInstance().getBankAccountsController().createReceipt(response, "move", money, user.getBankAccountId(),Shop.getInstance().getShopBankId(), "");
        if (!Pattern.matches("[\\d+]", receiptId))
            return receiptId;
        String finalResponse = MainController.getInstance().getBankAccountsController().pay(receiptId);
        if(finalResponse.equals("done successfully"))
            user.setCredit(user.getCredit() + Long.parseLong(money));
        return finalResponse;
    }
}
