package testing;

import testing.CalcFrontend.MainFrame;

import java.awt.*;

// класс запуска приложения
public class Main {
    private static final MainFrame mainFrame = new MainFrame();
    public static void main(String[] args){

        EventQueue.invokeLater(() -> {
            mainFrame.setVisible(true);
        });
    }
}
