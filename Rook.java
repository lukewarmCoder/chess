package chess;

public class Rook extends Piece {
    
    public Rook(ReturnPiece.PieceType type, ReturnPiece.PieceFile pieceFile, int rank) {
        super(type, pieceFile, rank, (type == ReturnPiece.PieceType.WR) ? pieceColor.white : pieceColor.black);
    }

    @Override
    public boolean isLegalMove(String from, String to, Piece[][] board) {

        Board boardUtil = new Board();

        int[] fromIndex = boardUtil.chessToArrayIndex(from);
        int fromRow = fromIndex[0], fromCol = fromIndex[1];

        int[] toIndex = boardUtil.chessToArrayIndex(to);
        int toRow = toIndex[0], toCol = toIndex[1];

        // Rook can only move in a straight line (same row or same column)
        if (fromRow != toRow && fromCol != toCol) {
            return false;
        }

        // Check if path is clear
        if (!isPathClear(fromRow, fromCol, toRow, toCol, board)) {
            return false;
        }

        // Check if destination is empty or contains opponent's piece
        Piece destinationPiece = board[toRow][toCol];
        if (destinationPiece == null || destinationPiece.isOpponent(this)) {
            return true;
        }

        return false;
    }

    private boolean isPathClear(int startRow, int startCol, int endRow, int endCol, Piece[][] board) {
        int rowStep = Integer.compare(endRow, startRow); // -1, 0, or 1
        int colStep = Integer.compare(endCol, startCol); // -1, 0, or 1

        int row = startRow + rowStep;
        int col = startCol + colStep;

        while (row != endRow || col != endCol) {
            if (board[row][col] != null) { // There's a piece blocking
                return false;
            }
            row += rowStep;
            col += colStep;
        }
        return true;
    }

}
