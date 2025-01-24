package service;

import dao.UserDAO;
import dao.UserDAOImplements;
import model.User;
import util.PasswordUtil;
import java.time.LocalDate;

public class UserService {

    private final AuthService authService = new AuthService();
    private final UserDAO userDAO = new UserDAOImplements();

    public boolean createUser(String cpf, String name, String email, String phone, LocalDate birthDate, String accountType, String password) {

        /* Valida o formato do CPF
        String cpfFormatError = authService.validateCpfFormat(cpf);
        if (cpfFormatError != null) {
            System.out.println(cpfFormatError);
            return false;
        }

        // Valida se o CPF já existe
        String cpfError = authService.isCpfRegistered(cpf);
        if (cpfError != null) {
            System.out.println(cpfError);
            return false;
        }

        // Validação de tipo de conta
        String accountTypeError = authService.isValidAccountType(accountType);
        if (accountTypeError != null) {
            System.out.println(accountTypeError);
            return false;
        }

        // Validação do formato da data
        if (birthDate == null) {
            System.out.println("Invalid birth date.");
            return false;
        }
        */


        // Criar objeto User
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
