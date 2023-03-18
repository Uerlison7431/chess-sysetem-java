package boardgame.chess;



import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
//import boardgame.Position;
import boardgame.chess.pieces.King;
import boardgame.chess.pieces.Rook;
//import boardgame.chess.Color;;

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

    //Metodo que indica no tabuleiro as jogadas possiveis ao jogador
    public boolean[][] possibleMoves(ChessPosition sourPosition){
        Position position = sourPosition.toPosition();
        validateSourcePosition(position);
        return board.piece(position).possibleMoves();
    }

   //(SobreCarga)Metodo para fazer um movimento de peça,da posiçao de origem até o destino informado.
    public ChessPiece performChessMove(ChessPosition sourcePosuPosition, ChessPosition targPosition){
        Position source = sourcePosuPosition.toPosition();
        Position target = targPosition.toPosition();
        validateSourcePosition(source);
        validateTargetPosition(source, target);
        Piece capturedPiece = makeMove(source, target);
        return (ChessPiece)capturedPiece;
    }

    //Metodo qye realiza o movimento de remoção e alocação da peça
    private Piece makeMove(Position source, Position target){
        Piece p = board.removePiece(source);
        Piece capturedPiece = board.removePiece(target);
        board.placePiece(p, target);
        return capturedPiece;
    }
    //Metodo de validação de posição de origem para movimento da peça
    private void validateSourcePosition(Position position){
        if(!board.thereIsAPiece(position)){
            throw new ChessException("There is no piece on source position");
        }
        if(!board.piece(position).isThereAnyPossibleMove()){
            throw new ChessException("There is no possible moves for the chosen piece");
        }
    }

    //Metdo de validade de movimento da posição origem para a destino
    private void validateTargetPosition(Position source, Position target){
        if(!board.piece(source).possibleMove(target)){
            throw new ChessException("The chosen piece can't move to target position");
        }
    }
    
    private void placeNewPiece(char column, int row, ChessPiece piece){
        board.placePiece(piece, new ChessPosition(column, row).toPosition());
    }

    //Metodo para iniciar e colocar as peças no tabuleiro
    private void initialSetup(){
        placeNewPiece('c', 1, new Rook(board, Color.WHITE));
        placeNewPiece('c', 2, new Rook(board, Color.WHITE));
        placeNewPiece('d', 2, new Rook(board, Color.WHITE));
        placeNewPiece('e', 2, new Rook(board, Color.WHITE));
        placeNewPiece('e', 1, new Rook(board, Color.WHITE));
        placeNewPiece('d', 1, new King(board, Color.WHITE));

        placeNewPiece('c', 7, new Rook(board, Color.BLACK));
        placeNewPiece('c', 8, new Rook(board, Color.BLACK));
        placeNewPiece('d', 7, new Rook(board, Color.BLACK));
        placeNewPiece('e', 7, new Rook(board, Color.BLACK));
        placeNewPiece('e', 8, new Rook(board, Color.BLACK));
        placeNewPiece('d', 8, new King(board, Color.BLACK));
    }
}