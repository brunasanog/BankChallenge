package service;

import dao.UserDAO;
import dao.UserDAOImplements;
import model.AccountType;

public class AuthService {

    private final UserDAO userDAO = new UserDAOImplements();

    // Validação do formato do CPF
    public String validateCpfFormat(String cpf){

        if (!cpf.matches("\\d+")) {
            return "Invalid CPF: CPF must contain only numbers.";
        }

        if (cpf == null || cpf.length() != 11) {
            return "Invalid CPF: CPF must have exactly 11 digits.";
        }
        return null;
    }

    // Verifica se o CPF já está registrado
    public String isCpfRegistered(String cpf) {
        if (userDAO.isCpfRegistered(cpf)) {
            return "CPF already registered! Please use a different CPF or login.";
        }
        return null;
    }

    // Valida o tipo de conta
    public String isValidAccountType(String accountTypeInput) {
        try {
            AccountType.valueOf(accountTypeInput.toUpperCase());  // Verifica se o tipo de conta é válido
            return null;
        } catch (IllegalArgumentException e) {
            return "Invalid account type! Please choose CHECKING, SAVINGS, or SALARY.";
        }
    }
}
