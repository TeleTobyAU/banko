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
