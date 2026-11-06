package com.example.retrogames.controller;


import com.example.retrogames.model.Game;
import com.example.retrogames.repository.GameRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

// 3. Controlador REST
@RestController
@RequestMapping("/api")
class GameController {

    private final GameRepository repository;

    public GameController(GameRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/games")
    public List<Game> getAllGames() {
        return repository.findAll();
    }

    @GetMapping("/games/random")
    public Game getRandomGame() {
        List<Game> allGames = repository.findAll();
        if (allGames.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No hay juegos en la base de datos");
        }
        int randomIndex = ThreadLocalRandom.current().nextInt(allGames.size());
        return allGames.get(randomIndex);
    }

    @PostMapping("/games")
    @ResponseStatus(HttpStatus.CREATED)
    public Game createGame(@RequestBody Game game) {
        return repository.save(game);
    }

    @GetMapping("/health")
    public Map<String, String> healthcheck() {
        String hostname = System.getenv().getOrDefault("HOSTNAME", "local-dev");
        return Map.of("status", "UP", "container_hostname", hostname);
    }
}