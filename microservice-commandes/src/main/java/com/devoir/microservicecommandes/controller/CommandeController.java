package com.devoir.microservicecommandes.controller;

import com.devoir.microservicecommandes.model.Commande;
import com.devoir.microservicecommandes.repository.CommandeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/commandes")
@RequiredArgsConstructor
public class CommandeController {

    private final CommandeRepository service;

    @Value("${mes-config-ms.commandes-last:10}")
    private int commandesLast;

    @GetMapping
    public List<Commande> all() { return service.findAll(); }

    @GetMapping("/{id}")
    public ResponseEntity<Commande> get(@PathVariable Long id) {
        return service.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Commande create(@RequestBody Commande c) {
        if (c.getDateCommande() == null) c.setDateCommande(LocalDate.now());
        return service.save(c);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Commande> update(@PathVariable Long id, @RequestBody Commande c) {
        return service.findById(id).map(existing -> {
            existing.setDescription(c.getDescription());
            existing.setMontant(c.getMontant());
            existing.setQuantite(c.getQuantite());
            existing.setDateCommande(c.getDateCommande());
            service.save(existing);
            return ResponseEntity.ok(existing);
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (service.findById(id).isPresent()) { service.deleteById(id); return ResponseEntity.ok().build(); }
        return ResponseEntity.notFound().build();
    }

    // retourne commandes des 'commandesLast' derniers jours (valeur via config)
    @GetMapping("/recent")
    public List<Commande> recent() {
        return service.findByDateCommandeAfter(LocalDate.now().minusDays(commandesLast));
    }

}
