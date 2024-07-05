package com.example.discordchatbot.util;

import com.example.discordchatbot.dto.JokeDTO;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class JokeUtil {
    public static JokeDTO[] requestToJokeAPI() {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://official-joke-api.appspot.com/jokes/programming/random";

        return restTemplate.getForObject(url, JokeDTO[].class);
    }
}