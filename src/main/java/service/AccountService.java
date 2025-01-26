package service;

import dao.AccountDAO;
import model.Account;

public class AccountService {

    private final AccountDAO accountDAO = new AccountDAO();

    //CREATE
    public void createAccount(int userId, String accountType) {
        Account account = new Account(userId, 0.0, accountType);
        accountDAO.createAccount(account);
    }

    //GET ACCOUNT BY ID
    public Account getAccountByUserId(int userId) {
        return accountDAO.getAccountByUserId(userId);
    }

    //DEPOSIT
    public void depositToAccount(int accountId, double amount) {
        Account account = accountDAO.getAccountByUserId(accountId);

        if (account != null) {
            account.setBalance(account.getBalance() + amount);
            accountDAO.updateAccount(account);
            System.out.println(String.format("Deposit of R$%.2f successfully made to account ID: %d%nYour new balance is: R$%.2f", amount, accountId, account.getBalance()));
        } else {
            System.out.println("Account not found.");
        }
    }

    //WITHDRAW
    public void withdrawFromAccount(int accountId, double amount) {
        Account account = accountDAO.getAccountByUserId(accountId);

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
            System.out.println(String.format("Withdrawal of R$%.2f successfully made from account ID: %d%n" +
                                             "Your new balance is: R$%.2f", amount, accountId, account.getBalance()));
        } else {
            System.out.println("Account not found.");
        }
    }

    // CHECK BALANCE
    public double checkBalance(int accountId) {
        Account account = accountDAO.getAccountByUserId(accountId);

        if (account != null) {
            return account.getBalance();
        } else {
            System.out.println("Account not found.");
            return 0.0;
        }
    }

    // TRANSFER
    public void transferBetweenAccounts(int sourceAccountId, int targetAccountId, double amount) {
        Account sourceAccount = accountDAO.getAccountByUserId(sourceAccountId);
        Account targetAccount = accountDAO.getAccountByUserId(targetAccountId);

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

        System.out.println(String.format("Transfer of R$%.2f successfully made from account ID: %d to account ID: %d%n" +
                "Your new balance is: R$%.2f", amount, sourceAccountId, targetAccountId, sourceAccount.getBalance()));
    }

}
