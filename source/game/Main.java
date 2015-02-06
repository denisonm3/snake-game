/*
 * Copyright 2015 Denison.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package game;

import game.base.JGame;
import game.snake.gui.JSnake;
import java.awt.Component;
import java.awt.event.KeyListener;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

/**
 *
 * @author Denison
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        JFrame janela = new JFrame("Snake");
        janela.setLocationRelativeTo(null);
        janela.setResizable(false);
        final JGame pn = configurarJogo(janela);
        janela.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent evt) {
                pn.exit();
                System.exit(0);
            }
        });
        janela.setVisible(true);
    }

    private static JGame configurarJogo(JFrame janela) {
        JGame game = new JSnake();
        janela.setIconImage(new ImageIcon(Main.class.getResource("snake/gui/img/cabeca.png")).getImage());
        janela.setSize(320, 350);
        janela.add((Component) game);
        janela.addKeyListener((KeyListener) game);
        Thread thread = new Thread((Runnable) game);
        thread.start();
        return game;
    }

}
