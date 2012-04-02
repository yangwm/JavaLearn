package basics.learning.inheritances;

class ChessAlgorithm {
    enum ChessPlayer { WHITE, BLACK }
    final ChessPlayer getFirstPlayer() {
        return ChessPlayer.WHITE;
    }
}
