package br.com.brasileirao_api.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public interface ScrapingConstants {

    Logger LOGGER = LoggerFactory.getLogger(ScrapingConstants.class);

    String BASE_URL_GOOGLE = "https://www.google.com/search?q=";
    String COMPLEMENTO_URL_GOOGLE = "&hl=pt-BR";

    String GOLS_EQUIPE_CASA = "div[class=imso_gs__tgs imso_gs__left-team]";
    String GOLS_EQUIPE_VISITANTE = "div[class=imso_gs__tgs imso_gs__right-team]";
    String PENALIDADES = "div[class=imso_mh_s__psn-sc]";
    String ITEM_GOLS = "div[class=imso_gs__gs-r]";
    String PLACAR_EQUIPE_CASA = "div[class=imso_mh__l-tm-sc imso_mh__scr-it imso-light-font]";
    String PLACAR_EQUIPE_VISITANTE = "div[class=imso_mh__r-tm-sc imso_mh__scr-it imso-light-font]";
    String LOGO_EQUIPE_CASA = "div[class=imso_mh__first-tn-ed imso_mh__tnal-cont imso-tnol]";
    String LOGO_EQUIPE_VISITANTE = "div[class=imso_mh__second-tn-ed imso_mh__tnal-cont imso-tnol]";
    String PARTIDA_NAO_INICIADA = "div[class=imso_mh__vs-at-sep imso_mh__team-names-have-regular-font]";
    String JOGO_ROLANDO = "div[class=imso_mh__lv-m-stts-cont]";
    String PARTIDA_ENCERRADA = "span[class=imso_mh__ft-mtch imso-medium-font imso_mh__ft-mtchc]";
    String ITEM_LOGO = "img[class=imso_btl__mh-logo]";

    String CASA = "casa";
    String PENALTIS = "Pênaltis";
    String VISITANTE = "visitante";
    String SPAN = "span";
    String HTTP = "https:";
    String SRC = "src";
}
