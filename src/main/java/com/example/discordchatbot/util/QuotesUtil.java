package com.example.discordchatbot.util;

import com.example.discordchatbot.dto.QuoteDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@Slf4j
public class QuotesUtil {
    public static QuoteDTO requestToQuotesAPI() {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://korean-advice-open-api.vercel.app/api/advice";

        return restTemplate.getForObject(url, QuoteDTO.class);
    }
}
