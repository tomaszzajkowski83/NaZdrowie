package Models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Phisiotherapist extends MedicalEmployee{
    private LocalDate graduationDate;
    private Education education;

    public Phisiotherapist(){}

    public Phisiotherapist(String telephone, LocalDate hireDate, int jobLicence, LocalDate graduationDate, Education education, String... prevEmploers) throws Exception{
        super(telephone, hireDate, jobLicence, prevEmploers);
        if(graduationDate == null){
            throw new Exception("graduationDate must be set!!!");
        }
        setGraduationDate(graduationDate);
        setEducation(education);
    }

    public LocalDate getGraduationDate() {
        return graduationDate;
    }

    public void setGraduationDate(LocalDate graduationDate) {
        this.graduationDate = graduationDate;
    }

    public Education getEducation() {
        return education;
    }

    public void setEducation(Education education) {
        this.education = education;
    }

    @Transient
    public String getExperience(){
        String exp = "";
        long tmp = (LocalDate.now().toEpochDay())-(graduationDate.toEpochDay());
        exp += tmp/365 + " years " + tmp%365/30 + " months";
        return exp;
    }

    @Override
    public double calculateSalary(){
        double sal = 4000;
        return sal;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }else if(obj instanceof Phisiotherapist){
            return ((Phisiotherapist) obj).getPerson().equals(this.getPerson());
        }else
            return false;
    }

    @Override
    public String toString() {
        String about = super.toString();
        about += "\tGraduation: " + graduationDate;
        return about;
    }

}
