package com.example.retrogames;

import jakarta.persistence.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

@SpringBootApplication
public class RetroGamesApplication {

    public static void main(String[] args) {
        SpringApplication.run(RetroGamesApplication.class, args);
    }

    // Semilla de datos inicial al arrancar la aplicación
    @Bean
    CommandLineRunner initDatabase(GameRepository repository) {
        return args -> {
            if (repository.count() == 0) {
                repository.saveAll(List.of(
                        new Game("Super Mario World", "Super Nintendo (SNES)", 1990,
                                "Introdujo a Yoshi por primera vez; Miyamoto quería incluirlo desde la NES pero las limitaciones técnicas no lo permitían."),
                        new Game("The Legend of Zelda: Link's Awakening", "Game Boy", 1993,
                                "Comenzó como un proyecto experimental que los desarrolladores programaban fuera de horario como si fuera una parodia de Zelda."),
                        new Game("Tetris", "Game Boy", 1989,
                                "Fue el primer videojuego jugado en el espacio exterior: el cosmonauta Aleksandr Serebrov llevó su Game Boy a la estación Mir en 1993."),
                        new Game("Pokémon Ediciones Roja y Azul", "Game Boy", 1996,
                                "Satoshi Tajiri tuvo la idea inspirándose en su afición infantil de coleccionar insectos e imaginar que viajaban por el cable Game Link.")
                ));
            }
        };
    }
}

// 1. Entidad JPA (Tabla 'games' en PostgreSQL)
@Entity
@Table(name = "games")
class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String platform;
    private Integer year;
    @Column(length = 500)
    private String fact;

    public Game() {}

    public Game(String title, String platform, Integer year, String fact) {
        this.title = title;
        this.platform = platform;
        this.year = year;
        this.fact = fact;
    }

    public Long getId() { return id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getPlatform() { return platform; }
    public void setPlatform(String platform) { this.platform = platform; }
    public Integer getYear() { return year; }
    public void setYear(Integer year) { this.year = year; }
    public String getFact() { return fact; }
    public void setFact(String fact) { this.fact = fact; }
}

// 2. Repositorio Spring Data JPA
interface GameRepository extends JpaRepository<Game, Long> {}

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