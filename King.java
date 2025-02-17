package chess;

public class King extends Piece {

    public King(ReturnPiece.PieceType type, ReturnPiece.PieceFile pieceFile, int rank) {
        super(type, pieceFile, rank, (type == ReturnPiece.PieceType.WK) ? pieceColor.white : pieceColor.black);
    }

    @Override
    public boolean isLegalMove(int fromRow, int fromCol, int toRow, int toCol, Piece[][] board) {
        
        // Calculate row and column differences
        int rowDiff = Math.abs(fromRow - toRow);
        int colDiff = Math.abs(fromCol - toCol);

        // The king can move exactly 1 square in any direction
        if (rowDiff > 1 || colDiff > 1) {
            return false;
        }

        // Check if the destination contains the same color piece
        Piece destinationPiece = board[toRow][toCol];
        if (destinationPiece != null && !destinationPiece.isOpponent(this)) {
            return false;
        }
        
        return true;
    }
}
