package util;

import service.ValidationService;

public class ValidationUtil {

    ValidationService validationService = new ValidationService();

    public boolean cpfFormat(String cpf) {
        String cpfValidationMessage = validationService.validateCpfFormat(cpf);
        if (cpfValidationMessage != null) {
            System.out.println(cpfValidationMessage);
            return false;
        }
        return true;
    }

    public boolean cpfRegistered(String cpf) {
        String registrationMessage = validationService.isCpfRegistered(cpf);
        if (registrationMessage != null) {
            System.out.println(registrationMessage);
            return false;
        }
        return true;
    }
}
