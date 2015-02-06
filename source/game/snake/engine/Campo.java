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
package game.snake.engine;

import game.base.Bloco;
import game.base.Map;
import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author denison_usuario
 */
public class Campo {

    private final int altura;
    private final int largura;
    private ArrayList<Bloco> obstaculos;
    private final Random sorteia;
    private final Map nivelMap[];
    private int nivel;

    public Campo(int alt, int larg) {
        this.nivel = 0;
        altura = alt;
        largura = larg;
        sorteia = new Random();
        nivelMap = new Map[5];
        nivelMap[0] = new Map(Campo.class.getResource("map/level1.bmp"));
        nivelMap[1] = new Map(Campo.class.getResource("map/level2.bmp"));
        nivelMap[2] = new Map(Campo.class.getResource("map/level3.bmp"));
        nivelMap[3] = new Map(Campo.class.getResource("map/level4.bmp"));
        nivelMap[4] = new Map(Campo.class.getResource("map/level5.bmp"));
        obstaculos = new ArrayList<>();
    }

    public boolean collisionField(Bloco pos) {
        for (Bloco obs : obstaculos) {
            if (obs.collision(pos)) {
                return true;
            }
        }
        return false;
    }

    public int getAltura() {
        return altura;
    }

    public int getLargura() {
        return largura;
    }

    public void nextLevel(Bloco pos, int modo) {
        if (modo == Motor.FAST) {
            int linha = sorteia.nextInt(altura);
            int coluna = sorteia.nextInt(altura);
            Bloco bc = new Bloco(linha, coluna, Bloco.BARREIRA);
            int incLin = 0, incCol = 0, direcao = sorteia.nextInt(4);
            if (direcao == Bloco.JOGADOR_RIGHT) {
                incCol = 1;
            } else if (direcao == Bloco.JOGADOR_LEFT) {
                incCol = -1;
            } else if (direcao == Bloco.JOGADOR_UP) {
                incLin = -1;
            } else if (direcao == Bloco.JOGADOR_DOWN) {
                incLin = 1;
            }
            for (int i = 0; i < 10; i++) {
                if (!pos.collision(bc) && !collisionField(bc)) {
                    obstaculos.add(bc);
                }
                linha += incLin;
                coluna += incCol;
                if (linha > altura) {
                    linha = 0;
                } else if (linha < 0) {
                    linha = altura;
                }
                if (coluna > largura) {
                    coluna = 0;
                } else if (coluna < 0) {
                    coluna = largura;
                }
                bc = new Bloco(linha, coluna, Bloco.BARREIRA);
            }
        } else {
            nivel++;
            if (nivel == 6) {
                nivel = 0;
                obstaculos = new ArrayList<>();
            } else {
                obstaculos = nivelMap[nivel-1].getObstaculos();
            }
        }
    }

    public void sortPos(Bloco item) {
        do {
            item.setLinha(sorteia.nextInt(altura));
            item.setColuna(sorteia.nextInt(largura));
        } while (collisionField(item));
    }

    public ArrayList<Bloco> getObstaculos() {
        return obstaculos;
    }

    public void setObstaculos(ArrayList lista) {
        obstaculos = lista;
    }
}
