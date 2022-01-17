package Models;

import Services.DbContext;
import org.hibernate.Session;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.Duration;
import java.time.LocalDate;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Employee {
    private long Id;
    private String businessPhone;
    private LocalDate hireDate;
    private Person person;

    public Employee() {
    }

    public Employee(String businessPhone, LocalDate hireDate) {
        setBusinessPhone(businessPhone);
        setHireDate(hireDate);
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

    @OneToOne(mappedBy = "employee")
    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        if (person != null) {
            if (this.person != person)
                if (this.person != null) {
                    this.person.setEmployee(null);
                }
            this.person = person;
            person.setEmployee(this);
        }
    }

    public String getBusinessPhone() {
        return businessPhone;
    }

    public void setBusinessPhone(String businessPhone) {
        this.businessPhone = businessPhone;
    }

    public LocalDate getHireDate() {
        return hireDate;
    }

    public void setHireDate(LocalDate hireDate) {
        this.hireDate = hireDate;
    }

    @Transient
    public LocalDate getSeniority() {
        return LocalDate.ofEpochDay((Duration.between(getHireDate(), LocalDate.now())).toDays());
    }

    @Transient
    public static List<Employee> getEmployees() {
        Session session = DbContext.getSession();
        session.beginTransaction();
        List<Employee> employees = (List<Employee>) session.createQuery("from Employee ").list();
        session.getTransaction().commit();
        session.close();
        return employees;
    }

    public abstract double calculateSalary();

    public static Employee findEmployee(long empId) {
        Session session = DbContext.getSession();
        session.beginTransaction();
        Employee employee = (Employee) session.createQuery("from Employee where id = :identifier")
                .setParameter("identifier", empId).list();
        session.getTransaction().commit();
        session.close();
        return employee;
    }

    public void removeEmployee() {
        Session session = DbContext.getSession();
        session.beginTransaction();
        session.delete(this);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public String toString() {
        String tmp = "; Pracownik zatrudniony od: " + getHireDate()
                        + "nr tel: " + getBusinessPhone();
        return tmp;
    }
}
