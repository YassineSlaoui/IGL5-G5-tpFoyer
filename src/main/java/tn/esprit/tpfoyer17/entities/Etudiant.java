package tn.esprit.tpfoyer17.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Etudiant implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private long idEtudiant;

    private String nomEtudiant;

    private String prenomEtudiant;

    private long cinEtudiant;

    private Date dateNaissance;

    @ToString.Exclude
    @ManyToMany(mappedBy = "etudiants")
    private Set<Reservation> reservations;

}
