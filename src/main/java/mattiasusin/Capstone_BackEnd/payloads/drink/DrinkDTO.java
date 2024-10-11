package mattiasusin.Capstone_BackEnd.payloads.drink;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record DrinkDTO(@NotEmpty(message = "Campo obbligatorio. Inserire Titolo del drink.")
                       @Size(min = 3, max = 30, message = "Il nome deve essere compreso tra 3 e 30 caratteri")
                       String titolo,
                       @NotEmpty(message = "Campo obbligatorio. Inserire descrizione del drink.")
                       @Size(min = 3, max = 100, message = "Il nome deve essere compreso tra 3 e 30 caratteri")
                       String descrizione,
                       @NotEmpty(message = "Campo obbligatorio. Inserire il prezzo del drink.")
                       @Size(min = 1, max = 30, message = "Il nome deve essere compreso tra 3 e 30 caratteri")
                       String prezzo,
                       @NotEmpty(message = "Campo obbligatorio. Inserire il tipo del drink.")
                       @Size(min = 1, max = 30, message = "Il nome deve essere compreso tra 3 e 30 caratteri")
                       String tipoDrink,
                       String immagine
                       ) {
}
