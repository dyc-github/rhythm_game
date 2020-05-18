
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.FileAlreadyExistsException;

/**
 * the Recorder class is meant to record the users inputs and output them into a
 * new file
 * 
 * @author Ryan Tai, David Choi
 *
 */
public class Recorder {
	private MusicPlayer musicPlayer;
	private PrintWriter output;
	private String path;

	/**
	 * A constructor that defines the musicPlayer and creates a new directory, if
	 * one did not already exist, to store the levels
	 * 
	 * @param musicPlayer
	 */
	public Recorder(MusicPlayer musicPlayer) {
		this.musicPlayer = musicPlayer;
		new File("levels").mkdir(); // creates a new levels folder if one was not created
		path = System.getProperty("user.dir") + "/levels/";
	}

	/**
	 * creates a new txt file based on the songTitle
	 * 
	 * @param songTitle the title of the song
	 * @throws FileAlreadyExistsException if the file to a song already exists then
	 *                                    this method will throuw a
	 *                                    FileAlreadyExists Exception
	 */
	public void startNewRecodring(String songTitle) throws FileAlreadyExistsException {
		String levelPath = path + songTitle + "_Arrow.txt";

		File outputFile = new File(levelPath);

		if (outputFile.exists()) {
			throw new FileAlreadyExistsException(levelPath);
		} else {
			try {
				outputFile.createNewFile();
			} catch (IOException e) {
				System.out.println("issue creating new file");
				e.printStackTrace();
			}
			try {
				output = new PrintWriter(levelPath);
			} catch (FileNotFoundException e) {
				System.out.println("issue finding output file");
				e.printStackTrace();
			}
		}
	}

	/**
	 * records inputs into the the level file
	 * 
	 * @param direction the direction of the input
	 */
	public void record(String direction) {
		if (musicPlayer.isRunning()) {
			String outputString = "";
			switch (direction) {
			case "Up":
				outputString = "U";
				break;
			case "Down":
				outputString = "D";
				break;
			case "Left":
				outputString = "L";
				break;
			case "Right":
				outputString = "R";
				break;
			}
			if (!outputString.isEmpty()) {
				output.println(outputString + (musicPlayer.getCurrentTime()));
			}
		}
	}

	/**
	 * Ends the recording of user inputs.
	 */
	public void stopRecording() {
		System.out.println("close");
		output.close();
	}

}
