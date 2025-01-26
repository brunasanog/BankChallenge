package ui;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import dao.TransactionDAO;
import model.Account;
import model.Transaction;
import model.User;
import service.AccountService;
import service.ValidationService;
import service.UserService;
import util.ValidationUtil;

import static br.com.compass.App.bankMenu;

//----------------------------CREATE ACCOUNT----------------------------
public class UserInteraction {

    private final Scanner scanner;
    private final UserService userService;
    private final ValidationService authService;
    private final AccountService accountService;
    public final ValidationUtil validationUtil;

    public UserInteraction(Scanner scanner, UserService userService, ValidationService authService, AccountService accountService, ValidationUtil validationUtil) {
        this.scanner = scanner;
        this.userService = userService;
        this.authService = authService;
        this.accountService = accountService;
        this.validationUtil = validationUtil;
    }


    public void openAccount() {
        // CPF
        String cpf;
        while (true) {
            System.out.print("Enter cpf: ");
            cpf = scanner.nextLine();

            if (!validationUtil.cpfFormat(cpf)) {
                continue;
            }

            if (!validationUtil.cpfRegistered(cpf)) {
                return;
            }

            break;
        }

        //NAME
        String name;
        while (true) {
            System.out.print("Enter username: ");
            name = scanner.nextLine();

            String nameValidationMessage = authService.validateName(name);
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

            String emailValidationMessage = authService.validateEmail(email);
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

            String phoneValidationMessage = authService.validatePhone(phone);
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

            String birthDateValidationMessage = authService.validateBirthDate(birthDateInput);
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

            String accountTypeValidationMessage = authService.validateAccountType(accountTypeInput);
            if (accountTypeValidationMessage != null) {
                System.out.println(accountTypeValidationMessage);
                continue;
            }

            break;
        }

        // PASSWORD
        String password;
        while (true) {
            System.out.print("Enter your password: ");
            password = scanner.nextLine();

            String passwordValidationMessage = authService.validatePassword(password);
            if (passwordValidationMessage != null) {
                System.out.println(passwordValidationMessage);
                continue;
            }
            break;
        }

        // ACCOUNT CREATED
        userService.createUser(cpf, name, email, phone, birthDate, accountTypeInput, password);
        System.out.println("Account Opening.");
    }

    //----------------------------LOGIN----------------------------
    public void login() {
        boolean loggedIn = false;

        while (!loggedIn) {
            System.out.print("Enter CPF: ");
            String cpf = scanner.nextLine();

            if (!validationUtil.cpfFormat(cpf)) {
                continue;
            }

            System.out.print("Enter password: ");
            String password = scanner.nextLine();

            User user = userService.login(cpf, password);
            if (user != null) {
                System.out.println("\nLogin successful! Welcome, " + user.getName() + "!\n");


                List<Account> accounts = accountService.getAccountsByUserId(user.getId());

                if (accounts.size() > 1) {
                    System.out.println("You have multiple accounts. Please choose one to continue:");
                    for (int i = 0; i < accounts.size(); i++) {
                        System.out.printf("%d. Account ID: %d | Type: %s%n", i + 1, accounts.get(i).getId(), accounts.get(i).getAccountType());
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
                    bankMenu(scanner, user, this, selectedAccount, this.accountService);
                } else if (accounts.size() == 1) {
                    bankMenu(scanner, user, this, accounts.get(0), this.accountService);
                } else {
                    System.out.println("No accounts found for this user.");
                }

                loggedIn = true;
            } else {
                System.out.println("Invalid CPF or password. Please try again.");
                System.out.print("Would you like to try again? (yes/no): ");
                String choice = scanner.nextLine();

                if (choice.equalsIgnoreCase("no")) {
                    System.out.println("Returning to the main menu...");
                    break;
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
                System.out.println("Invalid input: Please enter a valid number.");
                continue;
            }

            double amount;
            
            try {
                amount = Double.parseDouble(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input: Please enter a valid number.");
                continue;
            }
            
            String validationMessage = authService.validateDepositAmount(amount);
            if (validationMessage != null) {
                System.out.println(validationMessage);
                continue; 
            }
            
            accountService.depositToAccount(account.getId(), amount);
            break;
        }
    }

    //----------------------------WITHDRAW----------------------------
    public void withdraw(Account account) {
        scanner.nextLine();
        double currentBalance = accountService.checkBalance(account.getId());

        while (true) {
            System.out.print("Enter the amount to withdraw: ");
            String input = scanner.nextLine().trim();

            if (input.isEmpty()) {
                System.out.println("Invalid input: Please enter a valid number.");
                continue;
            }

            double amount;

            try {
                amount = Double.parseDouble(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input: Please enter a valid number.");
                continue;
            }

            String validationMessage = authService.validateWithdrawAmount(amount, currentBalance);
            if (validationMessage != null) {
                System.out.println(validationMessage);
                continue;
            }

            accountService.withdrawFromAccount(account.getId(), amount);
            System.out.printf("Withdrawal of R$%.2f successfully made from account ID: %d%n", amount, account.getId());
            break;
        }
    }

    //----------------------------CHECK BALANCE----------------------------
    public void checkBalance(Account account) {
        double balance = accountService.checkBalance(account.getId());
        System.out.printf("Your current balance is: R$%.2f%n", balance);
    }

    //----------------------------TRANSFER----------------------------
    public void transfer(Account sourceAccount) {
        String accountType = accountService.getAccountTypeById(sourceAccount.getId());

        if (accountType == null) {
            System.out.println("Account not found.");
            return;
        }

        if (!accountType.equalsIgnoreCase("CHECKING")) {
            System.out.println("Transfers are only allowed from CHECKING accounts.");
            return;
        }

        System.out.print("Enter the target account ID: ");
        int targetAccountId = scanner.nextInt();
        scanner.nextLine();

        if (targetAccountId == sourceAccount.getId()) {
            System.out.println("Invalid operation: You cannot transfer money to the same account.");
            return;
        }

        Account targetAccount = accountService.getAccountById(targetAccountId);
        String accountValidationMessage = authService.validateAccountExistence(targetAccount);
        if (accountValidationMessage != null) {
            System.out.println(accountValidationMessage);
            return;
        }

        System.out.print("Enter the amount to transfer: ");
        String input = scanner.nextLine().trim();

        if (input.isEmpty()) {
            System.out.println("Invalid input: Please enter a valid number.");
            return;
        }

        double amount;

        try {
            amount = Double.parseDouble(input);
        } catch (NumberFormatException e) {
            System.out.println("Invalid input: Please enter a valid number.");
            return;
        }

        double currentBalance = accountService.checkBalance(sourceAccount.getId());
        if (amount > currentBalance) {
            System.out.println("Insufficient funds for this transfer.");
            return;
        }

        String validationMessage = authService.validateTransferAmount(amount);
        if (validationMessage != null) {
            System.out.println(validationMessage);
            return;
        }

        accountService.transferBetweenAccounts(sourceAccount.getId(), targetAccountId, amount);
    }

    //BANK STATEMENT
    public void viewTransactions(Account account) {
        TransactionDAO transactionDAO = new TransactionDAO();
        List<Transaction> transactions = transactionDAO.getTransactionsByAccountId(account.getId());

        if (transactions.isEmpty()) {
            System.out.println("No transactions found for this account.");
        } else {
            System.out.println("Transaction History:");
            for (Transaction transaction : transactions) {
                System.out.printf("ID: %d | Type: %s | Amount: R$%.2f | Date: %s%n",
                        transaction.getId(), transaction.getTransactionType(), transaction.getAmount(), transaction.getTransactionDate());
            }
        }
    }

    //----------------------------CREATE NEW ACCOUNT----------------------------
    public void createNewAccount(User user) {
        scanner.nextLine();

        List<Account> existingAccounts = accountService.getAccountsByUserId(user.getId());
        List<String> existingAccountTypes = new ArrayList<>();
        for (Account existingAccount : existingAccounts) {
            String accountType = existingAccount.getAccountType();
            existingAccountTypes.add(accountType);
        }

        System.out.println("You already have the following account types: " + existingAccountTypes +
                           ". Remember, you can't create another account of the same type.");

        String accountTypeInput;
        while (true) {
            System.out.print("Enter account type to create (CHECKING, SAVINGS, SALARY): ");
            accountTypeInput = scanner.nextLine().toUpperCase();


            if (existingAccountTypes.contains(accountTypeInput)) {
                System.out.println("You already have this type of account. Please choose a different type.");
                continue;
            }

            String accountTypeValidationMessage = authService.validateAccountType(accountTypeInput);
            if (accountTypeValidationMessage != null) {
                System.out.println(accountTypeValidationMessage);
                continue;
            }

            break;
        }

        accountService.createAccount(user.getId(), accountTypeInput);
        System.out.println("Account of type " + accountTypeInput + " created successfully.");
    }
}
