package boardgame.chess.pieces;

import boardgame.Board;
import boardgame.chess.ChessPiece;
import boardgame.chess.Color;

public class King extends ChessPiece{

    public King(Board board, Color color) {
        super(board, color);
    }

    public String toString(){
        return "K";
    }
    

    @Override
    public boolean[][] possibleMoves() {
        boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];
        return mat;
    }
}
