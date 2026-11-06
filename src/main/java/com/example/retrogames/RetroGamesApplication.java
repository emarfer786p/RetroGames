package com.example.retrogames;

import com.example.retrogames.model.Game;
import com.example.retrogames.repository.GameRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

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




