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

            switch (option) {
                case 1:
                    bankMenu(scanner);
                    return;

                case 2:
                    UserService userService = new UserService();
                    AuthService authService = new AuthService();

                    String cpf;
                    while (true) {
                        System.out.print("Enter cpf: ");
                        cpf = scanner.next();

                        if (authService.isCpfRegistered(cpf) != null) {
                            System.out.println(authService.isCpfRegistered(cpf)); // Exibe a mensagem de erro
                            break;
                        }

                        if (authService.validateCpfFormat(cpf) != null) {
                            System.out.println(authService.validateCpfFormat(cpf));
                            continue;
                        }

                        break;
                    }


                    if (authService.isCpfRegistered(cpf) == null) {
                        System.out.print("Enter username: ");
                        String name = scanner.next();

                        System.out.print("Enter email: ");
                        String email = scanner.next();

                        System.out.print("Enter phone number: ");
                        String phone = scanner.next();

                        System.out.print("Enter your birth date (dd/MM/yyyy): ");
                        String birthDateInput = scanner.next();
                        LocalDate birthDate = LocalDate.parse(birthDateInput, DateTimeFormatter.ofPattern("dd/MM/yyyy"));

                        System.out.print("Enter account type (CHECKING, SAVINGS, SALARY): ");
                        String accountTypeInput = scanner.next();

                        System.out.print("Enter your password: ");
                        String password = scanner.next();

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
