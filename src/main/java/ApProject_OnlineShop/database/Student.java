package ApProject_OnlineShop.database;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "testCustomer")
public class Student {
    @Id
    @Column(name = "customerID")
    private int id;

    @Column(name = "username", unique = true)
    private String username;

    public Student(int id, String username) {
        this.id = id;
        this.username = username;
    }

    public Student() {
    }

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

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", username='" + username + '\'' +
                '}';
    }
}
