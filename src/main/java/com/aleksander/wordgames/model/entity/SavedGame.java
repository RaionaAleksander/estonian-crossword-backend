package com.aleksander.wordgames.model.entity;

import java.time.Instant;

import com.aleksander.wordgames.common.enums.GameType;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "saved_games")
public class SavedGame {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private GameType gameType;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String payload;

    private Instant createdAt;
}