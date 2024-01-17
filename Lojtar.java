import java.io.*;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Lojtar implements Serializable {
    public static Labirint harte;
    public static int levizjet = 0;
    public static int nrThesareveTeMbledhura = 0;
    public static int piketFituara=0;
    public static boolean ne_Dalje=false;
    public static boolean nderpreLojen=false;

    int rreshta = 1;
    int kolona = 0;

    public void loja() {
        Scanner input = new Scanner(System.in);
        int opsioni;

        while(true) {
            shfaqMenu();
            System.out.println("Opsioni qe zgjidhni eshte:");
            try {
                opsioni = input.nextInt();

                switch (opsioni) {
                    case 1: {
                        System.out.println("Suksese ne lojen e re!");
                        LojeERe();
                        filloLojen();
                        break;
                    }
                    case 2: {
                        vazhdoLojen();
                        break;
                    }
                    case 3: {
                        shfaqPozicionin();
                        break;
                    }
                    case 4: {
                        ruajLojen();
                        break;
                    }
                    case 5: {
                        System.out.println("Loja e fundit e ruajtur eshte:");
                        ngarkoLojen();
                        vazhdoLojen();
                        break;
                    }
                    case 6: {
                        System.out.println("Shpresojme te keni kaluar nje loje te kendshme! Mirupafshim!");
                        System.out.println(" Keni bere " + levizjet + " levizje.");
                        System.out.println("Keni mbledhur "+harte.nrThesareveTeMbledhura+ " thesare.");
                        System.out.println("Keni grumbulluar "+harte.piketEFituara+ " pike.");
                        System.exit(0);
                    }
                    default: {
                        System.out.println("Numri qe dhate nuk i perket menuse. Ju lutem zgjidhni nje opsion tjeter!");
                        break;
                    }
                }
            }catch(InputMismatchException ex){
                System.out.println("Numer i pasakte. Ju lutem zgjidhni nje numer mes 1 dhe 6!");
            }
        }
    }

    public void filloLojen() {
        harte.afishoHarte();
        levizjet=0;

        while (!lojaKaMbaruar()) {
            String userDirection = perdoruesiLeviz();
            nrThesareveTeMbledhura=harte.nrThesareveTeMbledhura;
            piketFituara=harte.piketEFituara;
            rreshta = harte.getRreshta();
            kolona = harte.getKolona();
            if(ne_Dalje){
                ne_Dalje=false;
                return;
            }else if(nderpreLojen){
                nderpreLojen=false;
                return;
            }

        }
    }

    public void vazhdoLojen() {
        harte.afishoHarte();

        while (!lojaKaMbaruar()) {
            String userDirection = perdoruesiLeviz();
            nrThesareveTeMbledhura=harte.nrThesareveTeMbledhura;
            piketFituara=harte.piketEFituara;
            rreshta = harte.getRreshta();
            kolona = harte.getKolona();

            if(ne_Dalje){
                ne_Dalje=false;
                return;
            }else if(nderpreLojen){
                nderpreLojen=false;
                return;
            }


        }
    }
    public void LojeERe() {
        System.out.println("Mire se erdhet ne Lojen e Labirintit!");
        System.out.println("Pozicioni juaj aktual:");
        harte=new Labirint();
    }

    public static void mesazhiLevizjeve(int levizjet) {

    }
    public  static void mesazhiThesareve(int nrThesareveTeMbledhura){
        System.out.println("Ju keni mbledhur: "+nrThesareveTeMbledhura+ " thesare.");
    }

    public static String perdoruesiLeviz() {
        System.out.println("Per t'u rikthyer te menuja shtypni nje buton cfaredo.");
        Scanner input = new Scanner(System.in);
        String drejtimi = "";

        do {
            if (ne_Dalje=true) {
                System.out.print("Ku do te levizni? (R, L, U, D)    ");
                drejtimi = input.next();
            }

            if (drejtimi.matches("[RLUD]")) {
                mesazhiLevizjeve(++Lojtar.levizjet);

                if (harte.kontrollDjathtas() && drejtimi.equals("R")) {
                    harte.levizDjathtas();
                } else if (harte.kontrollMajtas() && drejtimi.equals("L")) {
                    harte.levizMajtas();
                } else if (harte.kontrollLart() && drejtimi.equals("U")) {
                    harte.levizLart();
                } else if (harte.kontrollPoshte() && drejtimi.equals("D")) {
                    harte.levizPoshte();
                } else {
                    if (ne_Dalje=true) {
                        System.out.println("Ups! Jeni perplasur me nje mur.");
                        System.out.print("Loja Mbaroi!");
                        System.out.println(" Keni bere " + levizjet + " levizje.");
                        System.out.println("Keni mbledhur "+harte.nrThesareveTeMbledhura+ " thesar(e).");
                        System.out.println("Keni grumbulluar "+harte.piketEFituara+ " pike.");
                        return drejtimi;
                    }
                }
                harte.afishoHarte();
                if (harte.arriturNeDalje()) {
                    ne_Dalje=true;
                    System.out.println("Urime ju arritet ne dalje!");
                    System.out.println("Keni bere " + levizjet + " levizje.");
                    System.out.println("Keni mbledhur " + harte.nrThesareveTeMbledhura + " thesar(e).");
                    System.out.println("Keni grumbulluar " + harte.piketEFituara + " pike.");
                    return drejtimi;
                }


            }
        } while (drejtimi.matches("[RLUD]"));

        nderpreLojen=true;

        return drejtimi;
    }

    public void shfaqPozicionin() {
        System.out.println("Pozicioni aktual i lojtarit: Rreshta " + rreshta + ", Kolona " + kolona);
    }

    public boolean lojaKaMbaruar() {
        return harte.arritjeFitores();
    }

    public static void shfaqMenu() {
        System.out.println("Zgjidhni nje nga opsionet e meposhtme:");
        System.out.println("------------------------------------\n");
        System.out.println("1. Fillo nje loje te re.");
        System.out.println("2. Vazhdo lojen.");
        System.out.println("3. Shfaq pozicionin aktual te lojtarit.");
        System.out.println("4. Ruaj progresin e lojes.");
        System.out.println("5. Ngarko nje loje te ruajtur.");
        System.out.println("6. Dil.");
    }

    public static void ngarkoLojen(){
        try(ObjectInputStream ngarkim = new ObjectInputStream(new FileInputStream("labirinti.dat"))){
            harte = (Labirint)ngarkim.readObject();
        }catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    public static void ruajLojen(){
        try(ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream("labirinti.dat"))){
            output.writeObject(harte);
            System.out.print("Loja u ruajt me sukses!");
        }catch(IOException e){
            e.printStackTrace();
        }
    }

}
