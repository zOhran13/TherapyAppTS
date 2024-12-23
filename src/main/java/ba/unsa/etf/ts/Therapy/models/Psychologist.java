package ba.unsa.etf.ts.Therapy.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;
@Entity
@Getter
@Setter
@Table(name = "psychologist")
@DiscriminatorValue("psychologist")
public class Psychologist  {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", columnDefinition = "VARCHAR(64)")
    private String id;

    @Column(name = "user_id", columnDefinition = "VARCHAR(64)")
    private String userId;
    public Psychologist(UUID uuid) {
        super();
    }

    public Psychologist() {
        super();
    }
}