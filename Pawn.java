package chess;

public class Pawn extends Piece {
    
    public Pawn(ReturnPiece.PieceType type, ReturnPiece.PieceFile pieceFile, int rank) {
        super(ReturnPiece.PieceType.WP, pieceFile, rank);
    }

    @Override
    public boolean isLegalMove(String from, String to, Piece[][] board) {
        return false;
    }
}
