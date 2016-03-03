package connect5.ia;

/*
 * Si vous utilisez Java, vous devez modifier ce fichier-ci.
 *
 * Vous pouvez ajouter d'autres classes sous le package connect5.ia.
 *
 * Simon Drouin     (DROS04078908)
 * Prénom Nom    (CODE00000002)
 */

import connect5.Grille;
import connect5.Joueur;
import connect5.Position;
import java.util.ArrayList;
import java.util.Random;


public class JoueurArtificiel implements Joueur {

    private final Random random = new Random();

    /**
     * Voici la fonction à modifier.
     * Évidemment, vous pouvez ajouter d'autres fonctions dans JoueurArtificiel.
     * Vous pouvez aussi ajouter d'autres classes, mais elles doivent être
     * ajoutées dans le package connect5.ia.
     * Vous de pouvez pas modifier les fichiers directement dans connect., car ils seront écrasés.
     *
     * @param grille Grille reçu (état courrant). Il faut ajouter le prochain coup.
     * @param delais Délais de rélexion en temps réel.
     * @return Retourne le meilleur coup calculé.
     */
    @Override
    public Position getProchainCoup(Grille grille, int delais) {
        // retourne une value et non un choix pour linstant
        int[] choix = alphaBeta(0, grille, Integer.MIN_VALUE, Integer.MAX_VALUE, getCasesVides(grille).get(0));
        assert(choix[0] != -1);

        int nbCol = grille.getData()[0].length;

        return new Position(choix[0] / nbCol, choix[0] % nbCol);
    }

    @Override
    public String getAuteurs() {
        return "Simon Drouin (DROS04078908)  et  Prénom2 Nom2 (CODE00000002)";
    }


    // noJoueur est 0 ou 1.  (0 ==> max, 1 ==> min)
    // Pseudo code de wiki, on fera la version NegaMax si on desire simplifier le code.
    // https://fr.wikipedia.org/wiki/%C3%89lagage_alpha-b%C3%AAta
    private int[] alphaBeta(int noJoueur, Grille grille, int alpha, int beta, int noCaseVide){
        if(grille.nbLibre() == 0) {
            System.out.println("VALUEEEEE");
            return new int[]{noCaseVide, evaluate(noCaseVide)};
        }

        if(noCaseVide != -1) System.out.println("l : " + noCaseVide / grille.getData()[0].length + "c : " + noCaseVide % grille.getData()[0].length);

        // J'ai gardé le arraylist du prof, pour éventuellement faire un élagage des noeuds à visiter.
        // Pour l'instant je met toutes les cases vides.
        ArrayList<Integer> casesVides = getCasesVides(grille);
        int bestValue = Integer.MIN_VALUE;

        for(int i = 0; i < casesVides.size(); i++){
            Grille grilleProchainCoup = grille.clone();
            grilleProchainCoup.set(casesVides.get(i) / grille.getData()[0].length, casesVides.get(i) % grille.getData()[0].length, (noJoueur+1)%2);

            int[] coup = alphaBeta((noJoueur+1)%2, grilleProchainCoup, -beta, -alpha, casesVides.get(i));
            coup[1] = -coup[1];

            if(coup[1] > bestValue) {
                bestValue = coup[1];

                if(bestValue > alpha) {
                    alpha = bestValue;

                    if(alpha >= beta) return new int[]{noCaseVide, bestValue};
                }
            }
        }

        return new int[]{noCaseVide, bestValue};
    }

    private int evaluate(int coup){
        // TODO
        return 1;
    }

    private ArrayList<Integer> getCasesVides(Grille grille){
        ArrayList<Integer> casesVides = new ArrayList<Integer>();
        int nbcol = grille.getData()[0].length;
        for(int l=0;l<grille.getData().length;l++)
            for(int c=0;c<nbcol;c++)
                if(grille.getData()[l][c]==0)
                    casesVides.add(l*nbcol+c);

        return casesVides;
    }
}
