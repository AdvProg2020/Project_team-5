package Model.Persons;

import Model.Persons.Person;

public class Manager extends Person {

    public Manager(String username, String firstName, String lastName, String email, String phoneNumber, String password) {
        super(username, firstName, lastName, email, phoneNumber, password);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
