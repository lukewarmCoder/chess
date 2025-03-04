package chess;

public class Pawn extends Piece {

    public boolean enPassantVulnerable;
    
    public Pawn(ReturnPiece.PieceType type, ReturnPiece.PieceFile pieceFile, int rank) {
        super(type, pieceFile, rank, (type == ReturnPiece.PieceType.WP) ? PieceColor.white : PieceColor.black);
        enPassantVulnerable = false;
    }

    @Override
    public Piece copy() {
        return new Pawn(this.getPieceType(), this.getPieceFile(), this.getPieceRank());
    }

    @Override
    public boolean isLegalMove(int fromRow, int fromCol, int toRow, int toCol, Piece[][] board) {

        // Determine the pawn's direction based on color
        int direction = (this.getColor() == PieceColor.white) ? -1 : 1; // White moves up (-1), Black moves down (+1)

        // One square forward
        if (toCol == fromCol && toRow == fromRow + direction && board[toRow][toCol] == null) {
            return true;
        }

        // Double jump (only from starting position)
        int startingRow = (this.getColor() == PieceColor.white) ? 6 : 1;
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

            // En passant check
            if (board[fromRow][toCol] instanceof Pawn) {
                Pawn adjacentPawn = (Pawn)board[fromRow][toCol];
                if (adjacentPawn.enPassantVulnerable && adjacentPawn.getColor() != this.getColor()) {
                    return true;
                }
            }
        }


        return false;
    }

    public boolean isDoubleJump(int fromRow, int toRow, Piece[][] board) {
        return Math.abs(toRow - fromRow) == 2;
    }

    public void promotePawn(String requestedPiece, int toRow, int toCol, Piece[][] board) {
        Piece newPiece;

        newPiece = switch (requestedPiece) {
            case "R" -> new Rook( ((this.getColor() == PieceColor.white) ? ReturnPiece.PieceType.WR : ReturnPiece.PieceType.BR), this.getPieceFile(), this.getPieceRank());
            case "N" -> new Knight( ((this.getColor() == PieceColor.white) ? ReturnPiece.PieceType.WN : ReturnPiece.PieceType.BN), this.getPieceFile(), this.getPieceRank());
            case "B" -> new Bishop( ((this.getColor() == PieceColor.white) ? ReturnPiece.PieceType.WB : ReturnPiece.PieceType.BB), this.getPieceFile(), this.getPieceRank());
            default  -> new Queen( ((this.getColor() == PieceColor.white) ? ReturnPiece.PieceType.WQ : ReturnPiece.PieceType.BQ), this.getPieceFile(), this.getPieceRank());
        };
        
        board[toRow][toCol] = null; // Remove the pawn from the board
        board[toRow][toCol] = newPiece; // Add the newly promoted piece

        System.out.println("Piece swapped in: " + newPiece);
    }
}