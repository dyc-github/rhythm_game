package rhythm_game.RhythmGame.src;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

public class Recorder {
	private MusicPlayer musicPlayer;
	private PrintWriter output;
	private String path;
	private final int CONSTANT = 0;

	public Recorder(MusicPlayer musicPlayer) {
		this.musicPlayer = musicPlayer;
		new File("levels").mkdir(); // creates a new levels folder if one was not created
		path = System.getProperty("user.dir") + "/levels/";
	}

	public void startNewRecodring(String songTitle) {
		String levelPath = path + songTitle + "_Arrow.txt";
		
		musicPlayer.defineSong(songTitle + ".wav");
		
		File outputFile = new File(levelPath);
		
		if (outputFile.exists()) {
			System.out.println("file already exists");
			return;// TODO define the specifics of what happens if a record file already exists
		} else {
			try {
				outputFile.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("error creating file");
				e.printStackTrace();
				return;
			}

			try {
				output = new PrintWriter(levelPath);
			} catch (FileNotFoundException e) {
				System.out.println("cannot find " + songTitle + "_Arrow.txt");
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			musicPlayer.play();
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
				output.println(outputString + (musicPlayer.getCurrentTime() - CONSTANT));
			}
		}
	}
	public void stopRecording() {
		System.out.println("close");
		output.close();
	}

}
