package connect5.ia;
import connect5.Grille;
import java.util.ArrayList;

public class UtilitaireGrille {
    public static int getNbCols(Grille grille){ return grille.getData()[0].length; }
    public static int getNbLigs(Grille grille){ return grille.getData().length; }

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

            for(int l = i, c = 0; l < getNbLigs(grille) && c < getNbCols(grille); l++, c++){
                int pionCourant = grille.get(l, c);
                if(l + 1 != getNbLigs(grille) && c - 1 != -1 && pionCourant != grille.get(l - 1 , c + 1)) noInnerBloc++;

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

            for(int l = getNbLigs(grille) - 1, c = i; l < getNbLigs(grille) && c < getNbCols(grille); l++, c++){
                int pionCourant = grille.get(l, c);
                if(l - 1 != -1 && c + 1 != getNbCols(grille) && pionCourant != grille.get(l - 1 , c + 1)) noInnerBloc++;

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

    public static int finPartie(Grille grille, int positionCoup){
        if (ligneHorizontaleGagnante(grille, positionCoup)
            || ligneVerticaleGagnante(grille, positionCoup)
            || diagonaleDescendantesGagnantes(grille, positionCoup)
            || diagonaleAscendantesGagnantes(grille, positionCoup)) return 1;

        if (grille.nbLibre() == 0) return 0;
        return -1;
    }

    private static Boolean ligneHorizontaleGagnante(Grille grille, int positionCoup){
        int nbCols = getNbCols(grille);
        int nbLigs = getNbLigs(grille);
        int x = positionCoup / nbCols;
        int y = positionCoup % nbCols;
        int noJoueur = grille.get(x, y);
        int nbAlignes = 1;

        int xx = x - 1;


        // Regarder a gauche
        while(true) {
            if (xx == -1) break;

            if (grille.get(xx, y) == noJoueur) {
                nbAlignes++;
                xx--;

                if(nbAlignes > 5) return false;
                continue;
            }

            break;
        }

        xx = x + 1;

        // Regarder a droite
        while(true) {
            if (xx == nbLigs) break;

            if (grille.get(xx, y) == noJoueur) {
                nbAlignes++;
                xx++;

                if(nbAlignes > 5) return false;
                continue;
            }

            break;
        }
        return nbAlignes == 5;
    }

    private static Boolean ligneVerticaleGagnante(Grille grille, int positionCoup){
        int nbCols = getNbCols(grille);
        int nbLigs = getNbLigs(grille);
        int x = positionCoup / nbCols;
        int y = positionCoup % nbCols;
        int noJoueur = grille.get(x, y);
        int nbAlignes = 1;

        int yy = y - 1;


        // Regarder en haut
        while(true) {
            if (yy == -1) break;

            if (grille.get(x, yy) == noJoueur) {
                nbAlignes++;
                yy--;

                if(nbAlignes > 5) return false;
                continue;
            }

            break;
        }

        yy = y + 1;

        // Regarder en bas
        while(true) {
            if (yy == nbCols) break;

            if (grille.get(x, yy) == noJoueur) {
                nbAlignes++;
                yy++;

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
        int x = positionCoup / nbCols;
        int y = positionCoup % nbCols;
        int noJoueur = grille.get(x, y);
        int nbAlignes = 1;

        int xx = x - 1;
        int yy = y - 1;


        // Regarder en haut et a gauche
        while(true) {
            if (xx == -1 || yy == -1) break;

            if (grille.get(xx, yy) == noJoueur) {
                nbAlignes++;
                xx--;
                yy--;

                if(nbAlignes > 5) return false;
                continue;
            }

            break;
        }

        xx = x + 1;
        yy = y + 1;

        // Regarder en bas et a droite
        while(true) {
            if (xx == nbLigs || yy == nbCols) break;

            if (grille.get(xx, yy) == noJoueur) {
                nbAlignes++;
                xx++;
                yy++;

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
        int x = positionCoup / nbCols;
        int y = positionCoup % nbCols;
        int noJoueur = grille.get(x, y);
        int nbAlignes = 1;

        int xx = x + 1;
        int yy = y - 1;


        // Regarder en haut et a gauche
        while(true) {
            if (xx == nbLigs || yy == -1) break;

            if (grille.get(xx, yy) == noJoueur) {
                nbAlignes++;
                xx++;
                yy--;

                if(nbAlignes > 5) return false;
                continue;
            }

            break;
        }

        xx = x - 1;
        yy = y + 1;

        // Regarder en bas et a droite
        while(true) {
            if (xx == -1 || yy == nbCols) break;

            if (grille.get(xx, yy) == noJoueur) {
                nbAlignes++;
                xx--;
                yy++;

                if(nbAlignes > 5) return false;
                continue;
            }

            break;
        }
        return nbAlignes == 5;
    }
}
