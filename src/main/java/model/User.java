package model;

import java.time.LocalDate;

public class User {
    private String cpf;
    private String name;
    private String email;
    private String phone;
    private LocalDate birthDate;
    private String accountType;
    private String password;

    // Construtor
    public User(String cpf, String name, String email, String phone, LocalDate birthDate, String accountType, String password) {
        this.cpf = cpf;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.birthDate = birthDate;
        this.accountType = accountType;
        this.password = password;
    }

    // Getters
    public String getCpf() {
        return cpf;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public String getAccountType() {
        return accountType;
    }

    public String getPassword() {
        return password;
    }

    // Setters
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

}
