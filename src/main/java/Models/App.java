package Models;

import Services.WelcomeFrame;

import javax.swing.*;

public class App {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new WelcomeFrame());

    }
}
