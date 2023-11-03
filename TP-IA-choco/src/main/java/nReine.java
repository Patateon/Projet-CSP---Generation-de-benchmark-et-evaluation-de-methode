import org.chocosolver.solver.Model;
import org.chocosolver.solver.constraints.extension.Tuples;
import org.chocosolver.solver.variables.IntVar;
import java.lang.Math;
public class nReine {
    public static void main(String[] args) {

        // Création du modele
        Model model = new Model("n-Reine");

        int nbReine = 16;

        // Création des variables
        IntVar [] nReine = model.intVarArray("x",nbReine,1,nbReine);

        // Création des contraintes
        model.allDifferent(nReine).post();
        for (int i = 0; i < nbReine; i++) {
            for (int j = i; j <nbReine; j++){
                if (i!=j){
                    model.arithm(nReine[i].dist(nReine[j]).intVar(), "!=", Math.abs(i-j)).post();
                }
            }
        }


        // Affichage du réseau de contraintes créé
        System.out.println("*** Réseau Initial ***");
//        System.out.println(model);


        // Calcul de la première solution
        if(model.getSolver().solve()) {
            System.out.println("\n\n*** Première solution ***");
//            System.out.println(model);
        }


        // Calcul de toutes les solutions
        System.out.println("\n\n*** Autres solutions ***");
        while(model.getSolver().solve()) {
//            System.out.println("Sol "+ model.getSolver().getSolutionCount()+"\n"+model);
        }

        // Affichage de l'ensemble des caractéristiques de résolution
        System.out.println("\n\n*** Bilan ***");
        model.getSolver().printStatistics();
    }
}
