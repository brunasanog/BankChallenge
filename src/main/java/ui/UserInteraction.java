package ui;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import service.AuthService;
import service.UserService;

//CREATE ACCOUNT
public class UserInteraction {

    private final Scanner scanner;
    private final UserService userService;
    private final AuthService authService;

    public UserInteraction(Scanner scanner, UserService userService, AuthService authService) {
        this.scanner = scanner;
        this.userService = userService;
        this.authService = authService;
    }

    public void openAccount() {
        // CPF
        String cpf;
        while (true) {
            System.out.print("Enter cpf: ");
            cpf = scanner.nextLine();

            String cpfValidationMessage = authService.validateCpfFormat(cpf);
            if (cpfValidationMessage != null) {
                System.out.println(cpfValidationMessage);
                continue;
            }

            if (authService.isCpfRegistered(cpf) != null) {
                System.out.println(authService.isCpfRegistered(cpf));
                return;
            }

            break;
        }

        //NAME
        String name;
        while (true) {
            System.out.print("Enter username: ");
            name = scanner.nextLine();

            String nameValidationMessage = authService.validateName(name);
            if (nameValidationMessage != null) {
                System.out.println(nameValidationMessage);
                continue;
            }
            break;
        }

        // EMAIL
        String email;
        while (true) {
            System.out.print("Enter email: ");
            email = scanner.nextLine();

            String emailValidationMessage = authService.validateEmail(email);
            if (emailValidationMessage != null) {
                System.out.println(emailValidationMessage);
                continue;
            }
            break;
        }

        // PHONE
        String phone;
        while (true) {
            System.out.print("Enter phone number (DDD + XXXXXXXX): ");
            phone = scanner.nextLine();

            String phoneValidationMessage = authService.validatePhone(phone);
            if (phoneValidationMessage != null) {
                System.out.println(phoneValidationMessage);
                continue;
            }
            break;
        }

        // BIRTHDATE
        String birthDateInput;
        LocalDate birthDate = null;

        while (true) {
            System.out.print("Enter your birth date (dd/MM/yyyy): ");
            birthDateInput = scanner.nextLine();

            String birthDateValidationMessage = authService.validateBirthDate(birthDateInput);
            if (birthDateValidationMessage != null) {
                System.out.println(birthDateValidationMessage);
                continue;
            }

            birthDate = LocalDate.parse(birthDateInput, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            break;
        }

        // ACCOUNT TYPE
        String accountTypeInput;
        while (true) {
            System.out.print("Enter account type (CHECKING, SAVINGS, SALARY): ");
            accountTypeInput = scanner.nextLine();

            String accountTypeValidationMessage = authService.isValidAccountType(accountTypeInput);
            if (accountTypeValidationMessage != null) {
                System.out.println(accountTypeValidationMessage);
                continue;
            }

            break;
        }

        // PASSWORD
        String password;
        while (true) {
            System.out.print("Enter your password: ");
            password = scanner.nextLine();

            String passwordValidationMessage = authService.validatePassword(password);
            if (passwordValidationMessage != null) {
                System.out.println(passwordValidationMessage);
                continue;
            }
            break;
        }

        // ACCOUNT CREATED
        userService.createUser (cpf, name, email, phone, birthDate, accountTypeInput, password);
        System.out.println("Account Opening.");
    }
}
