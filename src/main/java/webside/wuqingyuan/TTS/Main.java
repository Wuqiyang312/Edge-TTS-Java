package webside.wuqingyuan.TTS;

import com.formdev.flatlaf.FlatDarculaLaf;
import webside.wuqingyuan.TTS.ui.MainGui;

import javax.swing.*;

public class Main {
    public static void main(String[] args) throws Exception {
        System.setProperty("sun.java2d.noddraw", "true");
        // 设置FlatLaf为界面外观
        UIManager.setLookAndFeel(new FlatDarculaLaf());

        MainGui mainGui = new MainGui();
        mainGui.setVisible(true);
    }
}