package model;

import java.time.LocalDate;

public class User {
    private int id;
    private String cpf;
    private String name;
    private String email;
    private String phone;
    private LocalDate birthDate;
    private String accountType;
    private String password;

    public User() {
    }

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
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

}
