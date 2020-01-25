import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Random;

public class SimulatedAnnealing {

    static LinkedList<Mesto> mesta;

    public static void main (String []args) throws IOException {

        double T = 1;
        double Tmin = 0.0001;
        double alpha = 0.9;
        int numIterations = 500;

        mesta = new LinkedList<>();

        double maxCap = read("Problem1.txt");

        int steviloTovornjakovOrganski = (int) (Math.ceil(vsotaSmeti(1) / maxCap));
        int steviloTovornjakovPlastika = (int) Math.ceil(vsotaSmeti(2) / maxCap);
        int steviloTovornjakovPapir = (int) Math.ceil(vsotaSmeti(3) / maxCap);

        Solution fs = firstSolution(steviloTovornjakovOrganski, steviloTovornjakovPlastika, steviloTovornjakovPapir, maxCap);

        fs.cena = fs.vsotaCen();

        System.out.println("Organski:");
        for (int i = 0; i<fs.tOrganski.size(); i++){
            System.out.println(Arrays.toString(fs.tOrganski.get(i).pot.toArray()));
        }
        System.out.println(fs.jeCisto(1));

        System.out.println("\n\nPlastika:");
        for (int i = 0; i<fs.tPlastika.size(); i++){
            System.out.println(Arrays.toString(fs.tPlastika.get(i).pot.toArray()));
        }
        System.out.println(fs.jeCisto(2));

        System.out.println("\n\nPapir:");
        for (int i = 0; i<fs.tPapir.size(); i++){
            System.out.println(Arrays.toString(fs.tPapir.get(i).pot.toArray()));
        }
        System.out.println(fs.jeCisto(3));


        double min = fs.cena;

        while (T > Tmin) {
            for (int i=0;i<numIterations;i++){

                if (fs.cena < min){
                    min = fs.cena;
                }

                Solution nSol = new Solution(mesta, maxCap, fs);

                for (int j = 0; j<500; j++){
                    Solution nSol1 = new Solution(mesta, maxCap, fs);
                    if (nSol1.cena < nSol.cena)
                        nSol = nSol1;
                }

                double ap = Math.pow(Math.E, (fs.cena - nSol.cena)/T);
                if (ap > Math.random())
                    fs = nSol;
            }
            T *= alpha;

        }

        System.out.println("\n\n\n\n========================================================================");
        System.out.println(fs.cena);
        System.out.println("Organski:");
        for (int i = 0; i<fs.tOrganski.size(); i++){
            System.out.println(Arrays.toString(fs.tOrganski.get(i).pot.toArray()));
        }
        System.out.println(fs.jeCisto(1));

        System.out.println("\n\nPlastika:");
        for (int i = 0; i<fs.tPlastika.size(); i++){
            System.out.println(Arrays.toString(fs.tPlastika.get(i).pot.toArray()));
        }
        System.out.println(fs.jeCisto(2));

        System.out.println("\n\nPapir:");
        for (int i = 0; i<fs.tPapir.size(); i++){
            System.out.println(Arrays.toString(fs.tPapir.get(i).pot.toArray()));
        }
        System.out.println(fs.jeCisto(3));

    }

    private static Solution firstSolution(int steviloTovornjakovOrganski, int steviloTovornjakovPlastika, int steviloTovornjakovPapir, double maxCap) {

        int stevec = 0;

        Solution sol = new Solution(steviloTovornjakovOrganski, steviloTovornjakovPlastika, steviloTovornjakovPapir, mesta ,maxCap);

        dodajRandom(sol, 1);
        dodajRandom(sol, 2);
        dodajRandom(sol, 3);

        sol.initVsaMesta();

        return sol;

    }

    public static void dodajRandom(Solution sol, int tip){

        int stevec = 0;

        LinkedList<Tovornjak> tovornjaki = sol.getTovornjake(tip);

        while (sol.jeCisto(tip) > 0){
            int index = getRandomMesto();
            while (tovornjaki.get(stevec).pot.size() > 0 && tovornjaki.get(stevec).pot.get(tovornjaki.get(stevec).pot.size()-1) == index+1){
                index = getRandomMesto();
            }
            tovornjaki.get(stevec).pot.add(index+1);
            sol.setkolicina(index, tip, 0);
            stevec++;
            if (stevec == tovornjaki.size())
                stevec = 0;
        }

        for (int i = 0; i < tovornjaki.size(); i++){
            if (tovornjaki.get(i).pot.get(tovornjaki.get(i).pot.size()-1) != 1)
                tovornjaki.get(i).pot.add(1);
        }

    }

    public static int getRandomMesto (){
        Random rand = new Random();
        int n = rand.nextInt(mesta.size());
        return n;
    }

    public static double vsotaSmeti(int tip){
        double vsota = 0;
        for (int i = 0; i<mesta.size(); i++){
            if (tip == 1){
                vsota += mesta.get(i).organski;
            }
            else if (tip == 2){
                vsota += mesta.get(i).plastika;
            }
            else {
                vsota += mesta.get(i).papir;
            }
        }
        return vsota;
    }

    private static double read(String s) throws IOException {

        File file = new File(s);

        BufferedReader br = new BufferedReader(new FileReader(file));

        String [] line1 = br.readLine().split(",");
        int steviloMest = Integer.parseInt(line1[0]);
        double maxCap = Double.parseDouble(line1[1]);

        for (int i = 0; i<steviloMest; i++){
            String [] line = br.readLine().split(",");
            Mesto m = new Mesto(Integer.parseInt(line[0]), Double.parseDouble(line[1]), Double.parseDouble(line[2]),  Double.parseDouble(line[3]),  Double.parseDouble(line[4]),  Double.parseDouble(line[5]));
            mesta.add(m);
        }

        String st;
        while ((st = br.readLine()) != null){
            String [] line = st.split(",");
            Mesto m1 = mesta.get(Integer.parseInt(line[0])-1);
            Mesto m2 = mesta.get(Integer.parseInt(line[1])-1);
            Razdalja r = new Razdalja(Double.parseDouble(line[4]), Double.parseDouble(line[2]));
            if (Integer.parseInt(line[3]) == 0){
                if (m1.sosedje.get(m2.index) == null) {
                    m1.sosedje.put(m2.index, new LinkedList<Razdalja>());
                }
                m1.sosedje.get(m2.index).add(r);
                m1.sosedjeIndex.add(m2.index);
                if (m2.sosedje.get(m1.index) == null) {
                    m2.sosedje.put(m1.index, new LinkedList<Razdalja>());
                }
                m2.sosedje.get(m1.index).add(r);
                m2.sosedjeIndex.add(m1.index);
            }
            else {
                if (m1.sosedje.get(m2.index) == null) {
                    m1.sosedje.put(m2.index, new LinkedList<Razdalja>());
                }
                m1.sosedje.get(m2.index).add(r);
                m1.sosedjeIndex.add(m2.index);
            }
        }

        return maxCap;

    }

}
