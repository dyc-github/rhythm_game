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



public class MusicPlayer
{
	private String musicFile = "music"; 
	// ah fyi System.getProperty("user.dir") just get the directory to RhythmGame
	private String path = System.getProperty("user.dir") + "/" + musicFile;
	private AudioInputStream audioInputStream;
	private Clip clip; 
	
	public MusicPlayer() {
		new File(musicFile).mkdir();
	}

	public boolean defineSong(String songTitle) {
		if (clip!=null) {
			clip.close();
		}
		// ah i ripped this code from
		// https://www.geeksforgeeks.org/play-audio-file-using-java/
		try {
			// create AudioInputStream object
			audioInputStream = AudioSystem.getAudioInputStream(new File(path + "/" + songTitle)	.getAbsoluteFile());

			// create clip reference
			clip = AudioSystem.getClip();

			// open audioInputStream to the clip
			clip.open(audioInputStream);
			
			return true;
		}

		
		// TODO catch error things
		catch (FileNotFoundException e) {
<<<<<<< HEAD
			System.out.println("idk dont @ me");
			return false;
		}
		catch (UnsupportedAudioFileException e) {
			System.out.println("idk dont @ me");
			return false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("idk dont @ me");
=======
			System.out.println("idk dont @ me1");
			return false;
		}
		catch (UnsupportedAudioFileException e) {
			System.out.println("idk dont @ me2");
			return false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("idk dont @ me3");
>>>>>>> 4831d1edb1fb1bef7a5bfd1d4beed4d5361004c5
			e.printStackTrace();
			return false;
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
<<<<<<< HEAD
			System.out.println("idk dont @ me");
=======
			System.out.println("idk dont @ me4");
>>>>>>> 4831d1edb1fb1bef7a5bfd1d4beed4d5361004c5
			e.printStackTrace();
			return false;
		}
	}
	public void play() {
		
			clip.start();
	}
	
	public void pause (){
		clip.stop();
	}
	public void reset() {
		clip.setFramePosition(0);
	}
	
	public boolean isRunning() {
		return clip.isRunning();
	}
	
	public int getCurrentTime(){
		return (int)(clip.getMicrosecondPosition()+50000)/10000;
	}
	public int getTotalTime() {
		return (int)(clip.getMicrosecondLength()+50000)/10000;
	}
	

	
	
	public static void main(String[] args) { 
		MusicPlayer musicPlayer = new MusicPlayer();
		if (musicPlayer.defineSong("song2.wav")) {

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

