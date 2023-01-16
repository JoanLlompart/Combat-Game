import java.util.Random;

public class Jugador {
    String nom;
    String tipus;
    int nivell;
    int punts;
    int puntsDeVide;
    int maxPuntsDeVidaMaxims;
    int capAtac;
    int capDefensa;

    public void mostrarEstat() {
        System.out.printf("Nom: %s (%s)\n ",nom , tipus);
        System.out.printf("Nivell: %d\n" +
                "Punts: %d\n" +
                "Punts de vida: %d\n" +
                "Cap Atac: %d\n" +
                "Cap Defensa: %d\n",nivell,punts, puntsDeVide,capAtac,capDefensa);
    }

    public boolean haPerdut() {
        return puntsDeVide <= 0;
    }

    public String triaEstrategiaAleatoria() {
        Random r = new Random();
        String[] estrategies = {"A","D","E","M"};
        int index = r.nextInt(estrategies.length);
        return estrategies[index];
    }

    public void dany(int exit) {
        puntsDeVide -= exit;
        if (puntsDeVide<0) {
            puntsDeVide = 0;
        }
    }

    public void guarit(int exit) {
        puntsDeVide += exit;
        if (puntsDeVide >= maxPuntsDeVidaMaxims) {
            puntsDeVide = maxPuntsDeVidaMaxims;
        }
    }
    public void penalitza (int exit) {
        if (Math.random() < 0.5) {
            this.capDefensa -= exit;
        } else {
            this.capAtac -= exit;
        }
        if (this.capDefensa < 1) capDefensa= 1;
        if (capAtac < 1) capAtac = 1;
    }
}
