package dk.zealandcs.gilbert.domain;

import java.sql.Date;

public class User {
    private int id;

    private String name;
    private String email;
    private String password;
    private Date dateOfBirth;
    private UserRole role;

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
