package connect5.ia;
import connect5.ia.UtilitaireGrille;

/*
 * Si vous utilisez Java, vous devez modifier ce fichier-ci.
 *
 * Vous pouvez ajouter d'autres classes sous le package connect5.ia.
 *
 * Simon Drouin     (DROS04078908)
 * Mathy Scott      (SCOM15079104)
 */

import connect5.Grille;
import connect5.Joueur;
import connect5.Position;

import java.util.*;

public class JoueurArtificiel implements Joueur {
    private int PROFONDEUR_MAX = 5;
    private int DELAIS = -1;
    private long DEBUT_TIMER = 0;
    private int TIMER_CONTINUE = 0;
    private int TIMER_STOP = 1;
    private int MAX_VALUE = 100000000;
    private int MIN_VALUE = -100000000;

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
        if(DELAIS ==-1){ DELAIS = delais-50; }
        DEBUT_TIMER = System.currentTimeMillis();

        int nbCol = grille.getData()[0].length;

        DEBUT_TIMER = System.currentTimeMillis();

        ArrayList<Integer> casesVides = getCasesVides(grille);
        int noJoueur = ((grille.getSize() - casesVides.size()) % 2) + 1;

        if(grille.nbLibre() == grille.getSize()){
            int coup = getCaseAleatoire(grille);
            return new Position(coup / nbCol, coup % nbCol);
        }
        int[] choix = negaMaxInit(noJoueur, grille);

        return new Position(choix[0] / nbCol, choix[0] % nbCol);
    }

    public int getAdversaire(int noJoueur){
        return noJoueur == 1 ? 2 : 1;
    }

    @Override
    public String getAuteurs() {
        return "Simon Drouin (DROS04078908)  et  Mathy Scott (SCOM15079104)";
    }

    private int[] negaMaxInit(int noJoueur, Grille grille) {
        return negaMax(noJoueur, grille, MIN_VALUE, MAX_VALUE, -1, 0);
    }

    // noJoueur est 0 ou 1.  (0 ==> max, 1 ==> min)
    // Pseudo code de wiki, la version NegaMax
    // https://fr.wikipedia.org/wiki/%C3%89lagage_alpha-b%C3%AAta
    //
    // Je retourne le format [numeroCase , valeur, flag_du_timer] pour éventuellement garder une trace des noeuds
    // afin de pouvoir retourner une valeur pertinente en temps réel.
    private int[] negaMax(int noJoueur, Grille grille, int alpha, int beta, int positionCoup, int profondeur){

        if(System.currentTimeMillis() - DEBUT_TIMER > DELAIS) {
            //DEBUG
            System.out.println("TIMEOUT profondeur: " + profondeur);
            return new int[]{positionCoup, evaluate(grille, noJoueur)/profondeur, TIMER_STOP};
        }

        if (profondeur == PROFONDEUR_MAX) return new int[]{positionCoup, evaluate(grille,noJoueur)/profondeur, TIMER_CONTINUE};
        profondeur++;

        TreeSet<int[]> casesVides = getOrderedCasesVides(grille, noJoueur);
        Iterator<int[]> it_casesVides = casesVides.iterator();

        int[] meilleurCoup = {positionCoup, MIN_VALUE, TIMER_CONTINUE};

        while(it_casesVides.hasNext()){
            int caseVide = it_casesVides.next()[0];

            Grille grilleProchainCoup = grille.clone();
            grilleProchainCoup.set(caseVide / grille.getData()[0].length, caseVide % grille.getData()[0].length, noJoueur);

            if(UtilitaireGrille.gagneOuNul(grilleProchainCoup, caseVide)){
                return new int[]{caseVide, MAX_VALUE/profondeur, TIMER_CONTINUE};
            }

            int[] coup = negaMax(getAdversaire(noJoueur), grilleProchainCoup, -beta, -alpha, caseVide, profondeur);
            coup[1] = -coup[1];

            if (coup[2] == TIMER_STOP) {
                coup[0] = caseVide;
                meilleurCoup[2] = TIMER_STOP;

                return (meilleurCoup[1] >= coup[1] ? meilleurCoup : coup);
            }


            if(coup[1] >= meilleurCoup[1]) {
                meilleurCoup = new int[]{caseVide, coup[1], TIMER_CONTINUE};

                if(meilleurCoup[1] > alpha) {
                    alpha = meilleurCoup[1];

                    if(alpha >= beta) return meilleurCoup;
                }
            }
        }

        return meilleurCoup;
    }

    private int getCaseAleatoire(Grille grille){

        int nbCols      = UtilitaireGrille.getNbCols(grille);
        int nbLigs      = UtilitaireGrille.getNbLigs(grille);
        int minCol      = nbCols/3;
        int maxCol      = minCol+minCol;
        int minLig      = nbLigs/3;
        int maxLig      = minLig+minLig;
        Random random   = new Random();
        int l           = random.nextInt(maxLig-minLig+1) +minLig;
        int c           = random.nextInt(maxCol-minCol+1) + minCol;
        int coup        = l*nbCols+c;

        return coup;
    }

    private TreeSet<int[]> getOrderedCasesVides(Grille grille, int noJoueur) {
        int valeurGrille = evaluate(grille, noJoueur);

        TreeSet<Integer> casesVides = UtilitaireGrille.getCasesVidesRadius2(grille);
        Iterator<Integer> it_casesVides = casesVides.iterator();

        int[][] evaluations = new int[casesVides.size()][2];

        int i = 0;
        while(it_casesVides.hasNext()){

            Grille grilleTmp = grille.clone();
            int caseVide = it_casesVides.next();

            grilleTmp.set(caseVide / grille.getData()[0].length, caseVide % grille.getData()[0].length, noJoueur);
            evaluations[i][0] = caseVide;
            evaluations[i][1] = Math.abs(valeurGrille - evaluate(grilleTmp, noJoueur));

            i++;
        }

        TreeSet<int[]> orderedCasesVides = new TreeSet<int[]>(new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return o2[1] - o1[1];
            }
        });

        for (i = 0; i < evaluations.length; i++){
            orderedCasesVides.add(evaluations[i]);
        }

        return orderedCasesVides;
    }

    private int evaluate(Grille grille, int noJoueur){
        ArrayList<ArrayList<int[]>> blocsVerticaux = UtilitaireGrille.construireBlocsVerticaux(grille);
        ArrayList<ArrayList<int[]>> blocsHorizontaux = UtilitaireGrille.construireBlocsHorizontaux(grille);
        ArrayList<ArrayList<int[]>> blocsDiagonauxDescedants = UtilitaireGrille.construireBlocsDiagonauxDescendants(grille);
        ArrayList<ArrayList<int[]>> blocsDiagonauxAscendants = UtilitaireGrille.construireBlocsDiagonauxAscendants(grille);

        int pertinenceV = UtilitaireGrille.determinerPertinence(blocsVerticaux, noJoueur);
        int pertinenceAdvV = UtilitaireGrille.determinerPertinence(blocsVerticaux,getAdversaire(noJoueur)) - 2 ;

        int pertinenceH = UtilitaireGrille.determinerPertinence(blocsHorizontaux, noJoueur);
        int pertinenceAdvH = UtilitaireGrille.determinerPertinence(blocsHorizontaux,getAdversaire(noJoueur)) - 4;

        int pertinenceDD = UtilitaireGrille.determinerPertinence(blocsDiagonauxDescedants,noJoueur);
        int pertinenceDDAdv = UtilitaireGrille.determinerPertinence(blocsDiagonauxDescedants,getAdversaire(noJoueur)) - 6;

        int pertinenceDA = UtilitaireGrille.determinerPertinence(blocsDiagonauxAscendants,noJoueur);
        int pertinenceDAAdv = UtilitaireGrille.determinerPertinence(blocsDiagonauxAscendants,getAdversaire(noJoueur)) - 8;

        int total = pertinenceV + pertinenceH + pertinenceDD + pertinenceDA
                - (pertinenceAdvV + pertinenceAdvH + pertinenceDDAdv + pertinenceDAAdv);
        return total;
    }

    //prof
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
