package chess;

public class Pawn extends Piece {
    
    public Pawn(ReturnPiece.PieceType type, ReturnPiece.PieceFile pieceFile, int rank) {
        super(type, pieceFile, rank, (type == ReturnPiece.PieceType.WP) ? pieceColor.white : pieceColor.black);
    }

    @Override
    public boolean isLegalMove(int fromRow, int fromCol, int toRow, int toCol, Piece[][] board) {

        // Determine the pawn's direction based on color
        int direction = (this.color == pieceColor.white) ? -1 : 1; // White moves up (-1), Black moves down (+1)

        // One square forward
        if (toCol == fromCol && toRow == fromRow + direction && board[toRow][toCol] == null) {
            return true;
        }

        // Double jump (only from starting position)
        int startingRow = (this.color == pieceColor.white) ? 6 : 1;
        if (fromRow == startingRow && toCol == fromCol && toRow == fromRow + 2 * direction 
            && board[toRow][toCol] == null && board[fromRow + direction][toCol] == null) {
            return true;
        }

        // Capture: Moving diagonally if there's an opponent piece
        if (Math.abs(toCol - fromCol) == 1 && toRow == fromRow + direction) {
            Piece destinationPiece = board[toRow][toCol];
            if (destinationPiece != null && destinationPiece.isOpponent(this)) {
                return true;
            }
        }

        // Call to resultsInCheck()

        return false;
    }


    // Given the generic letter of a piece (i.e. "R" for rook), returns the appropriate, colored PieceType
    private ReturnPiece.PieceType stringToPieceType(String requestedPiece, pieceColor color) {
        ReturnPiece.PieceType pieceType = ReturnPiece.PieceType.WR; // Default initialization

        if (requestedPiece.equals("R")) {
            pieceType = (color == pieceColor.white) ? ReturnPiece.PieceType.WR : ReturnPiece.PieceType.BR;
        } else if (requestedPiece.equals("N")) {
            pieceType = (color == pieceColor.white) ? ReturnPiece.PieceType.WN : ReturnPiece.PieceType.BN;
        } else if (requestedPiece.equals("B")) {
            pieceType = (color == pieceColor.white) ? ReturnPiece.PieceType.WB : ReturnPiece.PieceType.BB;
        } else if (requestedPiece.equals("P")) {
            pieceType = (color == pieceColor.white) ? ReturnPiece.PieceType.WP : ReturnPiece.PieceType.BP;
        } else if (requestedPiece.equals("Q")) {
            pieceType = (color == pieceColor.white) ? ReturnPiece.PieceType.WQ : ReturnPiece.PieceType.BQ;
        } else {
            pieceType = (color == pieceColor.white) ? ReturnPiece.PieceType.WQ : ReturnPiece.PieceType.BQ;
        }

        return pieceType;
    }

    public boolean isLegalPromotion(Piece piece, String requestedPiece, Piece[][] board) {
        
        ReturnPiece.PieceType requestedPieceType = stringToPieceType(requestedPiece, piece.getColor());

        System.out.println(requestedPieceType);

        // Call to resultsInCheck()

        return false;
    }

    
}
