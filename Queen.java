package chess;

public class Queen extends Piece{

    public Queen(ReturnPiece.PieceType type, ReturnPiece.PieceFile pieceFile, int rank) {
        super(type, pieceFile, rank, (type == ReturnPiece.PieceType.WQ) ? pieceColor.white : pieceColor.black);
    }

    @Override
    public boolean isLegalMove(String from, String to, Piece[][] board) {

        Board boardUtil = new Board();

        int[] fromIndex = boardUtil.chessToArrayIndex(from);
        int fromRow = fromIndex[0], fromCol = fromIndex[1];

        int[] toIndex = boardUtil.chessToArrayIndex(to);
        int toRow = toIndex[0], toCol = toIndex[1];
        
        return false;
    }
}
