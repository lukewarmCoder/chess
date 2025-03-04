package chess;

abstract class Piece {
    public enum PieceColor { white, black }

    private PieceColor color;
    private Chess.Player owner;

    private ReturnPiece.PieceType pieceType;
    private ReturnPiece.PieceFile pieceFile;
    private int pieceRank;
    
    public Piece(ReturnPiece.PieceType pieceType, ReturnPiece.PieceFile pieceFile, int pieceRank, PieceColor color) {
        this.pieceType = pieceType;
        this.pieceFile = pieceFile;
        this.pieceRank = pieceRank;
        this.color = color;
        this.owner = (color == PieceColor.white) ? Chess.Player.white : Chess.Player.black;
    }

    // Abstract method to be implemented by each specific piece
    public abstract boolean isLegalMove(int fromRow, int fromCol, int toRow, int toCol, Piece[][] board);

    public abstract Piece copy();

    // Override the toString method to return a string representation
    @Override
    public String toString() {
        return pieceFile + "" + pieceRank + ": " + pieceType;
    }


    public boolean isOwnedBy(Chess.Player player) {
        return this.owner == player;
    }

    public boolean isOpponent(Piece other) {
        return other != null && !this.color.equals(other.color);
    }


    // Getter for color
    public PieceColor getColor() {
        return color;
    }

    // Getter for owner
    public Chess.Player getOwner() {
        return owner;
    }

    // Getter for pieceType
    public ReturnPiece.PieceType getPieceType() {
        return pieceType;
    }

    // Getter for pieceFile
    public ReturnPiece.PieceFile getPieceFile() {
        return pieceFile;
    }

    // Setter for pieceFile
    public void setPieceFile(ReturnPiece.PieceFile pieceFile) {
        this.pieceFile = pieceFile;
    }

    // Getter for pieceRank
    public int getPieceRank() {
        return pieceRank;
    }

    // Setter for pieceRank
    public void setPieceRank(int pieceRank) {
        this.pieceRank = pieceRank;
    }

}
