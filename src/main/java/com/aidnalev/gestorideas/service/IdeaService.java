package com.aidnalev.gestorideas.service;

import com.aidnalev.gestorideas.entity.Idea;
import com.aidnalev.gestorideas.repository.IdeaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IdeaService {

    private final IdeaRepository ideaRepository;

    public IdeaService(IdeaRepository ideaRepository) {
        this.ideaRepository = ideaRepository;
    }

    public List<Idea> obtenerTodas() {
        return ideaRepository.findAll();
    }

    public Optional<Idea> obtenerPorId(Long id) {
        return ideaRepository.findById(id);
    }

    public Idea guardar(Idea idea) {
        return ideaRepository.save(idea);
    }

    public List<Idea> obtenerPorUsuario(Long usuarioId) {
    return ideaRepository.findByUsuarioId(usuarioId);
    }

    public Idea actualizar(Long id, Idea ideaActualizada) {
        return ideaRepository.findById(id).map(idea -> {
            idea.setTitulo(ideaActualizada.getTitulo());
            idea.setDescripcion(ideaActualizada.getDescripcion());
            return ideaRepository.save(idea);
        }).orElse(null);
    }

    public boolean eliminar(Long id) {
        if (ideaRepository.existsById(id)) {
            ideaRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
