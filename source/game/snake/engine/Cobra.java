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
import static game.base.Bloco.JOGADOR_DOWN;
import static game.base.Bloco.JOGADOR_LEFT;
import static game.base.Bloco.JOGADOR_RIGHT;
import static game.base.Bloco.JOGADOR_UP;
import java.util.ArrayList;

/**
 *
 * @author Denison
 */
public class Cobra {

    private int movAnt;
    private final ArrayList<Bloco> cobra;

    public Cobra(int posLinha, int posColuna, int tamanho) {
        cobra = new ArrayList(1);
        cobra.add(0, new Bloco(posLinha, posColuna, JOGADOR_RIGHT));
        for (int i = 0; i < tamanho; i++) {
            comer();
        }
        movAnt = -2;
    }

    public void mover(int direcao) {
        int linha = 0, coluna = 0;
        if (direcao == JOGADOR_UP && movAnt == JOGADOR_DOWN) {
            direcao = JOGADOR_DOWN;
        } else if (direcao == JOGADOR_DOWN && movAnt == JOGADOR_UP) {
            direcao = JOGADOR_UP;
        } else if (direcao == JOGADOR_LEFT && movAnt == JOGADOR_RIGHT) {
            direcao = JOGADOR_RIGHT;
        } else if (direcao == JOGADOR_RIGHT && movAnt == JOGADOR_LEFT) {
            direcao = JOGADOR_LEFT;
        } else {
            movAnt = direcao;
        }
        switch (direcao) {
            case JOGADOR_LEFT:
                linha = 0;
                coluna = -1;
                break;
            case JOGADOR_UP:
                linha = -1;
                coluna = 0;
                break;
            case JOGADOR_DOWN:
                linha = 1;
                coluna = 0;
                break;
            case JOGADOR_RIGHT:
                linha = 0;
                coluna = 1;
                break;
        }
        for (int i = cobra.size() - 1; i > 0; i--) {
            cobra.get(i).setLinha(cobra.get(i - 1).getLinha());
            cobra.get(i).setColuna(cobra.get(i - 1).getColuna());
            cobra.get(i).setTipo(cobra.get(i - 1).getTipo());
        }
        cobra.get(0).incLinha(linha);
        cobra.get(0).incColuna(coluna);
        cobra.get(0).setTipo(direcao);
    }

    public void comer() {
        int linha, coluna, direcao;
        linha = cobra.get(cobra.size() - 1).getLinha();
        coluna = cobra.get(cobra.size() - 1).getColuna();
        direcao = cobra.get(cobra.size() - 1).getTipo();
        switch (direcao) {
            case JOGADOR_RIGHT:
                coluna -= 1;
                break;
            case JOGADOR_DOWN:
                linha -= 1;
                break;
            case JOGADOR_UP:
                linha += 1;
                break;
            case JOGADOR_LEFT:
                coluna += 1;
                break;
        }
        cobra.add(new Bloco(linha, coluna, direcao));
    }

    public boolean collision() {
        for (int i = 1; i < cobra.size(); i++) {
            if (cobra.get(i).collision(cobra.get(0))) {
                return true;
            }
        }
        return false;
    }

    public ArrayList<Bloco> getBlocos() {
        return cobra;
    }
}
