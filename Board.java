package chess;

public class Board {

    public Piece[][] board;

    public Board() {
        board = new Piece[8][8];
    }

    public void addPiece(Piece piece) {

        int fileIndex = piece.pieceFile.ordinal(); // Convert 'a' to 0, 'b' to 1, ...
        int rankIndex = 8 - piece.pieceRank; // Convert rank 1 to row 7, rank 8 to row 0

        board[rankIndex][fileIndex] = piece;
        // System.out.println("Piece added at " + piece.pieceFile + piece.pieceRank);

    }

    // Convert from RankFile to (row, col)  'a1' -> (7,0)
    public int[] chessToArrayIndex(String RankFile) {
        
        int row = 8 - Integer.parseInt(RankFile.charAt(1) + "");
        int col = RankFile.charAt(0) - 'a';

        return new int[] {row, col};
    }

    // Return piece given an array index (not RankFile)
    public Piece getPiece(int[] index) {
        return board[index[0]][index[1]];
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