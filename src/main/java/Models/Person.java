package Models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Entity
public class Person {
    private long id;
    private String firstName;
    private String lastName;
    private String pesel;
    private Address address;
    private LocalDate dateOfBirth;
    private Employee employee;
    private Patient patient;

    public Person() {
    }

    public Person(String firstName, String lastName, String pesel, Address address, LocalDate dateOfBirth) throws Exception{
        if(address == null)
            throw new Exception("Every person must an address");
        setAddress(address);
        setDateOfBirth(dateOfBirth);
        setFirstName(firstName);
        setLastName(lastName);
        setPesel(pesel);
    }

    public Person(String firstName, String lastName, String pesel, Address address, LocalDate dateOfBirth, int nrId, double weight, int height) throws Exception{
        this(firstName, lastName, pesel, address, dateOfBirth);
        createPatient(nrId, weight, height);
    }

    public Person(String firstName, String lastName, String pesel, Address address, LocalDate dateOfBirth, String telSluzb, LocalDate hireDate, int jobLicence, boolean surgeon, String... prevEmploers) throws Exception{
        this(firstName, lastName, pesel, address, dateOfBirth);
        createDoctor(telSluzb, hireDate, jobLicence, surgeon, prevEmploers);
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

    @ManyToOne(cascade = CascadeType.ALL)
    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        if (address != null) {
            if (this.address != address) {
                if (this.address != null) {
                    this.address.getPersons().remove(this);
                }
                this.address = address;
                address.addPerson(this);
            }
        }
    }

    @OneToOne
    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        if (employee != null) {
            if (this.employee != employee) {
                if (this.employee != null) {
                    this.employee.setPerson(null);
                }
                this.employee = employee;
                employee.setPerson(this);
            }
        }
    }

    @OneToOne
    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        if (patient != null) {
            if (this.patient != patient) {
                if (this.patient != null) {
                    this.patient.setPerson(null);
                }
                this.patient = patient;
                patient.setPerson(this);
            }
        }
    }

    public Employee addEmployee(String businessPhone, LocalDate hireDate, Department department, String... languages) {
        OtherEmployee employee = new OtherEmployee(businessPhone, hireDate, department, languages);
        return employee;
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

    public String getPesel() {
        return pesel;
    }

    public void setPesel(String pesel) {
        this.pesel = pesel;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    @Override
    public String toString() {
        return firstName + " " +
                lastName +
                "\t PESEL " + pesel;
    }


    public void createPatient(int nrId, double weight, int height) {
        Patient pat = new Patient(nrId, weight, height);
        setPatient(pat);
    }

    public void removeVisit(Visit visit) throws NotPacjententException {
        if (patient == null)
            throw new NotPacjententException();
        patient.getVisits().remove(visit);
    }

    public void addVisit(Visit visit) throws NotPacjententException {
        if (patient == null)
            throw new NotPacjententException();
        patient.addVisit(visit);
    }

    @Transient
    public int getHeight() throws NotPacjententException {
        if (patient == null)
            throw new NotPacjententException();
        return patient.getHeight();
    }

    public void setHeight(int height) throws NotPacjententException {
        if (patient == null)
            throw new NotPacjententException();
        patient.setHeight(height);
    }

    @Transient
    public int getPatientNumber() throws NotPacjententException {
        if (patient == null)
            throw new NotPacjententException();
        return patient.getPatientNumber();
    }

    public void setPatientNumber(int patientNumber) throws NotPacjententException {
        if (patient == null)
            throw new NotPacjententException();
        patient.setPatientNumber(patientNumber);
    }

    @Transient
    public Set<ImagingTest> getImagingTests() throws NotPacjententException {
        if (patient == null)
            throw new NotPacjententException();
        return patient.getImagingTests();
    }

    public void addImagingTests(ImagingTest imagingTests) throws NotPacjententException {
        if (patient == null)
            throw new NotPacjententException();
        patient.addImagingTest(imagingTests);
    }

    @Transient
    public List<Visit> getVisits() throws NotPacjententException {
        if (patient == null)
            throw new NotPacjententException();
        return patient.getVisits();
    }

    public void setVisits(List<Visit> visits) throws NotPacjententException {
        if (patient == null)
            throw new NotPacjententException();
        patient.setVisits(visits);
    }

    @Transient
    public double getWeight() throws NotPacjententException {
        if (patient == null)
            throw new NotPacjententException();
        return patient.getWeight();
    }

    public void setWeight(double weight) throws NotPacjententException {
        if (patient == null)
            throw new NotPacjententException();
        patient.setWeight(weight);
    }

    public void createDoctor(String businessPhone, LocalDate hireDate, int jobLicence, boolean surgeon, String... prevEmploers) {
        Doctor doc = new Doctor(businessPhone, hireDate, jobLicence, surgeon, prevEmploers);
        setEmployee(doc);
    }
}