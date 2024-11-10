package br.com.brasileirao_api.service;

import br.com.brasileirao_api.dto.partida.PartidaDTO;
import br.com.brasileirao_api.dto.partida.PartidaResponseDTO;
import br.com.brasileirao_api.exception.BadRequestException;
import br.com.brasileirao_api.exception.NotFoundException;
import br.com.brasileirao_api.model.Partida;
import br.com.brasileirao_api.repository.PartidaRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@AllArgsConstructor
@Service
public class PartidaService {

    private static final Logger logger = LoggerFactory.getLogger(PartidaService.class);
    private final PartidaRepository partidaRepository;
    private final ModelMapper modelMapper;
    private final EquipeService equipeService;

    public Partida buscarPartidaPorId(Long id) {
        logger.info("Buscando partida com ID: {}", id);
        return partidaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Nenhuma partida encontrada com o ID informado: " + id));
    }

    public PartidaResponseDTO listarPartidas() {
        logger.info("Listando todas as partidas");
        PartidaResponseDTO partidas = new PartidaResponseDTO();
        partidas.setPartidas(partidaRepository.findAll());
        return partidas;
    }

    public Partida inserirPartida(PartidaDTO dto) {
        logger.info("Inserindo nova partida");
        Partida partida = modelMapper.map(dto, Partida.class);
        partida.setEquipeCasa(equipeService.buscarEquipeNome(dto.getNomeEquipeCasa()));
        partida.setEquipeVisitante(equipeService.buscarEquipeNome(dto.getNomeEquipeVisitante()));
        return salvarPartida(partida);
    }

    public void alterarPartida(Long id, PartidaDTO dto) {
        logger.info("Alterando partida com ID: {}", id);
        if (!partidaRepository.existsById(id)) {
            throw new NotFoundException("Não foi possível atualizar a partida: ID inexistente");
        }

        Partida partida = buscarPartidaPorId(id);
        partida.setEquipeCasa(equipeService.buscarEquipeNome(dto.getNomeEquipeCasa()));
        partida.setEquipeVisitante(equipeService.buscarEquipeNome(dto.getNomeEquipeVisitante()));
        partida.setDataHoraPartida(dto.getDataHoraPartida());
        partida.setLocalPartida(dto.getLocalPartida());

        salvarPartida(partida);
    }

    private Partida salvarPartida(Partida partida) {
        logger.info("Salvando partida no repositório");
        return partidaRepository.save(partida);
    }

}
