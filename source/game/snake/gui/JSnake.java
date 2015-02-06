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
package game.snake.gui;

import game.base.JGame;
import game.base.Bloco;
import game.snake.engine.Campo;
import game.snake.engine.Motor;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;

/**
 * Interface de apresentação do jogo
 *
 * @author denison_usuario
 */
public class JSnake extends JPanel implements JGame, KeyListener, Runnable {

    private static final int INCREMENTO = 10;

    private final Motor jogo;
    private final Imagens img;
    private final Sounds snd;
    private final int pos[];
    private boolean continuar = true;
    private String modo;

    public JSnake() {
        this.modo = "Normal";
        jogo = new Motor();
        pos = new int[jogo.getCampo().getLargura() + 2];
        for (int i = 0; i <= jogo.getCampo().getLargura() + 1; i++) {
            pos[i] = i * INCREMENTO;
        }
        img = new Imagens();
        snd = new Sounds();
        setBounds(pos[0], pos[0], pos[pos.length - 1], pos[pos.length - 1] + INCREMENTO);
        setSize(pos[pos.length - 1], pos[pos.length - 1] + INCREMENTO);
    }

    @Override
    public void paint(Graphics g) {
        //g.clearRect(0, 0, this.getHeight(), this.getHeight());
        if (jogo.isExecutar()) {
            //desenhar campo
            paintField(g);
            //desenha a comida
            paintCoin(g);
            //desenha a cobrinha
            paintPlayer(g);
            //desenha as informações da partida
            paintInfo(g);
        } else {
            paintMenu(g);
        }
        g.setColor(Color.RED);
    }

    private void paintField(Graphics g) {
        Campo cp = jogo.getCampo();
        g.setColor(img.COR_CAMPO);
        g.fillRect(pos[0], pos[0], pos[pos.length - 1], pos[pos.length - 1]);
        for (int i = 2; i < pos.length - 1; i += 5) {
            for (int j = 2; j < pos.length - 1; j += 5) {
                g.drawImage(img.CAMPO, pos[i], pos[j - 1], null);
                g.drawImage(img.CAMPO, pos[i - 1], pos[j], null);
                g.drawImage(img.CAMPO, pos[i], pos[j], null);
                g.drawImage(img.CAMPO, pos[i + 1], pos[j], null);
                g.drawImage(img.CAMPO, pos[i], pos[j + 1], null);
            }
        }
        for (Bloco bc : cp.getObstaculos()) {
            g.drawImage(img.OBSTACULO, pos[bc.getColuna()], pos[bc.getLinha()], null);
        }
    }

    private void paintPlayer(Graphics g) {
        List<Bloco> cobra = jogo.getJogador().getBlocos();
        Bloco bc = cobra.get(0);
        g.drawImage(img.getCabeca(bc.getTipo()), pos[bc.getColuna()], pos[bc.getLinha()], null);
        int lastDir = bc.getTipo();
        for (int i = 1; i < cobra.size() - 1; i++) {
            bc = cobra.get(i);
            if (bc.getTipo() != lastDir) {
                g.drawImage(img.CURVA[bc.getTipo()][lastDir], pos[bc.getColuna()], pos[bc.getLinha()], null);
            } else {
                g.drawImage(img.getCorpo(bc.getTipo()), pos[bc.getColuna()], pos[bc.getLinha()], null);
            }
            lastDir = bc.getTipo();
        }
        bc = cobra.get(cobra.size() - 1);
        if (0 <= bc.getLinha() && bc.getLinha() < pos.length && 0 <= bc.getLinha() && bc.getColuna() < pos.length) {
            if (bc.getTipo() == lastDir) {
                g.drawImage(img.getRabo(bc.getTipo()), pos[bc.getColuna()], pos[bc.getLinha()], null);
            } else {
                g.drawImage(img.getRabo(lastDir), pos[bc.getColuna()], pos[bc.getLinha()], null);
            }

        }
    }

    private void paintCoin(Graphics g) {
        for (Bloco bc : jogo.getComida()) {
            g.drawImage(img.COMIDA, pos[bc.getColuna()], pos[bc.getLinha()], null);
        }
    }

    private void paintInfo(Graphics g) {
        g.setColor(Color.lightGray);
        g.fillRect(pos[0], pos[pos.length - 1], pos[pos.length - 1], pos[1]);
        g.setColor(Color.black);
        g.drawString(jogo.toString() + "    " + modo, pos[0], pos[pos.length - 1] + INCREMENTO);
    }

    private void paintMenu(Graphics g) {
        if (jogo.getVidas() > 0) {
            g.setColor(Color.cyan);
            g.fillRect(pos[5], pos[5], pos[20], pos[10]);
            g.setColor(Color.black);
            g.drawString("Press Enter to return.", pos[9], pos[11]);
        } else {
            g.setColor(Color.cyan);
            g.fillRect(pos[5], pos[5], pos[20], pos[15]);
            g.setColor(Color.black);
            g.drawRect(pos[5], pos[5], pos[20], pos[15]);
            g.drawString(" Press Enter to Start.", pos[9], pos[10]);
            g.drawString("Space to change mode:", pos[9], pos[13]);
            g.drawString("           " + modo, pos[9], pos[16]);
        }
    }

    public Motor getJogo() {
        return jogo;
    }

    @Override
    public void run() {
        snd.start();
        while (continuar) {
            try {
                int event = jogo.executar();
                if (event != Motor.EV_PARAR) {
                    switch (event) {
                        case Motor.EV_COMER: snd.comer(); break;
                        case Motor.EV_BATER: snd.bater(); break;
                        case Motor.EV_NEWLV: snd.novoNivel(); break;
                        case Motor.EV_FIMJG: snd.fimJogo(); break;
                    }
                    repaint();
                }
                Thread.sleep(50);
            } catch (InterruptedException ex) {
                Logger.getLogger(JSnake.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        snd.stop();
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_RIGHT:
                jogo.mudarDirecao(Bloco.JOGADOR_RIGHT);
                break;
            case KeyEvent.VK_LEFT:
                jogo.mudarDirecao(Bloco.JOGADOR_LEFT);
                break;
            case KeyEvent.VK_UP:
                jogo.mudarDirecao(Bloco.JOGADOR_UP);
                break;
            case KeyEvent.VK_DOWN:
                jogo.mudarDirecao(Bloco.JOGADOR_DOWN);
                break;
            case KeyEvent.VK_SPACE:
                if (jogo.getVidas() <= 0) {
                    modo = jogo.modeChange();
                    repaint();
                }
                break;
            case KeyEvent.VK_ENTER:
                if (jogo.getVidas() <= 0) {
                    jogo.reiniciar();
                    jogo.setExecutar(true);
                } else {
                    jogo.setExecutar(!jogo.isExecutar());
                }
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    public void exit() {
        continuar = false;
    }

}
