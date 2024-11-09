package br.com.brasileirao_api.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ScrapingConstants {

    public static final Logger LOGGER = LoggerFactory.getLogger(ScrapingConstants.class);

    public static final String BASE_URL_GOOGLE = "https://www.google.com/search?q=";
    public static final String COMPLEMENTO_URL_GOOGLE = "&hl=pt-BR";

    public static final String GOLS_EQUIPE_CASA = "div[class=imso_gs__tgs imso_gs__left-team]";
    public static final String GOLS_EQUIPE_VISITANTE = "div[class=imso_gs__tgs imso_gs__right-team]";
    public static final String PENALIDADES = "div[class=imso_mh_s__psn-sc]";
    public static final String ITEM_GOLS = "div[class=imso_gs__gs-r]";
    public static final String PLACAR_EQUIPE_CASA = "div[class=imso_mh__l-tm-sc imso_mh__scr-it imso-light-font]";
    public static final String PLACAR_EQUIPE_VISITANTE = "div[class=imso_mh__r-tm-sc imso_mh__scr-it imso-light-font]";
    public static final String LOGO_EQUIPE_CASA = "div[class=imso_mh__first-tn-ed imso_mh__tnal-cont imso-tnol]";
    public static final String LOGO_EQUIPE_VISITANTE = "div[class=imso_mh__second-tn-ed imso_mh__tnal-cont imso-tnol]";
    public static final String PARTIDA_NAO_INICIADA = "div[class=imso_mh__vs-at-sep imso_mh__team-names-have-regular-font]";
    public static final String JOGO_ROLANDO = "div[class=imso_mh__lv-m-stts-cont]";
    public static final String PARTIDA_ENCERRADA = "span[class=imso_mh__ft-mtch imso-medium-font imso_mh__ft-mtchc]";
    public static final String ITEM_LOGO = "img[class=imso_btl__mh-logo]";

    public static final String CASA = "casa";
    public static final String PENALTIS = "Pênaltis";
    public static final String VISITANTE = "visitante";
    public static final String SPAN = "span";
    public static final String HTTP = "https:";
    public static final String SRC = "src";

    private ScrapingConstants() {
    }
}
