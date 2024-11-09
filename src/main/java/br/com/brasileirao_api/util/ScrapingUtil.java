package br.com.brasileirao_api.util;

import br.com.brasileirao_api.dto.PartidaGoogleDTO;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ScrapingUtil {

    private static final Logger LOGGER = ScrapingConstants.LOGGER;

    public static void main(String[] args) {
        String url = ScrapingConstants.BASE_URL_GOOGLE + "real+madrid+e+osasuna+09/11/24" + ScrapingConstants.COMPLEMENTO_URL_GOOGLE;
        ScrapingUtil scrapingUtil = new ScrapingUtil();
        scrapingUtil.obtemInformacoesPartida(url);
    }

    public PartidaGoogleDTO obtemInformacoesPartida(String url) {
        PartidaGoogleDTO partida = new PartidaGoogleDTO();
        Document document;

        try {
            document = Jsoup.connect(url).get();
            String title = document.title();
            LOGGER.info("Título da Página: {}", title);

            StatusPartida statusPartida = obtemStatusPartida(document);
            LOGGER.info("Status partidas: {}", statusPartida);

            if (statusPartida != StatusPartida.PARTIDA_NAO_INICIADA) {
                String tempoPartida = obtemTempoPartida(document);
                LOGGER.info("Tempo partida: {}", tempoPartida);

                Integer placarEquipeCasa = recuperaPlacarEquipe(document, ScrapingConstants.PLACAR_EQUIPE_CASA);
                LOGGER.info("Placar Casa: {}", placarEquipeCasa);

                Integer placarEquipeVisitante = recuperaPlacarEquipe(document, ScrapingConstants.PLACAR_EQUIPE_VISITANTE);
                LOGGER.info("Placar Visitante: {}", placarEquipeVisitante);
            }

            String nomeEquipeCasa = recuperaNomeEquipe(document, ScrapingConstants.LOGO_EQUIPE_CASA);
            LOGGER.info("Nome equipe Casa: {}", nomeEquipeCasa);

            String nomeEquipeVisitante = recuperaNomeEquipe(document, ScrapingConstants.LOGO_EQUIPE_VISITANTE);
            LOGGER.info("Nome equipe Visitante: {}", nomeEquipeVisitante);

            String urlLogoEquipeCasa = reuperaLogoEquipe(document, ScrapingConstants.LOGO_EQUIPE_CASA);
            LOGGER.info("Url logo equipe casa: {}", urlLogoEquipeCasa);

            String urlLogoEquipeVisitante = reuperaLogoEquipe(document, ScrapingConstants.LOGO_EQUIPE_VISITANTE);
            LOGGER.info("Url logo equipe visitante: {}", urlLogoEquipeVisitante);

            String golsEquipeCasa = recuperaGolsEquipe(document, ScrapingConstants.GOLS_EQUIPE_CASA);
            LOGGER.info("Gols equipe casa: {}", golsEquipeCasa);

            String golsEquipeVisitante = recuperaGolsEquipe(document, ScrapingConstants.GOLS_EQUIPE_VISITANTE);
            LOGGER.info("Gols equipe visitante: {}", golsEquipeVisitante);

            Integer placarEstendidoEquipeCasa = buscarPenalidade(document, ScrapingConstants.CASA);
            LOGGER.info("placar estendido equipe casa: {}", placarEstendidoEquipeCasa);

            Integer placarEstendidoEquipeVisitante = buscarPenalidade(document, ScrapingConstants.VISITANTE);
            LOGGER.info("placar estendido equipe visitante: {}", placarEstendidoEquipeVisitante);

        } catch (IOException e) {
            LOGGER.error("Erro ao tentar conectar no GOOGLE COM JSOUP -> {}", e.getMessage(), e);
        }

        return partida;
    }

    public StatusPartida obtemStatusPartida(Document document) {
        StatusPartida statusPartida = StatusPartida.PARTIDA_NAO_INICIADA;
        boolean isTempoPartida = document.select(ScrapingConstants.PARTIDA_NAO_INICIADA).isEmpty();
        if (!isTempoPartida) {
            statusPartida = StatusPartida.PARTIDA_NAO_INICIADA;
        }

        isTempoPartida = document.select(ScrapingConstants.JOGO_ROLANDO).isEmpty();
        if (!isTempoPartida) {
            String tempoPartida = document.select(ScrapingConstants.JOGO_ROLANDO).first().text();
            statusPartida = StatusPartida.PARTIDA_EM_ANDAMENTO;
            if (tempoPartida.contains(ScrapingConstants.PENALTIS)) {
                statusPartida = StatusPartida.PARTIDA_PENALTIS;
            }
        }

        isTempoPartida = document.select(ScrapingConstants.PARTIDA_ENCERRADA).isEmpty();
        if (!isTempoPartida) {
            statusPartida = StatusPartida.PARTIDA_ENCERRADA;
        }

        return statusPartida;
    }

    public String obtemTempoPartida(Document document) {
        String tempoPartida = null;
        boolean isTempoPartida = document.select(ScrapingConstants.PARTIDA_NAO_INICIADA).isEmpty();
        if (!isTempoPartida) {
            tempoPartida = document.select(ScrapingConstants.PARTIDA_NAO_INICIADA).first().text();
        }

        isTempoPartida = document.select(ScrapingConstants.JOGO_ROLANDO).isEmpty();
        if (!isTempoPartida) {
            tempoPartida = document.select(ScrapingConstants.JOGO_ROLANDO).first().text();
        }

        isTempoPartida = document.select(ScrapingConstants.PARTIDA_ENCERRADA).isEmpty();
        if (!isTempoPartida) {
            tempoPartida = document.select(ScrapingConstants.PARTIDA_ENCERRADA).first().text();
        }

        return corrigeTempoPartida(tempoPartida);
    }

    private static String corrigeTempoPartida(String tempo) {
        String tempoPartida = "";
        if (tempo.contains("'")) {
            tempoPartida = tempo.replace(" ", "").replace("'", "").concat(" min");
        } else if (tempo.contains("+")) {
            tempoPartida = tempo.replace(" ", "").concat(" min");
        } else {
            return tempo;
        }
        return tempoPartida;
    }

    public String recuperaNomeEquipe(Document document, String itemHtml) {
        Element elementNomeEquipe = document.select(itemHtml).first();
        return elementNomeEquipe.select(ScrapingConstants.SPAN).text();
    }

    public String reuperaLogoEquipe(Document document, String itemHtml) {
        Element elemento = document.selectFirst(itemHtml);
        return elemento != null ? ScrapingConstants.HTTP + elemento.select(ScrapingConstants.ITEM_LOGO).attr(ScrapingConstants.SRC) : "";
    }

    public Integer recuperaPlacarEquipe(Document document, String itemHtml) {
        String placarEquipe = document.select(itemHtml).first().text();
        return formataPlacarStringInteger(placarEquipe);
    }

    public String recuperaGolsEquipe(Document document, String itemHtml) {
        List<String> golsEquipe = new ArrayList<>();
        Elements timeCasa = document.select(itemHtml).select(ScrapingConstants.ITEM_GOLS);
        for (Element e : timeCasa) {
            String infoGol = e.select(ScrapingConstants.ITEM_GOLS).text();
            golsEquipe.add(infoGol);
        }
        return golsEquipe.isEmpty() ? null : String.join(", ", golsEquipe);
    }

    public Integer buscarPenalidade(Document document, String tipoEquipe) {
        boolean isPenalidades = document.select(ScrapingConstants.PENALIDADES).isEmpty();
        if (!isPenalidades) {
            String penalidades = document.select(ScrapingConstants.PENALIDADES).text();
            String penalidadesCompleta = penalidades.substring(0, 5).replace(" ", "");
            String[] divisao = penalidadesCompleta.split("-");
            return tipoEquipe.equals(ScrapingConstants.CASA) ? formataPlacarStringInteger(divisao[0]) : formataPlacarStringInteger(divisao[1]);
        }
        return null;
    }

    public Integer formataPlacarStringInteger(String placar) {
        try {
            return Integer.parseInt(placar);
        } catch (Exception e) {
            return 0;
        }
    }
}
