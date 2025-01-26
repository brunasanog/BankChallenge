package service;

import dao.AccountDAO;
import dao.TransactionDAO;
import model.Account;
import model.Transaction;

public class AccountService {

    private final AccountDAO accountDAO = new AccountDAO();

    //CREATE
    public void createAccount(int userId, String accountType) {
        Account account = new Account(userId, 0.0, accountType);
        accountDAO.createAccount(account);
    }

    //GET ACCOUNT BY USER ID
    public Account getAccountByUserId(int userId) {
        return accountDAO.getAccountByUserId(userId);
    }

    //GET ACCOUNT TYPE BY USER ID
    public String getAccountTypeById(int userId) {
        Account account = accountDAO.getAccountById(userId);
        if (account != null) {
            return account.getAccountType();
        } else {
            return null;
        }
    }

    //DEPOSIT
    public void depositToAccount(int accountId, double amount) {
        Account account = accountDAO.getAccountById(accountId);

        if (account != null) {
            account.setBalance(account.getBalance() + amount);
            accountDAO.updateAccount(account);

            Transaction transaction = new Transaction(accountId, "DEPOSIT", amount);
            TransactionDAO transactionDAO = new TransactionDAO();
            transactionDAO.createTransaction(transaction);

            System.out.printf("Deposit of R$%.2f successfully made to account ID: %d%nYour new balance is: R$%.2f%n", amount, accountId, account.getBalance());
        } else {
            System.out.println("Account not found.");
        }
    }

    //WITHDRAW
    public void withdrawFromAccount(int accountId, double amount) {
        Account account = accountDAO.getAccountById(accountId);

        if (account != null) {
            if (amount <= 0) {
                System.out.println("The withdrawal amount must be positive.");
                return;
            }

            if (amount > account.getBalance()) {
                System.out.println("Insufficient funds for this withdrawal.");
                return;
            }

            account.setBalance(account.getBalance() - amount);
            accountDAO.updateAccount(account);

            Transaction transaction = new Transaction(accountId, "WITHDRAW", amount);
            TransactionDAO transactionDAO = new TransactionDAO();
            transactionDAO.createTransaction(transaction);

            System.out.printf("Withdrawal of R$%.2f successfully made from account ID: %d%n" +
                    "Your new balance is: R$%.2f%n", amount, accountId, account.getBalance());
        } else {
            System.out.println("Account not found.");
        }
    }

    // CHECK BALANCE
    public double checkBalance(int accountId) {
        Account account = accountDAO.getAccountById(accountId);

        if (account != null) {
            return account.getBalance();
        } else {
            System.out.println("Account not found.");
            return 0.0;
        }
    }

    // TRANSFER
    public void transferBetweenAccounts(int sourceAccountId, int targetAccountId, double amount) {
        Account sourceAccount = accountDAO.getAccountById(sourceAccountId);
        Account targetAccount = accountDAO.getAccountById(targetAccountId);

        if (sourceAccount == null) {
            System.out.println("Source account not found.");
            return;
        }

        if (targetAccount == null) {
            System.out.println("Target account not found.");
            return;
        }

        if (amount <= 0) {
            System.out.println("The transfer amount must be positive.");
            return;
        }

        if (amount > sourceAccount.getBalance()) {
            System.out.println("Insufficient funds for this transfer.");
            return;
        }

        sourceAccount.setBalance(sourceAccount.getBalance() - amount);
        targetAccount.setBalance(targetAccount.getBalance() + amount);

        accountDAO.updateAccount(sourceAccount);
        accountDAO.updateAccount(targetAccount);

        Transaction transaction = new Transaction(sourceAccountId, "TRANSFER", amount);
        TransactionDAO transactionDAO = new TransactionDAO();
        transactionDAO.createTransaction(transaction);

        System.out.printf("Transfer of R$%.2f successfully made from account ID: %d to account ID: %d%n" +
                "Your new balance is: R$%.2f%n", amount, sourceAccountId, targetAccountId, sourceAccount.getBalance());
    }

}
