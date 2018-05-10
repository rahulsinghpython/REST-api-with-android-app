package api.restful.com.newrestapp;

public class Employee {
    private String email;
    private String name;
    private String role;
    private int id;

    public Employee(String email, String name, String role, int id) {
        this.email = email;

        this.name = name;
        this.role = role;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
