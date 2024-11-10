package br.com.brasileirao_api.repository;

import br.com.brasileirao_api.model.Equipe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EquipeRepository extends JpaRepository<Equipe, Long> {

    public Optional<Equipe> findByNomeEquipe(String nomeEquipe);

    public boolean existsByNomeEquipe(String nomeEquipe);


}


