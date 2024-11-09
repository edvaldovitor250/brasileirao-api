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

    private static final String CASA = "casa";
    private static final String VISITANTE = "visitante";


    public static void main(String[] args) {
        String url = BASE_URL_GOOGLE + "brasil+e+uruguai+06/07/24" + COMPLEMENTO_URL_GOOGLE;
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

                Integer placarEquipeCasa = recuperarPlacarEquipeCasa(document);
                LOGGER.info("Placar Casa: {}", placarEquipeCasa);

                Integer placarEquipeVisitante = recuperarPlacarEquipeVisitante(document);
                LOGGER.info("Placar Visitante: {}", placarEquipeVisitante);

            }

            String nomeEquipeCasa = recuperarNomeEquipeCasa(document);
            LOGGER.info("Nome equipe Casa: {}", nomeEquipeCasa);

            String nomeEquipeVisitante = recuperarNomeEquipeVisitante(document);
            LOGGER.info("Nome equipe Visitante: {}", nomeEquipeVisitante);

            String urlLogoEquipeCasa = reuperaLogoEquipeCasa(document);
            LOGGER.info("Url logo equipe casa: {}", urlLogoEquipeCasa);

            String urlLogoEquipeVisitante = reuperaLogoEquipeVisitante(document);
            LOGGER.info("Url logo equipe visitante: {}", urlLogoEquipeVisitante);

            String golsEquipeCasa = recupaGolsEquipeCasa(document);
            LOGGER.info("Gols equipe casa: {}", golsEquipeCasa);

            String golsEquipeVisitante = recupaGolsEquipeVisitante(document);
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

        boolean isTempoPartida = document.select("div[class=imso_mh_lv-m-stts-cont]").isEmpty();
        if (!isTempoPartida) {
            String tempoPartida = document.select("div[class=imso_mh_lv-m-stts-cont]").first().text();
            statusPartida = StatusPartida.PARTIDA_EM_ANDAMENTO;
            if (tempoPartida.contains("Pênaltis")) {
                statusPartida = StatusPartida.PARTIDA_PENALTIS;
            }

            LOGGER.info(tempoPartida);

            isTempoPartida = document.select("span[class=imso_mh__ft-mtch imso-medium-font imso_mh__ft-mtchc]").isEmpty();

            if (!isTempoPartida) {
                statusPartida = StatusPartida.PARTIDA_ENCERRADA;
            }
        }
        return statusPartida;
    }

    public String obtemTempoPartida(Document document) {
        String tempoPartida = null;

        boolean isTempoPartida = document.select("div[class=imso_mh_lv-m-stts-cont]").isEmpty();
        if (!isTempoPartida) {
            tempoPartida = document.select("div[class=imso_mh_lv-m-stts-cont]").first().text();
        }

        isTempoPartida = document.select("span[class=imso_mh__ft-mtch imso-medium-font imso_mh__ft-mtchc]").isEmpty();
        if (!isTempoPartida) {
            tempoPartida = document.select("span[class=imso_mh__ft-mtch imso-medium-font imso_mh__ft-mtchc]").first().text();
        }

        LOGGER.info(corrigeTempoPartida(tempoPartida));
        return tempoPartida;
    }

    public String corrigeTempoPartida(String tempo) {
        return tempo != null && tempo.contains("'") ? tempo.replace("'", "min") : tempo;
    }

    public String recuperarNomeEquipe(Document document, String seletor) {
        Element elemento = document.selectFirst(seletor);
        return elemento != null ? elemento.select("span").text() : "";
    }

    public String recuperarNomeEquipeCasa(Document document) {
        return recuperarNomeEquipe(document, "div[class=\"imso_mh__first-tn-ed imso_mh__tnal-cont imso-tnol\"]");
    }

    public String recuperarNomeEquipeVisitante(Document document) {
        return recuperarNomeEquipe(document, "div[class=imso_mh__second-tn-ed imso_mh__tnal-cont imso-tnol]");
    }

    public String reuperaLogoEquipeCasa(Document document) {
        Element elemento = document.selectFirst("div[class=imso_mh__first-tn-ed imso_mh__tnal-cont imso-tnol]");
        return elemento != null ? "https:" + elemento.select("img.imso_btl_mh-logo").attr("src") : "";
    }

    public String reuperaLogoEquipeVisitante(Document document) {
        Element elemento = document.selectFirst("div[class=imso_mh__second-tn-ed imso_mh__tnal-cont imso-tnol]");
        return elemento != null ? "https:" + elemento.select("img.imso_btl_mh-logo").attr("src") : "";
    }

    public Integer recuperarPlacarEquipeCasa(Document document) {
        Element placarElemento = document.selectFirst("div.imso_mh__l-tm-sc.imso_mh__scr-it.imso-light-font");
        return placarElemento != null ? Integer.parseInt(placarElemento.text()) : null;
    }

    public Integer recuperarPlacarEquipeVisitante(Document document) {
        Element placarElemento = document.selectFirst("div.imso_mh__r-tm-sc.imso_mh__scr-it.imso-light-font");
        return placarElemento != null ? Integer.parseInt(placarElemento.text()) : null;
    }

    public String recupaGolsEquipeCasa(Document document) {
        List<String> golsEquipe = new ArrayList<>();
        Elements elementos = document.select("div.imso_gs__tgs.imso_gs__left-team").select("div.imso_gs__gs-r");
        elementos.forEach(e -> golsEquipe.add(e.text()));
        return String.join(", ", golsEquipe);
    }

    public String recupaGolsEquipeVisitante(Document document) {
        List<String> golsEquipe = new ArrayList<>();
        Elements elementos = document.select("div.imso_gs__tgs.imso_gs__right-team").select("div.imso_gs__gs-r");
        elementos.forEach(item -> golsEquipe.add(item.text()));
        return String.join(", ", golsEquipe);
    }

    public Integer buscarPenalidade(Document document, String tipoEquipe){
        boolean isPenalidades = document.select("div[class=imso_mh_s__psn-sc]").isEmpty();

        if(!isPenalidades){
            String penalidades = document.select("div[class=imso_mh_s__psn-sc]").text();
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
