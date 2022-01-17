package Models;

import Services.DbContext;
import org.hibernate.Session;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
public class MedicalEmployee extends Employee {
    private int jobLicenceNumber;
    private List<String> previousEmployers = new ArrayList<>();
    private List<Visit> visits = new ArrayList<>();
    private Set<Treatment> treatments = new HashSet<>();

    public MedicalEmployee() {
    }

    public MedicalEmployee(String telephone, LocalDate hiredate, int jobLicence, String... prevEmploers) {
        super(telephone, hiredate);
        setJobLicenceNumber(jobLicence);
        setPreviousEmployers(previousEmployers.stream().collect(Collectors.toList()));
    }

    @OneToMany(mappedBy = "medicalEmployee")
    public List<Visit> getVisits() {
        return visits;
    }

    public void setVisits(List<Visit> visits) {
        this.visits = visits;
    }

    public void addVisit(Visit visit) {
        if (!getVisits().contains(visit)) {
            getVisits().add(visit);
            visit.setMedicalEmployee(this);
        }
    }

    @OneToMany(mappedBy = "medicalEmployee",fetch = FetchType.EAGER)
    public Set<Treatment> getTreatments() {
        return treatments;
    }

    public void setTreatments(Set<Treatment> treatments) {
        this.treatments = treatments;
    }

    public int getJobLicenceNumber() {
        return jobLicenceNumber;
    }

    public void setJobLicenceNumber(int jobLicenceNumber) {
        this.jobLicenceNumber = jobLicenceNumber;
    }

    @ElementCollection
    public List<String> getPreviousEmployers() {
        return previousEmployers;
    }

    public void setPreviousEmployers(List<String> previousEmployers) {
        this.previousEmployers = previousEmployers;
    }

    @Transient
    public boolean isAnyService() {
        getTreatments();
        getVisits();
        if (treatments.size() > 0 || visits.size() > 0) {
            return true;
        }
        return false;
    }

    public double calculateSalary() {
        return 0;
    }

    public  List<Treatment> showServices() {
        List<Treatment> result = new ArrayList();
        Session session = DbContext.getSession();
        session.beginTransaction();
        for(Treatment tr : (java.util.List<Treatment>)session.createQuery("from Treatment ").list()) {
            result.add(tr);
        }
        session.getTransaction().commit();
        session.close();
        return result;
    }

    public void writeDokument() {
    }

    public void makeCorrection() {
    }

    @Override
    public String toString() {
        return super.getPerson().getFirstName() + " "+
                super.getPerson().getLastName() + " "+
                "licence: " + jobLicenceNumber;
    }
}

