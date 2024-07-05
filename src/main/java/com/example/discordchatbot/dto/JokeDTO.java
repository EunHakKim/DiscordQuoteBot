package com.example.discordchatbot.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class JokeDTO {
    private String type;
    private String setup;
    private String punchline;
    private String id;
}
