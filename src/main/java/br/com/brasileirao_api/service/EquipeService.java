package br.com.brasileirao_api.service;

import br.com.brasileirao_api.dto.equipe.EquipeDTO;
import br.com.brasileirao_api.dto.equipe.EquipeResponseDTO;
import br.com.brasileirao_api.exception.BadRequestException;
import br.com.brasileirao_api.exception.NotFoundException;
import br.com.brasileirao_api.model.Equipe;
import br.com.brasileirao_api.repository.EquipeRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EquipeService {

    private final EquipeRepository equipeRepository;

    private final ModelMapper  modelMapper;


    public Equipe buscarEquipeId(Long id) {
        return equipeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Nenhuma equipe encontrada com o id informado: " + id));
    }

    public Equipe buscarEquipeNome(String nome) {
        return equipeRepository.findByNomeEquipe(nome)
                .orElseThrow(() -> new NotFoundException("Nenhuma equipe encontrada com o nome informado: " + nome));
    }


    public EquipeResponseDTO listarEquipes() {
        EquipeResponseDTO equipes = new EquipeResponseDTO();
        equipes.setEquipes(equipeRepository.findAll());
        return equipes;
    }

    public Equipe inserirEquipe(EquipeDTO dto) {
        boolean existe = equipeRepository.existsByNomeEquipe(dto.getNomeEquipe());
        if (existe){
            throw new BadRequestException("Já existe uma equipe cadastrada com o nome informado.");
        }
        Equipe equipe = modelMapper.map(dto, Equipe.class);

        return equipeRepository.save(equipe);

    }

    public void alterarEquipe(long id, EquipeDTO dto) {
        boolean exists = equipeRepository.existsById(id);
        if(!exists){
            throw new BadRequestException("Não foi possivel alterar a equipe: ID inexistente.");
        }

        Equipe equipe = modelMapper.map(dto, Equipe.class);
        equipe.setId(id);
        equipeRepository.save(equipe);
    }

}
