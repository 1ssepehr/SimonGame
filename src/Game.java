import java.awt.Color;
import java.awt.Font;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.*;

public class Game {

    // Panel size constants
    private static final int WINDOW_WIDTH = 600;  // Total Window's Width
    private static final int WINDOW_HEIGHT = WINDOW_WIDTH + 180;  // Total Window's Height
    private static final int MARGIN = 64;
    private static final int TOP_MARGIN = 88;
    private static final int TITLE_MARGIN = 18;
    private static final int BUTTON_WIDTH = 96;
    private static final int BUTTON_HEIGHT = 45;

    // Cell Size
    private static final int CELL_SIZE = (WINDOW_WIDTH - 3*MARGIN)/2;

    // Variable initialization
    private static List<Integer> Sequence = new ArrayList<>();      // The sequence of Cells Flashing
    private static int PlayerAttempt;                               // The Cell Number that Player attempts to guess
    private static int AttemptIndex = 0;                            // Index of the User's guess to check with Sequence
    private static Random NumberGenerator = new Random();           // Random generator for flashing
    private static int ScoreValue = 0;                              // Player's Score

    // Graphical Elements Definition
    private static JFrame gameFrame = new JFrame();
    private static JButton RedCell = new JButton();
    private static JButton GreenCell = new JButton();
    private static JButton BlueCell = new JButton();
    private static JButton YellowCell = new JButton();
    private static JLabel scoreLabel;

    // Logical booleans of the Game
    private static boolean canReceiveClicks = false;    // To prevent user clicks when showing Sequence
    private static boolean noPlayerMistake = true;      // To terminate game when user makes a mistake

    // main function
    public static void main(String[] args){
        // Initializing, placing, and displaying the GUI
        graphicElementsInitialize();

        ///////////////
        ///  START  ///
        ///////////////
        do {
            scoreLabel.setText("Score: " + ScoreValue);            // Increase the score
            ScoreValue++;

            Sequence.add(NumberGenerator.nextInt(4));       // Adds the next random to display
            playCells();                                           // Displays the Sequence by flashing the Cells

            while (noPlayerMistake && AttemptIndex < Sequence.size()) {
                sleep(100);
            }
            if (!noPlayerMistake) {
                gameOver();
            }
            AttemptIndex = 0;
        } while (noPlayerMistake);
    }

    private static void graphicElementsInitialize() { // Initializing, placing, and displaying the GUI

        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);       // Terminate upon exit

        //JPanel inside the gameFrame
        JPanel gameContentPanel = new JPanel();
        gameFrame.setContentPane(gameContentPanel);  // Adds the gameContentPanel to gameFrame

        // JFrame Construction, Size, Location, Exit state
        gameFrame.setTitle("Simon Game, by Sepehr Madani");             // Window Title
        gameFrame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);                 // Size
        gameFrame.setLocation(200, 200);                          // Location
        gameFrame.setResizable(false);                                  // Non-resizable

        // Title of the Game in the gameFrame
        JLabel GameTitle = new JLabel("Simon Game", JLabel.CENTER);
        GameTitle.setSize(WINDOW_WIDTH, MARGIN + TITLE_MARGIN);
        GameTitle.setFont(new Font(GameTitle.getFont().getName(), Font.PLAIN, 64));
        GameTitle.setLocation(0, TITLE_MARGIN);
        gameContentPanel.add(GameTitle);

        //Score in the gameFrame
        scoreLabel = new JLabel("Score: 0", JLabel.CENTER);
        scoreLabel.setSize(WINDOW_WIDTH, MARGIN + TITLE_MARGIN);
        scoreLabel.setFont(new Font(GameTitle.getFont().getName(), Font.PLAIN, 24));
        scoreLabel.setLocation(0, 4 * TITLE_MARGIN);
        gameContentPanel.add(scoreLabel);

        // About button at the bottom
        JButton aboutGameButton = new JButton("About...");
        aboutGameButton.setSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        aboutGameButton.setLocation((WINDOW_WIDTH - BUTTON_WIDTH) / 2, (WINDOW_HEIGHT - 5 * BUTTON_HEIGHT / 2));
        aboutGameButton.setOpaque(true);
        aboutGameButton.addActionListener(e -> aboutGamePopup());
        gameContentPanel.add(aboutGameButton);

        // Resolving compatibility issue with Mac
        try {
            UIManager.setLookAndFeel( UIManager.getCrossPlatformLookAndFeelClassName() );
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Red Button Construction
        RedCell.setBackground(Color.RED);
        RedCell.setBounds(MARGIN, MARGIN + TOP_MARGIN, CELL_SIZE, CELL_SIZE);
        RedCell.setOpaque(true);
        gameFrame.add(RedCell);

        // Green Button Construction
        GreenCell.setBackground(Color.GREEN);
        GreenCell.setBounds(2 * MARGIN + CELL_SIZE, MARGIN + TOP_MARGIN, CELL_SIZE, CELL_SIZE);
        GreenCell.setOpaque(true);
        gameFrame.add(GreenCell);

        // Blue Button Construction
        BlueCell.setBackground(Color.BLUE);
        BlueCell.setBounds(MARGIN, 2*MARGIN + CELL_SIZE + TOP_MARGIN, CELL_SIZE, CELL_SIZE);
        BlueCell.setOpaque(true);
        gameFrame.add(BlueCell);

        // Yellow Button Construction
        YellowCell.setBackground(Color.YELLOW);
        YellowCell.setBounds(2*MARGIN + CELL_SIZE, 2*MARGIN + CELL_SIZE + TOP_MARGIN, CELL_SIZE, CELL_SIZE);
        YellowCell.setOpaque(true);
        gameFrame.add(YellowCell);

        // Action Listeners for user click
        RedCell.addActionListener(e -> cellClick(RedCell));
        GreenCell.addActionListener(e -> cellClick(GreenCell));
        BlueCell.addActionListener(e -> cellClick(BlueCell));
        YellowCell.addActionListener(e -> cellClick(YellowCell));

        // Finalizing gameFrame visibility
        gameFrame.setLayout(null);
        gameFrame.setVisible(true);
    }

    private static void aboutGamePopup() {
        String aboutMessage = "Simon Game. Created with love by Sepehr Madani. \n\n" +
                "Originally invented by Ralph H. Baer and Howard J. Morrison. \n" +
                "All rights reserved for the publisher of the game, Hasbro. \n\n"+
                "To play, simply watch the sequence of cells and click them in order they lit up. \n\n\n" +
                "Dedicated to Emily Danielian.";
        JOptionPane.showMessageDialog(null, aboutMessage ,"About", JOptionPane.INFORMATION_MESSAGE);
    }

    private static void playCells() { // Displays the Sequence by flashing the Cells
        canReceiveClicks = false;                               // Disables player's ability to click and lose
        sleep(500);                                          // To wait after Player's last click
        gameFrame.getContentPane().setBackground(Color.GRAY);   // Makes the gameFrame gray --> Player now looks
        sleep(500);                                          // To grab player's attention after it turned gray
        for (Integer aSequence : Sequence) {                    // In a foreach loop, displays the Sequence on Cells
            switch (aSequence) {
                case 0 -> cellShow(RedCell);    // for Red
                case 1 -> cellShow(GreenCell);  // for Green
                case 2 -> cellShow(BlueCell);   // for Blue
                case 3 -> cellShow(YellowCell); // for Yellow
            }
        }
        sleep(500);                                      // Last delay to make a smooth transition to white
        gameFrame.getContentPane().setBackground(Color.WHITE);  // Returns color back to normal (Show's over!)
        canReceiveClicks = true;                            // Re-enables player's ability to click
    }
    private static void cellShow(JButton A) {       // When GAME plays the sequence for player
        Color PreviousColor = A.getBackground();
        A.setBackground(Color.WHITE);
        sleep(500);
        A.setBackground(PreviousColor);
        sleep(300);
    }

    private static void cellClick(JButton A) {    // When USER clicks to guess
        if (canReceiveClicks) {

            Color PrevColor = A.getBackground();    // The Animation
            A.setBackground(Color.WHITE);           // for the Cell
            sleep(100);                          // to shot that it
            A.setBackground(PrevColor);             // has been clicked

            if (PrevColor == Color.RED) { PlayerAttempt = 0; }
            if (PrevColor == Color.GREEN) { PlayerAttempt = 1; }
            if (PrevColor == Color.BLUE) { PlayerAttempt = 2; }
            if (PrevColor == Color.YELLOW) { PlayerAttempt = 3; }
            checkForPlayerMistake();
        }
    }

    private static void checkForPlayerMistake() {
        if (Sequence.get(AttemptIndex) == PlayerAttempt) {  // if player got it right, check for their next click
            AttemptIndex++;
        } else {
            noPlayerMistake = false; // else, then player made a mistake. Oops!
        }
    }
    private static void gameOver() {
        sleep(300);
        RedCell.setBackground(Color.BLACK);
        GreenCell.setBackground(Color.BLACK);
        BlueCell.setBackground(Color.BLACK);
        YellowCell.setBackground(Color.BLACK);
        ScoreValue--; // Fix the off-by-one during mistake
        String gameOverMessage = "Oops! You lost. Final score: " + ScoreValue;
        JOptionPane.showMessageDialog(null, gameOverMessage,"Game over", JOptionPane.ERROR_MESSAGE);
        sleep(500);
        gameFrame.dispatchEvent(new WindowEvent(gameFrame, WindowEvent.WINDOW_CLOSING));
    }

    private static void sleep(int n) {
        try {
            Thread.sleep(n);
        } catch(InterruptedException e) {
            System.out.println("Got interrupted!");
        }
    }
}