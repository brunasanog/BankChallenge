package service;

import dao.UserDAO;
import model.Account;
import model.enums.AccountType;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class ValidationService {

    private final UserDAO userDAO = new UserDAO();

    // CPF
    public String validateCpfFormat(String cpf) {

        if (cpf == null || cpf.trim().isEmpty()) {
            return "Invalid CPF: CPF cannot be null or empty.";
        }

        if (!cpf.matches("\\d+")) {
            return "Invalid CPF: CPF must contain only numbers.";
        }

        if (cpf.length() != 11) {
            return "Invalid CPF: CPF must have exactly 11 digits.";
        }
        return null;
    }

    //CPF ALREADY REGISTERED
    public String isCpfRegistered(String cpf) {
        if (userDAO.isCpfRegistered(cpf)) {
            return "CPF already registered! Please use a different CPF or login.";
        }
        return null;
    }

    //NAME
    public String validateName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return "Invalid name: Name cannot be null or empty.\n";
        }
        if (!name.matches("[\\p{L}\\s]+")) {
            return "Invalid name: Name cannot contain numbers or special characters.\n";
        }
        if (name.length() < 3) {
            return "Invalid name: Name must be at least 3 characters long.\n";
        }
        return null;
    }

    //EMAIL
    public String validateEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return "Invalid email: Email cannot be null or empty.\n";
        }

        String emailRegex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";

        if (!email.matches(emailRegex)) {
            return "Invalid email: Please provide a valid email (e.g., user@example.com).\n";
        }

        if (email.length() < 6) {
            return "Invalid email: Email must contain at least 6 characters.\n";
        }
        return null;
    }

    //PHONE
    public String validatePhone(String phone) {
        if (phone == null || phone.trim().isEmpty()) {
            return "Invalid phone: Phone cannot be null or empty.\n";
        }

        if (!phone.matches("\\d{11}")) {
            return "Invalid phone: Please enter your phone number in the format DDD + XXXXXXXX (e.g., 02187654321).\n";
        }
        return null;
    }

    // BIRTHDATE
    public String validateBirthDate(String birthDateInput) {
        if (birthDateInput == null || birthDateInput.trim().isEmpty()) {
            return "Invalid birth date: Birth date cannot be null or empty.\n";
        }

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate birthDate = LocalDate.parse(birthDateInput, formatter);

            int age = Period.between(birthDate, LocalDate.now()).getYears();
            int currentYear = LocalDate.now().getYear();

            if (birthDate.isAfter(LocalDate.now())) {
                return "Invalid birth date: Birth date cannot be in the future.\n";
            }

            if (birthDate.getYear() < 1900 || birthDate.getYear() > currentYear) {
                return "Invalid birth date: Year must be between 1900 and the current year.\n";
            }

            if (age < 18) {
                return "Invalid birth date: User must be at least 18 years old.\n";
            }

        } catch (DateTimeParseException e) {
            return "Invalid birth date: Please use a valid format and ensure the date is correct (dd/MM/yyyy).\n";
        }
        return null;
    }


    // ACCOUNT TYPE
    public String validateAccountType(String accountTypeInput) {
        if (accountTypeInput == null || accountTypeInput.trim().isEmpty()) {
            return "Invalid account type: Account type cannot be null or empty.\n";
        }

        try {
            AccountType.valueOf(accountTypeInput.toUpperCase());
            return null;
        } catch (IllegalArgumentException e) {
            return "Invalid account type: Please choose CHECKING, SAVINGS, or SALARY.\n";
        }
    }

    //PASSWORD
    public String validatePassword(String password) {
        if (password == null || password.trim().isEmpty()) {
            return "Invalid password: Password cannot be null or empty.\n";
        }
        if (password.length() < 8) {
            return "Invalid password: Password must be at least 8 characters long.\n";
        }
        if (!password.matches(".*[A-Z].*")) {
            return "Invalid password: Password must contain at least one uppercase letter.\n";
        }
        if (!password.matches(".*[a-z].*")) {
            return "Invalid password: Password must contain at least one lowercase letter.\n";
        }
        if (!password.matches(".*\\d.*")) {
            return "Invalid password: Password must contain at least one digit.\n";
        }
        if (!password.matches(".*[@#$%^&+=!*].*")) {
            return "Invalid password: Password must contain at least one special character (@#$%^&+=!).\n";
        }
        return null;
    }

    //VALIDATE DEPOSIT
    public String validateDepositAmount(Double amount) {
        if (amount == null) {
            return "Invalid amount: Amount cannot be null.\n";
        }
        if (amount <= 0) {
            return "Invalid amount: Amount must be greater than zero.\n";
        }
        return null;
    }

    //VALIDATE WITHDRAW
    public String validateWithdrawAmount(double amount, double currentBalance) {
        if (amount <= 0) {
            return "Invalid amount: Withdraw amount must be greater than zero.\n";
        }
        if (amount > currentBalance) {
            return "Invalid amount: Withdraw amount cannot exceed the current balance.\n";
        }
        return null;
    }

    // VALIDATE TRANSFER
    public String validateTransferAmount(double amount) {
        if (amount <= 0) {
            return "Invalid amount: Transfer amount must be greater than zero.\n";
        }
        return null;
    }

    // VALIDATE ACCOUNT
    public String validateAccountExistence(Account account) {
        if (account == null) {
            return "Invalid account: Account does not exist.\n";
        }
        return null;
    }

    //VALIDATE STRING
    public boolean isInputValid(String input) {
        if (input == null || input.trim().isEmpty()) {
            System.out.println("Input cannot be empty! Please try again.");
            return false;
        }
        return true;

    }
}
