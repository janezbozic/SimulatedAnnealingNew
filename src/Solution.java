import java.util.LinkedList;

public class Solution {

    public LinkedList<Tovornjak> tOrganski;
    public LinkedList<Tovornjak> tPlastika;
    public LinkedList<Tovornjak> tPapir;
    double cena;
    public LinkedList<Mesto> mesta;
    public int [] vsaMestaSanje;

    public Solution (LinkedList<Tovornjak> to, LinkedList<Tovornjak> tp, LinkedList<Tovornjak> tpa, LinkedList<Mesto> m){
        tOrganski = to;
        tPlastika = tp;
        tPapir = tpa;

        cena = 0;
        mesta = m;
        vsaMestaSanje = new int [mesta.size()];
    }

    public LinkedList<Tovornjak> gettOrganski() {
        return tOrganski;
    }

    public void settOrganski(LinkedList<Tovornjak> tOrganski) {
        this.tOrganski = tOrganski;
    }

    public LinkedList<Tovornjak> gettPlastika() {
        return tPlastika;
    }

    public void settPlastika(LinkedList<Tovornjak> tPlastika) {
        this.tPlastika = tPlastika;
    }

    public LinkedList<Tovornjak> gettPapir() {
        return tPapir;
    }

    public void settPapir(LinkedList<Tovornjak> tPapir) {
        this.tPapir = tPapir;
    }

    public double getCena() {
        return cena;
    }

    public void setCena(double cena) {
        this.cena = cena;
    }



}
