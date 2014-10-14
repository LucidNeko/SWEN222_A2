package engine.util;

import javax.swing.JLabel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Hamish
 *
 */
public class Messenger implements Service {
	private static final Logger log = LogManager.getLogger();

	private final JLabel label;

	public Messenger(JLabel label) {
		if(label == null)
			throw new IllegalArgumentException("label cannot be null");

		this.label = label;
	}

	/**
	 * Prints the message to the target label.<br>
	 * ("Hello World") -> "Hello World"<br>
	 * ("Hello {}", "World") -> "Hello World"<br>
	 * ("{} {}", "Hello", "World") -> "Hello World"<br>
	 * @param message
	 * @param args
	 */
	public void sendMessage(String message, Object... args) {
		if(message == null) {
			log.error("message cannot be null");
			return;
		}

		//if there are actually some args.
		if(args != null && args.length > 0) {
			int index = 0;
			//while there are args left AND there are still {} to replace.
			while(index < args.length && message.contains("{}")) {
				message = message.replaceFirst("\\{\\}", args[index++].toString());
			}
		}

		label.setText(message);
	}

}
