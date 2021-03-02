package qna.bot.java;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import discord4j.core.DiscordClientBuilder;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.lifecycle.ReadyEvent;
import discord4j.core.object.entity.User;
import qna.bot.java.constants.Constants;
import qna.bot.java.util.MessageEventListenerUtil;

public class QNABot {
	
	private static final Logger logger = LoggerFactory.getLogger(QNABot.class);
	private static GatewayDiscordClient client;
	private static User user;
	
	static {
		setClient(DiscordClientBuilder.create(Constants.BOT_TOKEN)
	    		.build()
	    		.login()
	    		.block());
	}
	
	public static GatewayDiscordClient getClient() {
		return client;
	}
	
	private static void setClient(GatewayDiscordClient newClient) {
		client = newClient;
	}
	
	public static User getUser() {
		return user;
	}
	
	private static void setUser(User newUser) {
		user = newUser;
	}

	public static void main(String[] args) {
		getClient().getEventDispatcher().on(ReadyEvent.class)
    	.subscribe(event -> {
    		setUser(event.getSelf());
    		logger.debug(String.format("Logged in as %s#%s", getUser().getUsername(), getUser().getDiscriminator()));
    	});
	    
	    MessageEventListenerUtil.beginListening();
	    
	    getClient().onDisconnect().block();
	  }
}
