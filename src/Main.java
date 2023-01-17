import java.util.Random;
import java.util.Scanner;

public class Main {
    /*
    tendrem

    Jugadors i tipo de jugador, nivell(numero), punts que ha conseguit comensant per 0

            3 tipus de punts de VIDA--> Punts de vida, Punts de vida Maxims(fins els punts de vida que pot recuperar,varia segons el nivell del jugador)

            - Capacitat de atac.
            - Capacitat de defensa.

       Cada vegada que se fa una ronda hem de veure els atributs del jugador i del contrincant. (2 jugadors)

       Cada ronda el jugador tria quina estrategia vol jugar de les 4 estrategias(ATAC, DEFENSA, Engany i MANIOBRA). EL JUGADOR TRIA PRIMER LA ESTRATEGIA antes de veure la de el altre.

                - Si tria Atac o Engany: grau de exit de la estrategia Capacitat de Atac
                - Si tria Defensa o Maniobra Capacitat Def

       ESTATS :Mentres dura el combat (reinicien a cada ronda)
        - Res
        - Dany: el jugador pert una quantitat de punts de vida igual al punts d'exit de l'atacant
        - Guarit: Se protegeix
        - Penalitzat: El lluitador veu penalitzat la capacitat de atac i de defensa d'acord amb l'exit de el contrincant(No pot baixar mai de 1 la capacitat de atac ni de  Defensa.)

     */
    static final int NCOMBATS = 5;

    public static void main(String[] args) {
        Screen.clear();

        Screen.show();
        Jugador jugador1 = Bestiari.triaJugador();
        Jugador jugador2 = Bestiari.jugadorAleatori();


        for (int i = 0; i < NCOMBATS; i++) {
            System.out.println("Començ combat: " + i + 1);
            combat(jugador1, jugador2);
        }
    }


    static void esborraPantalla() {
        for (int i = 0; i < 100; i++) {
            System.out.println();
        }
    }

    static void dibuixaQuadrat(int x,int y,int w,int h) {


        for (int i = 0; i < w; i++) {
            Screen.printChar(i+x,y,'═',Screen.PURPLE);
            Screen.printChar(i+x,y+h, '═',Screen.PURPLE);
        }
        for (int i = 0; i < h; i++) {
            Screen.printChar(0+x,i + y,'║',Screen.PURPLE);
            Screen.printChar(w+x,i + y,'║',Screen.PURPLE);
        }
        Screen.printChar(x,y,'╔',Screen.PURPLE);
        Screen.printChar(x+w,y,'╗',Screen.PURPLE);
        Screen.printChar(x,y+h,'╚',Screen.PURPLE);
        Screen.printChar(x+w,y+h,'╝',Screen.PURPLE);

    }

    static void mostraInfo(Jugador jugador1,Jugador jugador2) {

        mostraInfoJugador(0,0,jugador1);
        mostraInfoJugador(45,0,jugador2);
    }

    private static void mostraInfoJugador(int x, int y, Jugador jugador) {
            dibuixaQuadrat(x,y,25,9);
        for (int i = 0; i < 40; i++) {
            Screen.printChar(i, 2, '-',Screen.GREEN);
            Screen.printChar(i, 11, '-',Screen.GREEN);

        }
        for (int i = 0; i <= 9; i++) {
            Screen.printChar(0, i + 2, '|',Screen.GREEN);
            Screen.printChar(40, i + 2, '|',Screen.GREEN);
        }

        //
        // dibuixaQuadrat(x,y,25,9);

        Screen.print(2+x, 3+y, String.format("Nom: %s (%s)", jugador.nom,jugador.tipus),Screen.CYAN);
        Screen.print(2+x, 4+y,String.format("Tipus: %s" + jugador.tipus,'◬'),Screen.CYAN);
        Screen.print(2+x, 4+y, "Nivell: %s" + jugador.nivell,Screen.CYAN);
        Screen.print(2+x, 4+y, "Punts: %s" + jugador.punts,Screen.CYAN);
        Screen.print(2+x, 4+y,String.format("Vida: %s",dibuixaBarra(jugador.puntsDeVide,'♥')), Screen.GREEN);
        Screen.print(2+x, 4+y, "CapAtac: %s" + jugador.capAtac,Screen.PURPLE);
        Screen.print(2+x, 4+y, "CapDefensa: %s" + jugador.capDefensa,Screen.RED);


    }

    private static Object dibuixaBarra(int n,char c) {

        StringBuilder s = new StringBuilder();
        for (int i = 0; i < n; i++) {
            s.append(c); //s+=c
        }
        return s.toString();
    }

    private static void combat(Jugador jugador1, Jugador jugador2) {
        Scanner scanner = new Scanner(System.in);
        while (!jugador1.haPerdut() && !jugador2.haPerdut()) { //mentres no pert ni un ni el altre.
            Screen.clear();
            mostraInfo(jugador1,jugador2);
            Screen.show();
            System.out.println("---");
            jugador1.mostrarEstat();
            System.out.println("---");
            jugador2.mostrarEstat();
            System.out.println("---");
            System.out.printf("%s, quina estratègia vols? (A,D,E,M): ",
                    jugador1.nom); //%s cambia per el nom de el jugador
            String estraJugador1 = scanner.nextLine();
            String estraJugador2 = jugador2.triaEstrategiaAleatoria();

            int exit1 = tiraSegonsEstrategia(jugador1, estraJugador1);//num cares de la moneda que surt
            int exit2 = tiraSegonsEstrategia(jugador2, estraJugador2);//num cares de la moneda que surt

            mostrarEstrategiaIPunts(estraJugador1,estraJugador2,exit1,exit2,jugador1);
            Screen.show();
            System.out.println("Pitja ENTER per passar");
            scanner.nextLine();
            resolCombat(jugador1, jugador2,
                    estraJugador1, estraJugador2,
                    exit1, exit2);
        }
    }

    private static void mostrarEstrategiaIPunts(String estraJugador1,String estraJugador2,int exit1,int exit2,Jugador jugador) {
        Screen.clear();
        System.out.printf(jugador.nom + "\n" + "EstraJugador: "+  estraJugador1 + "\n exit "+ exit1+"\n");
        //System.out.printf("jugador2"+ Jugador.jugador2 + estraJugador2 + exit2);
    }

    private static void resolCombat(Jugador jugador1, Jugador jugador2, String estraJugador1, String estraJugador2, int exit1, int exit2) {
        if (estraJugador1.equals("A")) { //SI JUGADOR 1 TRIA ATAC "A"
            if (estraJugador2.equals("A")) {
                // 1A 2A
                jugador1.dany(exit2);
                jugador2.dany(exit1);
            } else if (estraJugador2.equals("D")) {
                // 1A 2D
                jugador2.guarit(exit2);
            } else if (estraJugador2.equals("E")) {
                // 1A 2E
                jugador2.dany(exit2);
            } else if (estraJugador2.equals("M")) {
                // 1A 2M
                jugador2.dany(exit1);
            }
        } else if (estraJugador1.equals("D")) {
            if (estraJugador2.equals("A")) {
                // 1D 2A
                jugador1.guarit(exit1);
            } else if (estraJugador2.equals("D")) {
                // 1D 2D
                jugador1.guarit(exit1);
                jugador2.guarit(exit2);
            } else if (estraJugador2.equals("E")) {
                // 1D 2E
                jugador1.dany(exit2 * 2);
            } else if (estraJugador2.equals("M")) {
                // 1D 2M
                jugador1.penalitza(exit2);
            }
        } else if (estraJugador1.equals("E")) {
            if (estraJugador2.equals("A")) {
                // 1E 2A
                jugador1.dany(exit2);
            } else if (estraJugador2.equals("D")) {
                // 1E 2D
                jugador2.dany(exit1 * 2);
            } else if (estraJugador2.equals("E")) {
                // 1E 2E
                jugador1.dany(exit2);
                jugador2.dany(exit1);
            } else if (estraJugador2.equals("M")) {
                // 1E 2M
                jugador1.penalitza(exit2);
            }
        } else if (estraJugador1.equals("M")) {
            if (estraJugador2.equals("A")) {
                // 1M 2A
                jugador1.dany(exit2);
            } else if (estraJugador2.equals("D")) {
                // 1M 2D
                jugador2.penalitza(exit1);
            } else if (estraJugador2.equals("E")) {
                // 1M 2E
                jugador2.penalitza(exit1);
            } else if (estraJugador2.equals("M")) {
                // 1M 2M
                jugador1.penalitza(exit2);
                jugador2.penalitza(exit1);
            }
        }
    }

    private static int tiraSegonsEstrategia(Jugador jugador, String estrategia) {
        if (estrategia.equals("A") || estrategia.equals("E")) {
            return Monedes.tira(jugador.capAtac);
        }
        return Monedes.tira(jugador.capDefensa);
    }
}

