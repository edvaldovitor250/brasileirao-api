package br.com.brasileirao_api.service;

import br.com.brasileirao_api.dto.equipe.EquipeResponseDTO;
import br.com.brasileirao_api.exception.NotFoundException;
import br.com.brasileirao_api.model.Equipe;
import br.com.brasileirao_api.repository.EquipeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EquipeService {

    private final EquipeRepository equipeRepository;


    public Equipe buscarEquipeId(Long id) {
        return equipeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Nenhuma equipe encontrada com o id informado: " + id));
    }

    public EquipeResponseDTO listarEquipes() {
        EquipeResponseDTO equipes = new EquipeResponseDTO();
        equipes.setEquipes(equipeRepository.findAll());
        return equipes;
    }
}
