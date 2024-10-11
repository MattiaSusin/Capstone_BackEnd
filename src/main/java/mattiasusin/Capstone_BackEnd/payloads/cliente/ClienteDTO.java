package mattiasusin.Capstone_BackEnd.payloads.cliente;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ClienteDTO(
        @NotEmpty(message = "Campo obbligatorio. Inserire nome.")
        @Size(min = 3, max = 30, message = "Il nome deve essere compreso tra 3 e 30 caratteri")
        String nome,
        @NotEmpty(message = "Campo obbligatorio. Inserire cognome.")
        @Size(min = 3, max = 30, message = "Il cognome deve essere compreso tra 3 e 30 caratteri")
        String cognome,
        @Size(min = 3, max = 30, message = "L'email deve essere compreso tra 3 e 30 caratteri")
        @Email(message = "Inserire una email corretta.")
        String email,
        @NotNull(message = "Inserire il numero di telefono")
        @Size(min = 3, max = 10, message = "Il numero di telefono deve essere di 9 cifre")
        String telefono
)
{
}
