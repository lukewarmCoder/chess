package chess;

public class Queen extends Piece{

    public Queen(ReturnPiece.PieceType type, ReturnPiece.PieceFile pieceFile, int rank) {
        super(type, pieceFile, rank, (type == ReturnPiece.PieceType.WQ) ? PieceColor.white : PieceColor.black);
    }

    @Override
    public Piece copy() {
        return new Queen(this.getPieceType(), this.getPieceFile(), this.getPieceRank());
    }

    @Override
    public boolean isLegalMove(int fromRow, int fromCol, int toRow, int toCol, Piece[][] board) {

        // Check if the move is either diagonal (like a bishop) or straight (like a rook)
        boolean isDiagonalMove = Math.abs(fromRow - toRow) == Math.abs(fromCol - toCol);
        boolean isStraightMove = (fromRow == toRow || fromCol == toCol);

        if (!isDiagonalMove && !isStraightMove) {
            return false; // Invalid move for a queen
        }
 
        // Check if the path is clear
        if (!isPathClear(fromRow, fromCol, toRow, toCol, board)) {
            return false; // Path is blocked
        }

        // Check if the destination is occupied by an opponent or empty
        Piece destinationPiece = board[toRow][toCol];

        if (destinationPiece != null && !destinationPiece.isOpponent(this)) {
            return false; // Cannot capture a piece of the same color
        }

    

        return true; // Valid queen move
    
    }

    private boolean isPathClear(int fromRow, int fromCol, int toRow, int toCol, Piece[][] board) {
        int rowStep = Integer.compare(toRow, fromRow); // -1, 0, or 1 (direction of row movement)
        int colStep = Integer.compare(toCol, fromCol); // -1, 0, or 1 (direction of column movement)

        int currentRow = fromRow + rowStep;
        int currentCol = fromCol + colStep;

        while (currentRow != toRow || currentCol != toCol) {
            if (board[currentRow][currentCol] != null) {
                return false; // Path is blocked
            }
            currentRow += rowStep;
            currentCol += colStep;
        }

        return true; // Path is clear
    }


}
