package com.aidnalev.gestorideas.repository;

import com.aidnalev.gestorideas.entity.Idea;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IdeaRepository extends JpaRepository<Idea, Long> {
    List<Idea> findByUsuarioId(Long usuarioId);
}
