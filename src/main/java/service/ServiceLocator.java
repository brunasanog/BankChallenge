package service;

import util.ValidationUtil;

public class ServiceLocator {
    private final UserService userService;
    private final AccountService accountService;
    private final ValidationService validationService;
    private final ValidationUtil validationUtil;

    public ServiceLocator() {
        this.userService = new UserService();
        this.accountService = new AccountService();
        this.validationService = new ValidationService();
        this.validationUtil = new ValidationUtil();
    }

    //GETTERS AND SETTERS
    public UserService getUserService() {
        return userService;
    }

    public ValidationService getValidationService() {
        return validationService;
    }

    public AccountService getAccountService() {
        return accountService;
    }

    public ValidationUtil getValidationUtil() {
        return validationUtil;
    }
}