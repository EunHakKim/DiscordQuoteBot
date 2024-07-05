package com.example.discordchatbot.util;

import com.example.discordchatbot.dto.QuoteDTO;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class QuoteUtil {
    public static QuoteDTO requestToQuoteAPI() {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://korean-advice-open-api.vercel.app/api/advice";

        return restTemplate.getForObject(url, QuoteDTO.class);
    }
}
