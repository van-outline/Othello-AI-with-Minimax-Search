package othello;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import tetris.CpuTimeTracker;

/**
  * This is the  main class where your Othello game will start.
  * The main method of this application calls the App constructor. You
  * will need to fill in the constructor to instantiate your game.
  *
  * This is the Othello App class. This class is run to start the game.
  *
  */

public class App extends Application {

    /**
     * This method is eventually called by the given launch method. It sets up the scene
     * of the game and the PaneOrganizer, which starts the game up.
     */
    @Override
    public void start(Stage stage) {
        // Create top-level object, set up the scene, and show the stage here.
        stage.setTitle("Othello");
        PaneOrganizer organizer = new PaneOrganizer();
        Scene scene = new Scene(
                organizer.getRoot(),
                Constants.SCENE_WIDTH,
                Constants.SCENE_HEIGHT);
        stage.setScene(scene);
        stage.show();
    }

    /*
    * Here is the mainline! No need to change this.
    */
    public static void main(String[] argv) {
        // launch is a method inherited from Application
        launch(argv);
    }
}
