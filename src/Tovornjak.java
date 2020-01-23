import java.util.LinkedList;

public class Tovornjak {

    double cena;
    int vrstaSmeti;
    LinkedList<Integer> pot;
    double pobrano;
    double cas;
    double dolzinaPoti;
    double maxCap;

    public Tovornjak (int vs, double mc){
        cena = 10;
        vrstaSmeti = vs;
        pot = new LinkedList<>();
        pobrano = 0;
        cas = 0;
        dolzinaPoti = 0;
        maxCap = mc;
    }

    public double getCena() {
        return cena;
    }

    public void setCena(double cena) {
        this.cena = cena;
    }

    public int getVrstaSmeti() {
        return vrstaSmeti;
    }

    public void setVrstaSmeti(int vrstaSmeti) {
        this.vrstaSmeti = vrstaSmeti;
    }

    public LinkedList<Integer> getPot() {
        return pot;
    }

    public void setPot(LinkedList<Integer> pot) {
        this.pot = pot;
    }

    public double getPobrano() {
        return pobrano;
    }

    public void setPobrano(double pobrano) {
        this.pobrano = pobrano;
    }

    public double getCas() {
        return cas;
    }

    public void setCas(double cas) {
        this.cas = cas;
    }

    public double getDolzinaPoti() {
        return dolzinaPoti;
    }

    public void setDolzinaPoti(double dolzinaPoti) {
        this.dolzinaPoti = dolzinaPoti;
    }
}