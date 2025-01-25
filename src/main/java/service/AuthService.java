package service;

import dao.UserDAO;
import model.AccountType;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class AuthService {

    private final UserDAO userDAO = new UserDAO();

    // CPF
    public String validateCpfFormat(String cpf) {

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

    //NAME
    public String validateName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return "Invalid name: Name cannot be null or empty.";
        }
        if (!name.matches("[\\p{L}\\s]+")) {
            return "Invalid name: Name cannot contain numbers or special characters.";
        }
        if (name.length() < 03) {
            return "Invalid name: Name must be at least 3 characters long.";
        }
        return null;
    }

    //EMAIL
    public String validateEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return "Invalid email: Email cannot be null or empty.";
        }

        String emailRegex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";

        if (!email.matches(emailRegex)) {
            return "Invalid email: Please provide a valid email (e.g., user@example.com).";
        }

        if (email.length() < 6) {
            return "Invalid email: Email must contain at least 6 characters.";
        }
        return null;
    }

    //PHONE
    public String validatePhone(String phone) {
        if (phone == null || phone.trim().isEmpty()) {
            return "Invalid phone: Phone cannot be null or empty.";
        }

        if (!phone.matches("\\d{11}")) {
            return "Invalid phone: Please enter your phone number in the format DDD + XXXXbirtXXXX (e.g., 02187654321).";
        }
        return null;
    }

    // BIRTHDATE
    public String validateBirthDate(String birthDateInput) {
        if (birthDateInput == null || birthDateInput.trim().isEmpty()) {
            return "Invalid birth date: Birth date cannot be null or empty.";
        }

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate birthDate = LocalDate.parse(birthDateInput, formatter);

            int age = Period.between(birthDate, LocalDate.now()).getYears();
            int currentYear = LocalDate.now().getYear();

            if (birthDate.getYear() < 1900 || birthDate.getYear() > currentYear) {
                return "Invalid birth date: Year must be between 1900 and the current year.";
            }

            if (birthDate.isAfter(LocalDate.now())) {
                return "Invalid birth date: Birth date cannot be in the future.";
            }

            if (age < 18) {
                return "Invalid birth date: User must be at least 18 years old.";
            }

        } catch (DateTimeParseException e) {
            return "Invalid birth date: Please use a valid format and ensure the date is correct (dd/MM/yyyy).";
        }
        return null;
    }


    // ACCOUNT TYPE
    public String isValidAccountType(String accountTypeInput) {
        if (accountTypeInput == null || accountTypeInput.trim().isEmpty()) {
            return "Invalid account type: Account type cannot be null or empty.";
        }

        try {
            AccountType.valueOf(accountTypeInput.toUpperCase());
            return null;
        } catch (IllegalArgumentException e) {
            return "Invalid account type: Please choose CHECKING, SAVINGS, or SALARY.";
        }
    }

    //PASSWORD
    public String validatePassword(String password) {
        if (password == null || password.trim().isEmpty()) {
            return "Invalid password: Password cannot be null or empty.";
        }
        if (password.length() < 8) {
            return "Invalid password: Password must be at least 8 characters long.";
        }
        if (!password.matches(".*[A-Z].*")) {
            return "Invalid password: Password must contain at least one uppercase letter.";
        }
        if (!password.matches(".*[a-z].*")) {
            return "Invalid password: Password must contain at least one lowercase letter.";
        }
        if (!password.matches(".*\\d.*")) {
            return "Invalid password: Password must contain at least one digit.";
        }
        if (!password.matches(".*[@#$%^&+=!*].*")) {
            return "Invalid password: Password must contain at least one special character (@#$%^&+=!).";
        }
        return null;
    }
}
