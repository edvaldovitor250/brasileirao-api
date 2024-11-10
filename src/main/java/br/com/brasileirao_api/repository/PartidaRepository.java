package br.com.brasileirao_api.repository;

import br.com.brasileirao_api.model.Partida;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PartidaRepository extends JpaRepository<Partida,Long> {
}
