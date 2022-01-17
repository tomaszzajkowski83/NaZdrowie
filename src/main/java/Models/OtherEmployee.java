package Models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Entity
public class OtherEmployee extends Employee{
    private long Id;
    private Department department;
    private List<String> languages;

    public OtherEmployee(){}

    public OtherEmployee(String businessPhone, LocalDate hireDate, Department department, String... languages) {
        super(businessPhone, hireDate);
        setDepartment(department);
        setLanguages(Arrays.stream(languages).collect(Collectors.toList()));
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

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    @ElementCollection
    public List<String> getLanguages() {
        return languages;
    }

    public void setLanguages(List<String> languages) {
        this.languages = languages;
    }

    @Override
    public double calculateSalary() {
        return 0;
    }

    @Override
    public String toString() {
        return super.toString()+
                "; OtherEmployee{" +
                ", department=" + department +
                ", languages=" + languages +
                '}';
    }
}
