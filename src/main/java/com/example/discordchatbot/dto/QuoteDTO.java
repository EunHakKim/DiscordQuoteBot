package com.example.discordchatbot.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class QuoteDTO {
    private String author;
    private String authorProfile;
    private String message;
}
