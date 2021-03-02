package qna.bot.java.util;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;
import discord4j.core.spec.MessageCreateSpec;
import qna.bot.java.QNABot;
import qna.bot.java.constants.Commands;
import qna.bot.java.constants.Constants;

public class MessageEventListenerUtil {

	private static final Logger logger = LoggerFactory.getLogger(MessageEventListenerUtil.class);
	
	public static void beginListening() {
		QNABot.getClient().getEventDispatcher().on(MessageCreateEvent.class)
    		.map(MessageCreateEvent::getMessage)	//react to MessageCreateEvent by getting the message
    		.filter(message -> message.getAuthor().map(user -> !user.isBot()).orElse(false))	//only operate if the message author is not a bot
    		.filter(message -> message.getContent().startsWith(Constants.COMMAND_PREFIX))	//only operate if message begins with command prefix
    		.filter(message -> inCommands(message.getContent().substring(1)))	//only operate if message is a recognized keyword
    		//TODO: Create a message object (embed?) dynamically based on the keyword, and send that instead
    		.flatMap(Message::getChannel)	//get the channel in which the message was posted
    		.flatMap(channel -> channel.createMessage("Pong!"))	//send "Pong!" in the same channel that the message was received in
    		.subscribe();	//execute
	}
	
	/**
	 * Checks for whether or not the passed string is a recognized command (is in the Commands enum
	 * @param keyword
	 * @return whether or not the keyword is a recognized command
	 */
	public static boolean inCommands(String keyword) {
		logger.debug(keyword + ", " + Commands.values());
		return Arrays.stream(Commands.values())
				.map(Commands::name)
				.anyMatch(cmdName -> cmdName.equals(keyword));
	}
	
	private static MessageCreateSpec getResponse(Message query) {
		//TODO: Implement dynamic response creation based on keyword and parameters
		return null;
	}
}
