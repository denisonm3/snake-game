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

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

/**
 * This class reads a map from a file .bmp or .txt
 *
 * @author Denison
 */
public class Map {

    private final File mapa;
    private ArrayList<Bloco> obstaculos;

    public Map(File mapa) {
        this.mapa = mapa;
    }
    
    public Map(URL mapa) {
        File temp = null;
        try {
            temp = new File(mapa.toURI());
        } catch (Exception e) {
            try {
                obstaculos = carregarBMP(mapa);
            } catch (IOException ex) {
                System.err.println(ex);
            }
        }
        this.mapa = temp;
    }

    public ArrayList<Bloco> getObstaculos() {
        if (obstaculos == null) {
            obstaculos = carregarObstaculos(mapa);
        }
        return obstaculos;
    }

    public static ArrayList<Bloco> carregarObstaculos(File mapa) {
        try {
            if (mapa.getName().endsWith(".txt")) {
                return carregarTXT(mapa);
            } else if (mapa.getName().endsWith(".bmp")) {
                return carregarBMP(mapa);
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, mapa);
            System.err.printf("Erro na abertura do arquivo: %s.\n", ex.getMessage());
        }
        return new ArrayList<>();
    }

    public static ArrayList<Bloco> carregarBMP(URL mapa) throws IOException {
        ArrayList<Bloco> blocos = new ArrayList<>();
        BufferedImage img = ImageIO.read(mapa);
        int height = img.getHeight();
        int width = img.getWidth();
        for (int linha = 0; linha < height; linha++) {
            for (int coluna = 0; coluna < width; coluna++) {
                int rgb = img.getRGB(coluna, linha);
                if (Color.BLACK.getRGB() == rgb) {
                    Bloco bc = new Bloco(linha, coluna, Bloco.BARREIRA);
                    blocos.add(bc);
                }
            }
        }
        return blocos;
    }

    public static ArrayList<Bloco> carregarBMP(File mapa) throws IOException {
        return carregarBMP(mapa.toURI().toURL());
    }

    public static ArrayList<Bloco> carregarTXT(File mapa) throws FileNotFoundException, IOException {
        return carregarTXT(mapa.toURI().toURL());
    }
    
    public static ArrayList<Bloco> carregarTXT(URL mapa) throws FileNotFoundException, IOException {
        ArrayList<Bloco> blocos = new ArrayList<>();
        Scanner lerArq = new Scanner(mapa.openStream());
        for (int i = 0; lerArq.hasNext(); i++) {
            String linha = lerArq.nextLine();
            if (!linha.startsWith("#")) {
                for (int j = 0; j < linha.length(); j++) {
                    if (linha.charAt(j) == 'B') {
                        Bloco bc = new Bloco(i, j, Bloco.BARREIRA);
                        blocos.add(bc);
                    }
                }
            }
        }
        lerArq.close();
        return blocos;
    }
}
