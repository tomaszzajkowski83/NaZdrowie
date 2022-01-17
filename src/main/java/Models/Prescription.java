package Models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Prescription {
    private long Id;
    private String recommendations;
    private String e_code;
    private Visit visit;
    private Set<Medicine> medicines = new HashSet<>();

    public Prescription(){}

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

    public String getRecommendations() {
        return recommendations;
    }

    public void setRecommendations(String recommendations) {
        this.recommendations = recommendations;
    }

    public String getE_code() {
        return e_code;
    }

    public void setE_code(String e_code) {
        this.e_code = e_code;
    }

    @ManyToOne
    public Visit getVisit() {
        return visit;
    }

    public void setVisit(Visit visit) {
        if (visit != null) {
            if (this.visit != visit) {
                if (this.visit != null) {
                    this.visit.getPrescriptions().remove(this);
                }
                this.visit = visit;
                visit.addPrescription(this);
            }
        }
    }

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "Prescription_Medicine", joinColumns = {@JoinColumn(name = "Prescription_Id")},
            inverseJoinColumns = {@JoinColumn(name = "Medicins_Id")})
    public Set<Medicine> getMedicines() {
        return medicines;
    }

    public void setMedicines(Set<Medicine> medicins) {
        this.medicines = medicins;
    }

    public void addMedicine(Medicine medicine){
        if(!getMedicines().contains(medicine)){
            getMedicines().add(medicine);
            medicine.addPrescription(this);
        }
    }

    @Transient
    public LocalDate getCreationDate(){
        return visit.getDate();
    }

    @Override
    public String toString() {
        return "Prescription{" +
                "e_code='" + e_code + '\'' +
                '}';
    }
}
