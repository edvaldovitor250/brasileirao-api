package br.com.brasileirao_api.repository;

import br.com.brasileirao_api.model.Partida;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PartidaRepository extends JpaRepository<Partida, Long> {

    @Query(value = "select count(*) from partida as p "
            + "where p.data_hora_partida between dateadd(hour, -3, current_timestamp) and current_timestamp "
            + "and coalesce(p.tempo_partida, 'Vazio') != 'Encerrado' ",
            nativeQuery = true)
    Integer buscarQuantidadePartidasPeriodo();

    @Query(value = "select * from partida as p "
            + "where p.data_hora_partida between dateadd(hour, -3, current_timestamp) and current_timestamp "
            + "and coalesce(p.tempo_partida, 'Vazio') != 'Encerrado' ",
            nativeQuery = true)
    List<Partida> listarPartidasPeriodo();
}
