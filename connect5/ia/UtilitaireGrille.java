package connect5.ia;
import connect5.Grille;

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
}
