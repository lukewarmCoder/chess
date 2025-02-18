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

        return false;
    }

    public boolean promotePawn(String requestedPiece, int toRow, int toCol, Piece[][] board) {
        Piece newPiece;

        newPiece = switch (requestedPiece) {
            case "R" -> new Rook( ((this.color == pieceColor.white) ? ReturnPiece.PieceType.WR : ReturnPiece.PieceType.BR), this.pieceFile, this.pieceRank);
            case "N" -> new Knight( ((this.color == pieceColor.white) ? ReturnPiece.PieceType.WN : ReturnPiece.PieceType.BN), this.pieceFile, this.pieceRank);
            case "B" -> new Bishop( ((this.color == pieceColor.white) ? ReturnPiece.PieceType.WB : ReturnPiece.PieceType.BB), this.pieceFile, this.pieceRank);
            default  -> new Queen( ((this.color == pieceColor.white) ? ReturnPiece.PieceType.WQ : ReturnPiece.PieceType.BQ), this.pieceFile, this.pieceRank);
        };
        
        board[toRow][toCol] = null; // Remove the pawn from the board
        board[toRow][toCol] = newPiece; // Add the newly promoted piece
        
        // if (!resultsInCheck()) {
        //      return false;
        //}
        
        return true;
    }
}