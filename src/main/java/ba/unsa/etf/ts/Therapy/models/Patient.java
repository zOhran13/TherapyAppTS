package ba.unsa.etf.ts.Therapy.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;
import java.util.UUID;

@Entity
@Table(name = "patient")
@DiscriminatorValue("patient")
@Getter
@Setter
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", columnDefinition = "VARCHAR(64)")
    private String id;

    @Column(name = "patient_user_id", columnDefinition = "VARCHAR(64)")
    private String userId;
    @NotNull
    @Range(min = 13, max = 100, message = "Age must be between 13 and 100")
    @Column(name = "age")
    private Integer age;
    @ManyToOne
    @JoinColumn(name = "selected_psychologist_id", referencedColumnName = "user_id", unique = false)
    private Psychologist selectedPsychologist;
    public Patient(UUID userId) {
        super();
    }

    public Patient(UUID userId, int i, Psychologist psychologist) {
        this.userId = userId.toString();
        age=i;
        selectedPsychologist=psychologist;
    }

    public Patient() {
        super();
    }

    public Patient(int i, Psychologist psychologist) {
        super();
        this.selectedPsychologist=psychologist;
        this.age=i;
    }

    public void setSelectedPsychologist(Psychologist selectedPsychologist) {
        this.selectedPsychologist = selectedPsychologist;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getSelectedPsychologistId() {
        if(selectedPsychologist!=null){
        return selectedPsychologist.getUserId();}
        else{
            return null;
        }
    }


}