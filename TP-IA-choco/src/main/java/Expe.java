import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.constraints.extension.Tuples;
import org.chocosolver.solver.variables.IntVar;

public class Expe {

	
	public static void writeCSV(String fileName, ArrayList<ArrayList<Integer>>  benchmark) throws IOException {
		File csvFile = new File(fileName);
        FileWriter fileWriter = new FileWriter(csvFile);
        fileWriter.write("\"durete\",\"solvables\",\"Temps(ms)\"\n");
        for (ArrayList<Integer> data : benchmark) {
            StringBuilder line = new StringBuilder();
            for (int i = 0; i < data.size(); i++) {
                line.append("\""+ data.get(i)+"\"");
                if (i != data.size() - 1) {
                    line.append(',');
                }
                
            }
            line.append("\n");
            fileWriter.write(line.toString());
        }
        fileWriter.close();
	}
	
	private static Model lireReseau(BufferedReader in) throws Exception{
			Model model = new Model("Expe");
			int nbVariables = Integer.parseInt(in.readLine());				// le nombre de variables
			int tailleDom = Integer.parseInt(in.readLine());				// la valeur max des domaines
			IntVar []var = model.intVarArray("x",nbVariables,0,tailleDom-1); 	
			int nbConstraints = Integer.parseInt(in.readLine());			// le nombre de contraintes binaires		
			for(int k=1;k<=nbConstraints;k++) { 
				String chaine[] = in.readLine().split(";");
				IntVar portee[] = new IntVar[]{var[Integer.parseInt(chaine[0])],var[Integer.parseInt(chaine[1])]}; 
				int nbTuples = Integer.parseInt(in.readLine());				// le nombre de tuples		
				Tuples tuples = new Tuples(new int[][]{},true);
				for(int nb=1;nb<=nbTuples;nb++) { 
					chaine = in.readLine().split(";");
					int t[] = new int[]{Integer.parseInt(chaine[0]), Integer.parseInt(chaine[1])};
					tuples.add(t);
				}
				model.table(portee,tuples).post();
			}
			in.readLine();
			return model;
	}	
		
			
	public static void main(String[] args) throws Exception{
		
		ArrayList<ArrayList<Integer>> benchmark = new ArrayList<ArrayList<Integer>>();
		for(int i=10;i<120;i+=10) {
			ArrayList<Integer> cspCourant = new ArrayList<Integer>();
			String ficName = "csp"+i+".txt";
			int nbRes=100;
			int nbSatisf = 0;
			int time = 0;
			BufferedReader readFile = new BufferedReader(new FileReader(ficName));
			for(int nb=1 ; nb<=nbRes; nb++) {
				Model model=lireReseau(readFile);
				model.getSolver().limitTime("10s");
				if(model==null) {
					System.out.println("Problème de lecture de fichier !\n");
					return;
				}
				//System.out.println("Réseau lu "+nb+" :\n"+model+"\n\n");
				
				if(model.getSolver().solve()){
					nbSatisf++;
				}
				time = (int)model.getSolver().getTimeCount();
				
			}
			cspCourant.add(i);
			cspCourant.add(nbSatisf);
			cspCourant.add(time);
			benchmark.add(cspCourant);
			
			
			System.out.println("Nombre de réseaux satisfiés: "+nbSatisf+"/"+nbRes+" | "+((float)nbSatisf/(float)nbRes)*100+"% en "+time+"s\n");
		}
		

		writeCSV("test.csv", benchmark);
		return;	
	}
	
}
