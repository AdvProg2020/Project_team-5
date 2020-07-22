package ApProject_OnlineShop.bank;

import ApProject_OnlineShop.bank.DateBaseForBank.DataBaseForBank;

import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Pattern;

public class BankServerMain {
    public static void main(String[] args) throws IOException {
//        String input;
//        Scanner scanner = new Scanner(System.in);
//        do {
//            System.out.println("enter port: example: [port 8080]");
//            input = scanner.nextLine();
//        } while (!Pattern.matches("port [\\d]{4}", input));
//        String port = input.substring("port ".length());
//        BankServer bankServer=new BankServer(Integer.parseInt(port));
        DataBaseForBank.getInstance().initializeBankServer();
        BankServer.getInstance().run();
    }
}
