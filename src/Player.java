/**
 * The player starts the whole thing
 * Creates a game object and parses it to the menu through its constructor to start the game
 */
public class Player {

    public static void main(String[] args) {
        Game game = new Game();
        new Menu(game);
    }
}
