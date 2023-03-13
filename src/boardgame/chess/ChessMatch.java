package boardgame.chess;

import boardgame.Board;
import boardgame.Position;
import boardgame.chess.pieces.King;
import boardgame.chess.pieces.Rook;

public class ChessMatch {
    
    private Board board;

    public ChessMatch(){
        board = new Board(8, 8);
        //metodo de inicio da partida
        initialSetup();
    }

    public ChessPiece[][] getPieces(){
        ChessPiece[][] mat = new ChessPiece[board.getRows()][board.getColumns()];
        for(int i=0; i<board.getRows(); i++){
            for(int j=0; j<board.getColumns(); j++){
                //DownCast passando a peça sendo da camnada chess, e não board
                mat[i][j] = (ChessPiece) board.piece(i, j); 
            }
        }

        return mat;
    }

    //Metodo para iniciar e colocar as peças no tabuleiro
    private void initialSetup(){
        board.placePiece(new Rook(board, Color.WHITE), new Position(2, 1));
        board.placePiece(new King(board, Color.Black), new Position(0, 4));
    }
}