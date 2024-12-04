package Models;

public class UsersReg {
    private String email;
    private String password;
    private String name;

    public UsersReg(String email, String password, String name ) {
        this.email=email;
        this.password=password;
        this.name=name;
    }

    public UsersReg(String email, String password) {
        this.email =email;
        this.password =password;
    }

    public UsersReg(String email) {
        this.email =email;
    }


    @Override
    public String toString() {
        return super.toString();
    }

    public String getEmail() {
        return email;
    }

    public void setLogin(String login) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String Name) {
        this.name = name;
    }
}

