package br.com.brasileirao_api.repository;

import br.com.brasileirao_api.model.Equipe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EquipeRepository extends JpaRepository<Equipe, Long> {
}


