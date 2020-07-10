package ApProject_OnlineShop.model.persons;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "Password")
public class Password implements Serializable {
    @Id
    @Column(name = "PersonId", nullable = false)
    private long personId;

    @Column(name = "Password", nullable = false)
    private String hashedPassword;

    @Column(name = "Salt", nullable = false)
    private String salt;

    public Password(long personId, String password) {
        this.personId = personId;
        //TODO
    }

    public Password() {
    }

    public long getPersonId() {
        return personId;
    }

    public void setPersonId(long personId) {
        this.personId = personId;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }
}
