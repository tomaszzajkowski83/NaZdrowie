package Models;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Visit {
    private long Id;
    private LocalDate date;
    private String diagnosis;
    private String medicalInterviev;
    private MedicalEmployee medicalEmployee;
    private VisitStatus status;
    private Patient patient;
    private static Set<Treatment> allTreatments = new HashSet<>();
    private List<Treatment> treatments = new ArrayList<>();
    private Set<AdditionalDocument> additionalDocuments = new HashSet<>();
    private List<Prescription> prescriptions = new ArrayList<>();

    public Visit() {
    }

    public Visit(LocalDate date, Patient patient) {
        setDate(date);
        setStatus(VisitStatus.ZAREZERVWANA);
        setPatient(patient);
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

    @ManyToOne
    public MedicalEmployee getMedicalEmployee() {
        return medicalEmployee;
    }

    public void setMedicalEmployee(MedicalEmployee medicalEmployee) {
        if (medicalEmployee != null) {
            if (this.medicalEmployee != medicalEmployee) {
                if (this.medicalEmployee != null) {
                    this.medicalEmployee.getVisits().remove(this);
                }
                this.medicalEmployee = medicalEmployee;
                medicalEmployee.addVisit(this);
            }
        }
    }

    @ManyToOne(cascade = CascadeType.ALL)
    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        if (patient != null) {
            if (this.patient != patient) {
                if (this.patient != null) {
                    this.patient.removeVisit(this);
                }
                this.patient = patient;
                patient.addVisit(this);
            }
        }
    }

    @OneToMany(mappedBy = "visit", cascade = CascadeType.ALL, orphanRemoval = true)
    public List<Treatment> getTreatments() {
        return treatments;
    }

    public void setTreatments(List<Treatment> treatements) {
        this.treatments = treatements;
    }

    @OneToMany(mappedBy = "visit", cascade = CascadeType.ALL, orphanRemoval = true)
    @LazyCollection(LazyCollectionOption.FALSE)
    public Set<AdditionalDocument> getAdditionalDocuments() {
        return additionalDocuments;
    }

    public void setAdditionalDocuments(Set<AdditionalDocument> additionalDocuments) {
        this.additionalDocuments = additionalDocuments;
    }
    public void addAdditionalDocument(AdditionalDocument document){
        if(!getAdditionalDocuments().contains(document)){
            getAdditionalDocuments().add(document);
            document.setVisit(this);
        }
    }

    @OneToMany(mappedBy = "visit",cascade = CascadeType.ALL, orphanRemoval = true)
    @LazyCollection(LazyCollectionOption.FALSE)
    public List<Prescription> getPrescriptions() {
        return prescriptions;
    }

    public void setPrescriptions(List<Prescription> prescriptions) {
        this.prescriptions = prescriptions;
    }

    public void addPrescription(Prescription prescription){
        if(!getPrescriptions().contains(prescription)){
            getPrescriptions().add(prescription);
            prescription.setVisit(this);
        }
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String getMedicalInterviev() {
        return medicalInterviev;
    }

    public void setMedicalInterviev(String medicalInterviev) {
        this.medicalInterviev = medicalInterviev;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public VisitStatus getStatus() {
        return status;
    }

    public void setStatus(VisitStatus status) {
        this.status = status;
    }

    @Transient
    public static Set<Treatment> getAllTreatments() {
        return allTreatments;
    }

    public static void setAllTreatments(Set<Treatment> allTreatments) {
        Visit.allTreatments = allTreatments;
    }

    public void addTreatment(Treatment treatment) throws Exception {
        if (!getTreatments().contains(treatment)) {
            if (allTreatments.contains(treatment)) {
                throw new Exception("This threatment is already conected with another Visit");
            }
            getTreatments().add(treatment);
            allTreatments.add(treatment);
            treatment.setVisit(this);
        }
    }

    public void finish(){
        setStatus(VisitStatus.ZREALIZOWANA);
    }

    @Override
    public String toString() {
        return date +" " + patient.getPerson().toString();
    }

    public void createTreatment(ServiceType type, String name, double price, Duration duration) throws Exception{
        Treatment treatment = new Treatment(type,name,price,duration);
        addTreatment(treatment);
    }

    @Transient
    public boolean isAdditionalDocument() {
        return false;
    }
}
