/**
 * The sheet class represents the sheet of 15 numbers ordinarily used for real life banko
 */
class Sheet {

    private int[] numbers;

    /**
     * Constructor for the class Sheet, created in the menu class and parsed to the game class
     * @param numbers - The numbers on the sheet
     */
    Sheet(int[] numbers) {
        this.numbers = numbers;
    }

    /**
     * Returns the numbers on the sheet
     * @return - the numbers on the sheet
     */
    int[] getSheetNumbers() {
        return numbers;
    }
}
