package tr.com.myasir;


import tr.com.myasir.gui.LoginFE;

import javax.swing.*;

public class run {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new LoginFE();
            }
        });
    }
}

