package ApProject_OnlineShop.model.persons;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "Manager")
public class Manager extends Person implements Serializable {

    public Manager(String username, String firstName, String lastName, String email, String phoneNumber, String password) {
        super(username, firstName, lastName, email, phoneNumber, password);
    }

    public Manager() {
    }

    @Override
    public String toString() {
        return super.toString() + "\n-------------------";
    }
}
