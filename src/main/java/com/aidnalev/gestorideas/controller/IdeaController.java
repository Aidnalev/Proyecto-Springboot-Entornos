package com.aidnalev.gestorideas.controller;

import com.aidnalev.gestorideas.entity.Idea;
import com.aidnalev.gestorideas.entity.Usuario;
import com.aidnalev.gestorideas.security.UsuarioDetails;
import com.aidnalev.gestorideas.service.IdeaService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

import java.util.List;

@RestController
@RequestMapping("/api/ideas")
@CrossOrigin(origins = "*")
public class IdeaController {

    private final IdeaService ideaService;

    public IdeaController(IdeaService ideaService) {
        this.ideaService = ideaService;
    }

    @GetMapping
    public List<Idea> listarIdeas(@AuthenticationPrincipal UsuarioDetails usuarioDetails) {
        Long usuarioId = usuarioDetails.getUsuario().getId();
        return ideaService.obtenerPorUsuario(usuarioId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Idea> obtenerIdea(@PathVariable Long id) {
        return ideaService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Idea> crearIdea(@RequestBody Idea idea,@AuthenticationPrincipal UsuarioDetails usuarioDetails) {
      Usuario usuario = usuarioDetails.getUsuario();
      idea.setUsuario(usuario);
      idea.setFechaCreacion(LocalDate.now());
      idea.setEstado("Pendiente");
      return ResponseEntity.ok(ideaService.guardar(idea));
    }


    @PutMapping("/{id}")
    public ResponseEntity<Idea> actualizarIdea(@PathVariable Long id, @RequestBody Idea ideaDetalles) {
        return ideaService.obtenerPorId(id)
                .map(idea -> {
                    idea.setTitulo(ideaDetalles.getTitulo());
                    idea.setDescripcion(ideaDetalles.getDescripcion());
                    idea.setEstado(ideaDetalles.getEstado());
                    Idea ideaActualizada = ideaService.guardar(idea);
                    return ResponseEntity.ok(ideaActualizada);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarIdea(@PathVariable Long id) {
        if (ideaService.eliminar(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
