package chess;

public class Knight extends Piece {
    
    public Knight(ReturnPiece.PieceType type, ReturnPiece.PieceFile pieceFile, int rank) {
        super(type, pieceFile, rank, (type == ReturnPiece.PieceType.WN) ? PieceColor.white : PieceColor.black);
    }

    @Override
    public Piece copy() {
        return new Knight(this.getPieceType(), this.getPieceFile(), this.getPieceRank());
    }

    @Override
    public boolean isLegalMove(int fromRow, int fromCol, int toRow, int toCol, Piece[][] board) {

        // Calculate row and column differences
        int rowDiff = Math.abs(fromRow - toRow);
        int colDiff = Math.abs(fromCol - toCol);

        // Check if the move is an "L" shape (2 squares in one direction, 1 in the other)
        if (!((rowDiff == 2 && colDiff == 1) || (rowDiff == 1 && colDiff == 2))) {
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
