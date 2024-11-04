package br.com.brasileirao_api.dto;

import lombok.*;

import java.io.Serializable;

@Data
public class PartidaGoogleDTO implements Serializable {


    private static final long serialVersionUID = 1L;

    private String statusPartida;
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
