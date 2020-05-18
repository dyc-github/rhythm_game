
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.FileAlreadyExistsException;

public class Recorder {
	private MusicPlayer musicPlayer;
	private PrintWriter output;
	private String path;

	public Recorder(MusicPlayer musicPlayer) {
		this.musicPlayer = musicPlayer;
		new File("levels").mkdir(); // creates a new levels folder if one was not created
		path = System.getProperty("user.dir") + "/levels/";
	}

	public void startNewRecodring(String songTitle) throws FileAlreadyExistsException  {
		String levelPath = path + songTitle + "_Arrow.txt";
		
		File outputFile = new File(levelPath);
		
		if (outputFile.exists()) {
			throw new FileAlreadyExistsException(levelPath);
		} 
		else {
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
	public void stopRecording() {
		System.out.println("close");
		output.close();
	}

}
