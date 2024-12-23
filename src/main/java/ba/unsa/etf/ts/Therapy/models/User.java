//package ba.unsa.etf.ts.Therapy.models;
//
//import jakarta.persistence.*;
//import lombok.Getter;
//import lombok.Setter;
//
//import java.util.UUID;
//
//@Entity
//@Getter
//@Setter
//@Table(name = "user_helper")
//@Inheritance(strategy = InheritanceType.JOINED)
//@DiscriminatorColumn(name = "user_type", discriminatorType = DiscriminatorType.STRING)
//public class User {
//    @Id
//    @GeneratedValue(strategy = GenerationType.UUID)
//    @Column(name = "id", columnDefinition = "VARCHAR(64)")
//    private String id;
//
//    @Column(name = "user_id", columnDefinition = "VARCHAR(64)")
//    private String userId;
//    public User(String uuid) {
//
//    }
//
//    public User() {
//    }
//
//    public String getUserId() {
//        return userId;
//    }
//}
