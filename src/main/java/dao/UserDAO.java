package dao;

import model.User;

public interface UserDAO {
    void create(User user);
    boolean isCpfRegistered(String cpf);
}
