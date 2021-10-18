package nl.inholland.model;

public class User {
    private final String firstName;
    private final String lastName;
    private final String username;
    private final String password;
    private final AccessLevel accessLevel;

    public User(String firstName, String lastName, String username, String password, AccessLevel accessLevel) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.accessLevel = accessLevel;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public AccessLevel getAccessLevel() {
        return accessLevel;
    }
}
