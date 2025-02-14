package chess;

import java.util.Arrays;

public class Board {

    public Piece[][] board;

    public Board() {
        board = new Piece[8][8];
    }

    // File = a, b, c, d, e, f, g, h      Rank = 1, 2, 3, 4, 5, 6, 7, 8
    // Converts FileRank (a2) to array index (6, 0)
    public int[] chessToArrayIndex(String FileRank) {
        int row = 8 - Integer.parseInt(FileRank.charAt(1) + ""); // Convert rank 1 to row 7, rank 8 to row 0
        int col = FileRank.charAt(0) - 'a'; // Convert 'a' to 0, 'b' to 1, ...
        return new int[] {row, col};
    }

    public void addPiece(Piece piece) {
        String FileRank = piece.pieceFile.name() + piece.pieceRank; // Combines file and rank into one string
        int[] index = chessToArrayIndex(FileRank); // Converts FileRank to an array index
        board[index[0]][index[1]] = piece;
    }

    // Return piece given an array index (not RankFile)
    public Piece getPiece(int[] index) {
        return board[index[0]][index[1]];
    }

    // Used only after move is validated!
    public void movePiece(Piece piece, int[] start, int[] destination) {

    }

    public void printBoard() {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if (board[row][col] == null) {
                    System.out.print("empty\t");
                } else {
                    System.out.print(" " + board[row][col].pieceType + " " + "\t");
                }
            }
            System.out.println();
        }
    }

}