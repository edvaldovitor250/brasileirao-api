package br.com.brasileirao_api.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "partida")
public class Partida implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "partida_id")
    private Long id;

    @Transient
    private String statusPartida;

    @ManyToOne
    @JoinColumn(name = "equipe_casa_id")
    private Equipe equipeCasa;

    @ManyToOne
    @JoinColumn(name = "equipe_visitante_id")
    private Equipe equipeVisitante;

    @Column(name = "placar_equipe_casa")
    private Integer placarEquipeCasa;

    @Column(name = "placar_equipe_visitante")
    private Integer placarEquipeVisitante;

    @Column(name = "gols_equipe_casa")
    private String golsEquipeCasa;

    @Column(name = "gols_equipe_visitante")
    private String golsEquipeVisitante;

    @Column(name = "placar_estendido_equipe_casa")
    private Integer placarEstendidoEquipeCasa;

    @Column(name = "placar_estendido_equipe_visitante")
    private Integer placarEstendidoEquipeVisitante;

    @Schema(example = "yyyy-MM-dd")
    @Column(name = "data_hora_partida")
    private LocalDate dataHoraPartida;

    @Column(name = "local_partida")
    private String localPartida;

    @Column(name = "tempo_partida")
    private String tempoPartida;
}
