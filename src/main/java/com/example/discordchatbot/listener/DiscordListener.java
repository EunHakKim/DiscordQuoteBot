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
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

@Slf4j
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
            textChannel.sendMessage(quoteDTO.getMessage() + "   - " + quoteDTO.getAuthor() + "(" + quoteDTO.getAuthorProfile() + ") -").queue();
        } else if (message.getContentDisplay().equals("!농담")) {
            JokeDTO jokeDTO = Arrays.asList(JokeUtil.requestToJokeAPI()).get(0);
            textChannel.sendMessage("Q: " + jokeDTO.getSetup() + "\n3초 뒤에 정답이 출력됩니다.").queue();
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            textChannel.sendMessage("A: " + jokeDTO.getPunchline()).queue();
        } else if(message.getContentDisplay().equals("!도움말")) {
            textChannel.sendMessage("저는 일정한 시간마다 명언을 출력하는 명언봇입니다.\n명언이 필요하시면 \"!명언\"을,\n개발자를 위한 농담이 필요하시면 \"!농담\"을 입력해주세요.").queue();
        }
    }

    @Override
    public void onReady(@NotNull ReadyEvent event) {
        super.onReady(event);
        log.info("DiscordQuoteBot is ready!!");

        List<TextChannel> textChannels = event.getJDA().getTextChannels();
        for (TextChannel textChannel : textChannels) {
            if(textChannel != null) {
                textChannel.sendMessage("도움이 필요하시면 \"!도움말\"을 입력해주세요!!").queue();
                scheduleMessage(textChannel);
            } else {
                log.info("채널을 찾을 수 없습니다.");
            }
        }
    }

    private void scheduleMessage(TextChannel textChannel) {
        new Timer().schedule(
                new TimerTask() {
                    @Override
                    public void run() {
                        QuoteDTO quoteDTO = QuoteUtil.requestToQuoteAPI();
                        textChannel.sendMessage(quoteDTO.getMessage() + "   - " + quoteDTO.getAuthor() + "(" + quoteDTO.getAuthorProfile() + ") -").queue();
                    }
                },
                30000, 30000
        );
    }
}
