package mattiasusin.Capstone_BackEnd.entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "prenotazioni")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Prenotazione implements UserDetails {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "cognome")
    private String cognome;

    @Column(name = "email")
    private String email;

    @Column(name = "numero_telefono")
    private String telefono;

    @Column(name = "data")
    private LocalDate data;

    @Column(name = "numero_Coperti")
    private int numeroCoperti;

    @Column(name = "orario")
    private double orario;

    /*@OneToMany(cascade = CascadeType.ALL)
    @JsonIgnore
    private List<CopertiDisponibili> copertiDisponibiliList;*/



    // COSTUTTORI

    public Prenotazione(String nome, String cognome, String email, String telefono, LocalDate data, int numeroCoperti, String orario) {
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.telefono = telefono;
        this.data = data;
        this.numeroCoperti = numeroCoperti;
        this.orario = Double.parseDouble(orario);


    }


    // METODI
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        return this.email;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    public void getNumeroCoperti(int i) {
    }
}
