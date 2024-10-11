package mattiasusin.Capstone_BackEnd.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "contatti")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Contatto {
    @Id
    @Setter(AccessLevel.NONE)
    @GeneratedValue
    private UUID id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "cognome")
    private String cognome;

    @Column(name = "email")
    private String email;

    @Column(name = "messaggio")
    private String messaggio;

    // COSTRUTTORI

    public Contatto(String nome, String cognome, String email, String messaggio) {
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.messaggio = messaggio;
    }

}
