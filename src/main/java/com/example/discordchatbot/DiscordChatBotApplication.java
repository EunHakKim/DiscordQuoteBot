package com.example.discordchatbot;

import com.example.discordchatbot.listener.DiscordListener;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.security.auth.login.LoginException;

@SpringBootApplication
public class DiscordChatBotApplication {

	public static void main(String[] args) throws LoginException {
		ApplicationContext context = SpringApplication.run(DiscordChatBotApplication.class, args);

		DiscordBotToken discordBotTokenEntity = context.getBean(DiscordBotToken.class);
		String discordBotToken = discordBotTokenEntity.getDiscordBotToken();

		JDA jda = JDABuilder.createDefault(discordBotToken)
				.setActivity(Activity.playing("명언 대기"))
				.enableIntents(GatewayIntent.MESSAGE_CONTENT)
				.addEventListeners(new DiscordListener())
				.build();

	}

	@Component
	 class DiscordBotToken {
		@Value("${discord.bot.token}")
		private String discordBotToken;

		public String getDiscordBotToken() {
			return discordBotToken;
		}
	}
}
