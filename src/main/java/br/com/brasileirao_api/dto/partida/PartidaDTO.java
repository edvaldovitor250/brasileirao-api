package br.com.brasileirao_api.dto.partida;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PartidaDTO {

    private static final long serialVersionUID = 1L;

    @NotBlank
    private String nomeEquipeCasa;

    @NotBlank
    private String nomeEquipeVisitante;

    @NotBlank
    private String localPartida;

    @Schema(example = "yyyy-MM-dd")
    @NotNull
    private LocalDate dataHoraPartida;
}
