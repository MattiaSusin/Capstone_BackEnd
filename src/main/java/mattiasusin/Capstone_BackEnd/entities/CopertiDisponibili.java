package mattiasusin.Capstone_BackEnd.entities;

import jakarta.persistence.*;
import lombok.*;
import mattiasusin.Capstone_BackEnd.exceptions.BadRequestException;

import java.time.LocalDate;
import java.util.UUID;


@Entity
@Table(name = "coperti_Disponibili")
@Setter
@Getter
@ToString
@NoArgsConstructor
public class CopertiDisponibili {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    @Column(name = "id")
    private UUID id;

    private LocalDate data;
    private int copertiDisponibili;

   /* @ManyToOne
    @JoinColumn
    private Prenotazione prenotazione;*/

    // COSTRUTTORI

    public CopertiDisponibili(UUID id, LocalDate data, int copertiDisponibili) {
        this.id = id;
        this.data = data;
        this.copertiDisponibili = copertiDisponibili;
    }

    // METODI

    public void setCopertiDisponibili(int copertiDisponibili) {
        this.copertiDisponibili = copertiDisponibili;
    }

    public void scalaCoperti(int numeroCoperti) {
        if (this.copertiDisponibili >= numeroCoperti) {
            this.copertiDisponibili -= numeroCoperti;
        } else {
            throw new BadRequestException("Coperti non sufficienti.");
        }
    }


    public void azzeraCoperti() {
        this.copertiDisponibili = 120;
    }

}