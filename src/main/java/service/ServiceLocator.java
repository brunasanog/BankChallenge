package service;

public class ServiceLocator {
    private final UserService userService;
    private final AccountService accountService;
    private final ValidationService validationService;

    public ServiceLocator() {
        this.userService = new UserService();
        this.accountService = new AccountService();
        this.validationService = new ValidationService();
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
}