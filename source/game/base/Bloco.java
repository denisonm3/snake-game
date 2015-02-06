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
package game.base;

/**
 * Elemento base do jogo
 * Representa jogador, inimigo ou elementos do cenário
 * @author denison
 */
public class Bloco {

    public static final int JOGADOR_UP = 0;
    public static final int JOGADOR_DOWN = 1;
    public static final int JOGADOR_LEFT = 2;
    public static final int JOGADOR_RIGHT = 3;
    public static final int COIN_UP = 4;
    public static final int COIN_DOWN = 5;
    public static final int COIN_LEFT = 6;
    public static final int COIN_RIGHT = 7;
    public static final int INIMIGO_UP = 8;
    public static final int INIMIGO_DOWN = 9;
    public static final int INIMIGO_LEFT = 10;
    public static final int INIMIGO_RIGHT = 11;
    public static final int BARREIRA = 12;

    private int tipo = COIN_UP;

    private int coluna;
    private int linha;

    public Bloco(int linha, int coluna) {
        this.linha = linha;
        this.coluna = coluna;
    }

    public Bloco(int linha, int coluna, int tipo) {
        this.linha = linha;
        this.coluna = coluna;
        this.tipo = tipo;
    }

    //get e set
    public int getColuna() {
        return coluna;
    }

    public void setColuna(int coluna) {
        this.coluna = coluna;
    }

    public int getLinha() {
        return linha;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public void setLinha(int linha) {
        this.linha = linha;
    }

    public void incColuna(int valor) {
        this.coluna += valor;
    }

    public void incLinha(int valor) {
        this.linha += valor;
    }

    public void decColuna(int valor) {
        this.coluna -= valor;
    }

    public void decLinha(int valor) {
        this.linha -= valor;
    }

    public boolean collision(Bloco bc) {
        return linha == bc.linha && coluna == bc.coluna;
    }

    @Override
    public String toString() {
        return super.toString()+" ["+coluna+"] ["+linha+"]\n";
    }
}
