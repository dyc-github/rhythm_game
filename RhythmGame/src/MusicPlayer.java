
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * The MusicPlayer class is meant to play whatever music file it is given as
 * well as give specific information about the music playing
 * 
 * @author Ryan Tai, David Choi
 * @version May 11, 2020
 * @author Period: 1
 * @author Assignment: RhythmGame
 * 
 * @author Sources: GeeksForGeeks - How to play an Audio file using Java:
 *         https://www.geeksforgeeks.org/play-audio-file-using-java/
 */
public class MusicPlayer {
	private AudioInputStream audioInputStream;
	private Clip clip;

	/**
	 * defines the song given a path
	 * 
	 * @param path the path to the song
	 * @return
	 */
	public boolean defineSong(String path) {
		if (clip != null) {
			clip.close();
		}
		try {
			// create AudioInputStream object
			audioInputStream = AudioSystem.getAudioInputStream(new File(path).getAbsoluteFile());

			// create clip reference
			clip = AudioSystem.getClip();

			// open audioInputStream to the clip
			clip.open(audioInputStream);

			return true;
		} catch (FileNotFoundException e) {
			System.out.println("Could not find the specified file");
			return false;
		} catch (UnsupportedAudioFileException e) {
			System.out.println("The audio file selected is unsuported");
			return false;
		} catch (IOException e) {
			System.out.println("error occcured in retrieving the file");
			e.printStackTrace();
			return false;
		} catch (LineUnavailableException e) {
			System.out.println("line unavailable (baing used by another application)");
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * plays the music
	 * 
	 * @return boolean to indicate whether starting the music was successful
	 */
	public boolean play() {
		try {
			clip.start();
			return true;
		} catch (Exception e) {
			System.out.println("string not found");
			return false;
		}
	}

	/**
	 * pauses the music
	 */
	public void pause() {
		clip.stop();
	}

	/**
	 * resets the music
	 */
	public void reset() {
		clip.setFramePosition(0);
	}

	/**
	 * indicates whether the song is still running
	 * 
	 * @return true - song is still running false song has stopped
	 */
	public boolean isRunning() {
		if (clip == null) {
			return false;
		}
		return clip.isRunning();
	}

	/**
	 * return the current position of the song in milliseconds
	 * 
	 * @return the current position of the song in milliseconds
	 */
	public int getCurrentTime() {
		// convert to milliseconds
		return (int) (clip.getMicrosecondPosition() + 500) / 1000;
	}

	/**
	 * returns the total time of the song in milliseconds
	 * 
	 * @return the total time of the song in milliseconds
	 */
	public int getTotalTime() {
		// convert to milliseconds
		return (int) (clip.getMicrosecondLength() + 500) / 1000;
	}

	/**
	 * a main method used to test this class
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		MusicPlayer musicPlayer = new MusicPlayer();
		if (musicPlayer.defineSong(System.getProperty("user.dir") + "\\music" + "\\song2.wav")) {

			Scanner scanner = new Scanner(System.in);

			while (true) {
				String input = scanner.nextLine();
				switch (input) {
				case "play":
					musicPlayer.play();
					break;
				case "pause":
					musicPlayer.pause();
					break;
				case "reset":
					musicPlayer.reset();
					break;
				case "new":
					musicPlayer.defineSong("song1.wav");
					break;
				case "length":
					System.out.println(musicPlayer.getTotalTime());
					break;
				case "pos":
					System.out.println(musicPlayer.getCurrentTime());
					break;
				case "end":
					return;
				}
			}
		}

	}
}
