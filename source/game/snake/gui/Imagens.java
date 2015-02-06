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

import static game.base.Bloco.JOGADOR_DOWN;
import static game.base.Bloco.JOGADOR_LEFT;
import static game.base.Bloco.JOGADOR_RIGHT;
import static game.base.Bloco.JOGADOR_UP;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;

/**
 *
 * @author Denison
 */
public class Imagens {
    
    public final Image COMIDA = new ImageIcon(JSnake.class.getResource("img/comida.png")).getImage();

    public final Image CAMPO = new ImageIcon(JSnake.class.getResource("img/terra.png")).getImage();

    public final Image OBSTACULO = new ImageIcon(JSnake.class.getResource("img/bloco.png")).getImage();
    
    public final Color COR_CAMPO = new Color(235, 215, 170);
    
    private final Image cobra[][];
    
    public final Image[][] CURVA;
    
    private static final int COBRA_CABECA = 0;
    private static final int COBRA_CORPO = 1;
    private static final int COBRA_RABO = 2;
    private boolean anima = true;
    
    public Imagens() {
        cobra = new Image[3][4];
        cobra[COBRA_CABECA][JOGADOR_RIGHT] = new ImageIcon(Imagens.class.getResource("img/cabeca.png")).getImage();
        cobra[COBRA_CABECA][JOGADOR_LEFT] = verticalFlip(cobra[COBRA_CABECA][JOGADOR_RIGHT]);
        cobra[COBRA_CABECA][JOGADOR_DOWN] = flip90(cobra[COBRA_CABECA][JOGADOR_RIGHT]);
        cobra[COBRA_CABECA][JOGADOR_UP] = horizontalFlip(cobra[COBRA_CABECA][JOGADOR_DOWN]);
        cobra[COBRA_CORPO][JOGADOR_RIGHT] = new ImageIcon(JSnake.class.getResource("img/corpo_1.png")).getImage();
        cobra[COBRA_CORPO][JOGADOR_LEFT] = new ImageIcon(JSnake.class.getResource("img/corpo_2.png")).getImage();
        cobra[COBRA_CORPO][JOGADOR_DOWN] = flip90(cobra[COBRA_CORPO][JOGADOR_RIGHT]);
        cobra[COBRA_CORPO][JOGADOR_UP] = flip90(cobra[COBRA_CORPO][JOGADOR_LEFT]);
        cobra[COBRA_RABO][JOGADOR_RIGHT] = new ImageIcon(Imagens.class.getResource("img/rabo.png")).getImage();
        cobra[COBRA_RABO][JOGADOR_LEFT] = verticalFlip(cobra[COBRA_RABO][JOGADOR_RIGHT]);
        cobra[COBRA_RABO][JOGADOR_DOWN] = flip90(cobra[COBRA_RABO][JOGADOR_RIGHT]);
        cobra[COBRA_RABO][JOGADOR_UP] = horizontalFlip(cobra[COBRA_RABO][JOGADOR_DOWN]);
        
        CURVA = new Image[4][4];
        CURVA[JOGADOR_UP][JOGADOR_RIGHT] = new ImageIcon(Imagens.class.getResource("img/curva.png")).getImage();
        CURVA[JOGADOR_UP][JOGADOR_LEFT] = verticalFlip(CURVA[JOGADOR_UP][JOGADOR_RIGHT]);
        CURVA[JOGADOR_DOWN][JOGADOR_RIGHT] = horizontalFlip(CURVA[JOGADOR_UP][JOGADOR_RIGHT]);
        CURVA[JOGADOR_DOWN][JOGADOR_LEFT] = horizontalFlip(CURVA[JOGADOR_UP][JOGADOR_LEFT]);
        CURVA[JOGADOR_RIGHT][JOGADOR_UP] = CURVA[JOGADOR_DOWN][JOGADOR_LEFT];
        CURVA[JOGADOR_RIGHT][JOGADOR_DOWN] = CURVA[JOGADOR_UP][JOGADOR_LEFT];
        CURVA[JOGADOR_LEFT][JOGADOR_UP] = CURVA[JOGADOR_DOWN][JOGADOR_RIGHT];
        CURVA[JOGADOR_LEFT][JOGADOR_DOWN] = CURVA[JOGADOR_UP][JOGADOR_RIGHT];
    }

    private Image verticalFlip(Image oldImage) {
        int width = oldImage.getWidth(null);
        int height = oldImage.getHeight(null);
        // Create a new Image
        BufferedImage newImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        // Flip the old image horizontally
        newImage.getGraphics().drawImage(oldImage, 0, 0, width, height, width, 0, 0, height, null);
        return newImage;
    }

    private Image flip90(Image oldImage) {
        int width = oldImage.getHeight(null);
        int height = oldImage.getWidth(null);
        // Create a new Image
        BufferedImage newImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = (Graphics2D) newImage.getGraphics();
        // Flip the old image 90º
            int oldW = oldImage.getWidth(null);
            int oldH = oldImage.getHeight(null);
            g2d.translate(width / 2, height / 2);
            g2d.rotate(Math.toRadians(90));
            g2d.translate(-(oldW / 2), -(oldH / 2));
            g2d.drawImage(oldImage, 0, 0, null);
        return newImage;
    }

    private Image horizontalFlip(Image oldImage) {
        int width = oldImage.getWidth(null);
        int height = oldImage.getHeight(null);
        // Create a new Image
        BufferedImage newImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        // Flip the old image horizontally
        newImage.getGraphics().drawImage(oldImage, 0, 0, width, height, 0, height, width, 0, null);
        return newImage;
    }

    public Image getCabeca(int tipo) {
        return cobra[COBRA_CABECA][tipo];
    }

    public Image getRabo(int tipo) {
        return cobra[COBRA_RABO][tipo];
    }

    public Image getCorpo(int tipo) {
        if(tipo == JOGADOR_LEFT || tipo == JOGADOR_RIGHT) {
            if(anima) {
                anima=!anima;
                return cobra[COBRA_CORPO][JOGADOR_RIGHT];
            } else {
                anima=!anima;
                return cobra[COBRA_CORPO][JOGADOR_LEFT];
            }
        } else {
            if(anima) {
                anima=!anima;
                return cobra[COBRA_CORPO][JOGADOR_UP];
            } else {
                anima=!anima;
                return cobra[COBRA_CORPO][JOGADOR_DOWN];
            }
        }
    }
    
    
}
