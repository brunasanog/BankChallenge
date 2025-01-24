package service;

import dao.UserDAO;
import dao.UserDAOImplements;
import model.AccountType;

public class AuthService {

    private final UserDAO userDAO = new UserDAOImplements();

    // CPF
    public String validateCpfFormat(String cpf){

        if (cpf == null || cpf.trim().isEmpty()) {
            return "Invalid CPF: CPF cannot be null or empty.";
        }

        if (!cpf.matches("\\d+")) {
            return "Invalid CPF: CPF must contain only numbers.";
        }

        if (cpf == null || cpf.length() != 11) {
            return "Invalid CPF: CPF must have exactly 11 digits.";
        }
        return null;
    }

    public String isCpfRegistered(String cpf) {
        if (userDAO.isCpfRegistered(cpf)) {
            return "CPF already registered! Please use a different CPF or login.";
        }
        return null;
    }

    //NOME
    public String validateName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return "Invalid name: Name cannot be null or empty.";
        }
        if (!name.matches("[\\p{L}\\s]+")) {
            return "Invalid name: Name cannot contain numbers or special characters.";
        }
        return null;
    }

    // CONTA
    public String isValidAccountType(String accountTypeInput) {
        try {
            AccountType.valueOf(accountTypeInput.toUpperCase());
            return null;
        } catch (IllegalArgumentException e) {
            return "Invalid account type! Please choose CHECKING, SAVINGS, or SALARY.";
        }
    }
}
