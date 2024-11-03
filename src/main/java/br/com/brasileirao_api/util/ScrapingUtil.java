package br.com.brasileirao_api.util;

import br.com.brasileirao_api.dto.PartidaGoogleDTO;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class ScrapingUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(ScrapingUtil.class);
    private static final String BASE_URL_GOOGLE = "https://www.google.com/search?q=";
    private static final String COMPLEMENTO_URL_GOOGLE  = "&hl=pt-BR";

    public static void main(String[] args) {

        String url = BASE_URL_GOOGLE + "flamengo+x+atletico+mg" + COMPLEMENTO_URL_GOOGLE;

        ScrapingUtil scrapingUtil = new ScrapingUtil();
        scrapingUtil.obtemInformacoesPartida(url);
    }

    public PartidaGoogleDTO obtemInformacoesPartida(String url) {
        PartidaGoogleDTO partida = new PartidaGoogleDTO();
        Document doc = null;

        try {
            doc = Jsoup.connect(url).get();

            String title = doc.title();
            LOGGER.info("Título da Página: {}", title);

        } catch (IOException e) {
            LOGGER.error("Erro ao tentar conectar no GOOGLE COM JSOUP -> {}", e.getMessage(), e);
        }

        return partida;
    }
}
