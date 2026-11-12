package client;

import chess.*;
import ui.*;

public class ClientMain {
    public static void main(String[] args) throws Exception {
        var piece = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
        var pieceString = piece.printChessPiece();
        String pieceUnicode = chessPieceToUnicode(pieceString);
        System.out.println("♕ 240 Chess Client: " + pieceUnicode);
        int port = 8080;
        Repl terminalServer = new Repl(port);
        terminalServer.run();
    }

    public static String chessPieceToUnicode(String printedChess){
        switch(printedChess){
            case "WHITE_PAWN" -> {
                return EscapeSequences.WHITE_PAWN;
            }
            case "WHITE_BISHOP" -> {
                return EscapeSequences.WHITE_BISHOP;
            }
            case "WHITE_KNIGHT" -> {
                return EscapeSequences.WHITE_KNIGHT;
            }
            case "WHITE_ROOK" -> {
                return EscapeSequences.WHITE_ROOK;
            }
            case "WHITE_QUEEN" -> {
                return EscapeSequences.WHITE_QUEEN;
            }
            case "WHITE_KING" -> {
                return EscapeSequences.WHITE_KING;
            }
            case "BLACK_PAWN" -> {
                return EscapeSequences.BLACK_PAWN;
            }
            case "BLACK_BISHOP" -> {
                return EscapeSequences.BLACK_BISHOP;
            }
            case "BLACK_KNIGHT" -> {
                return EscapeSequences.BLACK_KNIGHT;
            }
            case "BLACK_ROOK" -> {
                return EscapeSequences.BLACK_ROOK;
            }
            case "BLACK_QUEEN" -> {
                return EscapeSequences.BLACK_QUEEN;
            }
            case "BLACK_KING" -> {
                return EscapeSequences.BLACK_KING;
            }
            default -> {
                return "";
            }
        }
    }
}
