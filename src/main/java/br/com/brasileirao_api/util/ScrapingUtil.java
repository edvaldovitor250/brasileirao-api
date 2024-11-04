package br.com.brasileirao_api.util;

import br.com.brasileirao_api.dto.PartidaGoogleDTO;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class ScrapingUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(ScrapingUtil.class);
    private static final String BASE_URL_GOOGLE = "https://www.google.com/search?q=";
    private static final String COMPLEMENTO_URL_GOOGLE  = "&hl=pt-BR";

    public static void main(String[] args) {

        String url = BASE_URL_GOOGLE + "flamengo+x+atlético" + COMPLEMENTO_URL_GOOGLE;

        ScrapingUtil scrapingUtil = new ScrapingUtil();
        scrapingUtil.obtemInformacoesPartida(url);
    }

    public PartidaGoogleDTO obtemInformacoesPartida(String url) {

        PartidaGoogleDTO partida = new PartidaGoogleDTO();

        Document document = null;

        try {
            document = Jsoup.connect(url).get();

            String title = document.title();
            LOGGER.info("Título da Página: {}", title);

            StatusPartida  statusPartida =  obtemStatusPartida(document);
            LOGGER.info("Status partidas: {}", statusPartida);

            if (statusPartida != StatusPartida.PARTIDA_NAO_INICIADA){
                String tempoPartida = obtemTempoPartida(document);
                LOGGER.info("Tempo partida: ", tempoPartida);
            }

            String nomeEquipeCasa = recuperarNomeEquipeCasa(document);
            LOGGER.info("Nome equipe Casa: {}",nomeEquipeCasa);

            String nomeEquipeVisitante = recuperarNomeEquipeVisitante(document);
            LOGGER.info("Nome equipe Casa: {}",nomeEquipeVisitante);

        } catch (IOException e) {
            LOGGER.error("Erro ao tentar conectar no GOOGLE COM JSOUP -> {}", e.getMessage(), e);
        }

        return partida;
    }

    public StatusPartida obtemStatusPartida(Document document){

        StatusPartida statusPartida = StatusPartida.PARTIDA_NAO_INICIADA;

        boolean isTempoPartida = document.select("div[class=imso_mh_lv-m-stts-cont]").isEmpty();
        if (!isTempoPartida){
            String tempoPartida = document.select("div[class=imso_mh_lv-m-stts-cont]").first().text();
            statusPartida = StatusPartida.PARTIDA_EM_ANDAMENTO;
            if (tempoPartida.contains("Pênaltis")){
                statusPartida = StatusPartida.PARTIDA_PENALTIS;
            }

            LOGGER.info(tempoPartida);

            isTempoPartida = document.select("span[class=imso_mh__ft-mtch imso-medium-font imso_mh__ft-mtchc]").isEmpty();

            if (!isTempoPartida){
                statusPartida = StatusPartida.PARTIDA_ENCERRADA;
            }
        }
        return  statusPartida;

    }

    public String obtemTempoPartida(Document document){
        String tempoPartida = null;

        boolean isTempoPartida = document.select("div[class=imso_mh_lv-m-stts-cont]").isEmpty();

        if (!isTempoPartida){
            tempoPartida = document.select("div[class=imso_mh_lv-m-stts-cont]").first().text();
        }

        isTempoPartida = document.select("span[class=imso_mh__ft-mtch imso-medium-font imso_mh__ft-mtchc]").isEmpty();
        if(!isTempoPartida){
            tempoPartida = document.select("span[class=imso_mh__ft-mtch imso-medium-font imso_mh__ft-mtchc]").first().text();
        }

        LOGGER.info(corrigeTempoPartida(tempoPartida));
        return tempoPartida;
    }

    public String corrigeTempoPartida(String tempo) {
        return tempo.contains("'") ? tempo.replace("'", "min") : tempo;
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






}

