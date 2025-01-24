package service;

import dao.UserDAO;
import dao.UserDAOImplements;
import model.User;
import util.PasswordUtil;
import java.time.LocalDate;

public class UserService {

    private final UserDAO userDAO = new UserDAOImplements();

    public boolean createUser(String cpf, String name, String email, String phone, LocalDate birthDate, String accountType, String password) {

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

        userDAO.create(user);
        return true;
    }
}
