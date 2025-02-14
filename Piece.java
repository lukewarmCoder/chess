package chess;

abstract class Piece {

    public enum pieceColor { white, black }

    ReturnPiece.PieceType pieceType;
    ReturnPiece.PieceFile pieceFile;
    int pieceRank;
    pieceColor color;
    
    
    public Piece(ReturnPiece.PieceType pieceType, ReturnPiece.PieceFile pieceFile, int pieceRank, pieceColor color) {
        this.pieceType = pieceType;
        this.pieceFile = pieceFile;
        this.pieceRank = pieceRank;
        this.color = color;
    }

    // Abstract method to be implemented by each specific piece
    public abstract boolean isLegalMove(String from, String to, Piece[][] board);

    // Override the toString method to return a string representation
    @Override
    public String toString() {
        return pieceFile + "" + pieceRank + ": " + pieceType;
    }

    public pieceColor getColor() {
        return color;
    }

    public boolean isOpponent(Piece other) {
        return other != null && !this.color.equals(other.color);
    }

}
