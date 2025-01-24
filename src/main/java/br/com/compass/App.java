package br.com.compass;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import model.AccountType;
import service.AuthService;
import service.UserService;

public class App {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        mainMenu(scanner);

        scanner.close();
        System.out.println("Application closed");
    }

    public static void mainMenu(Scanner scanner) {
        boolean running = true;

        while (running) {
            System.out.println("========= Main Menu =========");
            System.out.println("|| 1. Login                ||");
            System.out.println("|| 2. Account Opening      ||");
            System.out.println("|| 0. Exit                 ||");
            System.out.println("=============================");
            System.out.print("Choose an option: ");

            int option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1:
                    bankMenu(scanner);
                    return;

                case 2:
                    UserService userService = new UserService();
                    AuthService authService = new AuthService();

                    //CPF
                    String cpf;
                    while (true) {
                        System.out.print("Enter cpf: ");
                        cpf = scanner.next();

                        String cpfValidationMessage = authService.validateCpfFormat(cpf);
                        if (cpfValidationMessage != null) {
                            System.out.println(cpfValidationMessage);
                            continue;
                        }

                        if (authService.isCpfRegistered(cpf) != null) {
                            System.out.println(authService.isCpfRegistered(cpf));
                            break;
                        }

                        break;
                    }

                    scanner.nextLine();


                    if (authService.isCpfRegistered(cpf) == null) {

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

                        //EMAIL
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

                        //PHONE
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
                            System.out.print("Enter your birth date (DD/MM/YYYY): ");
                            birthDateInput = scanner.nextLine();

                            String birthDateValidationMessage = authService.validateBirthDate(birthDateInput);
                            if (birthDateValidationMessage != null) {
                                System.out.println(birthDateValidationMessage);
                                continue;
                            }

                            birthDate = LocalDate.parse(birthDateInput, DateTimeFormatter.ofPattern("DD/MM/YYYY"));
                            break;
                        }

                        //ACCOUNT TYPE
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

                        //PASSWORD
                        String password;
                        while (true) {
                            System.out.print("Enter your password: ");
                            password = scanner.nextLine();

                            String passwordValidationMessage = authService.validatePassword(password);
                            if (passwordValidationMessage != null) {
                                System.out.println(passwordValidationMessage);
                                continue;
                            } break;
                        }

                        //CREATING ACCOUNT
                        userService.createUser (cpf, name, email, phone, birthDate, accountTypeInput, password);
                        System.out.println("Account Opening.");
                    }
                    break;

                case 0:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option! Please try again.");
            }
        }
    }

    public static void bankMenu(Scanner scanner) {
        boolean running = true;

        while (running) {
            System.out.println("========= Bank Menu =========");
            System.out.println("|| 1. Deposit              ||");
            System.out.println("|| 2. Withdraw             ||");
            System.out.println("|| 3. Check Balance        ||");
            System.out.println("|| 4. Transfer             ||");
            System.out.println("|| 5. Bank Statement       ||");
            System.out.println("|| 0. Exit                 ||");
            System.out.println("=============================");
            System.out.print("Choose an option: ");

            int option = scanner.nextInt();

            switch (option) {
                case 1:
                    // ToDo...
                    System.out.println("Deposit.");
                    break;
                case 2:
                    // ToDo...
                    System.out.println("Withdraw.");
                    break;
                case 3:
                    // ToDo...
                    System.out.println("Check Balance.");
                    break;
                case 4:
                    // ToDo...
                    System.out.println("Transfer.");
                    break;
                case 5:
                    // ToDo...
                    System.out.println("Bank Statement.");
                    break;
                case 0:
                    // ToDo...
                    System.out.println("Exiting...");
                    running = false;
                    return;
                default:
                    System.out.println("Invalid option! Please try again.");
            }
        }
    }

}
