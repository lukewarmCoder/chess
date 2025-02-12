package chess;

abstract class Piece {

    ReturnPiece.PieceType type;
    ReturnPiece.PieceFile file;
    int rank;
    Chess.Player player;
    
    public Piece(ReturnPiece.PieceType type, ReturnPiece.PieceFile file, int rank, Chess.Player player) {
        this.type = type;
        this.file = file;
        this.rank = rank;
        this.player = player;
    }

    public abstract boolean isLegalMove(String from, String to, Piece[][] board);

}
