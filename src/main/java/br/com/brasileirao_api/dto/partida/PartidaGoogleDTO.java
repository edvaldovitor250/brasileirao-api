package br.com.brasileirao_api.dto.partida;

import br.com.brasileirao_api.util.StatusPartida;
import lombok.*;

import java.io.Serializable;

@Data
public class PartidaGoogleDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private StatusPartida statusPartida;
    private String tempoPartida;

    private String nomeEquipeCasa;
    private String urlLogoEquipeCasa;
    private Integer placarEquipeCasa;
    private String golsEquipeCasa;
    private String placarEstendidoEquipeCasa;

    private String nomeEquipeVisitante;
    private String urlLogoEquipeVisitante;
    private Integer placarEquipeVisitante;
    private String golsEquipeVisitante;
    private String placarEstendidoEquipeVisitante;


}
