package connect5.ia;
import connect5.Grille;
import java.util.ArrayList;
import java.util.Iterator;
import java.lang.Math;
import java.util.TreeSet;

public class UtilitaireGrille {
    public static int getNbCols(Grille grille){ return grille.getData()[0].length; }
    public static int getNbLigs(Grille grille){ return grille.getData().length; }

    public static ArrayList<Integer> getCasesVidesRadius2(Grille grille){
        int nbCols = getNbCols(grille);
        int nbLigs = getNbLigs(grille);
        TreeSet<Integer> casesVides = new TreeSet<Integer>();

        for (int l = 0; l < nbLigs; l++) {
            for (int c = 0; c < nbCols; c++) {
                int pion = grille.get(l, c);
                if(pion != 0) {

                    for (int i = -2; i <= 2; i++) {
                        int lpp = l + i;

                        for (int j = -2; j <= 2; j++) {
                            int cpp = c + j;

                            if(lpp > -1 &&  lpp < nbLigs
                                && cpp > -1 && cpp < nbCols
                                && grille.get(lpp, cpp) == 0){

                                casesVides.add((lpp) * nbCols + (cpp));
                            }
                        }
                    }
                }
            }
        }

        return new ArrayList(casesVides);
    }

    public static ArrayList<ArrayList<int[]>> construireBlocsHorizontaux(Grille grille) {
        ArrayList<ArrayList<int[]>> blocs = new ArrayList<ArrayList<int[]>>();

        int noBloc = 0;

        for (int i = 0; i < getNbLigs(grille); i++) {

            ArrayList<int[]> innerBlocs = new ArrayList<int[]>();
            int noInnerBloc = 0;

            for (int j = 0; j < getNbCols(grille); j++) {
                int pionCourant = grille.get(i,j);
                if(j - 1 != -1 && pionCourant != grille.get(i , j - 1)) noInnerBloc++;

                if(noInnerBloc >= innerBlocs.size())
                    innerBlocs.add(noInnerBloc, new int[]{pionCourant, 1});
                else {
                    int[] bloc = innerBlocs.get(noInnerBloc);
                    bloc[1]++;
                    innerBlocs.set(noInnerBloc, bloc);
                }
            }

            blocs.add(noBloc++, innerBlocs);
        }

        return blocs;
    }

    public static ArrayList<ArrayList<int[]>> construireBlocsVerticaux(Grille grille) {
        ArrayList<ArrayList<int[]>> blocs = new ArrayList<ArrayList<int[]>>();

        int noBloc = 0;

        for (int i = 0; i < getNbCols(grille); i++) {

            ArrayList<int[]> innerBlocs = new ArrayList<int[]>();
            int noInnerBloc = 0;

            for (int j = 0; j < getNbLigs(grille); j++) {
                int pionCourant = grille.get(j,i);
                if(j - 1 != -1 && pionCourant != grille.get(j - 1 , i)) noInnerBloc++;

                if(noInnerBloc >= innerBlocs.size())
                    innerBlocs.add(noInnerBloc, new int[]{pionCourant, 1});
                else {
                    int[] bloc = innerBlocs.get(noInnerBloc);
                    bloc[1]++;
                    innerBlocs.set(noInnerBloc, bloc);
                }
            }

            blocs.add(noBloc++, innerBlocs);
        }

        return blocs;
    }

    public static ArrayList<ArrayList<int[]>> construireBlocsDiagonauxDescendants(Grille grille) {
        ArrayList<ArrayList<int[]>> blocs = new ArrayList<ArrayList<int[]>>();

        int noBloc = 0;

        for (int i = 0; i < getNbLigs(grille); i++) {
            ArrayList<int[]> innerBlocs = new ArrayList<int[]>();
            int noInnerBloc = 0;

            for(int l = i, c = 0; l < getNbLigs(grille) && c < getNbCols(grille); l++, c++){
                int pionCourant = grille.get(l, c);
                if(l - 1 != -1 && c - 1 != -1 && pionCourant != grille.get(l - 1 , c - 1)) noInnerBloc++;

                if(noInnerBloc >= innerBlocs.size())
                    innerBlocs.add(noInnerBloc, new int[]{pionCourant, 1});
                else {
                    int[] bloc = innerBlocs.get(noInnerBloc);
                    bloc[1]++;
                    innerBlocs.set(noInnerBloc, bloc);
                }
            }

            blocs.add(noBloc++, innerBlocs);
        }

        for (int i = 1; i < getNbCols(grille); i++) {
            ArrayList<int[]> innerBlocs = new ArrayList<int[]>();
            int noInnerBloc = 0;

            for(int l = 0, c = i; l < getNbLigs(grille) && c < getNbCols(grille); l++, c++){
                int pionCourant = grille.get(l, c);
                if(l - 1 != -1 && c - 1 != -1 && pionCourant != grille.get(l - 1 , c - 1)) noInnerBloc++;

                if(noInnerBloc >= innerBlocs.size())
                    innerBlocs.add(noInnerBloc, new int[]{pionCourant, 1});
                else {
                    int[] bloc = innerBlocs.get(noInnerBloc);
                    bloc[1]++;
                    innerBlocs.set(noInnerBloc, bloc);
                }
            }

            blocs.add(noBloc++, innerBlocs);
        }

        return blocs;
    }

    public static ArrayList<ArrayList<int[]>> construireBlocsDiagonauxAscendants(Grille grille) {
        ArrayList<ArrayList<int[]>> blocs = new ArrayList<ArrayList<int[]>>();

        int noBloc = 0;

        for (int i = 0; i < getNbLigs(grille); i++) {
            ArrayList<int[]> innerBlocs = new ArrayList<int[]>();
            int noInnerBloc = 0;

            for(int l = i, c = 0; l >= 0 && c < getNbCols(grille); l--, c++){
                int pionCourant = grille.get(l, c);
                if(l + 1 != getNbLigs(grille) && c - 1 != -1 && pionCourant != grille.get(l + 1 , c - 1)) noInnerBloc++;

                if(noInnerBloc >= innerBlocs.size())
                    innerBlocs.add(noInnerBloc, new int[]{pionCourant, 1});
                else {
                    int[] bloc = innerBlocs.get(noInnerBloc);
                    bloc[1]++;
                    innerBlocs.set(noInnerBloc, bloc);
                }
            }

            blocs.add(noBloc++, innerBlocs);
        }

        for (int i = 1; i < getNbCols(grille); i++) {
            ArrayList<int[]> innerBlocs = new ArrayList<int[]>();
            int noInnerBloc = 0;

            for(int l = getNbLigs(grille) - 1, c = i; l >= 0 && c < getNbCols(grille); l--, c++){
                int pionCourant = grille.get(l, c);
                if(l + 1 != getNbLigs(grille) && c - 1 != getNbCols(grille) && pionCourant != grille.get(l + 1 , c - 1)) noInnerBloc++;

                if(noInnerBloc >= innerBlocs.size())
                    innerBlocs.add(noInnerBloc, new int[]{pionCourant, 1});
                else {
                    int[] bloc = innerBlocs.get(noInnerBloc);
                    bloc[1]++;
                    innerBlocs.set(noInnerBloc, bloc);
                }
            }

            blocs.add(noBloc++, innerBlocs);
        }

        return blocs;
    }

    public static Boolean finPartie(Grille grille, int positionCoup){
        if (positionCoup == -1) return false; // Cas limite sur la premiere (fausse) iteration de l'arbre.
        return (ligneHorizontaleGagnante(grille, positionCoup)
            || ligneVerticaleGagnante(grille, positionCoup)
            || diagonaleDescendantesGagnantes(grille, positionCoup)
            || diagonaleAscendantesGagnantes(grille, positionCoup));
    }

    private static Boolean ligneVerticaleGagnante(Grille grille, int positionCoup){
        int nbCols = getNbCols(grille);
        int nbLigs = getNbLigs(grille);
        int l = positionCoup / nbCols;
        int c = positionCoup % nbCols;
        int noJoueur = grille.get(l, c);
        int nbAlignes = 1;

        int ll = l - 1;


        // Regarder en haut
        while(true) {
            if (ll == -1) break;

            if (grille.get(ll, c) == noJoueur) {
                nbAlignes++;
                ll--;

                if(nbAlignes > 5) return false;
                continue;
            }

            break;
        }

        ll = l + 1;

        // Regarder en bas
        while(true) {
            if (ll == nbLigs) break;

            if (grille.get(ll, c) == noJoueur) {
                nbAlignes++;
                ll++;

                if(nbAlignes > 5) return false;
                continue;
            }

            break;
        }
        return nbAlignes == 5;
    }

    private static Boolean ligneHorizontaleGagnante(Grille grille, int positionCoup){
        int nbCols = getNbCols(grille);
        int l = positionCoup / nbCols;
        int c = positionCoup % nbCols;
        int noJoueur = grille.get(l, c);
        int nbAlignes = 1;

        int cc = c - 1;


        // Regarder a gauche
        while(true) {
            if (cc == -1) break;

            if (grille.get(l, cc) == noJoueur) {
                nbAlignes++;
                cc--;

                if(nbAlignes > 5) return false;
                continue;
            }

            break;
        }

        cc = c + 1;

        // Regarder a droite
        while(true) {
            if (cc == nbCols) break;

            if (grille.get(l, cc) == noJoueur) {
                nbAlignes++;
                cc++;

                if(nbAlignes > 5) return false;
                continue;
            }

            break;
        }
        return nbAlignes == 5;
    }

    private static Boolean diagonaleDescendantesGagnantes(Grille grille, int positionCoup){
        int nbCols = getNbCols(grille);
        int nbLigs = getNbLigs(grille);
        int l = positionCoup / nbCols;
        int c = positionCoup % nbCols;
        int noJoueur = grille.get(l, c);
        int nbAlignes = 1;

        int ll = l - 1;
        int cc = c - 1;


        // Regarder en haut et a gauche
        while(true) {
            if (ll == -1 || cc == -1) break;

            if (grille.get(ll, cc) == noJoueur) {
                nbAlignes++;
                ll--;
                cc--;

                if(nbAlignes > 5) return false;
                continue;
            }

            break;
        }

        ll = l + 1;
        cc = c + 1;

        // Regarder en bas et a droite
        while(true) {
            if (ll == nbLigs || cc == nbCols) break;

            if (grille.get(ll, cc) == noJoueur) {
                nbAlignes++;
                ll++;
                cc++;

                if(nbAlignes > 5) return false;
                continue;
            }

            break;
        }
        return nbAlignes == 5;
    }

    private static Boolean diagonaleAscendantesGagnantes(Grille grille, int positionCoup){
        int nbCols = getNbCols(grille);
        int nbLigs = getNbLigs(grille);
        int l = positionCoup / nbCols;
        int c = positionCoup % nbCols;
        int noJoueur = grille.get(l, c);
        int nbAlignes = 1;

        int ll = l + 1;
        int cc = c - 1;


        // Regarder en bas et a gauche
        while(true) {
            if (ll == nbLigs || cc == -1) break;

            if (grille.get(ll, cc) == noJoueur) {
                nbAlignes++;
                ll++;
                cc--;

                if(nbAlignes > 5) return false;
                continue;
            }

            break;
        }

        ll = l - 1;
        cc = c + 1;

        // Regarder en bas et a droite
        while(true) {
            if (ll == -1 || cc == nbCols) break;

            if (grille.get(ll, cc) == noJoueur) {
                nbAlignes++;
                ll--;
                cc++;

                if(nbAlignes > 5) return false;
                continue;
            }

            break;
        }
        return nbAlignes == 5;
    }


    //bloc(type,longueur)
    static int INDEX_TYPE = 0;
    static int INDEX_LONG = 1;
    static int BLOC_VIDE = 0;
    static int BLOC_INEXISTANT = -1;
    public static int determinerPertinence(ArrayList<ArrayList<int[]>> grille, int joueur){
        int pertinenceDeLaGrille=0;
        int POIDS_PERTINENCE = 5;
        //pour chaque ligne

        Iterator<ArrayList<int[]>> it = grille.iterator();
        while(it.hasNext()) {
            ArrayList<int[]> ligne = it.next();

            //pour chaque bloc
            for(int i =0; i < ligne.size(); i++){
                int[] blocCourant = ligne.get(i);
                int longueur = blocCourant[INDEX_LONG];
                int nbPionsConcernes = blocCourant[INDEX_LONG];
                int longueurVideTemp = 0;

                //evaluer la pertinence du bloc dans sa ligne
                if (blocCourant[INDEX_TYPE] == joueur) {
                    if (blocCourant[INDEX_LONG] > 5) continue;

                    if (estVideAGauche(ligne,i)){
                        //Si le bloc suivant est ennemi ou null
                        if (getTypeBloc(ligne, i-2) == BLOC_INEXISTANT ||
                                (getTypeBloc(ligne,i-2) != joueur && getTypeBloc(ligne,i-2)!= BLOC_VIDE)){
                            //Ajouter taille du vide a gauche
                            longueur+= ligne.get(i-1)[INDEX_LONG];
                        } else {
                            //Si le bloc suivant est au joueur
                            //Ajouter taille du vide a gauche -1
                            //(Permet d'eviter de faire 6)
                            longueur+= ligne.get(i-1)[INDEX_LONG] -1;
                        }
                    }
                    if ( estVideADroite(ligne,i)){
                        //Si le bloc suivant est ennemi ou null
                        if(getTypeBloc(ligne, i+2) == BLOC_INEXISTANT ||
                                (getTypeBloc(ligne,i+2) != joueur && getTypeBloc(ligne,i+2) != BLOC_VIDE)){
                            //Ajouter temporairement la longueur du vide a droite
                            longueur+=ligne.get(i+1)[INDEX_LONG];
                            longueurVideTemp = ligne.get(i+1)[INDEX_LONG];
                        } else {
                            //Si le bloc suivant est au joueur
                            //Ajouteur temporairement la longueur du vide a droite -1
                            //(Permet d'eviter de faire 6)
                            longueur += ligne.get(i+1)[INDEX_LONG] -1;
                            longueurVideTemp = ligne.get(i+1)[INDEX_LONG]-1;
                        }
                    }
                    if(longueur>= 5){
                        //Analyse du bloc terminee.
                        //Mettre a jour petinence de la grille
                        //Passer au bloc suivant
                        pertinenceDeLaGrille += (int)Math.pow(POIDS_PERTINENCE,nbPionsConcernes);
                        continue;
                    }
                    //Retirer le vide a droite temporaire
                    longueur-= longueurVideTemp;
                    int positionBlocAjouter = i+1;
                    //Analyser vers la droite puis mettre a jour et passer au bloc suivant
                    nbPionsConcernes = ajouterADroite(ligne,positionBlocAjouter, longueur, joueur,nbPionsConcernes);
                    if( nbPionsConcernes > 0) {
                        pertinenceDeLaGrille += (int) Math.pow(POIDS_PERTINENCE, nbPionsConcernes);
                    }
                }
            }
        }
        return pertinenceDeLaGrille;
    }
    //Retourne le nouveau nbPionsConcernes
    public static int ajouterADroite(ArrayList<int[]> ligne, int positionBlocAjouter, int longueur, int joueur, int nbPionsConcernes){
        while (longueur <5) {
            //Si plus de bloc disponible.
            if (positionBlocAjouter>= ligne.size() ||
                    (getTypeBloc(ligne,positionBlocAjouter) != BLOC_VIDE &&
                            getTypeBloc(ligne,positionBlocAjouter) != joueur)) return 0;
            //Si le bloc est vide
            if (getTypeBloc(ligne,positionBlocAjouter) == BLOC_VIDE){
                //Si le bloc suivant est null ou enemi
                if(getTypeBloc(ligne,positionBlocAjouter+1) == BLOC_INEXISTANT ||
                        (getTypeBloc(ligne,positionBlocAjouter+1)!=joueur &&
                                getTypeBloc(ligne,positionBlocAjouter+1)!=BLOC_VIDE)){
                    longueur += ligne.get(positionBlocAjouter)[INDEX_LONG];
                } else {
                    //Le cas ou le bloc suivant est au joueur
                    longueur += ligne.get(positionBlocAjouter)[INDEX_LONG]-1;
                }
                //pertinent, on retourne
                if (longueur >= 5) return nbPionsConcernes;
            } else {
                //Si le bloc est ennemi ou nul.
                //On peut d�duire ici que la longueur < 5 et qu'il n'y a plus de possibilit�s
                return 0 ;
            }
            //On passe au bloc suivant a droite
            positionBlocAjouter++;
            //Ce bloc suivant n'est pertinent que s'il est au joueur, car on arrive
            // d'un bloc vide.
            if(getTypeBloc(ligne, positionBlocAjouter) == joueur){
                //+1 pour compenser le -1 dans le bloc vide precedent
                longueur+= ligne.get(positionBlocAjouter)[INDEX_LONG] + 1;
                nbPionsConcernes += ligne.get(positionBlocAjouter)[INDEX_LONG];
                //pertinent
                if(longueur == 5) return nbPionsConcernes;
                //non pertinent
                if( longueur > 5 ) return 0;

                positionBlocAjouter++;
            }
        }
        //Ne devrait pas arriver.
        return 0;
    }
    public static int getTypeBloc(ArrayList<int[]> ligne, int positionBloc){
        if (positionBloc < 0 || positionBloc >= ligne.size()) return BLOC_INEXISTANT;
        return ligne.get(positionBloc)[INDEX_TYPE];
    }
    public static boolean estVideAGauche(ArrayList<int[]> ligne, int positionBlocCourant){
        if(positionBlocCourant == 0) return false;
        return ligne.get(positionBlocCourant-1)[INDEX_TYPE] == BLOC_VIDE;
    }

    public static boolean estVideADroite(ArrayList<int[]> ligne, int positionBlocCourant){
        if(positionBlocCourant == ligne.size()-1) return false;
        return ligne.get(positionBlocCourant+1)[INDEX_TYPE] == BLOC_VIDE;
    }

}
