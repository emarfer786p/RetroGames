package com.example.retrogames.model;

import jakarta.persistence.*;

// 1. Entidad JPA (Tabla 'games' en PostgreSQL)
@Entity
@Table(name = "games")
public class Game {
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