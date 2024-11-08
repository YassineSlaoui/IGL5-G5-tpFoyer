package tn.esprit.tpfoyer17.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import tn.esprit.tpfoyer17.entities.enumerations.TypeChambre;

import java.io.Serializable;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Chambre implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private long idChambre;

    private long numeroChambre;

    @Enumerated(EnumType.STRING)
    private TypeChambre typeChambre;

    @JsonIgnore
    @ToString.Exclude
    @ManyToOne
    private Bloc bloc;

    @JsonIgnore
    @ToString.Exclude
    @OneToMany
    private Set<Reservation> reservations;
}
