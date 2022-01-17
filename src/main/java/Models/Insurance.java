package Models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Insurance {
    private long Id;
    private static double DISCOUNT = 15.0;
    private double monthlyFee;
    private InsuranceType insuranceType;
    private String insuranceNumber;
    private int numberOfPeople;
    private List<Patient> patientList = new ArrayList<>();

    public Insurance(){}

    public Insurance(String insuranceNumber, int numberOfPeople, InsuranceType type) {
        this.numberOfPeople = numberOfPeople;
        this.insuranceNumber = insuranceNumber;
        this.insuranceType = type;
    }

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    public long getId() {
        return Id;
    }

    private void setId(long id) {
        Id = id;
    }

    @OneToMany(mappedBy = "insurance", cascade = CascadeType.ALL, orphanRemoval = true)
    public List<Patient> getPatientList() {
        return patientList;
    }

    public void setPatientList(List<Patient> patientList) {
        this.patientList = patientList;
    }
    public void addPatient(Patient patient){
        if(!(getPatientList().contains(patient))) {
            getPatientList().add(patient);
            patient.setInsurance(this);
        }
    }

    public static double getDISCOUNT() {
        return DISCOUNT;
    }

    public static void setDISCOUNT(double DISCOUNT) {
        Insurance.DISCOUNT = DISCOUNT;
    }

    @Enumerated(value = EnumType.STRING)
    public InsuranceType getInsuranceType() {
        return insuranceType;
    }

    public void setInsuranceType(InsuranceType insuranceType) {
        this.insuranceType = insuranceType;
    }

    public double getMonthlyFee() {
        return monthlyFee;
    }

    public void setMonthlyFee(double motklyFee) {
        this.monthlyFee = motklyFee;
    }

    public String getInsuranceNumber() {
        return insuranceNumber;
    }

    public void setInsuranceNumber(String insuranceNumber) {
        this.insuranceNumber = insuranceNumber;
    }

    public int getNumberOfPeople() {
        return numberOfPeople;
    }

    public void setNumberOfPeople(int numberOfPeople) {
        this.numberOfPeople = numberOfPeople;
    }

    public static void changeDiscount(double value) {
        if (Math.abs(value - DISCOUNT) >5) {
            System.out.println("The maximum Discount change is 5%......\nThe Value of Discount has not been changed...\n");
            return;
        }
        DISCOUNT = value;
    }
}
