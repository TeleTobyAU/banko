import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

/**
 * The Menu class is responsible for displaying numbers, marking them when clicked and resetting the view when a new round starts
 * Also responsible for taking inputs from the user, checking validity of the input and parsing it to the game
 *
 * @author Tobias Laursen
 */
class Menu {
    //Constants
    private int counter = 0;
    private boolean firstRun = true;
    private Game game;

    //Menu layout
    private JFrame frame;
    private JPanel menuPanel;
    private JPanel gamePanel;

    //Menu buttons
    private JButton reset;
    private JButton addSheet;
    private JButton quit;

    //Play buttons
    private ArrayList<JButton> playButtons = new ArrayList<>();
    private JButton button1, button2, button3, button4, button5,
            button6, button7, button8, button9, button10,
            button11, button12, button13, button14, button15;

    /**
     * Constructor for the menu
     * @param game - The game provides the numbers to be drawn
     */
    Menu(Game game) {
        this.game = game;

        frame = new JFrame();

        drawPlaySheet();
    }

    /**
     * Checks for a sheet in the game, if there is a sheet it is drawn
     * if there is not we request an input from the user
     */
    private void drawPlaySheet() {
        int[] sheetAttempt = stringArrayToIntArray(new Loader().loadPreviousSheet());
        if (sheetAttempt.length < 1) {
            createInputFrame();
        } else {
            game.addSheet(sheetAttempt);
            redraw();
        }
    }

    /**
     * The redraw method draws the whole menu from scratch
     * Used in initial setup, but also for reset
     */
    private void redraw() {
        counter = 0;

        frame.setVisible(false);
        frame = new JFrame();

        menuPanel = new JPanel();
        menuPanel.setLayout(new GridLayout(3, 1));
        createMenuButtons();
        setMenuButtonsFunction();
        setMenuButtons();

        gamePanel = new JPanel();
        gamePanel.setLayout(new GridLayout(3, 5));
        playButtons.clear();
        createPlayButtons();
        setPlayButtons();
        setPlayButtonFunction();
        resetPlayButtonsColor();
        makePlayButtonsPretty();

        frame.add(menuPanel);
        frame.add(gamePanel);

        frame.setSize(new Dimension(800, 400));
        frame.setLayout(new GridLayout(1, 2));
        frame.setVisible(true);
    }

    /**
     * Resets the playbuttons background colo to gray
     */
    private void resetPlayButtonsColor() {
        for (JButton b : playButtons) {
            b.setBackground(Color.GRAY);
        }
    }

    /**
     * Next 3 methods are for setting up the menu buttons, their functions and adding them to the menupanel
     */
    private void createMenuButtons() {
        reset = new JButton("Reset");
        makeButtonPretty(reset);
        addSheet = new JButton("Add a new sheet");
        makeButtonPretty(addSheet);
        quit = new JButton("Quit");
        makeButtonPretty(quit);
    }

    private void setMenuButtons() {
        menuPanel.add(reset);
        menuPanel.add(addSheet);
        menuPanel.add(quit);
    }

    private void setMenuButtonsFunction() {
        //Removes all markings from the sheet
        reset.addActionListener(actionEvent -> redraw());

        //Opens menu for adding a new sheet with user chosen numbers
        addSheet.addActionListener(actionEvent -> createInputFrame());

        //Terminates the program
        quit.addActionListener(actionEvent -> {
            if (new Loader().saveCurrentSheet(game.getSheet())) {
                System.out.println("Save successful");
            } else {
                System.out.println("Save failed");
            }
            System.exit(0);
        });
    }

    /**
     * Next 3 methods are for setting up the 15 play buttons, their functions and adding them to the gamepanel
     */
    private void createPlayButtons() {
        int[] sheet = game.getSheet().getSheetNumbers();
        button1 = new JButton(String.valueOf(sheet[0]));
        button2 = new JButton(String.valueOf(sheet[1]));
        button3 = new JButton(String.valueOf(sheet[2]));
        button4 = new JButton(String.valueOf(sheet[3]));
        button5 = new JButton(String.valueOf(sheet[4]));
        button6 = new JButton(String.valueOf(sheet[5]));
        button7 = new JButton(String.valueOf(sheet[6]));
        button8 = new JButton(String.valueOf(sheet[7]));
        button9 = new JButton(String.valueOf(sheet[8]));
        button10 = new JButton(String.valueOf(sheet[9]));
        button11 = new JButton(String.valueOf(sheet[10]));
        button12 = new JButton(String.valueOf(sheet[11]));
        button13 = new JButton(String.valueOf(sheet[12]));
        button14 = new JButton(String.valueOf(sheet[13]));
        button15 = new JButton(String.valueOf(sheet[14]));
    }

    private void setPlayButtons() {
        playButtons.add(button1);
        playButtons.add(button2);
        playButtons.add(button3);
        playButtons.add(button4);
        playButtons.add(button5);
        playButtons.add(button6);
        playButtons.add(button7);
        playButtons.add(button8);
        playButtons.add(button9);
        playButtons.add(button10);
        playButtons.add(button11);
        playButtons.add(button12);
        playButtons.add(button13);
        playButtons.add(button14);
        playButtons.add(button15);
    }

    private void setPlayButtonFunction() {
        for (JButton b : playButtons) {
            setMarkFunction(b);
            gamePanel.add(b);
        }
        }

    private void setMarkFunction(JButton b) {
        b.setBackground(Color.GRAY);
        b.addActionListener(actionEvent -> {
            b.setBackground(Color.GREEN);
            b.setForeground(Color.black);
            counter++;
            bingoBanko();
            setUnmarkFunction(b);
        });
    }

    private void setUnmarkFunction(JButton b) {
        b.removeActionListener(b.getActionListeners()[0]);
        b.addActionListener(e -> {
            int result = JOptionPane.showConfirmDialog( null,
                    "Are you sure you want to unmark " + b.getText() + "?",
                    "Unmarking " + b.getText(), JOptionPane.OK_CANCEL_OPTION);
            if (result == 0) {
                for( ActionListener al : b.getActionListeners() ) {
                    b.removeActionListener( al );
                }
                setMarkFunction(b);
                b.repaint();
                makeButtonPretty(b);
                counter--;
            }
        });
    }

    private void makePlayButtonsPretty() {
        for (JButton b : playButtons) {
            b.setMinimumSize(new Dimension(40, 40));
            makeButtonPretty(b);
        }
    }

    private void makeButtonPretty(JButton b) {
            b.setFont(new Font("HelveticaNeue", Font.BOLD, 25));
            b.setBackground(Color.black);
            b.setForeground(Color.white);
    }

    /**
     * Displays the bingo banko dialog when we have 5, 10 and 15 numbers
     */
    private void bingoBanko() {
        if (counter % 5 == 0) {
            JOptionPane.showMessageDialog(frame, "BINGO BANKO!!!");
        }
    }

    /**
     * Takes sheet input from user
     * Makes sure there are 15 unique numbers in the interval 1 - 90
     * Uses the helper methods checkDuplicates() and allowedNumbers() to check validity
     */
    private void createInputFrame() {
        frame.setVisible(false);
        String userInput = JOptionPane.showInputDialog("Enter your 15 numbers, space separated");

        if (userInput == null && firstRun) {
            System.exit(0);
        } else if (userInput == null || userInput.length() < 1) {
            redraw();
        } else {
                int[] input = stringArrayToIntArray(userInput.split("\\s+")); //Splits a whitespaces

                boolean correctAmountOfNumbers = input.length == 15;
                boolean duplicates = !checkDuplicates(input);
                boolean numbersWithinOneAndNinety = allowedNumbers(input);

                boolean legalInput = correctAmountOfNumbers && !duplicates && numbersWithinOneAndNinety;

                if (!legalInput) {
                    JOptionPane.showMessageDialog(frame, "You have to enter 15 unique numbers between 1 and 90");
                    createInputFrame();
                } else {
                    game.addSheet(input);
                }
                redraw();
            }
        firstRun = false;
    }

    /**
     * Helper method to convert the user input to a number array, used in checking validity of input
     * @param input - The String array we get from the user
     * @return - The array as integers
     */
    private int[] stringArrayToIntArray(String[] input) {
        int[] output = new int[input.length];

        for (int i = 0; i < input.length; i++) {
            output[i] = Integer.parseInt(input[i]);
        }

        return output;
    }

    /**
     * Makes sure the input numbers are in the range 1 - 90
     * @param input - The integer array we are checking
     * @return - True if the input is valid, and false if it is not
     */
    private boolean allowedNumbers(int[] input) {
        boolean returnBool = true;

        for (int number : input) {
            if (number < 1 || number > 90) {
                returnBool = false;
                break;
            }
        }
        return returnBool;
    }

    /**
     * Checks the array for duplicates by turning into a set
     * @param input - The integer array we are checking
     * @return - True if there are no duplicates, false if there are
     */
    private boolean checkDuplicates(int[] input) {
        HashSet<Integer> numberSet = new HashSet<>();

        for (int value : input) {
            numberSet.add(value);
        }

        return numberSet.size() == input.length;
    }
}