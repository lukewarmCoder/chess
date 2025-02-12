package chess;

public class Pawn extends Piece {
    
    public Pawn(ReturnPiece.PieceFile file, int rank, Chess.Player player) {
        super(ReturnPiece.PieceType.WP, file, rank, player);
    }

    @Override
    public boolean isLegalMove(String from, String to, Piece[][] board) {
        // TODO Auto-generated method stub
        return false;
    }
}
