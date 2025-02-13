package chess;

abstract class Piece {

    ReturnPiece.PieceType pieceType;
    ReturnPiece.PieceFile pieceFile;
    int pieceRank;
    
    public Piece(ReturnPiece.PieceType pieceType, ReturnPiece.PieceFile pieceFile, int pieceRank) {
        this.pieceType = pieceType;
        this.pieceFile = pieceFile;
        this.pieceRank = pieceRank;
    }

    // Abstract method to be implemented by each specific piece
    public abstract boolean isLegalMove(String from, String to, Piece[][] board);

    // Override the toString method to return a string representation
    @Override
    public String toString() {
        return pieceFile + "" + pieceRank + ": " + pieceType;
    }

}
