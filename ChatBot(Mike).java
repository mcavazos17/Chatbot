import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import java.awt.Color;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.lang.Math;

public class ChatBot extends JFrame implements KeyListener {

	private static final long serialVersionUID = 1L;
	JPanel p = new JPanel();
	JTextArea dialog = new JTextArea(20, 50);
	JTextArea input = new JTextArea(1, 50);
	JScrollPane scroll = new JScrollPane(
		dialog, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
	);

	String userResponce = "unchanged";
	String reply = "unchanged";
	boolean loop = true;

	public static void main(String[] args){
		new ChatBot();
	}
	
	public ChatBot(){
		super("Chat Bot");
		setSize(600,400);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		dialog.setEditable(false);
		input.addKeyListener(this);
	
		p.add(scroll);
		p.add(input);
		p.setBackground(new Color(255,200,0));
		add(p);
		
		setVisible(true);
	}

	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			input.setEditable(false);
			// -----grab quote-----------
			userResponce = input.getText();
			reply = RespondTo(userResponce);
		}
	}

	public void keyTyped(KeyEvent e){}

	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			input.setEditable(true);
		}
	}

	public void addText(String str) {
		dialog.setText(dialog.getText() + str);
	}

	public static String RespondTo(String reply) {
		/*
		 * start finding key words, first we parse the string into the first word, test
		 * it, then the next and next... if the word is a key word then find out the
		 * correct responce for that word without testing any other words. This might
		 * make the come up with wierd resuls such as
		 * 
		 * User: "HELLO, My problem is that I have a srong HATE for pillows"
		 * Computer:"Hello" The following might happen if we respond to hello and hate.
		 * since we have no priority system. I would code one if I had more time...
		 * 
		 * anyway, let the butchering begin
		 */

		String ReplyString = "Please go on";
		int loop = 0;
		int wordStart;
		int wordEnd;
		boolean wordEnded = false;
		char test = ' ';
		String testString = " ";
		String word = "";
		String savedReply = " ";
		boolean space = false;
		boolean chars = false;
		int replyNumber = 6;// initialize to 6 because its not in use and wont sqrew with stuff;
		boolean replySaved = false;

		while (loop <= reply.length()) {
			// first we are going to error check in case the user had a spaz attack on the
			// spacebar

			if (loop < reply.length())// make sure we don't cause any errors on accident
			{
				test = reply.charAt(loop);

				testString = "" + test; // Convert to string so its easier to work with
			}
			space = testString.equals(" ");

			if (space == true && wordEnded == true) {

				Debug("get rid of spaces");

				wordStart = loop;

				word = "";// just in case
			}

			if (space == true && wordEnded == false || loop >= reply.length()) {

				if (replySaved) {
					ReplyString = savedReply + word + ".";
					loop = reply.length(); // break the loop

				}

				else {

					word = word.toLowerCase();

					Debug("get rid of spaces and find out if reply to " + word);// you will see these scattered
																				// throughout my code. they are just for
																				// debugging
																				// and are turned off right now. to turn
																				// them on go to the debug
																				// method and turn "debug" to true.

					replyNumber = GetReplyStat(word); // Find out if the word should be replyed to

					if (replyNumber != 4 && replyNumber != 0) {

						Debug("" + replyNumber);
						ReplyString = "" + GetReply(replyNumber);
					}

					if (replyNumber == 0) {
						savedReply = "Tell me more about your ";
						replySaved = true;
					}

					if (replyNumber != 0 && replyNumber != 4)
						loop = reply.length(); // break the loop here

					wordEnded = true;

					word = "";
				}
			}

			// Chars = true;
			if (space == false) {
				wordEnded = false;

				Debug("character" + testString);
				word = word + testString;

			}

			loop++;

		}

		return ReplyString;

	}

	public static void Debug(String s) {
		boolean debug = false;

		if (debug) {
			System.out.println(s);
		}
	}

	public static int GetReplyStat(String word) {

		int Stat;
		// fill this switch statment up with what words are to be replyed to...
		switch (word) {
		case ":D":
			Debug("got number");
			Stat = 1;// 1 is the number of the set response
			break;

		case "my":
			Stat = 0;// 0 means the next word needs to be found
			break;

		case "love":
			Stat = 2;// 2 is the number of the set response
			break;

		case "hate":
			Stat = 2;// 2 is the number of the set response
			break;

		case "hello":
			Stat = 3;// 3 is the number of the set response
			break;

		case "hi":
			Stat = 3;// 3 is the number of the set response
			break;

		case "goodbye":
			Stat = 5;
			break;

		default:
			Stat = 4;// 4 means there is no reply that is good yet.
			break;
		}

		return Stat;
	}

	public static String GetReply(int ReplyNumber) {

		// "Hey this looks like the last method", I know that but I cant return to
		// deferent types in one method so this will have to do.
		String Reply;

		switch (ReplyNumber) {
		case 1:
			Debug("Got reply");
			Reply = "At least your smiling...";

			break;

		case 2:
			Reply = "Those seem like some very strong words";
			break;

		case 3:
			Reply = "Hello";
			break;

		case 5:
			Reply = "take care";
			break;

		default:
			Reply = "I seem to be having health issues right now " + ReplyNumber;
			break;
		}

		return Reply;

	}
}