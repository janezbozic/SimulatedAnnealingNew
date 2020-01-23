import java.util.LinkedList;

public class Solution {

    public LinkedList<Tovornjak> tOrganski;
    public LinkedList<Tovornjak> tPlastika;
    public LinkedList<Tovornjak> tPapir;
    double cena;
    public LinkedList<Mesto> mesta;
    public double [] vsaMestaOrgranski;
    public double [] vsaMestaPlastika;
    public double [] vsaMestaPapir;
    public double maxCap;

    public Solution (int to, int tp, int tpa, LinkedList<Mesto> m, double mc){
        tOrganski = initTovornjaki(to, 1);
        tPlastika = initTovornjaki(tp, 2);
        tPapir = initTovornjaki(tpa, 3);
        maxCap = mc;

        cena = 0;
        mesta = m;
        vsaMestaOrgranski = new double [mesta.size()];
        vsaMestaPlastika = new double [mesta.size()];
        vsaMestaPapir = new double [mesta.size()];
        initVsaMesta();
    }

    private void initVsaMesta() {
        for (int i = 0; i<mesta.size(); i++){
            vsaMestaOrgranski[i] = mesta.get(i).getOdpadki(1);
            vsaMestaPlastika[i] = mesta.get(i).getOdpadki(2);
            vsaMestaPapir[i] = mesta.get(i).getOdpadki(3);
        }
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

    public LinkedList<Tovornjak> getTovornjake (int tip){
        if (tip == 1)
            return tOrganski;
        if (tip == 2)
            return tPlastika;
        return tPapir;
    }

    public boolean jeCisto (int tip){

        for (int i = 0; i<vsaMestaOrgranski.length; i++){
            if (tip == 1 && vsaMestaOrgranski[i] > 0)
                return false;
            if (tip == 2 && vsaMestaPlastika[i] > 0)
                return false;
            if (tip == 3 && vsaMestaPapir[i] > 0)
                return false;
        }

        return true;
    }

    public LinkedList<Tovornjak> initTovornjaki(int stevilo, int tip){

        LinkedList<Tovornjak> tovornjaki = new LinkedList<>();

        for (int i = 0; i<stevilo; i++) {
            Tovornjak t = new Tovornjak(tip);
            tovornjaki.add(t);
        }

        return tovornjaki;
    }

    public void setkolicina(int index, int tip, double vrednost){
        if (tip == 1)
            vsaMestaOrgranski[index] = vrednost;
        else if (tip == 2)
            vsaMestaPlastika[index] = vrednost;
        else
            vsaMestaPapir[index] = vrednost;
    }

}
