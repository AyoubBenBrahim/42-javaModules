package java01.ex02;

public class Program {

    public static void main(String[] args) {

        User user1 = new User("user1", 1000);
        User user2 = new User("user2", 2000);
        User user3 = new User("user3", 3000);
        User user4 = new User("user4", 4000);
        User user5 = new User("user5", 5000);
        User user6 = new User("user6", 6000);
        User user7 = new User("user7", 7000);
        User user8 = new User("user8", 8000);
        User user9 = new User("user9", 9000);
        User user10 = new User("user10", 10000);

        User user11 = new User("user11", 11000);
        User user12 = new User("user12", 12000);
        User user13 = new User("user13", 13000);
        User user14 = new User("user14", 14000);

        UsersArrayList usersList = new UsersArrayList();

        usersList.addUser(user1);
        usersList.addUser(user2);
        usersList.addUser(user3);

        // try {
            System.out.println(usersList.getUserById(2));
            System.out.println(usersList.getUserByIndex(2));
            System.out.println(usersList.getUserByIndex(3));
        // } catch (UserNotFoundException e) {
        //     System.out.println("UserNotFoundException");
        // }

        usersList.addUser(user4);
        usersList.addUser(user5);
        usersList.addUser(user6);
        usersList.addUser(user7);
        usersList.addUser(user8);

        System.out.println(usersList.getUserById(8));
        System.out.println("usersList.getcount() = " + usersList.getUsersCount());

        // try {
            usersList.addUser(user9);
            usersList.addUser(user10);
        // } catch (UserNotFoundException e) {
        //     System.out.println("UserNotFoundException");
        // }

        System.out.println("usersList.getcount() = " + usersList.getUsersCount());

        // try {
            usersList.addUser(user11);
            usersList.addUser(user12);
            usersList.addUser(user13);
            usersList.addUser(user14);
        // } catch (UserNotFoundException e) {
        //     System.out.println("UserNotFoundException");
        // }

        System.out.println("usersList.getcount() = " + usersList.getUsersCount());

        // try {
            System.out.println(usersList.getUserByIndex(0));
            System.out.println(usersList.getUserByIndex(1));
            usersList.addUser(null);
        // } catch (UserNotFoundException e) {
        //     System.out.println("UserNotFoundException");
        // }
    }

}