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

    public Solution (LinkedList<Mesto> m, double mc, Solution prevSol){

        maxCap = mc;

        cena = 0;
        mesta = m;
        vsaMestaOrgranski = new double [mesta.size()];
        vsaMestaPlastika = new double [mesta.size()];
        vsaMestaPapir = new double [mesta.size()];
        initVsaMesta();

        setFromPrev(prevSol);

    }

    public void initVsaMesta() {
        for (int i = 0; i<mesta.size(); i++){
            vsaMestaOrgranski[i] = mesta.get(i).getOdpadki(1);
            vsaMestaPlastika[i] = mesta.get(i).getOdpadki(2);
            vsaMestaPapir[i] = mesta.get(i).getOdpadki(3);
        }
    }

    public double costFunction(LinkedList<Tovornjak> tovornjaki, int tip){

        double cost = tovornjaki.size() * 10;

        double [] tab;

        if (tip == 1)
            tab = vsaMestaOrgranski;
        else if (tip == 2)
            tab = vsaMestaPlastika;
        else
            tab = vsaMestaPapir;

        for (int i = 0; i<tovornjaki.size(); i++){
            LinkedList<Integer> pot = tovornjaki.get(i).pot;
            for (int j = 0; j<pot.size()-1; j++){
                Mesto m1 = mesta.get(pot.get(j)-1);
                int index2 = pot.get(j+1);
                if (m1.index == 1) {
                    if(j!=0) {
                        tovornjaki.get(i).setPobrano(0);
                        cost += 10;
                        tovornjaki.get(i).cas += 30;
                    }
                }
                if (m1.sosedjeIndex.contains(index2)){
                    double razdalja = getMinRazdalja(tovornjaki.get(i), m1, index2);
                    if (razdalja != Double.MAX_VALUE){
                        if (tovornjaki.get(i).pobrano + tab[index2-1] <= maxCap){
                            cost += (razdalja * 0.1);
                            tovornjaki.get(i).cas += (razdalja/50*60);
                            if (tab[index2-1] > 0){
                                tovornjaki.get(i).cas += 12;
                                tovornjaki.get(i).pobrano += tab[index2-1];
                                tab[index2-1] = 0;
                            }
                        }
                        else {
                            //cost += 500;
                            cost += (razdalja * 0.1);
                            tovornjaki.get(i).cas += (razdalja/50*60);
                        }
                    }
                    else {
                        cost += 1500;
                    }
                }
                else {
                    cost += 2500;
                }
            }
            if (tovornjaki.get(i).pobrano > 0)
                tovornjaki.get(i).cas += 30;
            if (tovornjaki.get(i).cas > 8*60){
                cost += 8*10;
                cost += (tovornjaki.get(i).cas - 8*60) / 60 * 20;
            }
            else {
                cost += tovornjaki.get(i).cas / 60 * 10;
            }
        }

        return cost;
    }

    private static double getMinRazdalja(Tovornjak tovornjak, Mesto m1, int index2) {

        LinkedList<Razdalja> vsePoti = m1.sosedje.get(index2);

        double min = Double.MAX_VALUE;
        for (int i = 0; i<vsePoti.size(); i++){
            if (vsePoti.get(i).kapaciteta >= tovornjak.pobrano && vsePoti.get(i).velikost < min)
                min = vsePoti.get(i).velikost;
        }

        return min;

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

    public void setFromPrev (Solution prevSol){

        tOrganski = new LinkedList<>();
        tPlastika = new LinkedList<>();
        tPapir = new LinkedList<>();

        kopirajTovornjak(prevSol.tOrganski, tOrganski);
        kopirajTovornjak(prevSol.tPlastika, tPlastika);
        kopirajTovornjak(prevSol.tPapir, tPapir);

        double rand = Math.random();

        if(rand < 0.2){
            double randTip=Math.random();
            if(randTip<0.33) {
                int rTovornjak = (int) (Math.random() * (tOrganski.size()));
                int rMesto= (int) (Math.random() * (mesta.size()));
                int rIndex=(int) (Math.random() * (tOrganski.get(rTovornjak).pot.size()-2)+1);
                tOrganski.get(rTovornjak).pot.add(rIndex, mesta.get(rMesto-1).index);
            }
            else if(randTip>=0.33 && randTip<0.67) {
                int rTovornjak = (int) (Math.random() * (tPlastika.size()));
                int rMesto= (int) (Math.random() * (mesta.size()));
                int rIndex=(int) (Math.random() * (tPlastika.get(rTovornjak).pot.size()-2)+1);
                tPlastika.get(rTovornjak).pot.add(rIndex, mesta.get(rMesto-1).index);
            }
            else {
                int rTovornjak = (int) (Math.random() * (tPapir.size()));
                int rMesto= (int) (Math.random() * (mesta.size()));
                int rIndex=(int) (Math.random() * (tPapir.get(rTovornjak).pot.size()-2)+1);
                tPapir.get(rTovornjak).pot.add(rIndex, mesta.get(rMesto-1).index);
            }
        }
        else if (rand < 0.7){
            double randTip=Math.random();
            if(randTip<0.33 ) {
                int rTovornjak = (int) (Math.random() * (tOrganski.size()));
                if (tOrganski.get(rTovornjak).pot.size()-3 > 0) {
                    int rIndex = (int) (Math.random() * (tOrganski.get(rTovornjak).pot.size() - 3) + 1);
                    tOrganski.get(rTovornjak).pot.remove(rIndex+1);
                    if (!jeSosed(tOrganski.get(rTovornjak).pot.get(rIndex), tOrganski.get(rTovornjak).pot.get(rIndex+1))){
                        Mesto m1 = mesta.get(tOrganski.get(rTovornjak).pot.get(rIndex)-1);
                        int rSosed = (int) (Math.random() * (m1.sosedjeIndex.size() - 1));
                        tOrganski.get(rTovornjak).pot.add(rIndex+1, m1.sosedjeIndex.get(rSosed));
                    }
                }
            }
            else if(randTip>=0.33 && randTip<0.67) {
                int rTovornjak = (int) (Math.random() * (tPlastika.size()));
                if (tPlastika.get(rTovornjak).pot.size()-3 > 0) {
                    int rIndex=(int) (Math.random() * (tPlastika.get(rTovornjak).pot.size()-3)+1);
                    tPlastika.get(rTovornjak).pot.remove(rIndex+1);
                    if (!jeSosed(tPlastika.get(rTovornjak).pot.get(rIndex), tPlastika.get(rTovornjak).pot.get(rIndex+1))){
                        Mesto m1 = mesta.get(tPlastika.get(rTovornjak).pot.get(rIndex)-1);
                        int rSosed = (int) (Math.random() * (m1.sosedjeIndex.size() - 1));
                        tPlastika.get(rTovornjak).pot.add(rIndex+1, m1.sosedjeIndex.get(rSosed));
                    }
                }
            }
            else {
                int rTovornjak = (int) (Math.random() * (tPapir.size()));
                if (tPapir.get(rTovornjak).pot.size()-3 > 0) {
                    int rIndex=(int) (Math.random() * (tPapir.get(rTovornjak).pot.size()-3)+1);
                    tPapir.get(rTovornjak).pot.remove(rIndex+1);
                    if (!jeSosed(tPapir.get(rTovornjak).pot.get(rIndex), tPapir.get(rTovornjak).pot.get(rIndex+1))){
                        Mesto m1 = mesta.get(tPapir.get(rTovornjak).pot.get(rIndex)-1);
                        int rSosed = (int) (Math.random() * (m1.sosedjeIndex.size() - 1));
                        tPapir.get(rTovornjak).pot.add(rIndex+1, m1.sosedjeIndex.get(rSosed));
                    }
                }
            }
        }

    }

    private boolean jeSosed(int index1, int index2) {
        Mesto m1 = mesta.get(index1-1);
        if (m1.sosedjeIndex.contains(index2))
            return true;
        return false;
    }

    private void kopirajTovornjak (LinkedList<Tovornjak> tovornjaki, LinkedList<Tovornjak> destTovornjaki){
        for (int i = 0; i<tovornjaki.size(); i++){
            Tovornjak t = new Tovornjak(tovornjaki.get(i).getVrstaSmeti());
            for (int j = 1; j<tovornjaki.get(i).pot.size(); j++){
                t.pot.add(tovornjaki.get(i).pot.get(j).intValue());
            }
            destTovornjaki.add(t);
        }
    }

}
