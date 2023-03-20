package application;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import boardgame.chess.ChessMatch;
import boardgame.chess.ChessPiece;
import boardgame.chess.ChessPosition;
import boardgame.chess.Color;

public class UI {
    
// https://stackoverflow.com/questions/5762491/how-to-print-color-in-console-using-system-out-println

            public static final String ANSI_RESET = "\u001B[0m";
            public static final String ANSI_BLACK = "\u001B[30m";
            public static final String ANSI_RED = "\u001B[31m";
            public static final String ANSI_GREEN = "\u001B[32m";
            public static final String ANSI_YELLOW = "\u001B[33m";
            public static final String ANSI_BLUE = "\u001B[34m";
            public static final String ANSI_PURPLE = "\u001B[35m";
            public static final String ANSI_CYAN = "\u001B[36m";
            public static final String ANSI_WHITE = "\u001B[37m";

            public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
            public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
            public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
            public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
            public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
            public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
            public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
            public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";

        //metod para limpar manter/limpar a tela dpois de uma jogada
    public static void clearScreen(){
            System.out.println("\033[H\033[2J");
                System.out.flush();
        }

    public static ChessPosition readChessPosition(Scanner sc){
        try{
        String s = sc.nextLine();
        char column = s.charAt(0);
        int row = Integer.parseInt(s.substring(1));
        return new ChessPosition(column, row); 
        }
        catch (RuntimeException e){
            throw new InputMismatchException("Error reding ChessPosition. Valid values are from a1 to h8");
        }
    }

    //Metodo que mostra o turno e o jogador da rodada
    public static void printMatch(ChessMatch chessMatch, List<ChessPiece> captured){
        printBoard(chessMatch.getPieces());
        System.out.println();
        printCapturePieces(captured);
        System.out.println();
        System.out.println("Turn : " + chessMatch.getTurn());
        if(!chessMatch.getCheckMate()){
            System.out.println("Waiting player: " + chessMatch.getcurrentPlayer());
            if(chessMatch.getCheck()){
                System.out.println("Check!");
            }
        }else{
            System.out.println("CheckMate!");
            System.out.println("Winner: " + chessMatch.getcurrentPlayer());
        }
        

        if(chessMatch.getCheck()){
            System.out.println("Check");
        }
    }
    
    //Metodo que imprime o tabuleiro com as peças
    public static void printBoard(ChessPiece[][] pieces){
        for (int i=0; i<pieces.length; i++){
            System.out.print((8-i) + " ");
            for(int j=0; j<pieces.length; j++){
                printPiece(pieces[i][j], false);
            }

            System.out.println();
        }

        System.out.println("  a b c d e f g h");
    }
    //Metodo que imprime o tabuleiro com as peças e as jogadas possives
    public static void printBoard(ChessPiece[][] pieces, boolean[][] possibleMoves){
        for (int i=0; i<pieces.length; i++){
            System.out.print((8-i) + " ");
            for(int j=0; j<pieces.length; j++){
                printPiece(pieces[i][j], possibleMoves[i][j]);
            }

            System.out.println();
        }

        System.out.println("  a b c d e f g h");
    }

    //Metodo para imprimir uma peça
    private static void printPiece(ChessPiece piece, boolean backGround) {
	   	if(backGround){
            System.out.print(ANSI_BLUE_BACKGROUND);
        }
        if (piece == null) {
            System.out.print("-" + ANSI_RESET);
        }
        else {
            if (piece.getColor() == Color.WHITE) {
                System.out.print(ANSI_WHITE + piece + ANSI_RESET);
            }
            else {
                System.out.print(ANSI_YELLOW + piece + ANSI_RESET);
            }
        }
        System.out.print(" ");
	}

    //Metodo que mostra as peças capturadas
    private static void printCapturePieces(List<ChessPiece> captured){
        List<ChessPiece> white = captured.stream().filter(x -> x.getColor() == Color.WHITE).collect(Collectors.toList());
        List<ChessPiece> Black = captured.stream().filter(x -> x.getColor() == Color.BLACK).collect(Collectors.toList());
        System.out.println("Captured pieces");
        System.out.print("White: ");
        System.out.print(ANSI_WHITE);
        //Comando para percorrer a lista e passar para um array e imprimir as peças brancas
        System.out.println(Arrays.toString(white.toArray())); 
        System.out.print(ANSI_RESET);
        System.out.print("Black: ");
        System.out.print(ANSI_YELLOW);
        //Comando para percorrer a lista e passar para um array e imprimir as peças brancas
        System.out.println(Arrays.toString(Black.toArray()));
        System.out.print(ANSI_RESET);

    }
}
