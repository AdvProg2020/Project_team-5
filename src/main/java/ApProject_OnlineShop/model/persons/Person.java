package ApProject_OnlineShop.model.persons;

import com.google.gson.annotations.Expose;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "Persons")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Person implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PersonId", nullable = false,  unique = true)
    @Expose
    private long personId; //TODO

    @Column(name = "UserName", unique = true, nullable = false)
    @Expose
    private String username;

    @Column(name = "FirstName")
    @Expose
    private String firstName;

    @Column(name = "LastName")
    @Expose
    private String lastName;

    @Column(name = "Email")
    @Expose
    private String email;

    @Column(name = "PhoneNumber")
    @Expose
    private String phoneNumber;

    @Column(name = "Password", nullable = false)
    @Expose
    private String password;

    @Transient
    @Expose
    private String role = this.getClass().getSimpleName();

    public Person(String username, String firstName, String lastName, String email, String phoneNumber, String password) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
    }

    public Person() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public void setRole() {
        this.role = this.getClass().getSimpleName();
    }

    public String getRole() {
        return role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public long getPersonId() {
        return personId;
    }

    public void setPersonId(long personId) {
        this.personId = personId;
    }

    @Override
    public String toString() {
        return "-------------------\n" +
                "username = " + username + "\n" +
                "firstName = " + firstName + "\n" +
                "lastName = " + lastName + "\n" +
                "email = " + email + "\n" +
                "phoneNumber = " + phoneNumber;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Person) {
            Person person2 = (Person) obj;
            return (this.getUsername().equals(person2.getUsername()));
        }
        return super.equals(obj);
    }
}
