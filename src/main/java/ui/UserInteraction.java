package ui;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import dao.TransactionDAO;
import model.Account;
import model.User;
import service.ServiceLocator;


import static br.com.compass.App.bankMenu;

//----------------------------CREATE ACCOUNT----------------------------
public class UserInteraction {

    private final Scanner scanner;
    private final ServiceLocator serviceLocator;

    public UserInteraction(Scanner scanner, ServiceLocator serviceLocator) {
        this.scanner = scanner;
        this.serviceLocator = serviceLocator;
    }


    public void openAccount() {
        // CPF
        String cpf;
        while (true) {
            System.out.print("\nEnter cpf: ");
            cpf = scanner.nextLine();

            if (!serviceLocator.getValidationUtil().cpfFormat(cpf)) {
                continue;
            }

            if (!serviceLocator.getValidationUtil().cpfRegistered(cpf)) {
                return;
            }

            break;
        }

        //NAME
        String name;
        while (true) {
            System.out.print("Enter username: ");
            name = scanner.nextLine();

            String nameValidationMessage = serviceLocator.getValidationService().validateName(name);
            if (nameValidationMessage != null) {
                System.out.println(nameValidationMessage);
                continue;
            }
            break;
        }

        // EMAIL
        String email;
        while (true) {
            System.out.print("Enter email: ");
            email = scanner.nextLine();

            String emailValidationMessage = serviceLocator.getValidationService().validateEmail(email);
            if (emailValidationMessage != null) {
                System.out.println(emailValidationMessage);
                continue;
            }
            break;
        }

        // PHONE
        String phone;
        while (true) {
            System.out.print("Enter phone number (DDD + XXXXXXXX): ");
            phone = scanner.nextLine();

            String phoneValidationMessage = serviceLocator.getValidationService().validatePhone(phone);
            if (phoneValidationMessage != null) {
                System.out.println(phoneValidationMessage);
                continue;
            }
            break;
        }

        // BIRTHDATE
        String birthDateInput;
        LocalDate birthDate;

        while (true) {
            System.out.print("Enter your birth date (dd/MM/yyyy): ");
            birthDateInput = scanner.nextLine();

            String birthDateValidationMessage = serviceLocator.getValidationService().validateBirthDate(birthDateInput);
            if (birthDateValidationMessage != null) {
                System.out.println(birthDateValidationMessage);
                continue;
            }

            birthDate = LocalDate.parse(birthDateInput, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            break;
        }

        // ACCOUNT TYPE
        String accountTypeInput;
        while (true) {
            System.out.print("Enter account type (CHECKING, SAVINGS, SALARY): ");
            accountTypeInput = scanner.nextLine();

            String accountTypeValidationMessage = serviceLocator.getValidationService().validateAccountType(accountTypeInput);
            if (accountTypeValidationMessage != null) {
                System.out.println(accountTypeValidationMessage);
                continue;
            }

            break;
        }

        // PASSWORD
        String password;
        while (true) {
            System.out.print("Enter your password (min. 8 characters, 1 uppercase, 1 lowercase, 1 digit, 1 special character @#$%^&+=!*): ");
            password = scanner.nextLine();

            String passwordValidationMessage = serviceLocator.getValidationService().validatePassword(password);
            if (passwordValidationMessage != null) {
                System.out.println(passwordValidationMessage);
                continue;
            }
            break;
        }

        // ACCOUNT CREATED
        serviceLocator.getUserService().createUser(cpf, name, email, phone, birthDate, accountTypeInput, password);
        System.out.println("Account Opening.");
    }

    //----------------------------LOGIN----------------------------
    public void login() {
        boolean loggedIn = false;

        while (!loggedIn) {
            System.out.print("\nEnter CPF: ");
            String cpf = scanner.nextLine();

            if (!serviceLocator.getValidationUtil().cpfFormat(cpf)) {
                continue;
            }

            String password;
            while (true) {
                System.out.print("Enter password: ");
                password = scanner.nextLine();

                if (!serviceLocator.getValidationService().isInputValid(password)) {
                    continue;
                }
                break;
            }

            User user = serviceLocator.getUserService().login(cpf, password);
            if (user != null) {
                System.out.println("\nLogin successful! Welcome, " + user.getName() + "!");

                List<Account> accounts = serviceLocator.getAccountService().getAccountsByUserId(user.getId());

                if (accounts.size() > 1) {
                    System.out.println("You have multiple accounts. Please choose one to continue:\n");
                    for (int i = 0; i < accounts.size(); i++) {
                        System.out.printf("%d.  %s%n", i + 1, accounts.get(i).getAccountType());
                    }

                    int accountChoice;
                    while (true) {
                        System.out.print("Choose an account (1-" + accounts.size() + "): ");
                        accountChoice = scanner.nextInt();
                        scanner.nextLine();

                        if (accountChoice < 1 || accountChoice > accounts.size()) {
                            System.out.println("Invalid choice. Please try again.");
                        } else {
                            break;
                        }
                    }

                    Account selectedAccount = accounts.get(accountChoice - 1);
                    bankMenu(scanner, user, this, selectedAccount, this.serviceLocator);
                } else if (accounts.size() == 1) {
                    bankMenu(scanner, user, this, accounts.getFirst(), this.serviceLocator);
                } else {
                    System.out.println("No accounts found for this user.");
                }

                loggedIn = true;
            } else {
                while (true) {
                    System.out.println("\nInvalid CPF or password. Please try again.");
                    System.out.print("Would you like to try again? (yes/no): ");
                    String choice = scanner.nextLine().trim().toLowerCase();

                    if (choice.equals("yes") || choice.equals("sim")) {
                        break;
                    } else if (choice.equals("no") || choice.equals("não")) {
                        System.out.println("Returning to the main menu...");
                        return;
                    } else {
                        System.out.println("Unrecognized input. Please enter 'yes' or 'no'.");
                    }
                }
            }
        }
    }

    //----------------------------DEPOSIT----------------------------
    public void deposit(Account account) {
        scanner.nextLine();
        while (true) {
            System.out.print("Enter the amount to deposit: ");
            String input = scanner.nextLine().trim();

            if (input.isEmpty()) {
                System.out.println("Invalid input: Please enter a valid number.\n");
                continue;
            }

            double amount;

            try {
                amount = Double.parseDouble(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input: Please enter a valid number.\n");
                continue;
            }

            String validationMessage = serviceLocator.getValidationService().validateDepositAmount(amount);
            if (validationMessage != null) {
                System.out.println(validationMessage);
                continue;
            }

            serviceLocator.getAccountService().depositToAccount(account.getId(), amount);
            break;
        }
    }

    //----------------------------WITHDRAW----------------------------
    public void withdraw(Account account) {
        scanner.nextLine();
        double currentBalance = serviceLocator.getAccountService().checkBalance(account.getId());

        while (true) {
            System.out.print("Enter the amount to withdraw: ");
            String input = scanner.nextLine().trim();

            if (input.isEmpty()) {
                System.out.println("Invalid input: Please enter a valid number.\n");
                continue;
            }

            double amount;

            try {
                amount = Double.parseDouble(input);
            } catch (NumberFormatException e) {
                System.out.println("\nInvalid input: Please enter a valid number.\n");
                continue;
            }

            String validationMessage = serviceLocator.getValidationService().validateWithdrawAmount(amount, currentBalance);
            if (validationMessage != null) {
                System.out.println(validationMessage);
                continue;
            }

            serviceLocator.getAccountService().withdrawFromAccount(account.getId(), amount);
            break;
        }
    }

    //----------------------------CHECK BALANCE----------------------------
    public void checkBalance(Account account) {
        double balance = serviceLocator.getAccountService().checkBalance(account.getId());
        System.out.printf("Your current balance is: R$%.2f%n", balance);
    }

    //----------------------------TRANSFER----------------------------
    public void transfer(Account sourceAccount) {
        String accountType = serviceLocator.getAccountService().getAccountTypeById(sourceAccount.getId());

        if (accountType == null) {
            System.out.println("Account not found.\n");
            return;
        }

        if (!accountType.equalsIgnoreCase("CHECKING")) {
            System.out.println("\nTransfers are only allowed from CHECKING accounts.");
            return;
        }

        System.out.print("Enter the target account ID: ");
        int targetAccountId = scanner.nextInt();

        if (targetAccountId == sourceAccount.getId()) {
            System.out.println("Invalid operation: You cannot transfer money to the same account.");
            return;
        }

        Account targetAccount = serviceLocator.getAccountService().getAccountById(targetAccountId);
        String accountValidationMessage = serviceLocator.getValidationService().validateAccountExistence(targetAccount);
        if (accountValidationMessage != null) {
            System.out.println(accountValidationMessage);
            return;
        }

        System.out.print("Enter the amount to transfer: ");
        String input = scanner.nextLine().trim();

        if (input.isEmpty()) {
            System.out.println("Invalid input: Please enter a valid number.\n");
            return;
        }

        double amount;

        try {
            amount = Double.parseDouble(input);
        } catch (NumberFormatException e) {
            System.out.println("Invalid input: Please enter a valid number.\n");
            return;
        }

        double currentBalance = serviceLocator.getAccountService().checkBalance(sourceAccount.getId());
        if (amount > currentBalance) {
            System.out.println("Insufficient funds for this transfer.");
            return;
        }

        String validationMessage = serviceLocator.getValidationService().validateTransferAmount(amount);
        if (validationMessage != null) {
            System.out.println(validationMessage);
            return;
        }

        serviceLocator.getAccountService().transferBetweenAccounts(sourceAccount.getId(), targetAccountId, amount);
    }

    //BANK STATEMENT
    public void viewTransactions(Account account) {
        TransactionDAO transactionDAO = new TransactionDAO();
        List<String> formattedTransactions = transactionDAO.getFormattedTransactionsByAccountId(account.getId());

        if (formattedTransactions.isEmpty()) {
            System.out.println("No transactions found for this account.\n");
        } else {
            System.out.println("Transaction History:");

            for (String formattedTransaction : formattedTransactions) {
                System.out.println(formattedTransaction);
            }
        }
    }

    //----------------------------CREATE NEW ACCOUNT----------------------------
    public void createNewAccount(User user) {
        scanner.nextLine();

        List<Account> existingAccounts = serviceLocator.getAccountService().getAccountsByUserId(user.getId());
        List<String> existingAccountTypes = new ArrayList<>();
        for (Account existingAccount : existingAccounts) {
            String accountType = existingAccount.getAccountType();
            existingAccountTypes.add(accountType);
        }

        System.out.println("You already have the following account types: " + existingAccountTypes + ". " +
                "Remember, you can't create another account of the same type.\n");

        String accountTypeInput;
        while (true) {
            System.out.print("Enter account type to create (CHECKING, SAVINGS, SALARY): ");
            accountTypeInput = scanner.nextLine().toUpperCase();


            if (existingAccountTypes.contains(accountTypeInput)) {
                System.out.println("You already have this type of account. Please choose a different type.\n");
                continue;
            }

            String accountTypeValidationMessage = serviceLocator.getValidationService().validateAccountType(accountTypeInput);
            if (accountTypeValidationMessage != null) {
                System.out.println(accountTypeValidationMessage);
                continue;
            }

            break;
        }

        serviceLocator.getAccountService().createAccount(user.getId(), accountTypeInput);
        System.out.println("Account of type " + accountTypeInput + " created successfully.");
    }
}
