package chess;

public class Bishop extends Piece {

    public Bishop(ReturnPiece.PieceType type, ReturnPiece.PieceFile pieceFile, int rank) {
        super(type, pieceFile, rank, (type == ReturnPiece.PieceType.WB) ? pieceColor.white : pieceColor.black);
    } 

    @Override
    public boolean isLegalMove(int fromRow, int fromCol, int toRow, int toCol, Piece[][] board) {

        // Bishop moves diagonally: |rowDiff| == |colDiff|
        if (Math.abs(fromRow - toRow) != Math.abs(fromCol - toCol)) {
            return false;
        }

        // Check if the path is clear (no pieces in the way)
        if (!isPathClear(fromRow, fromCol, toRow, toCol, board)) {
            return false;
        }

        // Check if the destination contains the same color piece
        Piece destinationPiece = board[toRow][toCol];
        if (destinationPiece != null && !destinationPiece.isOpponent(this)) {
            return false;
        }

        return true;
    }

    private boolean isPathClear(int startRow, int startCol, int endRow, int endCol, Piece[][] board) {
        int rowStep = Integer.compare(endRow, startRow); // -1, 0, or 1
        int colStep = Integer.compare(endCol, startCol); // -1, 0, or 1

        int row = startRow + rowStep;
        int col = startCol + colStep;

        // Check all the squares between the start and end position
        while (row != endRow && col != endCol) {
            if (board[row][col] != null) { // There's a piece blocking the path
                return false;
            }
            row += rowStep;
            col += colStep;
        }

        return true;
    }

}
