package chess;

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

    // Add pieces given FileRank
    public void addPiece(Piece piece) {
        String FileRank = piece.pieceFile.name() + piece.pieceRank; // Combines file and rank into one string
        int[] index = chessToArrayIndex(FileRank); // Converts FileRank to an array index
        board[index[0]][index[1]] = piece;
    }

    // Add pieces given array index
    public void setPiece(int row, int col, Piece piece) {
        board[row][col] = piece;
    }

    // Return piece given an array index (not RankFile)
    public Piece getPiece(int row, int col) {
        return board[row][col];
    }

    // Get array index location given a piece
    public int[] getPiecePosition(Piece piece) {
        String FileRank = piece.pieceFile.name() + piece.pieceRank; // Combines file and rank into one string
        int[] index = chessToArrayIndex(FileRank); // Converts FileRank to an array index
        return index;
    }

    // Used only after move is validated!
    public void movePiece(Piece piece, int fromRow, int fromCol, int toRow, int toCol) {

        // If capturing a piece, add it to the list
        if (board[toRow][toCol] != null) {
            Chess.capturedPieces.add(getPiece(toRow, toCol));
        }

        board[toRow][toCol] = piece;
        board[fromRow][fromCol] = null;

        // Update the piece's file and rank
        piece.pieceFile = ReturnPiece.PieceFile.values()[toCol];
        piece.pieceRank = 8 - toRow;
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