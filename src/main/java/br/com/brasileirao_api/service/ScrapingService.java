package br.com.brasileirao_api.service;

import br.com.brasileirao_api.dto.partida.PartidaGoogleDTO;
import br.com.brasileirao_api.model.Partida;
import br.com.brasileirao_api.util.ScrapingUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class ScrapingService {

    private ScrapingUtil scrapingUtil;

    private PartidaService partidaService;

    public void verificaPartidaPeriodo() {

        Integer quantidadePartida = partidaService.buscarQuantidadePartidasPeriodo();

        if (quantidadePartida > 0) {
            List<Partida> partidas = partidaService.listarPartidasPeriodo();

            partidas.forEach(partida -> {
                String urlPartida = scrapingUtil.montarUrlGoogle(
                        partida.getEquipeCasa().getNomeEquipe(),
                        partida.getEquipeVisitante().getNomeEquipe());

                PartidaGoogleDTO partidaGoogle = scrapingUtil.obtemInformacoesPartida(urlPartida);

                partidaService.atualizaPartida(partida,partidaGoogle);

            });
        }
    }

}
