package chess;

public class Board {

    public Piece[][] board;

    public Board() {
        board = new Piece[8][8];
        System.out.println("Board created");
    }

    public void addPiece(Piece piece) {

        int fileIndex = piece.pieceFile.ordinal(); // Convert 'a' to 0, 'b' to 1, ...
        int rankIndex = 8 - piece.pieceRank; // Convert rank 1 to row 7, rank 8 to row 0

        board[rankIndex][fileIndex] = piece;
        System.out.println("Piece added at " + piece.pieceFile + piece.pieceRank);

    }

    public void printBoard() {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if (board[row][col] != null) {
                    System.out.print(board[row][col] + "\t");
                } else {
                    System.out.print("empty\t");
                }
            }
            System.out.println();
        }
    }


}