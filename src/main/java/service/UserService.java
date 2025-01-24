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

        // Valida o formato do CPF
        String cpfFormatError = authService.validateCpfFormat(cpf);
        if (cpfFormatError != null) {
            System.out.println(cpfFormatError);
            return false; // Retorna false se o CPF for inválido
        }

        // Valida se o CPF já existe
        String cpfError = authService.isCpfRegistered(cpf);
        if (cpfError != null) {
            System.out.println(cpfError);
            return false; // Retorna false se o CPF já estiver registrado
        }

        // Validação de tipo de conta
        String accountTypeError = authService.isValidAccountType(accountType);
        if (accountTypeError != null) {
            System.out.println(accountTypeError);
            return false; // Retorna false se o tipo de conta for inválido
        }

        // Validação do formato da data
        if (birthDate == null) {
            System.out.println("Data de nascimento inválida.");
            return false; // Retorna false se a data de nascimento for inválida
        }

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
