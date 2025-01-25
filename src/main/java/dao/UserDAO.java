package dao;

import model.User;

public interface UserDAO {
    int create(User user);
    boolean isCpfRegistered(String cpf);
}
