import java.awt.Color;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.*;

public class Game {

    // Frame Window Size Properties
    private static final int WINDOW_WIDTH = 600;                   //Total Window's Width
    private static final int WINDOW_HEIGHT = WINDOW_WIDTH + 100;   //Total Window's Height, a little more for the title
    private static final int MARGIN = 64;

    // Cell Size
    private static final int CELL_SIZE = (WINDOW_WIDTH - 3*MARGIN) / 2;

    // Variable initialization
    private static List<Integer> Sequence = new ArrayList<>();      // The sequence of Cells Flashing
    private static int PlayerAttempt;                               // The Cell Number that Player attempts to guess
    private static int AttemptIndex = 0;                            // Index of the User's guess to check with Sequence
    private static Random NumberGenerator = new Random();           // Random generator for flashing

    //Graphical Elements Definition
    private static JFrame frame = new JFrame();
    private static JButton RedCell = new JButton();
    private static JButton GreenCell = new JButton();
    private static JButton BlueCell = new JButton();
    private static JButton YellowCell = new JButton();

    // Logical booleans of the Game
    private static boolean canReceiveClicks = false;                // To prevent user clicks when showing Sequence
    private static boolean noPlayerMistake = true;                  // To terminate game when user makes a mistake

    ////////////////////////
    /////     MAIN     /////
    ////////////////////////
    public static void main(String[] args){
        // Initializing, placing, and displaying the GUI
        graphicElementsInitialize();

        ///////////////
        ///  START  ///
        ///////////////
        do {
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

        // JFrame Construction, Size, Location, Exit state
        frame.getContentPane().add(new JLabel("Welcome to the Secret Shop!"));
        frame.setTitle("Simon Game, by Sepehr Madani");             // Window Title
        frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);                 // Size
        frame.setLocation(200, 200);                          // Location
        frame.setBackground(Color.WHITE);                           // Color
        frame.setResizable(false);                                  // Non-resizable
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);       // Terminate upon exit

        // Red Button Construction
        RedCell.setBackground(Color.RED);
        RedCell.setBounds(MARGIN, MARGIN, CELL_SIZE, CELL_SIZE);
        frame.add(RedCell);

        // Green Button Construction
        GreenCell.setBackground(Color.GREEN);
        GreenCell.setBounds(2*MARGIN + CELL_SIZE, MARGIN, CELL_SIZE, CELL_SIZE);
        frame.add(GreenCell);

        // Blue Button Construction
        BlueCell.setBackground(Color.BLUE);
        BlueCell.setBounds(MARGIN, 2*MARGIN + CELL_SIZE, CELL_SIZE, CELL_SIZE);
        frame.add(BlueCell);

        // Yellow Button Construction
        YellowCell.setBackground(Color.YELLOW);
        YellowCell.setBounds(2*MARGIN + CELL_SIZE, 2*MARGIN + CELL_SIZE, CELL_SIZE, CELL_SIZE);
        frame.add(YellowCell);

        // Action Listeners for user click
        RedCell.addActionListener(e -> cellClick(RedCell));
        GreenCell.addActionListener(e -> cellClick(GreenCell));
        BlueCell.addActionListener(e -> cellClick(BlueCell));
        YellowCell.addActionListener(e -> cellClick(YellowCell));

        // Finalizing frame visibility
        frame.setLayout(null);
        frame.setVisible(true);
    }
    
    private static void playCells() { // Displays the Sequence by flashing the Cells
        canReceiveClicks = false;                          // Disables player's ability to click and lose
        sleep(500);                                     // To wait after Player's last click
        frame.getContentPane().setBackground(Color.GRAY);  // Makes the frame gray --> Player now looks
        sleep(500);                                     // To grab player's attention after it turned gray
        for (Integer aSequence : Sequence) {               // In a foreach loop, displays the Sequence on Cells
            switch (aSequence) {
                case 0: // for Red
                    cellShow(RedCell);
                    break;
                case 1: // for Green
                    cellShow(GreenCell);
                    break;
                case 2: // for Blue
                    cellShow(BlueCell);
                    break;
                case 3: // for Yellow
                    cellShow(YellowCell);
                    break;
            }
        }
        sleep(500);                                      // Last delay to make a smooth transition to white
        frame.getContentPane().setBackground(Color.WHITE);  // Returns color back to normal (Show's over!)
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
            Color PrevColor = A.getBackground();
            cellClickAnimation(A, PrevColor);
            if (PrevColor == Color.RED) { PlayerAttempt = 0; }
            if (PrevColor == Color.GREEN) { PlayerAttempt = 1; }
            if (PrevColor == Color.BLUE) { PlayerAttempt = 2; }
            if (PrevColor == Color.YELLOW) { PlayerAttempt = 3; }
            checkForPlayerMistake();
        }
    }
    private static void cellClickAnimation (JButton A, Color PrevColor) {
        A.setBackground(Color.WHITE);
        sleep(100);
        A.setBackground(PrevColor);
    }

    private static void checkForPlayerMistake() { // Checks if Player
        if (Sequence.get(AttemptIndex) == PlayerAttempt) {
            AttemptIndex++;
        } else {
            noPlayerMistake = false;
        }
    }
    private static void gameOver() {
        sleep(300);
        RedCell.setBackground(Color.BLACK);
        GreenCell.setBackground(Color.BLACK);
        BlueCell.setBackground(Color.BLACK);
        YellowCell.setBackground(Color.BLACK);
        JOptionPane.showMessageDialog(null, "Oops! You lost. Great Job!","Game over", JOptionPane.ERROR_MESSAGE);
        sleep(500);
        frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
    }

    private static void sleep(int n) {
        try {
            Thread.sleep(n);
        } catch(InterruptedException e) {
            System.out.println("Got interrupted!");
        }
    }
}