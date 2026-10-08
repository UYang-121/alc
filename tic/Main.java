package tic;

import engine.support.FXApplication;
import engine.support.FXFrontEnd;

/** Entry point for the Tic-Tac-Toe application. */
public class Main {

    public static void main(String[] args) {
        FXFrontEnd app = new App("Tic-Tac-Toe");
        FXApplication application = new FXApplication();
        application.begin(app);
    }
}
