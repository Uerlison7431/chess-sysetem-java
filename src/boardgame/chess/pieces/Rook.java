package boardgame.chess.pieces;

import boardgame.Board;
import boardgame.chess.ChessPiece;
import boardgame.chess.Color;

public class Rook extends ChessPiece{

    public Rook(Board board, Color color) {
        super(board, color);
    }

    @Override
    public String toString(){
        return "R";
    }
    
}
