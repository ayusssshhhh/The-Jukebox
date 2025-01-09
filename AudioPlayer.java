package DAO;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class AudioPlayer {
    private Clip clip;
    private boolean isPaused = false;

    public void play(String filePath) {
        try {
            File audioFile = new File(filePath);
            if (!audioFile.exists()) {
                System.out.println("File not found: " + filePath);
                return;
            }

            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
            System.out.println("Audio Format: " + audioStream.getFormat());

            clip = AudioSystem.getClip();
            clip.open(audioStream);
            System.out.println("Playback started.");

            clip.start();
            interactiveControls(); // Start interactive controls after playback begins
        } catch (UnsupportedAudioFileException e) {
            System.out.println("Unsupported audio file format.");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Error reading the audio file.");
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            System.out.println("Audio line unavailable.");
            e.printStackTrace();
        }
    }

    public void pause() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
            isPaused = true;
            System.out.println("Playback paused.");
        }
    }

    public void resume() {
        if (clip != null && isPaused) {
            clip.start();
            isPaused = false;
            System.out.println("Playback resumed.");
        }
    }

    public void stop() {
        if (clip != null) {
            clip.stop();
            clip.close();
            System.out.println("Playback stopped.");
        }
    }

    public void forward(long milliseconds) {
        if (clip != null) {
            long newPosition = clip.getMicrosecondPosition() + milliseconds * 1000;
            if (newPosition < clip.getMicrosecondLength()) {
                clip.setMicrosecondPosition(newPosition);
                System.out.println("Forwarded " + milliseconds + " milliseconds.");
            } else {
                System.out.println("Cannot forward beyond the track length.");
            }
        }
    }

    public void rewind(long milliseconds) {
        if (clip != null) {
            long newPosition = clip.getMicrosecondPosition() - milliseconds * 1000;
            if (newPosition > 0) {
                clip.setMicrosecondPosition(newPosition);
                System.out.println("Rewinded " + milliseconds + " milliseconds.");
            } else {
                clip.setMicrosecondPosition(0);
                System.out.println("Rewinded to the beginning.");
            }
        }
    }

    private void interactiveControls() {
        Scanner scanner = new Scanner(System.in);
        String command;

        System.out.println("Commands: pause, resume, stop, forward, rewind, exit");

        while (clip != null && clip.isOpen()) { // Ensure clip is not null
            System.out.print("Enter command: ");
            command = scanner.nextLine().trim().toLowerCase();

            switch (command) {
                case "pause":
                    pause();
                    break;

                case "resume":
                    resume();
                    break;

                case "stop":
                    stop();
                    return; // Exit interactive controls

                case "forward":
                    System.out.print("Enter milliseconds to forward: ");
                    try {
                        long forwardMs = scanner.nextLong();
                        scanner.nextLine(); // Consume leftover newline
                        forward(forwardMs);
                    } catch (Exception e) {
                        System.out.println("Invalid input for forward. Please enter a number.");
                        scanner.nextLine(); // Clear invalid input
                    }
                    break;

                case "rewind":
                    System.out.print("Enter milliseconds to rewind: ");
                    try {
                        long rewindMs = scanner.nextLong();
                        scanner.nextLine(); // Consume leftover newline
                        rewind(rewindMs);
                    } catch (Exception e) {
                        System.out.println("Invalid input for rewind. Please enter a number.");
                        scanner.nextLine(); // Clear invalid input
                    }
                    break;

                case "exit":
                    stop();
                    return;

                default:
                    System.out.println("Invalid command. Please try again.");
            }
        }

        System.out.println("Clip is closed. Exiting interactive controls.");
    }

}
