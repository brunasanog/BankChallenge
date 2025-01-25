package util;

import service.AuthService;

public class ValidationUtil {

    AuthService authService = new AuthService();

    public boolean cpfFormat(String cpf) {
        String cpfValidationMessage = authService.validateCpfFormat(cpf);
        if (cpfValidationMessage != null) {
            System.out.println(cpfValidationMessage);
            return false;
        }
        return true;
    }

    public boolean cpfRegistered(String cpf) {
        String registrationMessage = authService.isCpfRegistered(cpf);
        if (registrationMessage != null) {
            System.out.println(registrationMessage);
            return false;
        }
        return true;
    }
}
