package br.com.brasileirao_api.dto.equipe;

import br.com.brasileirao_api.model.Equipe;
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
public class EquipeResponseDTO implements Serializable {

    private static  final long seralVersionUID = 1L;

    private List<Equipe> equipes;

}
