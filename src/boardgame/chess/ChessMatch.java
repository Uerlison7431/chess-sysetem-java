package boardgame.chess;



import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
//import boardgame.Position;
import boardgame.chess.pieces.King;
import boardgame.chess.pieces.Rook;
//import boardgame.chess.Color;;

public class ChessMatch {
    
    private int turn;
    private Color currentPLayer;
    private Board board;
    private boolean check;
    private boolean checkMate;
    
    private List<Piece> piecesOnTheBoard = new ArrayList<>();
    private List<Piece> capturedPieces = new ArrayList<>();

    //Construtor
    public ChessMatch(){
        board = new Board(8, 8);
        turn =1;
        currentPLayer = Color.WHITE;
        //metodo de inicio da partida
        initialSetup();
    }

    public int getTurn(){
        return turn;
    }

    public Color getcurrentPlayer(){
        return currentPLayer;
    }

    public boolean getCheck(){
        return check;
    }

    public boolean getCheckMate(){
        return checkMate;
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

        //comando que informa que o jogador não pode se colocar em check
        if(testCheck(currentPLayer)){
            undoMovie(source, target, capturedPiece);
            throw new ChessException("You can't put yourself in check");
        }
        //Comando que verifica se o oponnente ficará em check depois da jogada.
        check = (testCheck(opponent(currentPLayer))) ? true : false;

        if(testCheckMate(opponent(currentPLayer))){
            checkMate = true;
        }else{
            nextTurn();
        }

        
        return (ChessPiece)capturedPiece;
    }

    //Metodo que realiza o movimento (Jogada) de remoção e alocação da peça
    private Piece makeMove(Position source, Position target){
        Piece p = board.removePiece(source);
        Piece capturedPiece = board.removePiece(target);
        board.placePiece(p, target);

        if(capturedPiece != null){
            piecesOnTheBoard.remove(capturedPiece);
            capturedPieces.add(capturedPiece);
        }
        return capturedPiece;
    }
    //Metodo que desfaz a jogada retornando as peças a posilçao de origen
    private void undoMovie(Position source, Position target, Piece capturedPiece){
        Piece p = board.removePiece(target);
        board.placePiece(p, source);
        //Comando que verifica se teve alguma peças capturada na jogada, devolvendo-a a sua origen
        if(capturedPiece != null){
            board.placePiece(capturedPiece, target);
            capturedPieces.remove(capturedPiece);
            piecesOnTheBoard.add(capturedPiece);
        }
    }
    //Metodo de validação de posição de origem para movimento da peça
    private void validateSourcePosition(Position position){
        if(!board.thereIsAPiece(position)){
            throw new ChessException("There is no piece on source position");
        }
        if(currentPLayer != ((ChessPiece) board.piece(position)).getColor()){
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
        piecesOnTheBoard.add(piece);
    }

    //Metodo que faz a mudança de turno
    private void nextTurn(){
        turn++;
        currentPLayer = (currentPLayer == Color.WHITE) ? Color.BLACK : Color.WHITE;
    }

    //Metodo que indica qual a cor do oponente
    private Color opponent(Color color){
        return (color == Color.WHITE) ? Color.BLACK : Color.WHITE;
    }

    //Metodo que procura o Rei na lista e indica com uma cor
    private ChessPiece king(Color color){
        List<Piece> list = piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor() == color).collect(Collectors.toList());
        for(Piece p : list){
            if(p instanceof King){
                return (ChessPiece) p;
            }
        }
        throw new IllegalStateException("There is no " + color + " King on the Board");
    }

    //Metodo que teste se o King pode ficar ou esta em check
    private boolean testCheck(Color color){
        Position kingPosition = king(color).getChessPosition().toPosition();
        List<Piece> opponentPieces = piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor() == opponent(color)).collect(Collectors.toList());
        for(Piece p : opponentPieces){
            boolean[][] mat = p.possibleMoves();
            if(mat[kingPosition.getRow()][kingPosition.getColumn()]){
                return true;
            }
        }
        return false;
    }

    //Metodo que teste o checkMate
    private boolean testCheckMate(Color color){
        //linha que testa se o King está em check
        if(!testCheck(color)){
            return false;
        }
        List<Piece> list = piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor() == color).collect(Collectors.toList());
        for(Piece p : list){
            boolean[][] mat = p.possibleMoves();
            for(int i=0; i<board.getRows(); i++){
                for(int j=0; j<board.getRows(); j++){
                    if(mat[i][j]){
                        Position source = ((ChessPiece)p).getChessPosition().toPosition();
                        Position target = new Position(i, j);
                        Piece capturedPiece = makeMove(source, target);
                        boolean testCheck = testCheck(color);
                        //Comando que desfaz o movimento dpois que testa
                        undoMovie(source, target, capturedPiece);
                        if(!testCheck){
                            return false;
                        }

                    }
                }
            }
        }
        return true;
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