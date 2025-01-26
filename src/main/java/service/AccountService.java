package service;

import dao.AccountDAO;
import model.Account;

public class AccountService {

    private final AccountDAO accountDAO = new AccountDAO();

    public void createAccount(int userId, String accountType) {
        Account account = new Account(userId, 0.0, accountType);
        accountDAO.createAccount(account);
    }

    public Account getAccountByUserId(int userId) {
        return accountDAO.getAccountByUserId(userId);
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
