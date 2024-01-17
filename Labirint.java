import java.io.*;
import java.util.Random;
import java.util.Stack;

public class Labirint implements Serializable {
    private char[][] harte;

    public int nrThesareveTeMbledhura = 0;
    public int piketEFituara=0;
    private int rreshta;
    private int kolona;
    int daljeRresht;
    int daljeKolone;
    private static int madhesiaX = 15;
    private static int madhesiaY = 30;
    int murRresht;
    int murKolone;

    public Labirint() {
        rreshta = 1;
        kolona = 0;
        harte = gjeneroLabirint();

        harte[rreshta][kolona] = 'X';
        Random random = new Random();


    }
    public Labirint(int rreshta, int kolona, char[][] harte) {
        this.rreshta = rreshta;
        this.kolona = kolona;
        this.harte = harte;

        this.harte[this.rreshta][this.kolona] = 'X';
        Random random = new Random();


        Random r = new Random();
        murKolone = r.nextInt(madhesiaY);
        murRresht = r.nextInt(madhesiaX);
        this.harte[murRresht][murKolone] = '|';
    }
    public char[][] gjeneroLabirint() {
        char[][] labirint = new char[madhesiaX][madhesiaY];


        for (int i = 0; i < madhesiaX; i++) {
            for (int j = 0; j < madhesiaY; j++) {
                labirint[i][j] = '|';
            }
        }

        Stack<int[]> stack = new Stack<>();


        int startX = 1 + 2 * (int) (Math.random() * ((madhesiaX - 1) / 2));
        int startY = 1 + 2 * (int) (Math.random() * ((madhesiaY - 1) / 2));

        stack.push(new int[]{startX, startY});

        while (!stack.isEmpty()) {
            int[] current = stack.pop();
            int currentX = current[0];
            int currentY = current[1];
            labirint[currentX][currentY] = '.';


            int[][] directions = {{0, 2}, {2, 0}, {0, -2}, {-2, 0}};
            for (int i = 0; i < directions.length; i++) {
                int randIndex = i + (int) (Math.random() * (directions.length - i));
                int[] temp = directions[i];
                directions[i] = directions[randIndex];
                directions[randIndex] = temp;
            }

            for (int[] direction : directions) {
                int newX = currentX + direction[0];
                int newY = currentY + direction[1];

                if (newX > 0 && newX < madhesiaX && newY > 0 && newY < madhesiaY && labirint[newX][newY] == '|') {
                    labirint[newX][newY] = '.';
                    stack.push(new int[]{newX, newY});
                    labirint[(currentX + newX) / 2][(currentY + newY) / 2] = '.';
                }
            }
        }


        rreshta = 1;
        kolona = 0;
        labirint[rreshta][kolona] = 'X';

        Random random = new Random();
        daljeRresht = madhesiaX - 2;
        daljeKolone = madhesiaY - 1;
        labirint[daljeRresht][daljeKolone] = '0';


        for (int i = 0; i < 15; i++) {
            int treasureRresht, treasureKolone;
            do {
                treasureRresht = random.nextInt(madhesiaX);
                treasureKolone = random.nextInt(madhesiaY);
            } while (labirint[treasureRresht][treasureKolone] != '.');
            labirint[treasureRresht][treasureKolone] = '*';
        }

        return labirint;
    }
    public boolean kontrollPerMur(String drejtimi) {
        if (drejtimi.equals("R")) return kontrollDjathtas();
        else if (drejtimi.equals("L")) return kontrollMajtas();

        else if (drejtimi.equals("U")) return kontrollLart();

        else return kontrollPoshte();

    }
    public void levizLojtar(String drejtimi) {
        if (drejtimi.equals("R") && kontrollDjathtas()) {
            leviz(0, 1);
        } else if (drejtimi.equals("L") && kontrollMajtas()) {
            leviz(0, -1);
        } else if (drejtimi.equals("U") && kontrollLart()) {
            leviz(-1, 0);
        } else if (drejtimi.equals("D") && kontrollPoshte()) {
            leviz(1, 0);
        }
    }
    public void afishoHarte() {
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 30; j++) {
                System.out.print(harte[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public boolean Levizje(int levizRresht, int levizKolone) {
        int newRr = rreshta + levizRresht;
        int newK = kolona + levizKolone;

        if (newRr >= 0 && newRr < 15 && newK >= 0 && newK < 30 && harte[newRr][newK] != '|') {
            return true;
        } else {
            return false;
        }
    }

    public boolean kontrollDjathtas() {
        return Levizje(0, 1);
    }

    public boolean kontrollMajtas() {
        return Levizje(0, -1);
    }

    public boolean kontrollLart() {
        return Levizje(-1, 0);
    }

    public boolean kontrollPoshte() {
        return Levizje(1, 0);
    }

    private void leviz(int levizrreshtin, int levizkolonen) {
        int newRow = rreshta + levizrreshtin;
        int newCol = kolona + levizkolonen;

        if (Levizje(levizrreshtin, levizkolonen)) {
            if (harte[newRow][newCol] == '*') {
                nrThesareveTeMbledhura++;
                Lojtar.mesazhiThesareve(nrThesareveTeMbledhura);
                piketEFituara+=5;
                hiqThesar();
            }

            harte[rreshta][kolona] = '&';
            rreshta = newRow;
            kolona= newCol;
            harte[rreshta][kolona] = 'X';

            if (arriturNeDalje()) {
                System.out.println("Urime, keni arritur deri ne dalje!");
            }
        }
    }

    public void levizDjathtas() {
        leviz(0, 1);
    }

    public void levizMajtas() {
        leviz(0, -1);
    }

    public void levizLart() {
        leviz(-1, 0);
    }

    public void levizPoshte() {
        leviz(1, 0);
    }

    private boolean lojaKaMbaruar = false;

    public boolean arritjeFitores() {
        return lojaKaMbaruar || (rreshta == daljeRresht && kolona == daljeKolone);
    }

    public boolean arriturNeDalje() {
        return rreshta == daljeRresht && kolona == daljeKolone;
    }


    public void hiqThesar() {
        harte[rreshta][kolona] = '.';
    }

    public char[][] getHarte() {
        return harte;
    }

    public int getRreshta(){
        return rreshta;
    }

    public int getKolona(){
        return kolona;
    }
    public static Labirint ruajLojen(char[][] labirint,String emriFile)throws IOException{
        try (PrintWriter file=new PrintWriter(new FileWriter("ruajtja.dat"))){
            for (int i=0;i<madhesiaX;i++){
                for (int j=0;j<madhesiaY;j++){
                    file.print(labirint[i][j]+ " ");
                }
                file.println();
            }

        }catch (IOException e){
            System.out.println("Gabim gjate ruajtjes ne file");
        }
        return  null;

    }



}



