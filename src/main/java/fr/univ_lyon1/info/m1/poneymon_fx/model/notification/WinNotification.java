package fr.univ_lyon1.info.m1.poneymon_fx.model.notification;

/**
 * Notification envoyée aux Observers quand un poney gagne.
 *
 */
public class WinNotification extends Notification {

    int winner;
    String winnerColor;

    /**
     * Constructeur de la notification de victoire.
     *
     * @param winner Numéro du poney gagnant
     * @param winnerColor Couleur du poney gagnant
     */
    public WinNotification(int winner, String winnerColor) {
        super("WIN");
        this.winner = winner;
        this.winnerColor = winnerColor;
    }

    public int getWinner() {
        return winner;
    }

    public String getWinnerColor() {
        return winnerColor;
    }
}
