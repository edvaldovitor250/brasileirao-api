package br.com.brasileirao_api.service;

import br.com.brasileirao_api.dto.partida.PartidaGoogleDTO;
import br.com.brasileirao_api.model.Partida;
import br.com.brasileirao_api.util.Campeonato;
import br.com.brasileirao_api.util.ScrapingUtil;
import br.com.brasileirao_api.util.StatusPartida;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class ScrapingService {

    private static final Logger logger = LoggerFactory.getLogger(ScrapingService.class);

    private ScrapingUtil scrapingUtil;
    private PartidaService partidaService;

    public void verificaPartidaPeriodo() {
        logger.info("Verificando partidas de todos os campeonatos");
        for (Campeonato campeonato : Campeonato.values()) {
            verificaPartidaPorCampeonato(campeonato.getDescricao());
        }
    }

    public void verificaPartidaPorCampeonato(String campeonato) {
        Integer quantidadePartida = partidaService.buscarQuantidadePartidasPeriodoPorCampeonato(campeonato);

        if (quantidadePartida > 0) {
            logger.info("Encontradas {} partidas em andamento para o campeonato: {}", quantidadePartida, campeonato);
            List<Partida> partidas = partidaService.listarPartidasPeriodoPorCampeonato(campeonato);

            partidas.forEach(partida -> {
                String urlPartida = scrapingUtil.montarUrlGoogle(
                        partida.getEquipeCasa().getNomeEquipe(),
                        partida.getEquipeVisitante().getNomeEquipe());

                PartidaGoogleDTO partidaGoogle = scrapingUtil.obtemInformacoesPartida(urlPartida);

                if (partidaGoogle.getStatusPartida() != StatusPartida.PARTIDA_NAO_INICIADA) {
                    partidaService.atualizaPartida(partida, partidaGoogle);
                }
            });
        }
    }
}
