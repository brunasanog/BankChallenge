package br.com.compass;

import java.util.Scanner;

import model.Account;
import model.User;
import service.AccountService;
import ui.UserInteraction;
import service.ValidationService;
import service.UserService;
import util.ValidationUtil;

public class App {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        mainMenu(scanner);

        scanner.close();
        System.out.println("Application closed");
    }

    public static void mainMenu(Scanner scanner) {
        boolean running = true;
        UserService userService = new UserService();
        ValidationService authService = new ValidationService();
        ValidationUtil validationUtil = new ValidationUtil();
        AccountService accountService = new AccountService();
        UserInteraction userInteraction = new UserInteraction(scanner, userService, authService, accountService, validationUtil);

        while (running) {
            System.out.println("========= Main Menu =========");
            System.out.println("|| 1. Login                ||");
            System.out.println("|| 2. Account Opening      ||");
            System.out.println("|| 0. Exit                 ||");
            System.out.println("=============================");
            System.out.print("Choose an option: ");

            int option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1:
                    userInteraction.login();
                    break;

                case 2:
                    userInteraction.openAccount();
                    break;

                case 0:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option! Please try again.");
            }
        }
    }

    public static void bankMenu(Scanner scanner, User user, UserInteraction userInteraction, Account selectedAccount, AccountService accountService) {
        boolean running = true;

        while (running) {

            System.out.println("\n========= Bank Menu =========");
            System.out.println("|| 1. Deposit              ||");
            System.out.println("|| 2. Withdraw             ||");
            System.out.println("|| 3. Check Balance        ||");
            System.out.println("|| 4. Transfer             ||");
            System.out.println("|| 5. Bank Statement       ||");
            System.out.println("|| 6. Create a new account ||");
            System.out.println("|| 0. Exit                 ||");
            System.out.println("=============================");

            selectedAccount = accountService.getAccountById(selectedAccount.getId());
            System.out.printf("Selected Account ID: %d | Type: %s | Balance: R$%.2f%n%n",
                    selectedAccount.getId(),
                    selectedAccount.getAccountType(),
                    selectedAccount.getBalance());

            System.out.print("Choose an option: ");

            int option = scanner.nextInt();

            switch (option) {
                case 1:
                    System.out.println("Deposit.");
                    userInteraction.deposit(selectedAccount);
                    break;
                case 2:
                    System.out.println("Withdraw.");
                    userInteraction.withdraw(selectedAccount);
                    break;
                case 3:
                    System.out.println("Check Balance.");
                    userInteraction.checkBalance(selectedAccount);
                    break;
                case 4:
                    System.out.println("Transfer.");
                    userInteraction.transfer(selectedAccount);
                    break;
                case 5:
                    System.out.println("Bank Statement.");
                    userInteraction.viewTransactions(selectedAccount);
                    break;
                case 6:
                    System.out.println("Creating a new account.");
                    userInteraction.createNewAccount(user);
                    break;
                case 0:
                    System.out.println("Exiting...");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option! Please try again.");
            }
        }
    }

}
