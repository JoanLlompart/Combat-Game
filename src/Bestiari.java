import java.util.Random;
import java.util.Scanner;

public class Bestiari {

    private static Jugador creaJugador(String nom, String tip) {
        Jugador jugador = new Jugador();
        jugador.nom = nom;
        jugador.tipus = tip;


        if (tip.equals("M")) {
            //Mag
            jugador.capAtac = 5;
            jugador.capDefensa = 10;
        }else if (tip.equals("O")) {
            //Orc
            jugador.capAtac = 10;
            jugador.capDefensa = 5;
        }else if (tip.equals("C")) {
            //Cavaller
            jugador.capAtac = 7;
            jugador.capDefensa = 4;
        }else {
            throw new RuntimeException("Tipus de jugador no reconegut");
        }

        jugador.nivell= 1;
        jugador.punts = 0;
        jugador.puntsDeVide = 10;
        jugador.maxPuntsDeVidaMaxims = 10;
        return jugador;
    }

    static Jugador triaJugador() { //ha de tornar jugador

        Scanner scanner = new Scanner(System.in);
        System.out.println("Done'm el teu nom");
        String nom = scanner.nextLine();
        System.out.println("Escriu el tipus de personatge (M,O,C):");
        String tipus = scanner.nextLine();
        //Jugador jugador = new Jugador();
        return creaJugador(nom,tipus);
    }
    static Jugador jugadorAleatori() {
        Random r = new Random();
        String nom = "ROBOT";
        String[] tipus = {"M","O","C"};
        int index = r.nextInt(100) % tipus.length;
        return creaJugador(nom,tipus[index]);
    }

}
