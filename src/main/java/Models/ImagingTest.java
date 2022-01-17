package Models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.swing.*;
import java.time.LocalDate;
import java.util.List;

@Entity
public class ImagingTest {
    private long Id;
    private boolean hasReferral;
    private TestType type;
    private String testedArea;
    private LocalDate examinationDate;
    private double price;
    private List<ImageIcon> images;
    private String description;
    private Patient patient;

    public ImagingTest() {
    }

    private ImagingTest(boolean referral, TestType type, String testedArea, LocalDate examinationDate, double price) {
        setHasReferral(referral);
        setType(type);
        setTestedArea(testedArea);
        setExaminationDate(examinationDate);
        setPrice(price);
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

    public boolean isHasReferral() {
        return hasReferral;
    }

    public void setHasReferral(boolean hasReferral) {
        this.hasReferral = hasReferral;
    }

    @ManyToOne
    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        if (patient != null) {
            if (this.patient != patient) {
                if (this.patient != null) {
                    this.patient.getImagingTests().remove(this);
                }
                this.patient = patient;
                patient.addImagingTest(this);
            }
        }
    }

    @Enumerated(value = EnumType.STRING)
    public TestType getType() {
        return type;
    }

    public void setType(TestType type) {
        this.type = type;
    }

    public String getTestedArea() {
        return testedArea;
    }

    public void setTestedArea(String testedArea) {
        this.testedArea = testedArea;
    }

    public LocalDate getExaminationDate() {
        return examinationDate;
    }

    public void setExaminationDate(LocalDate examinationDate) {
        this.examinationDate = examinationDate;
    }

    @ElementCollection
    public List<ImageIcon> getImages() {
        return images;
    }

    public void setImages(List<ImageIcon> images) {
        this.images = images;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        if (isHasReferral()) {
            this.price = price / 2;
        } else
            this.price = price;
    }

    public static ImagingTest makeReservation(boolean referral, TestType type, String testedArea, LocalDate examinationDate, double price) {
        return new ImagingTest(referral, type, testedArea, examinationDate, price);
    }

    @Override
    public String toString() {
        return "ImagingTest{" +
                "type=" + type +
                ", testedArea='" + testedArea + '\'' +
                ", examinationDate=" + examinationDate +
                ", price=" + price +
                ", images=" + images +
                ", description='" + description + '\'' +
                ", patient=" + patient +
                '}';
    }
}

