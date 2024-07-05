package com.example.discordchatbot.listener;

import com.example.discordchatbot.dto.JokeDTO;
import com.example.discordchatbot.dto.QuoteDTO;
import com.example.discordchatbot.util.JokeUtil;
import com.example.discordchatbot.util.QuoteUtil;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Slf4j
@Component
public class DiscordListener extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event){

        User user = event.getAuthor();
        TextChannel textChannel = event.getChannel().asTextChannel();
        Message message = event.getMessage();

        log.info("message : " + message.getContentDisplay());

        if (user.isBot()) {
            return;
        } else if (message.getContentDisplay().isEmpty()) {
            log.info("디스코드 Message 문자열 값 공백");
        }

        if (message.getContentDisplay().equals("!명언")) {
            QuoteDTO quoteDTO = QuoteUtil.requestToQuoteAPI();
            textChannel.sendMessage(quoteDTO.getAuthor() + " - " + quoteDTO.getMessage()).queue();
        } else if (message.getContentDisplay().equals("!농담")) {
            JokeDTO jokeDTO = Arrays.asList(JokeUtil.requestToJokeAPI()).get(0);
            textChannel.sendMessage("Q: " + jokeDTO.getSetup() + "\n5초 뒤에 정답이 출력됩니다.").queue();
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            textChannel.sendMessage("A: " + jokeDTO.getPunchline()).queue();
        }
    }
}
