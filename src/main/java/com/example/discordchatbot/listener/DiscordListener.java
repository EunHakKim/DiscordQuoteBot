package com.example.discordchatbot.listener;

import com.example.discordchatbot.dto.QuoteDTO;
import com.example.discordchatbot.util.QuotesUtil;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.springframework.stereotype.Component;

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
            QuoteDTO quoteDTO = QuotesUtil.requestToQuotesAPI();
            textChannel.sendMessage(quoteDTO.getAuthor() + " - " + quoteDTO.getMessage()).queue();
        }
    }
}
