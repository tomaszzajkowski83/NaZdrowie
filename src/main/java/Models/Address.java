package Models;

import Services.DbContext;
import org.hibernate.Session;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity(name = "Address")
public class Address {
    private long id;
    private String city;
    private String street;
    private int houseNumber;
    private Set<Person> persons = new HashSet<>();

    public Address() {
    }

    public Address(String city, String street, int houseNumber) {
        setCity(city);
        setStreet(street);
        setHouseNumber(houseNumber);
    }

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    public long getId() {
        return id;
    }

    private void setId(long id) {
        this.id = id;
    }

    @OneToMany(mappedBy = "address", cascade = CascadeType.ALL, orphanRemoval = true)
    public Set<Person> getPersons() {
        return persons;
    }

    public void setPersons(Set<Person> persons) {
        this.persons = persons;
    }

    public void addPerson(Person person){
        if(!getPersons().contains(person)) {
            getPersons().add(person);
            person.setAddress(this);
        }
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(int houseNumber) {
        this.houseNumber = houseNumber;
    }

    @Transient
    public static Set<Person> getLocators(long locatorId) {
        Session session = DbContext.getSession();
        session.beginTransaction();
        Query q = session.createQuery("from Address where id = ?1")
                .setParameter(1,locatorId);
        Address tmp = (Address) q.getSingleResult();
        session.getTransaction().commit();
        session.close();
        return tmp.getPersons();
    }

    @Override
    public String toString() {
        return "Miasto: " + getCity() + "; " + "ul." + getStreet() + " " + getHouseNumber();
    }
}

