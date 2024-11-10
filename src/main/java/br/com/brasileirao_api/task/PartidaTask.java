package br.com.brasileirao_api.task;

import br.com.brasileirao_api.service.ScrapingService;
import br.com.brasileirao_api.util.DataUtil;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Date;

@Configuration
@EnableScheduling
@AllArgsConstructor
public class PartidaTask {

    private static final Logger LOGGER = LoggerFactory.getLogger(PartidaTask.class);

    private static final String TIME_ZONE = "America/Sao_Paulo";
    private static final String DD_MM_YY_HH_MM_SS = "dd/MM/yyyy HH:mm:ss";

    private final ScrapingService scrapingService;

    @Scheduled(cron = "*/30 * * * * *", zone = TIME_ZONE)
    public void executarTaskDiariamente() {
        inicializaAgendamento("executarTaskDiariamente()");
        scrapingService.verificaPartidaPeriodo();
    }

    private void inicializaAgendamento(String descricaoTarefa) {
        this.gravaLogInfo(String.format("Inicializando agendamento: %s às %s",
                descricaoTarefa, DataUtil.formatarDateEmString(new Date(), DD_MM_YY_HH_MM_SS)));
        scrapingService.verificaPartidaPeriodo();
    }

    private void gravaLogInfo(String mensagem) {
        LOGGER.info(mensagem);
    }
}
