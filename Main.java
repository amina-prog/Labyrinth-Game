import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        Lojtar lojtar = new Lojtar();
        lojtar.loja();
        String file = "labirinti.dat";

        krijoFile(file);
    }
    public static void krijoFile(String file) {
        try {
            File skedar = new File(file);
            skedar.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
