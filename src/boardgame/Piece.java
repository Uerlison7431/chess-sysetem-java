package boardgame;

public abstract class Piece {
    
    protected Position position;
    private Board board;
    
    public Piece(Board board) {
        this.board = board;
        position = null;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    protected Board getBoard() {
        return board;
    }

    //Metod que permite a movimentação das peças de acordo com suas regras
    public abstract boolean[][] possibleMoves();    

    //Metodo que verifica se o movimento a ser realizado e possivel
    public boolean possibleMove(Position position){
        return possibleMoves()[position.getRow()][position.getColumn()];
    }

    //metodo que verifica se a peça possui ao menos uma possiblidade de movimento
    public boolean isThereAnyPossibleMove(){
        boolean[][] mat = possibleMoves();
        for(int i=0; i<mat.length; i++){
            for(int j=0; j<mat.length; j++){
                if(mat[i][j]){
                    return true;
                }
            }
        }
        return false;
    }
        
}
