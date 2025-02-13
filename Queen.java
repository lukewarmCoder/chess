package chess;

public class Queen extends Piece{

    public Queen(ReturnPiece.PieceType type, ReturnPiece.PieceFile pieceFile, int rank) {
        super(type, pieceFile, rank);
    }

    @Override
    public boolean isLegalMove(String from, String to, Piece[][] board) {
        return false;
    }
}
