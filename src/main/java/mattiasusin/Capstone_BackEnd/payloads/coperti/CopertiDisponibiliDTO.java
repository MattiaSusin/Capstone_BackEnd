package mattiasusin.Capstone_BackEnd.payloads.coperti;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CopertiDisponibiliDTO(@NotNull(message = "Campo obbligatorio. Inserire coperti Disponibili.")
                                    @Size(min = 3, max = 30, message = "Il nome deve essere compreso tra 1 e 3 caratteri")
                                    int copertiDisponibili
                                    ) {
}
