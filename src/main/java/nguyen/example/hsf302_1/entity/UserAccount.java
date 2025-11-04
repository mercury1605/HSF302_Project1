package nguyen.example.hsf302_1.entity;


import jakarta.persistence.*;

@Entity
@Table(name = "user_accounts")
public class UserAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private int role; // 1 = Manager, 2 = Staff, 3 = Guest

    // Constructors
    public UserAccount() {
    }

    public UserAccount(String username, String password, int role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    // Helper methods
    public boolean isManager() {
        return this.role == 1;
    }

    public boolean isStaff() {
        return this.role == 2;
    }

    public boolean isGuest() {
        return this.role == 3;
    }

    public String getRoleName() {
        return switch (this.role) {
            case 1 -> "Manager";
            case 2 -> "Staff";
            case 3 -> "Guest";
            default -> "Unknown";
        };
    }
}