package chess;

public class Board {

    public Piece[][] board;

    public Board() {
        board = new Piece[8][8];
    }

    // Converts FileRank (a2) to array index (6, 0)
    public int[] chessToArrayIndex(String FileRank) {
        int row = 8 - Integer.parseInt(FileRank.charAt(1) + ""); // Convert rank 1 to row 7, rank 8 to row 0
        int col = FileRank.charAt(0) - 'a'; // Convert 'a' to 0, 'b' to 1, ...
        return new int[] {row, col};
    }

    // Add pieces to the board using FileRank (a1)
    public void addPiece(Piece piece) {
        String FileRank = piece.getPieceFile().name() + piece.getPieceRank(); // Combines file and rank into one string
        int[] index = chessToArrayIndex(FileRank); // Converts FileRank to an array index
        board[index[0]][index[1]] = piece;
    }

    // Add pieces to the board using an array index
    public void addPiece(int row, int col, Piece piece) {
        board[row][col] = piece;
    }

    // Return piece given an array index
    public Piece getPiece(int row, int col) {
        return board[row][col];
    }

    // Get array index location given a piece
    public int[] getPiecePosition(Piece piece) {
        String FileRank = piece.getPieceFile().name() + piece.getPieceRank(); // Combines file and rank into one string
        int[] index = chessToArrayIndex(FileRank); // Converts FileRank to an array index
        return index;
    }

    // Used only after move is validated!
    public void movePiece(Piece piece, int fromRow, int fromCol, int toRow, int toCol) {

        board[toRow][toCol] = piece;
        board[fromRow][fromCol] = null;

        // Update the piece's file and rank
        piece.setPieceFile(ReturnPiece.PieceFile.values()[toCol]);
        piece.setPieceRank(8 - toRow);
    }

    public Board copy() {
        Board newBoard = new Board();

        // Copy each piece on the board 
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                Piece piece = this.getPiece(x, y);
                if (piece != null) {
                    newBoard.addPiece(x, y, piece.copy());
                }
            }
        }
        return newBoard;
    }

    public void printBoard() {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if (board[row][col] == null) {
                    System.out.print("empty\t");
                } else {
                    System.out.print(" " + board[row][col].getPieceType() + " " + "\t");
                }
            }
            System.out.println();
        }
    }

    private int[] findKing(ReturnPiece.PieceType pieceType) {
        // Search the board for the king's position
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Piece piece = board[i][j];
                if (piece != null && piece.getPieceType() == pieceType) {
                    return new int[] {i, j}; 
                }
            }
        }
        return null;
    }

    // Check if a player's move puts the opponent in check 
    public boolean putsOpponentInCheck(Piece.PieceColor opposingPlayerColor) {

        ReturnPiece.PieceType targetPieceType = (opposingPlayerColor == Piece.PieceColor.white) 
            ? ReturnPiece.PieceType.WK 
            : ReturnPiece.PieceType.BK;

        // Locate the oppposing player's king
        int[] kingCoords = this.findKing(targetPieceType);
        if (kingCoords == null || kingCoords.length < 2) {
            return false; // No king found, can't be in check
        }

        int kingX = kingCoords[0];
        int kingY = kingCoords[1];

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Piece piece = board[i][j];
                if (piece != null && piece.getColor() != opposingPlayerColor) {
                    if (piece.isLegalMove(i, j, kingX, kingY, board)) {
                        return true; // Return true if a piece can attack the king
                    }
                }
            }
        }

        return false;
    }

    // Check if a player's move puts their own king in check
    public boolean putsKingInCheck(int fromRow, int fromCol, int toRow, int toCol) {

        // Get current player's color and king piece type
        Piece.PieceColor currentPlayerColor = getPiece(fromRow, fromCol).getColor();
        ReturnPiece.PieceType targetPieceType = (currentPlayerColor == Piece.PieceColor.white) 
            ? ReturnPiece.PieceType.WK 
            : ReturnPiece.PieceType.BK;

        // Make a copy of the board and simulate the move
        Board simulateBoard = this.copy();
        simulateBoard.movePiece(getPiece(fromRow, fromCol), fromRow, fromCol, toRow, toCol);

        // Locate the current player's king
        int[] kingCoords = simulateBoard.findKing(targetPieceType);
        if (kingCoords == null || kingCoords.length < 2) {
            return false; // No king found, can't be in check
        }

        int kingX = kingCoords[0];
        int kingY = kingCoords[1];        
    
        // Iterate through all opponent pieces and check if any can attack the king
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Piece piece = simulateBoard.board[i][j];
                if (piece != null && piece.getColor() != currentPlayerColor) {
                    if (piece.isLegalMove(i, j, kingX, kingY, simulateBoard.board)) {
                        return true; // Return true if a piece can attack the king
                    }
                }
            }
        }
        return false; // Assume king is not in check by default
    }



    // Called right after a player makes a move, to check if the opposing player is in checkmate.
    public boolean isCheckmate(Piece.PieceColor opposingPlayerColor, Piece attackingPiece) {

        // Try every possible move for every piece of the opponent
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Piece piece = board[i][j];

                // If it's an opponent's piece, try moving it everywhere
                if (piece != null && piece.getColor() == opposingPlayerColor) {

                    for (int toRow = 0; toRow < 8; toRow++) {
                        for (int toCol = 0; toCol < 8; toCol++) {
                            if (piece.isLegalMove(i, j, toRow, toCol, board)) {
                                // Simulate the move
                                Board simulatedBoard = this.copy();
                                simulatedBoard.movePiece(simulatedBoard.getPiece(i, j), i, j, toRow, toCol);

                                // If after this move, the king is not in check, it's not checkmate
                                if (!simulatedBoard.putsOpponentInCheck(opposingPlayerColor)) {
                                    return false;
                                }
                            }
                        }
                    }
                }
            }
        }

        // No valid move found, must be checkmate
        return true;
    }

}