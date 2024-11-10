package br.com.brasileirao_api.dto.partida;

import br.com.brasileirao_api.model.Partida;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PartidaResponseDTO implements Serializable {


    private static  final long seralVersionUID = 1L;


    private List<Partida> partidas;

}
