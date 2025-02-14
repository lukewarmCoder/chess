package chess;

public class Knight extends Piece {
    
    public Knight(ReturnPiece.PieceType type, ReturnPiece.PieceFile pieceFile, int rank) {
        super(type, pieceFile, rank, (type == ReturnPiece.PieceType.WN) ? pieceColor.white : pieceColor.black);
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
