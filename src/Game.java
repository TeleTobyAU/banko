/**
 * The game class is used an intermediate step between the sheet and the menu
 * It makes checking for sheet at the beginning of the game simpler and allows adding new sheets easier
 */
class Game {

    private Sheet sheet;

    Game() {
        sheet  = null;
    }

    void addSheet(int[] numbers) {
        sheet = new Sheet(numbers);
    }

    Sheet getSheet() {
        return sheet;
    }

}
