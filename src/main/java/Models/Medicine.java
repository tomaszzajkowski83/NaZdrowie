package Models;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Medicine {
    private long Id;
    private String name;
    private MedicineGroup drugType;
    private boolean refunded;
    private String dosage;
    private Set<Prescription> prescriptions;

    public Medicine(){}

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MedicineGroup getDrugType() {
        return drugType;
    }

    public void setDrugType(MedicineGroup drugType) {
        this.drugType = drugType;
    }

    public boolean isRefunded() {
        return refunded;
    }

    public void setRefunded(boolean refunded) {
        this.refunded = refunded;
    }

    public String getDosage() {
        return dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    @ManyToMany(mappedBy = "medicines")
    @LazyCollection(LazyCollectionOption.FALSE)
    public Set<Prescription> getPrescriptions() {
        return prescriptions;
    }

    public void setPrescriptions(Set<Prescription> prescriptions) {
        this.prescriptions = prescriptions;
    }

    public void addPrescription(Prescription prescription){
        if(!getPrescriptions().contains(prescription)){
            getPrescriptions().add(prescription);
            prescription.addMedicine(this);
        }
    }

    @Override
    public String toString() {
        return  name;
    }
}
