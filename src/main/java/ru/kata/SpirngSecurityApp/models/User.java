package ru.kata.SpirngSecurityApp.models;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "users")
public class User {
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "lastName")
    private String lastName;

    @Column(name = "age")
    private byte age;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "user_roles")
    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinTable(
            name = "users_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> userRolesList;

    public User() {
    }

    public User(
            String firstName,
            String lastName,
            byte age,
            String email,
            String password,
            List<Role> userRolesList) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.email = email;
        this.password = password;
        this.userRolesList = userRolesList;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public byte getAge() {
        return age;
    }

    public void setAge(byte age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Role> getUserRolesList() {
        return userRolesList;
    }

    public void setUserRolesList(List<Role> userRolesList) {
        this.userRolesList = userRolesList;
    }

    @Override
    public String toString() {
        return "User with ID = " + this.id + ":"
                + "\ne-mail = " + this.email
                + "\nfirst name = " + this.firstName
                + "\nlast name = " + this.lastName
                + "\nage = " + this.age
                + "\n has roles" + this.userRolesList;
    }

    public boolean hasRole(String roleName) {
        for (Role role :
                this.userRolesList) {
            if (role.getName().equals(roleName)) {
                return true;
            }
        }
        return false;
    }
}
