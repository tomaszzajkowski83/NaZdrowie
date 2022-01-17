package Models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity(name = "Patient")
public class Patient {
    private long Id;
    private int patientNumber;
    private double weight;
    private int height;
    private Set<ImagingTest> imagingTests = new HashSet<>();
    private List<Visit> visits = new ArrayList<>();
    private Person person;
    private Insurance insurance;

    public Patient() {
    }

    public Patient(int nrId, double weight, int height) {
        setPatientNumber(nrId);
        setHeight(height);
        setWeight(weight);
    }

    @javax.persistence.Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    public long getId() {
        return Id;
    }

    private void setId(long id) {
        Id = id;
    }

    public int getPatientNumber() {
        return patientNumber;
    }

    public void setPatientNumber(int patientNumber) {
        this.patientNumber = patientNumber;
    }

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, orphanRemoval = true)
    public List<Visit> getVisits() {
        return visits;
    }

    public void setVisits(List<Visit> visits) {
        this.visits = visits;
    }

    @ManyToOne
    public Insurance getInsurance() {
        return insurance;
    }

    public void setInsurance(Insurance insurance) {
        if (insurance != null) {
            if (this.insurance != insurance) {
                if (this.insurance != null) {
                    this.insurance.getPatientList().remove(this);
                }
                this.insurance = insurance;
                insurance.addPatient(this);
            }
        }
    }

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    public Set<ImagingTest> getImagingTests() {
        return imagingTests;
    }

    public void setImagingTests(Set<ImagingTest> imagingTests) {
        this.imagingTests = imagingTests;
    }

    public void addImagingTest(ImagingTest test) {
        if (!getImagingTests().contains(test)) {
            getImagingTests().add(test);
            test.setPatient(this);
        }
    }

    @OneToOne(mappedBy = "patient", cascade = CascadeType.ALL, orphanRemoval = true)
    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        if (person != null) {
            if (this.person != person) {
                if (this.person != null) {
                    this.person.setPatient(null);
                }
                this.person = person;
                person.setPatient(this);
            }
        }
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    @Override
    public String toString() {
        return "Patient{" +
                "patientNumber=" + patientNumber +
                ", weight=" + weight +
                ", height=" + height +
                //          ", person=" + person +
                ", insurance=" + insurance +
                '}';
    }

    public void removeVisit(Visit visit) {
        getVisits().remove(visit);
    }

    public void addVisit(Visit visit) {
        if (!getVisits().contains(visit)) {
            getVisits().add(visit);
            //dodajemy połączenie zwrotne
            visit.setPatient(this);
        }
    }

}

