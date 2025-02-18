package chess;

// Contributors: Luke Fernandez - lmf232
//               Esha Tripathi - et522


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import chess.Piece.pieceColor;

public class Chess {

    enum Player { white, black }

    private static Board board; // Shared across all methods in the Chess class. Keeps track of the game
    private static Player currentPlayer;
    
    private static final List<String> PAWN_PROMOTION_ARGS = Arrays.asList("R", "N", "B", "Q");
    public static ArrayList<Piece> capturedPieces;

private static ArrayList<ReturnPiece> updatePiecesOnBoard() {
    ArrayList<ReturnPiece> piecesOnBoard = new ArrayList<>();

    // Iterate over the board and add pieces to piecesOnBoard
    for (int row = 0; row < 8; row++) {
        for (int col = 0; col < 8; col++) {
            Piece piece = board.board[row][col]; // Get the piece at (row, col)
            if (piece != null) { // Only add if there's a piece
                ReturnPiece returnPiece = new ReturnPiece();
                returnPiece.pieceType = piece.pieceType;
                returnPiece.pieceFile = ReturnPiece.PieceFile.values()[col]; // Map column to file ('a' - 'h')
                returnPiece.pieceRank = 8 - row; // Flip row to match chess notation (1-8)
                piecesOnBoard.add(returnPiece);
            }
        }
    }
    return piecesOnBoard;
}

private static boolean isValidSquare(String FileRank) {

    // Ensure input is exactly 2 characters to avoid exception error
    if (FileRank == null || FileRank.length() != 2) {
        return false;
    }

    char file = FileRank.charAt(0);
    char rank = FileRank.charAt(1);
    
    return (file >= 'a' && file <= 'h') && (rank >= '1' && rank <= '8');
}

private static boolean isValidOperation(String[] input) {

    // Must have 1-3 parts
    if (input.length < 1 || input.length > 3) {
        return false;
    }

    // Single-word commands: Only "resign" is allowed
    if (input.length == 1) {
        return input[0].equals("resign");
    }

    // Validate first and second arguments as valid squares
    if (!isValidSquare(input[0]) || !isValidSquare(input[1])) {
        return false;
    }

    // Third argument (if present) must be in the predefined valid set
    if (input.length == 3) {
        return PAWN_PROMOTION_ARGS.contains(input[2]) || input[2].equals("draw?");
    }

    return true;
}

private static ReturnPlay illegalMove(ReturnPlay result) {
    result.piecesOnBoard = updatePiecesOnBoard();
    result.message = ReturnPlay.Message.ILLEGAL_MOVE;
    return result;
}

/**
 * Plays the next move for whichever player has the turn.
 * 
 * @param move String for next move, e.g. "a2 a3"
 * 
 * @return A ReturnPlay instance that contains the result of the move.
 *         See the section "The Chess class" in the assignment description for details of
 *         the contents of the returned ReturnPlay instance.
 */

public static ReturnPlay play(String move) {

    ReturnPlay result = new ReturnPlay();

    // 1. Parse the move

    move = move.trim(); // Trim whitespace from the input string
    String[] moveParts = move.split(" "); // Split the move into parts

    // Validate the operation
    if (!isValidOperation(moveParts)) {
        System.out.println("Invalid input");
        return illegalMove(result);
    }

    // Handle resign -- return board unchanged
    if (moveParts.length == 1) {
        result.piecesOnBoard = updatePiecesOnBoard();
        result.message = (currentPlayer == Player.white) ? ReturnPlay.Message.RESIGN_BLACK_WINS : ReturnPlay.Message.RESIGN_WHITE_WINS;
        return result;
    }

    // Convert FileRank to array index
    String from = moveParts[0], to = moveParts[1];

    int[] fromIndex = board.chessToArrayIndex(from);
    int fromRow = fromIndex[0];
    int fromCol = fromIndex[1];

    int[] toIndex = board.chessToArrayIndex(to);
    int toRow = toIndex[0];
    int toCol = toIndex[1];

    String thirdArg = "";
    if (moveParts.length == 3) {
        thirdArg = moveParts[2];
    }


    // Check if there is actually a piece at the starting square
    if (board.board[fromRow][fromCol] == null) {
        System.out.println("There's no piece there.");
        return illegalMove(result);
    }

    // Get the piece trying to be moved.
    Piece piece = board.getPiece(fromRow, fromCol);
    
    // Check if the player is moving the correct color piece
    if (!(piece.color.name()).equals(currentPlayer.name())) {
        System.out.println("You cannot move a piece of the other color.");
        return illegalMove(result);
    }


    // 2. Validate the move



    // - Outsource redundant code?
    // - Implement illegal_move method in Chess class (returns a ReturnPlay object)
    // - Implement movePiece method in board class
    //      - needs to return any captured pieces

    // Implement public boolean resultsInCheck(): returns true if player's move puts their own king in check, false otherwise
    //      - In each isLegalMove method, need to make a call to resultsInCheck()

    
    // Pawn needs more testing
    if (piece instanceof Pawn) {

        Pawn pawn = (Pawn)piece;
        boolean isLegalMove = pawn.isLegalMove(fromRow, fromCol, toRow, toCol, board.board);

        if (isLegalMove) {
            board.movePiece(piece, fromRow, fromCol, toRow, toCol);

            // Check if piece qualifies for promotion
            if ((piece.color == pieceColor.white && toRow == 0) || 
                (piece.color == pieceColor.black && toRow == 7)) {

                // What if you do like g7 g8 draw?
                //    - For now, we assume the move will be carried out, the promotion defaults to a queen, then the draw is handled

                // Default to queen if no valid third argument is given
                // Default to queen if thirdArg is "draw?"
                String promotionPiece = PAWN_PROMOTION_ARGS.contains(thirdArg) ? thirdArg : "Q";
                boolean isLegalPromotion = pawn.promotePawn(promotionPiece, toRow, toCol, board.board);
                
                if (!isLegalPromotion) {
                    System.out.println("promotion failed, results in check");
                    return illegalMove(result);
                }
                System.out.println("promotion success");
            }
        } else {
            System.out.println("Invalid pawn movement");
            return illegalMove(result);
        }


    } else if (piece instanceof Rook) {

        Rook rook = (Rook)piece;
        boolean isLegalMove = rook.isLegalMove(fromRow, fromCol, toRow, toCol, board.board);

        if (isLegalMove) {
            board.movePiece(piece, fromRow, fromCol, toRow, toCol);
            rook.hasMovedOnce = true;
        } else {
            return illegalMove(result);
        }

    } else if (piece instanceof Knight) {

        Knight knight = (Knight)piece;
        boolean isLegalMove = knight.isLegalMove(fromRow, fromCol, toRow, toCol, board.board);

        if (isLegalMove) {
            board.movePiece(piece, fromRow, fromCol, toRow, toCol);
        } else {
            return illegalMove(result);
        }

    } else if (piece instanceof Bishop) {

        Bishop bishop = (Bishop)piece;
        boolean isLegalMove = bishop.isLegalMove(fromRow, fromCol, toRow, toCol, board.board);

        if (isLegalMove) {
            board.movePiece(piece, fromRow, fromCol, toRow, toCol);
        } else {
            return illegalMove(result);
        }

    } else if (piece instanceof Queen) {

        Queen queen = (Queen)piece;
        boolean isLegalMove = queen.isLegalMove(fromRow, fromCol, toRow, toCol, board.board);

        if (isLegalMove) {
            board.movePiece(piece, fromRow, fromCol, toRow, toCol);
        } else {
            return illegalMove(result);
        }

    } else if (piece instanceof King) {

        King king = (King)piece;
        boolean isLegalMove = king.isLegalMove(fromRow, fromCol, toRow, toCol, board.board);

        if (isLegalMove) {
            board.movePiece(piece, fromRow, fromCol, toRow, toCol);
            king.hasMovedOnce = true;
        } else {
            return illegalMove(result);
        }

    }




    // 4. Switch turns
    // currentPlayer = (currentPlayer == Player.white) ? Player.black : Player.white;

    // Handle draw
    if (thirdArg.equals("draw?")) {
        result.message = ReturnPlay.Message.DRAW;
    }

    // 5. Return updated state of board
    result.piecesOnBoard = updatePiecesOnBoard();
    
    // But we need to declare and initialize an ArrayList<ReturnPiece> type in Chess.java, 
    // then can add pieces to it, then set it to the piecesOnBoard field of a ReturnPlay object.

    return result;
}


/**
 * This method should reset the game, and start from scratch.
 */
public static void start() {

    board = new Board(); // Initialize a new game board
    capturedPieces = new ArrayList<>();
    currentPlayer = Player.white; // Start with white's turn
    
    // Add white pieces
    for (int i = 0; i < 8; i++) {
        board.addPiece(new Pawn(ReturnPiece.PieceType.WP, ReturnPiece.PieceFile.values()[i], 2));
    }

    board.addPiece(new Rook(ReturnPiece.PieceType.WR, ReturnPiece.PieceFile.a, 1));
    board.addPiece(new Knight(ReturnPiece.PieceType.WN, ReturnPiece.PieceFile.b, 1));
    board.addPiece(new Bishop(ReturnPiece.PieceType.WB, ReturnPiece.PieceFile.c, 1));
    board.addPiece(new Queen(ReturnPiece.PieceType.WQ, ReturnPiece.PieceFile.d, 1));
    board.addPiece(new King(ReturnPiece.PieceType.WK, ReturnPiece.PieceFile.e, 1));
    board.addPiece(new Bishop(ReturnPiece.PieceType.WB, ReturnPiece.PieceFile.f, 1));
    board.addPiece(new Knight(ReturnPiece.PieceType.WN, ReturnPiece.PieceFile.g, 1));
    board.addPiece(new Rook(ReturnPiece.PieceType.WR, ReturnPiece.PieceFile.h, 1));

    // Add black pieces
    for (int i = 0; i < 8; i++) {
        board.addPiece(new Pawn(ReturnPiece.PieceType.BP, ReturnPiece.PieceFile.values()[i], 7));
    }

    board.addPiece(new Rook(ReturnPiece.PieceType.BR, ReturnPiece.PieceFile.a, 8));
    board.addPiece(new Knight(ReturnPiece.PieceType.BN, ReturnPiece.PieceFile.b, 8));
    board.addPiece(new Bishop(ReturnPiece.PieceType.BB, ReturnPiece.PieceFile.c, 8));
    board.addPiece(new Queen(ReturnPiece.PieceType.BQ, ReturnPiece.PieceFile.d, 8));
    board.addPiece(new King(ReturnPiece.PieceType.BK, ReturnPiece.PieceFile.e, 8));
    board.addPiece(new Bishop(ReturnPiece.PieceType.BB, ReturnPiece.PieceFile.f, 8));
    board.addPiece(new Knight(ReturnPiece.PieceType.BN, ReturnPiece.PieceFile.g, 8));
    board.addPiece(new Rook(ReturnPiece.PieceType.BR, ReturnPiece.PieceFile.h, 8));

    // board.printBoard();

}

}