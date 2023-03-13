package boardgame;

public class Board {
    
    private int rows;
    private int columns;
    private Piece[][] pieces;
    
    public Board(int rows, int columns) {
        
        if(rows < 1 || columns <1){
            throw new BoardException("Erro creating boar: there must be at least 1 row and 1 colum");
        }
        
        this.rows = rows;
        this.columns = columns;
        pieces = new Piece[rows][columns];
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    
    //Retorna a matriz pices na linha e coluna informada no metodo
    public Piece piece(int row, int colum){
        if(!positionExists(row, colum)){
            throw new BoardException("Position no on the board");
        }
        return pieces[row][colum];
    }

    public Piece piece(Position position){
        if(!positionExists(position)){
            throw new BoardException("Position no on the board");
        }
        return pieces[position.getRow()][position.getColumn()];
    }

    //METODO QUE INDICA A POSIÇÃO PARA A PEÇA
    public void placePiece(Piece piece, Position position){
        //Exeption que informa se a posição informada esta ocupada
        if(thereIsAPiece(position)){
            throw new BoardException("There is already a piece on position " + position);
        }
        
        pieces[position.getRow()][position.getColumn()] = piece;
        piece.position = position;
    }

    //metodo auxiliar so pode ser acessado dentro desta clase. para ver se uma posição existe
    private boolean positionExists(int row, int colum){
        return row >=0 && row < rows && colum >=0 && colum < columns;
    }
    
    //metodo para pulbic para ver se uma posição existe
    public boolean positionExists(Position position){
        return positionExists(position.getRow(), position.getColumn());
    }

    //metodo para indicar se tem uma peça na posição
    public boolean thereIsAPiece(Position position){
       //metodo que confere se a posução existe
       if(!positionExists(position)){
        throw new BoardException("Position on the board");
       }
        return piece(position) != null;
    }
}
