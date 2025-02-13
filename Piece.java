package chess;

import chess.ReturnPiece.PieceType;

abstract class Piece {

    ReturnPiece.PieceType pieceType;
    ReturnPiece.PieceFile pieceFile;
    int pieceRank;
    Chess.Player color;
    
    public Piece(ReturnPiece.PieceType pieceType, ReturnPiece.PieceFile pieceFile, int pieceRank) {
        this.pieceType = pieceType;
        this.pieceFile = pieceFile;
        this.pieceRank = pieceRank;
        this.color = identifyColor(pieceType);
    }

    // Abstract method to be implemented by each specific piece
    public abstract boolean isLegalMove(String from, String to, Piece[][] board);

    // Override the toString method to return a string representation
    @Override
    public String toString() {
        return pieceFile + "" + pieceRank + ": " + pieceType;
    }

    private Chess.Player identifyColor(ReturnPiece.PieceType pieceType) {
        // Check if the piece is a white piece
        if (pieceType == PieceType.WP || pieceType == PieceType.WR || pieceType == PieceType.WN || pieceType == PieceType.WB || pieceType == PieceType.WQ || pieceType == PieceType.WK) {
            return Chess.Player.white;
        }
        // Otherwise, it's a black piece
        else if (pieceType == PieceType.BP || pieceType == PieceType.BR || pieceType == PieceType.BN || pieceType == PieceType.BB || pieceType == PieceType.BK || pieceType == PieceType.BQ) {
            return Chess.Player.black;
        }
        return null;
    }

    public Chess.Player getColor() {
        return color;
    }

}
