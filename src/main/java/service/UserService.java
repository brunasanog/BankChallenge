package service;

import dao.UserDAO;
import model.User;
import util.PasswordUtil;

import java.time.LocalDate;

public class UserService {

    private final UserDAO userDAO = new UserDAO();
    private final AccountService accountService = new AccountService();

    // CREATE USER
    public void createUser(String cpf, String name, String email, String phone, LocalDate birthDate, String accountType, String password) {

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
        } else {
            System.out.println("Failed to create account.");
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
