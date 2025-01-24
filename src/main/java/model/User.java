package model;

import java.time.LocalDate;

public class User {
    private int id;
    private String cpf;
    private String name;
    private String email;
    private String phone;
    private LocalDate birthDate;
    private String password;


public User() {
}

public User(int id, String cpf, String name, String email, String phone, LocalDate birthDate, String password){
    this.id = id;
    this.cpf = cpf;
    this.name = name;
    this.email = email;
    this.phone = phone;
    this.birthDate = birthDate;
    this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public LocalDate getBirth_date() {
        return birthDate;
    }

    public void setBirth_date(LocalDate birth_date) {
        this.birthDate = birthDate;
    }

    public String getPassword() {
        return password;
    }
}