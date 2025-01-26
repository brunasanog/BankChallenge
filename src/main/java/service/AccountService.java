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

    /*
    public double checkBalance(){

    }

    public void withdraw() {

    }

    public void deposit(){

    }

    public void transfer(){

    }
*/

}
