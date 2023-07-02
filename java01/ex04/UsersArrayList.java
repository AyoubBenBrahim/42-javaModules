package java01.ex04;

public class UsersArrayList implements UsersList {

    private static Integer USERS_MAX = 10;
    // private static Integer nbr_elem = 0;

    private User[] usersList;

    public UsersArrayList() {
        usersList = new User[USERS_MAX];
    }

    @Override
    public void addUser(User user) {

        if (user == null) {
            throw new UserNotFoundException();
        }

        if (getUsersCount() == 0) {
            usersList[0] = user;
            return;
        }
        if (getUsersCount() == USERS_MAX) {
            USERS_MAX = usersList.length + usersList.length / 2;
            User[] newUsersList = new User[USERS_MAX];
            newUsersList[0] = user;
            for (int i = 0; i < usersList.length; i++) {
                newUsersList[i + 1] = usersList[i];
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
            if (i == index && usersList[i] != null) {
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