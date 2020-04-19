import javax.swing.*;
import java.awt.*;
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

        frame.add(menuPanel);
        frame.add(gamePanel);

        frame.setSize(600, 420);
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
     * Checks for a sheet in the game, if there is a sheet it is drawn
     * if there is not we request an input from the user
     */
    private void drawPlaySheet() {
        if (game.getSheet() == null) {
            createInputFrame();
        } else {
            redraw();
        }
    }

    /**
     * Next 3 methods are for setting up the menu buttons, their functions and adding them to the menupanel
     */
    private void createMenuButtons() {
        reset = new JButton("Reset");
        addSheet = new JButton("Add a new sheet");
        quit = new JButton("Quit");
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
        quit.addActionListener(actionEvent -> System.exit(0));
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
            b.addActionListener(actionEvent -> {
                b.setBackground(Color.GREEN);
                counter++;
                bingoBanko();
                b.setEnabled(false);
            });
            gamePanel.add(b);
        }
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
        } else if (userInput == null) {
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