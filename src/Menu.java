import javax.swing.*;
import java.awt.*;
import java.util.*;

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
     * Takes a game as parameter and draws its sheet
     */
    Menu(Game game) {
        this.game = game;

        frame = new JFrame();

        drawPlaySheet();
    }

    /**
     * The redraw method draws the whole menu from scratch
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

    private void resetPlayButtonsColor() {
        for (JButton b : playButtons) {
            b.setBackground(Color.GRAY);
        }
    }

    private void drawPlaySheet() {
        if (game.getSheet() == null) {
            createInputFrame();
        } else {
            redraw();
        }
    }

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

    private void bingoBanko() {
        if (counter % 5 == 0) {
            JOptionPane.showMessageDialog(frame, "BINGO BANKO!!!");
        }
    }

    private void createInputFrame() {
        frame.setVisible(false);
        String userInput = JOptionPane.showInputDialog("Enter your 15 numbers, space separated");

        if (userInput == null && firstRun) {
            System.exit(0);
        } else if (userInput == null) {
            redraw();
        } else {
            int[] input = stringArrayToIntArray(userInput.split("\\s+"));

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

    private int[] stringArrayToIntArray(String[] input) {
        int[] output = new int[input.length];

        for (int i = 0; i < input.length; i++) {
            output[i] = Integer.parseInt(input[i]);
        }

        return output;
    }

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

    private boolean checkDuplicates(int[] input) {
        HashSet<Integer> numberSet = new HashSet<>();

        for (int value : input) {
            numberSet.add(value);
        }

        return numberSet.size() == input.length;
    }
}