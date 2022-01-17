package Models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class AdditionalDocument {
    private long Id;
    private DokumentType type;
    private LocalDate expirationDate;
    private String description;
    private Visit visit;

    public AdditionalDocument(){}

    public static AdditionalDocument createDocument(LocalDate expirationDateate, DokumentType type){
        AdditionalDocument doc = new AdditionalDocument();
        doc.setExpirationDate(expirationDateate);
        doc.setType(type);
        return doc;
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
    public Visit getVisit() {
        return visit;
    }

    public void setVisit(Visit visit) {
        if (visit != null) {
            if (this.visit != visit) {
                if (this.visit != null) {
                    this.visit.getAdditionalDocuments().remove(this);
                }
                this.visit = visit;
                visit.addAdditionalDocument(this);
            }
        }
    }

    public DokumentType getType() {
        return type;
    }

    public void setType(DokumentType type) {
        this.type = type;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Transient
    public LocalDate getCreationDate(){
        return LocalDate.now();
    }

    @Transient
    public int getPatientId(){
        return 0;
    }

    @Override
    public String toString() {
        return "AdditionalDocument{" +
                "type=" + type +
                ", expirationDate=" + expirationDate +
                ", description='" + description + '\'' +
                '}';
    }
}
