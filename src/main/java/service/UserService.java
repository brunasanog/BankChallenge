package service;

import dao.AccountDAO;
import dao.UserDAO;
import model.User;
import util.PasswordUtil;
import java.time.LocalDate;

public class UserService {

    private final UserDAO userDAO = new UserDAO();
    private final AccountDAO accountDAO= new AccountDAO();
    private final AccountService accountService = new AccountService();



    public boolean createUser (String cpf, String name, String email, String phone, LocalDate birthDate, String accountType, String password) {
        // CREATE USER
        User user = new User(
                cpf,
                name,
                email,
                phone,
                birthDate,
                accountType,
                PasswordUtil.hashPassword(password)
        );

        int userId = userDAO.createUser(user);

        if (userId != -1) {
            accountService.createAccount(userId, accountType);
            return true;
        } else {
            System.out.println("Failed to create account.");
            return false;
        }
    }

    //LOGIN
    public User login(String cpf, String password) {
        User user = userDAO.findByCpf(cpf);
        if (user != null && PasswordUtil.checkPassword(password, user.getPassword())) {
            return user;
        }
        return null;
    }
}
