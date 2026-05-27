package persistencia;

import jogo.Jogo;
import java.io.*;

public class SaveGame {

    private static final String ARQUIVO = "partida.dat";

    public static void salvar(Jogo jogo) {
        try {
            ObjectOutputStream out = new ObjectOutputStream(
                new FileOutputStream(ARQUIVO)
            );
            out.writeObject(jogo);
            out.close();
            System.out.println("Partida salva!");
        } catch (IOException e) {
            System.out.println("Erro ao salvar: " + e.getMessage());
        }
    }

    public static Jogo carregar() {
        try {
            ObjectInputStream in = new ObjectInputStream(
                new FileInputStream(ARQUIVO)
            );
            Jogo jogo = (Jogo) in.readObject();
            in.close();
            return jogo;
        } catch (Exception e) {
            System.out.println("Nenhuma partida salva encontrada.");
            return null;
        }
    }
}