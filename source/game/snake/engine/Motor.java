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
import java.util.ArrayList;

/**
 *
 * @author Denison
 */
public class Motor {

    //Eventos da execução
    public static final int EV_PARAR = 0;
    public static final int EV_ANDAR = 1;
    public static final int EV_COMER = 2;
    public static final int EV_BATER = 3;
    public static final int EV_NEWLV = 4;
    public static final int EV_FIMJG = 5;

    //Velocidade do jogo
    public static int SLOW = 5;
    public static int NORMAL = 2;
    public static int FAST = 0;
    
    private int pontuacao;
    private int direcao;
    private final ArrayList<Bloco> comida;
    private final Campo campo;
    private Cobra cobra;
    private int vidas;
    private boolean executar;
    private int modo;
    private int rodadas;
    private int velocidade;

    public Motor() {
        cobra = new Cobra(10, 10, 5);
        campo = new Campo(30, 30);
        pontuacao = 0;
        direcao = Bloco.JOGADOR_RIGHT;
        vidas = -1;
        executar = false;
        comida = new ArrayList<Bloco>();
        comida.add(new Bloco(10, 10));
        modo = NORMAL;
        velocidade = 0;
        rodadas = modo*2;
    }

    public void mudarDirecao(int novaDir) {
        direcao = novaDir;
    }

    public int executar() {
        velocidade++;
        if (executar && velocidade >= modo) {
            velocidade = 0;
            //Movimento da cobra
            cobra.mover(direcao);
            //Movimeto para atravesar a tela quando chaga em alguma extremidade
            Bloco pos = cobra.getBlocos().get(0);
            if (pos.getLinha() > campo.getAltura()) {
                pos.setLinha(0);
            } else if (pos.getColuna() > campo.getLargura()) {
                pos.setColuna(0);
            } else if (pos.getLinha() < 0) {
                pos.setLinha(campo.getAltura());
            } else if (pos.getColuna() < 0) {
                pos.setColuna(campo.getLargura());
            }
            //Verifica se a cobra colidiu com proprio corpo ou com obstaculo
            boolean fim = cobra.collision() || campo.collisionField(pos);
            if (fim) {
                vidas--;
                if (vidas <= 0) {
                    executar = false;
                    return EV_FIMJG;
                }
                return EV_BATER;
            }
            //Verifica se a cobra atingiu a comida
            for (Bloco item : comida) {
                if (pos.collision(item)) {
                    pontuacao += 100;
                    if (pontuacao % 1000 == 0) {
                        vidas++;
                    }
                    cobra.comer();
                    rodadas--;
                    if (rodadas <= 0) {
                        campo.nextLevel(pos,modo);
                        rodadas = modo * 5;
                        campo.sortPos(item);
                        return EV_NEWLV;
                    }
                    campo.sortPos(item);
                    return EV_COMER;
                }
            }
            return EV_ANDAR;
        }
        return EV_PARAR;
    }

    public Campo getCampo() {
        return campo;
    }

    public Cobra getJogador() {
        return cobra;
    }

    public ArrayList<Bloco> getComida() {
        return comida;
    }

    public int getVidas() {
        return vidas;
    }

    public void reiniciar() {
        vidas = 1;
        pontuacao = 0;
        cobra = new Cobra(10, 10, 5);
        direcao = Bloco.JOGADOR_RIGHT;
        campo.setObstaculos(new ArrayList());
        velocidade = 0;
        rodadas = modo * 5;
    }

    public void setExecutar(boolean b) {
        executar = b;
    }

    public boolean isExecutar() {
        return executar;
    }

    @Override
    public String toString() {
        return "Vida: " + vidas + "   Pontos: " + pontuacao;
    }

    public String modeChange() {
        if(modo==SLOW) {
            modo=NORMAL;
            return "Normal";
        } else if(modo==NORMAL) {
            modo=FAST;
            return "Fast";
        } else {
            modo=SLOW;
            return "Slow";
        }
    }
}
