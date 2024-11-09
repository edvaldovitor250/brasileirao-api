package br.com.brasileirao_api.util;

import br.com.brasileirao_api.dto.PartidaGoogleDTO;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ScrapingUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(ScrapingUtil.class);
    private static final String BASE_URL_GOOGLE = "https://www.google.com/search?q=";
    private static final String COMPLEMENTO_URL_GOOGLE  = "&hl=pt-BR";

    private static final String GOLS_EQUIPE_CASA = "div[class=imso_gs__tgs imso_gs__left-team]";
    private static final String GOLS_EQUIPE_VISITANTE = "div[class=imso_gs__tgs imso_gs__right-team]";
    private static final String PENALIDADES = "div[class=imso_mh_s__psn-sc]";
    private static final String ITEM_GOLS = "div[class=imso_gs__gs-r]";
    private static final String PLACAR_EQUIPE_CASA = "div[class=imso_mh__l-tm-sc imso_mh__scr-it imso-light-font]";
    private static final String PLACAR_EQUIPE_VISITANTE = "div[class=imso_mh__r-tm-sc imso_mh__scr-it imso-light-font]";
    private static final String LOGO_EQUIPE_CASA = "div[class=imso_mh__first-tn-ed imso_mh__tnal-cont imso-tnol]";
    private static final String LOGO_EQUIPE_VISITANTE = "div[class=imso_mh__second-tn-ed imso_mh__tnal-cont imso-tnol]";
    private static final String PARTIDA_NAO_INICIADA = "div[class=imso_mh__vs-at-sep imso_mh__team-names-have-regular-font]";
    private static final String JOGO_ROLANDO = "div[class=imso_mh__lv-m-stts-cont]";
    private static final String PARTIDA_ENCERRADA = "span[class=imso_mh__ft-mtch imso-medium-font imso_mh__ft-mtchc]";
    private static final String ITEM_LOGO = "img[class=imso_btl__mh-logo]";

    private static final String CASA = "casa";
    private static final String PENALTIS = "Pênaltis";
    private static final String VISITANTE = "visitante";
    private static final String SPAN = "span";
    private static final String HTTP = "https:";
    private static final String SRC = "src";



    public static void main(String[] args) {
        String url = BASE_URL_GOOGLE + "real+madrid+e+osasuna+09/11/24" + COMPLEMENTO_URL_GOOGLE;
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

                Integer placarEquipeCasa = recuperaPlacarEquipe(document,PLACAR_EQUIPE_CASA);
                LOGGER.info("Placar Casa: {}", placarEquipeCasa);

                Integer placarEquipeVisitante = recuperaPlacarEquipe(document,PLACAR_EQUIPE_VISITANTE);
                LOGGER.info("Placar Visitante: {}", placarEquipeVisitante);

            }

            String nomeEquipeCasa = recuperaNomeEquipe(document,LOGO_EQUIPE_CASA);
            LOGGER.info("Nome equipe Casa: {}", nomeEquipeCasa);

            String nomeEquipeVisitante = recuperaNomeEquipe(document,LOGO_EQUIPE_VISITANTE);
            LOGGER.info("Nome equipe Visitante: {}", nomeEquipeVisitante);

            String urlLogoEquipeCasa = reuperaLogoEquipe(document,LOGO_EQUIPE_CASA);
            LOGGER.info("Url logo equipe casa: {}", urlLogoEquipeCasa);

            String urlLogoEquipeVisitante = reuperaLogoEquipe(document,LOGO_EQUIPE_VISITANTE);
            LOGGER.info("Url logo equipe visitante: {}", urlLogoEquipeVisitante);

            String golsEquipeCasa = recuperaGolsEquipe(document,GOLS_EQUIPE_CASA);
            LOGGER.info("Gols equipe casa: {}", golsEquipeCasa);

            String golsEquipeVisitante = recuperaGolsEquipe(document,GOLS_EQUIPE_VISITANTE);
            LOGGER.info("Gols equipe visitante: {}", golsEquipeVisitante);

            Integer placarEstendidoEquipeCasa = buscarPenalidade(document,CASA);
            LOGGER.info("placar estendido equipe casa: {}", placarEstendidoEquipeCasa);

            Integer placarEstendidoEquipeVisitante = buscarPenalidade(document, VISITANTE);
            LOGGER.info("placar estendido equipe visitante: {}", placarEstendidoEquipeVisitante);

        } catch (IOException e) {
            LOGGER.error("Erro ao tentar conectar no GOOGLE COM JSOUP -> {}", e.getMessage(), e);
        }

        return partida;
    }

    public StatusPartida obtemStatusPartida(Document document) {
        StatusPartida statusPartida = StatusPartida.PARTIDA_NAO_INICIADA;
        boolean isTempoPartida = document.select(PARTIDA_NAO_INICIADA).isEmpty();
        if (!isTempoPartida) {
            statusPartida = StatusPartida.PARTIDA_NAO_INICIADA;
        }

        isTempoPartida = document.select(JOGO_ROLANDO).isEmpty();
        if (!isTempoPartida) {
            String tempoPartida = document.select(JOGO_ROLANDO).first().text();
            statusPartida = StatusPartida.PARTIDA_EM_ANDAMENTO;
            if (tempoPartida.contains(PENALTIS)) {
                statusPartida = StatusPartida.PARTIDA_PENALTIS;
            }
        }

        isTempoPartida = document.select(PARTIDA_ENCERRADA).isEmpty();
        if (!isTempoPartida) {
            statusPartida = StatusPartida.PARTIDA_ENCERRADA;
        }

        return statusPartida;
    }

    public String obtemTempoPartida(Document document) {

        String tempoPartida = null;
        boolean isTempoPartida = document.select(PARTIDA_NAO_INICIADA).isEmpty();
        if (!isTempoPartida) {
            tempoPartida = document.select(PARTIDA_NAO_INICIADA).first().text();
        }

        isTempoPartida = document.select(JOGO_ROLANDO).isEmpty();
        if (!isTempoPartida) {
            tempoPartida = document.select(JOGO_ROLANDO).first().text();
        }

        isTempoPartida = document.select(PARTIDA_ENCERRADA).isEmpty();
        if (!isTempoPartida) {
            tempoPartida = document.select(PARTIDA_ENCERRADA).first().text();
        }

        return corrigeTempoPartida(tempoPartida);
    }

    private static String corrigeTempoPartida(String tempo) {
        String tempoPartida = "";
        if (tempo.contains("'")) {
            tempoPartida = tempo.replace(" ", "");
            tempoPartida = tempoPartida.replace("'", "").concat(" min");
        } else {
            if (tempo.contains("+")) {
                tempoPartida = tempo.replace(" ", "").concat(" min");
            } else {
                return tempo;
            }
        }
        return tempoPartida;
    }

    public String recuperaNomeEquipe(Document document, String itemHtml) {
        Element elementNomeEquipe = document.select(itemHtml).first();
        String nomeEquipe = elementNomeEquipe.select(SPAN).text();

        return nomeEquipe;
    }

    public String reuperaLogoEquipe(Document document, String intemHtml) {
        Element elemento = document.selectFirst(intemHtml);
        return elemento != null ? HTTP + elemento.select(ITEM_LOGO).attr(SRC) : "";
    }
    

    public Integer recuperaPlacarEquipe(Document document, String itemHtml) {
        String placarEquipe = document.select(itemHtml).first().text();
        return formataPlacarStringInteger(placarEquipe);
    }


    public String recuperaGolsEquipe(Document document, String itemHtml) {
        List<String> golsEquipe = new ArrayList<>();

        Elements timeCasa = document.select(itemHtml).select(ITEM_GOLS);
        for (Element e : timeCasa) {
            String infoGol = e.select(ITEM_GOLS).text();
            golsEquipe.add(infoGol);
        }

        return golsEquipe.isEmpty() ? null : String.join(", ", golsEquipe);
    }


    public Integer buscarPenalidade(Document document, String tipoEquipe){
        boolean isPenalidades = document.select(PENALIDADES).isEmpty();

        if(!isPenalidades){
            String penalidades = document.select(PENALIDADES).text();
            String penalidadesCompleta = penalidades.substring(0,5).replace(" ","");
            String[] divisao = penalidadesCompleta.split("-");

            return  tipoEquipe.equals(CASA) ? formataPlacarStringInteger(divisao[0]) : formataPlacarStringInteger(divisao[1]);

        }

        return null;
    }

    public Integer formataPlacarStringInteger(String placar){
        Integer valor;
        try {
            valor =  Integer.parseInt(placar);
        } catch (Exception e){
            valor = 0;
        }
        return valor;
    }

}
