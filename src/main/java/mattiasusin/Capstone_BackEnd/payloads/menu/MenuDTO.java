package mattiasusin.Capstone_BackEnd.payloads.menu;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record MenuDTO(@NotEmpty(message = "Campo obbligatorio. Inserire Titolo del piatto.")
                      @Size(min = 3, max = 30, message = "Il nome deve essere compreso tra 3 e 30 caratteri")
                      String titolo,
                      @NotEmpty(message = "Campo obbligatorio. Inserire descrizione del piatto.")
                      @Size(min = 3, max = 100, message = "Il nome deve essere compreso tra 3 e 30 caratteri")
                      String descrizione,
                      @NotEmpty(message = "Campo obbligatorio. Inserire il prezzo del piatto.")
                      @Size(min = 1, max = 30, message = "Il nome deve essere compreso tra 3 e 30 caratteri")
                      String prezzo,
                      @NotEmpty(message = "Campo obbligatorio. Inserire il tipo del piatto.")
                      @Size(min = 1, max = 30, message = "Il nome deve essere compreso tra 3 e 30 caratteri")
                      String tipoPiatto,
                      String immagine
                      ) {
}
