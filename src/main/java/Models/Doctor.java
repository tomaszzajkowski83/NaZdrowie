package Models;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Doctor extends MedicalEmployee {
    private boolean isSurgeon;
    private List<Specialization> specialisations = new ArrayList<>();

    public Doctor(){}

    public Doctor(String telephone, LocalDate hireDater, int jobLicence, boolean surgeon, String... prevEmploers) {
        super(telephone, hireDater, jobLicence, prevEmploers);
        setSurgeon(surgeon);
    }

    public boolean isSurgeon() {
        return isSurgeon;
    }

    public void setSurgeon(boolean surgeon) {
        isSurgeon = surgeon;
    }

    @ElementCollection
    @Enumerated(EnumType.STRING)
    @LazyCollection(LazyCollectionOption.FALSE)
    public List<Specialization> getSpecialisations() {
        return specialisations;
    }

    public void setSpecialisations(List<Specialization> specialisations) {
        this.specialisations = specialisations;
    }

    @Override
    public double calculateSalary() {
        double sal = 5000;
        if (specialisations.size() > 0) {
            if (isSurgeon) {
                sal *= (1 + 0.15 * specialisations.size());
            }
            sal *= (1 + 0.1 * specialisations.size());

        } else {
            if (isSurgeon) {
                sal *= 1.05;
            }
            sal *= 1;
        }
        return sal;
    }

    @Override
    public String toString() {
        String about = super.toString();
        return about;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        } else if (obj instanceof Doctor) {
            return ((Doctor) obj).getPerson() .equals(this.getPerson());
        } else
            return false;
    }
}
