
package java01.ex02;

public interface UsersList {

    void addUser(User user);

    User getUserById(Integer id);

    User getUserByIndex(Integer index);

    Integer getUsersCount();
}