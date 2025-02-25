package com.hahn.software;


import com.hahn.software.ui.LoginScreen;

public class Main {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> new LoginScreen().setVisible(true));
    }
}
