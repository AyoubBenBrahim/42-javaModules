package java01.ex02;

public class UsersArrayList implements UsersList {

    private static Integer USERS_LEN = 10;

    private User[] usersList;

    public UsersArrayList() {
        usersList = new User[USERS_LEN];
    }

    @Override
    public void addUser(User user) {

        if (getUsersCount() == USERS_LEN) {
            Integer USERS_LEN = usersList.length + usersList.length / 2;
            User[] newUsersList = new User[USERS_LEN];
            for (int i = 0; i < usersList.length; i++) {
                newUsersList[i] = usersList[i];
            }
            usersList = newUsersList;
            return;
        }

        for (int i = 0; i < usersList.length; i++) {
            if (usersList[i] == null) {
                usersList[i] = user;
                return;
            }
        }
        throw new UserNotFoundException();
    }

    @Override
    public User getUserById(Integer id) {
        for (User user : usersList) {
            if (user.getIdentifier() == id) {
                return user;
            }
        }
        throw new UserNotFoundException();
    }

    @Override
    public User getUserByIndex(Integer index) {

        for (int i = 0; i < usersList.length; i++) {
            if (i == index) {
                return usersList[i];
            }
        }
        throw new UserNotFoundException();
    }

    @Override
    public Integer getUsersCount() {
        Integer count = 0;
        for (User user : usersList) {
            if (user != null) {
                count++;
            }
        }
        return count; 
    }

}