import org.chocosolver.solver.Model;
import org.chocosolver.solver.constraints.extension.Tuples;
import org.chocosolver.solver.variables.IntVar;

public class ZebreIntension {

    public static void main(String[] args) {

        // Création du modele
        Model model = new Model("Zebre");


        // Création des variables
        IntVar blu = model.intVar("Blue", 1, 5);	// blu est une variable entière dont le nom est "Blue" est le domaine [1,5]
        IntVar gre = model.intVar("Green", 1, 5);
        IntVar ivo = model.intVar("Ivory", 1, 5);
        IntVar red = model.intVar("Red", 1, 5);
        IntVar yel = model.intVar("Yellow", 1, 5);

        IntVar eng = model.intVar("English", 1, 5);
        IntVar jap = model.intVar("Japanese", 1, 5);
        IntVar nor = model.intVar("Norwegian", 1, 5);
        IntVar spa = model.intVar("Spanish", 1, 5);
        IntVar ukr = model.intVar("Ukrainian", 1, 5);

        IntVar cof = model.intVar("Coffee", 1, 5);
        IntVar mil = model.intVar("Milk", 1, 5);
        IntVar ora = model.intVar("Orange Juice", 1, 5);
        IntVar tea = model.intVar("Tea", 1, 5);
        IntVar wat = model.intVar("Water", 1, 5);

        IntVar dog = model.intVar("Dog", 1, 5);
        IntVar fox = model.intVar("Fox", 1, 5);
        IntVar hor = model.intVar("Horse", 1, 5);
        IntVar sna = model.intVar("Snail", 1, 5);
        IntVar zeb = model.intVar("Zebra", 1, 5);

        IntVar che = model.intVar("Chesterfield", 1, 5);
        IntVar koo = model.intVar("Kool", 1, 5);
        IntVar luc = model.intVar("Lucky Strike", 1, 5);
        IntVar old = model.intVar("Old Gold", 1, 5);
        IntVar par = model.intVar("Parliament", 1, 5);


        // Création des contraintes
        int [][] tEq = new int[][] {{1,1},{2,2},{3,3},{4,4},{5,5}};
        Tuples tuplesAutorises = new Tuples(tEq,true);		// création de Tuples de valeurs autorisés
        Tuples tuplesInterdits = new Tuples(tEq,false);		// création de Tuples de valeurs interdits

        IntVar[] houses = {blu, gre, ivo, red, yel};
        IntVar[] food = {eng, jap, nor, spa, ukr};
        IntVar[] pets = {cof, mil, ora, tea, wat};
        IntVar[] inhabitants = {dog, fox, hor, sna, zeb};
        IntVar[] cigarets = {che, koo, luc, old, par};

        model.allDifferent(houses).post();
        model.allDifferent(food).post();
        model.allDifferent(pets).post();
        model.allDifferent(inhabitants).post();
        model.allDifferent(cigarets).post();

        /************************************************************************
         *                                                                      *
         * Compléter en ajoutant les contraintes modélisant les phrases 2 à 15  *
         *
         * 2. The Englishman lives in the red house.
         * 3. The Spaniard owns the dog.
         * 4. Coffee is drunk in the green house.
         * 5. The Ukrainian drinks tea.
         * 6. The green house is immediately to the right of the ivory house.
         * 7. The Old Gold smoker owns snails.
         * 8. Kools are smoked in the yellow house.
         * 9. Milk is drunk in the middle house.
         * 10. The Norwegian lives in the first house.
         * 11. The man who smokes Chesterfields lives in the house next to the man with the fox.
         * 12. Kools are smoked in the house next to the house where the horse is kept. [should be "... a house ...",
         * see discussion below]
         * 13. The Lucky Strike smoker drinks orange juice.
         * 14. The Japanese smokes Parliaments.
         * 15. The Norwegian lives next to the blue house.
         *                                                                      *
         ************************************************************************/

        model.arithm(eng, "=", red).post(); // 2.
        model.arithm(spa, "=", dog).post(); // 3.
        model.arithm(cof, "=", gre).post(); // 4.
        model.arithm(ukr, "=", tea).post(); // 5.

        model.arithm(gre, "-",ivo, "=", 1).post(); // 6.

        model.arithm(old, "=", sna).post(); // 7.
        model.arithm(koo, "=", yel).post(); // 8.
        model.arithm(mil, "=", 3).post(); // 9.
        model.arithm(nor, "=", 1).post(); // 10.
        model.distance(che, fox, "=", 1).post(); // 11.
        model.distance(koo, hor, "=", 1).post(); // 12.
        model.arithm(luc, "=", ora).post(); // 13.
        model.arithm(jap, "=", par).post(); // 14.
        model.distance(nor, blu,  "=", 1).post(); // 15.



        // Affichage du réseau de contraintes créé
        System.out.println("*** Réseau Initial ***");
        System.out.println(model);


        // Calcul de la première solution
        if(model.getSolver().solve()) {
            System.out.println("\n\n*** Première solution ***");
            System.out.println(model);
        }


        /*
        // Calcul de toutes les solutions
        System.out.println("\n\n*** Autres solutions ***");
        while(model.getSolver().solve()) {
            System.out.println("Sol "+ model.getSolver().getSolutionCount()+"\n"+model);
        }
        */

        // Affichage de l'ensemble des caractéristiques de résolution
        System.out.println("\n\n*** Bilan ***");
        model.getSolver().printStatistics();
    }
}
