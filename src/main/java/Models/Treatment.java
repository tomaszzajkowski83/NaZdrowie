package Models;


import Services.DbContext;
import org.hibernate.Session;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "Treatment")
public class Treatment {
    private long Id;
    private LocalDate date;
    private MedicalEmployee medicalEmployee;
    private String name;
    private ServiceType type;
    private double price;
    private Duration duration;
    private String opis;
    private Visit visit;

    public Treatment() {
    }

    public Treatment(ServiceType type, String name, double price, Duration duration) {
        setName(name);
        setType(type);
        setPrice(price);
        setDuration(duration);
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

    @ManyToOne
    public MedicalEmployee getMedicalEmployee() {
        return medicalEmployee;
    }

    public void setMedicalEmployee(MedicalEmployee medicalEmployee) {
        this.medicalEmployee = medicalEmployee;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ServiceType getType() {
        return type;
    }

    public void setType(ServiceType type) {
        this.type = type;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    @ManyToOne
    public Visit getVisit() {
        return visit;
    }

    public void setVisit(Visit visit) throws Exception{
        if (visit != null) {
            if (this.visit != visit) {
                if (this.visit != null) {
                    this.visit.getTreatments().remove(this);
                }
                this.visit = visit;
                visit.addTreatment(this);
            }
        }
    }

    @Transient
    public List<Treatment> getAllTreatments() {
        List<Treatment> result = new ArrayList<>();
        Session session = DbContext.getSession();
        session.beginTransaction();
        result.addAll(session.createQuery("from Treatment").list());
        session.getTransaction().commit();
        session.close();
        return result;
    }

    @Override
    public String toString() {
        return visit.getPatient().getPerson().toString()+" "+
                date +
                " ; " + name + "\t" + " Usługa: " +
                type;
    }
}
