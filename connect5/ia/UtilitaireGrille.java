package connect5.ia;
import connect5.Grille;


/**
*iterer sur les blocs
    longueur = longueurde notre bloc
    nbPionsConcernés = longueur de notre bloc
    longueur_du_vide_temp = 0

    if  notre bloc
        if longueur bloc > 5 :  continue

        if a gauche est vide
            if  deux a gauche n'est pas nous  (enemi ou NULL)
                longueur += longueur du vide a gauche
            else
                longueur += longueur du vide - 1
        if a droite est vide
            if  bloc [ j + 1]   = enemi ou NULL
                longueur += longueur du vide a droite
                longueur_du_vide_temp = longeur du vide a droite
            else
                longueur += longueur du vide - 1
                longueur_du_vide_temp = longeur du vide a droite  - 1

        if longueur >= 5  return  nbPionsConcernes

        longueur -= longueur_du_vide_temp
        j = i + 1

        while longueur < 5
            expand_a_droite
        end
///////////
expand_a_droite
    if bloc [ j ]  est enemi   RETURN 0
    if bloc [ j ]  est vide

        if  bloc [ j + 1]   = enemi ou NULL
            longueur += longueur du vide a droite
        else
            longueur += longueur du vide - 1
        if longueur >= 5  return nbBlocsConcernes
        j ++

    if bloc [ j ]  est a nous

        longueur += longueur du bloc [ j ] + 1
        nbPionsConcernes += longueur du bloc [ j ]

        if longueur == 5  return nbPionsConcernes
        if longueur > 5 return 0
        j ++

*/

public class UtilitaireGrille {
    public static int finPartie(Grille grille, int positionCoup){
        if (ligneHorizontaleGagnante(grille, positionCoup)
            || ligneVerticaleGagnante(grille, positionCoup)
            || diagonaleDescendantesGagnantes(grille, positionCoup)
            || diagonaleAscendantesGagnantes(grille, positionCoup)) return 1;

        if (grille.nbLibre() == 0) return 0;
        return -1;
    }

    private static Boolean ligneHorizontaleGagnante(Grille grille, int positionCoup){
        int nbCols = grille.getData()[0].length;
        int nbLigs = grille.getData().length;
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
        int nbCols = grille.getData()[0].length;
        int nbLigs = grille.getData().length;
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
        int nbCols = grille.getData()[0].length;
        int nbLigs = grille.getData().length;
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
        int nbCols = grille.getData()[0].length;
        int nbLigs = grille.getData().length;
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

    public int test(){

    }
}
