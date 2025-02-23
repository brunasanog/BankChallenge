package br.com.compass;

import java.util.Scanner;

import model.Account;
import model.User;
import service.ServiceLocator;
import ui.UserInteraction;

public class App {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        ServiceLocator serviceLocator = new ServiceLocator();
        UserInteraction userInteraction = new UserInteraction(scanner, serviceLocator);

        mainMenu(scanner, userInteraction, serviceLocator);

        scanner.close();
        System.out.println("Application closed");
    }

    public static void mainMenu(Scanner scanner, UserInteraction userInteraction, ServiceLocator serviceLocator) {
        boolean running = true;

        while (running) {
            System.out.println("\n========= Main Menu =========");
            System.out.println("|| 1. Login                ||");
            System.out.println("|| 2. Account Opening      ||");
            System.out.println("|| 0. Exit                 ||");
            System.out.println("=============================");
            System.out.print("Choose an option: ");

            String input = scanner.nextLine();

            if (serviceLocator.getValidationService().isInputValid(input)) {
                continue;
            }

            try {
                int option = Integer.parseInt(input);

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
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a number.");
            }
        }
    }

    public static void bankMenu(Scanner scanner, User user, UserInteraction userInteraction, Account selectedAccount, ServiceLocator serviceLocator) {
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

            selectedAccount = serviceLocator.getAccountService().getAccountById(selectedAccount.getId());
            System.out.printf("Selected Account ID: %d | Type: %s | Balance: R$%.2f%n%n",
                    selectedAccount.getId(),
                    selectedAccount.getAccountType(),
                    selectedAccount.getBalance());

            System.out.print("Choose an option: ");
            String input = scanner.nextLine();

            if (serviceLocator.getValidationService().isInputValid(input)) {
                continue;
            }

            try {
                int option = Integer.parseInt(input);

                switch (option) {
                    case 1:
                        System.out.println("Deposit.\n");
                        userInteraction.deposit(selectedAccount);
                        break;
                    case 2:
                        System.out.println("Withdraw.\n");
                        userInteraction.withdraw(selectedAccount);
                        break;
                    case 3:
                        System.out.println("Check Balance.\n");
                        userInteraction.checkBalance(selectedAccount);
                        break;
                    case 4:
                        System.out.println("Transfer.\n");
                        userInteraction.transfer(selectedAccount);
                        break;
                    case 5:
                        System.out.println("Bank Statement.\n");
                        userInteraction.viewTransactions(selectedAccount);
                        break;
                    case 6:
                        System.out.println("Creating a new account.\n");
                        userInteraction.createNewAccount(user);
                        break;
                    case 0:
                        System.out.println("Exiting...\n");
                        running = false;
                        break;
                    default:
                        System.out.println("Invalid option! Please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a number.");
            }
        }
    }
}