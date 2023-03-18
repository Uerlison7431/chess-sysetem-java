package boardgame.chess;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;

public abstract class ChessPiece extends Piece {

    private Color color;

    public ChessPiece(Board board, Color color) {
        super(board);
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    //Metod que veririfica se a peça que ocupa dterminda posição e oponente
    protected boolean isThereOpponetPiece(Position position){
        ChessPiece p = (ChessPiece)getBoard().piece(position);
        return p != null && p.getColor() != color;
    }

        
}
