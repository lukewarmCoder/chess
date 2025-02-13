package chess;

public class King extends Piece {

    public King(ReturnPiece.PieceType type, ReturnPiece.PieceFile pieceFile, int rank) {
        super(ReturnPiece.PieceType.WK, pieceFile, rank);
    }

    @Override
    public boolean isLegalMove(String from, String to, Piece[][] board) {
        return false;
    }
}
