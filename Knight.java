package chess;

public class Knight extends Piece {
    
    public Knight(ReturnPiece.PieceType type, ReturnPiece.PieceFile pieceFile, int rank) {
        super(type, pieceFile, rank);
    }

    @Override
    public boolean isLegalMove(String from, String to, Piece[][] board) {
        return false;
    }

}
