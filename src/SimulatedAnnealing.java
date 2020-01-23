import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

public class SimulatedAnnealing {

    static LinkedList<Mesto> mesta;

    public static void main (String [] args) throws IOException {

        double T = 1;
        double Tmin = .0001;
        double alpha = 0.9;
        int numIterations = 10000;

        mesta = new LinkedList<>();

        double maxCap = read("Problem1.txt");

        int steviloTovornjakovOrganski = (int) (Math.ceil(vsotaSmeti(1) / maxCap));
        int steviloTovornjakovPlastika = (int) Math.ceil(vsotaSmeti(2) / maxCap);
        int steviloTovornjakovPapir = (int) Math.ceil(vsotaSmeti(3) / maxCap);

        Solution fistSolution = firstSolution(steviloTovornjakovOrganski, steviloTovornjakovPlastika, steviloTovornjakovPapir);


    }

    private static Solution firstSolution(int steviloTovornjakovOrganski, int steviloTovornjakovPlastika, int steviloTovornjakovPapir) {



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
