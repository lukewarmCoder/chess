package chess;

public class Pawn extends Piece {
    
    public Pawn(ReturnPiece.PieceType type, ReturnPiece.PieceFile pieceFile, int rank) {
        super(type, pieceFile, rank);
    }

    @Override
    public boolean isLegalMove(String from, String to, Piece[][] board) {

        Board boardUtil = new Board();

        int[] fromIndex = boardUtil.chessToArrayIndex(from);
        int fromRow = fromIndex[0];
        int fromCol = fromIndex[1];

        int[] toIndex = boardUtil.chessToArrayIndex(to);
        int toRow = toIndex[0];
        int toCol = toIndex[1];

        int direction = (this.pieceType == ReturnPiece.PieceType.WP) ? -1 : 1; // White moves up, Black moves down
        int startRank = (this.pieceType == ReturnPiece.PieceType.WP) ? 6 : 1; // (Rank = row)

        System.out.println(direction);
        System.out.println(startRank);

        // Standard one-step forward move
        if (toRow == fromRow + direction && toCol == fromCol && board[toRow][toCol] == null) {
            return true;
        }

        // Two-step forward move (only on first move)
        if (fromRow == startRank && toRow == fromRow + 2 * direction && toCol == fromCol && 
            board[toRow][toCol] == null && board[fromRow + direction][toCol] == null) {
            return true;
        }

        // Diagonal capture
        if (toRow == fromRow + direction && Math.abs(toCol - fromCol) == 1 &&
            board[toRow][toCol] != null && board[toRow][toCol].pieceType != this.pieceType) {
            return true;
        }

        return false;
    }
}
