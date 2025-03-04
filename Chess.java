package chess;

// Contributors: Luke Fernandez - lmf232
//               Esha Tripathi - et522

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Chess {

    enum Player { white, black }

    private static Board board; // Shared across all methods in the Chess class. Keeps track of the game
    private static Player currentPlayer;
    
    private static final List<String> PAWN_PROMOTION_ARGS = Arrays.asList("R", "N", "B", "Q");

private static ArrayList<ReturnPiece> updatePiecesOnBoard() {
    ArrayList<ReturnPiece> piecesOnBoard = new ArrayList<>();

    // Iterate over the board and add pieces to piecesOnBoard
    for (int row = 0; row < 8; row++) {
        for (int col = 0; col < 8; col++) {
            Piece piece = board.board[row][col]; // Get the piece at (row, col)
            if (piece != null) { // Only add if there's a piece
                ReturnPiece returnPiece = new ReturnPiece();
                returnPiece.pieceType = piece.getPieceType();
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
    if (!piece.isOwnedBy(currentPlayer)) {
        System.out.println("You cannot move a piece of the other color.");
        return illegalMove(result);
    }



    // 2. Validate the move


    // Implement public boolean resultsInCheck(): returns true if player's move puts their own king in check, false otherwise
    //      - In each isLegalMove method, need to make a call to resultsInCheck()

    
    if (piece instanceof Pawn) {

        Pawn pawn = (Pawn)piece;
        boolean isLegalMove = pawn.isLegalMove(fromRow, fromCol, toRow, toCol, board.board);

        if (isLegalMove) {

            // Does the move put the player's own king in check, or doesn't get their king out of check?
            boolean putsKingInCheck = board.putsKingInCheck(fromRow, fromCol, toRow, toCol);
            if (putsKingInCheck) {
                System.out.println("That move does not get your king out of check, or its puts your king into check");
                return illegalMove(result);
            }

            // Check if it's an en passant move
            if (piece instanceof Pawn && board.board[fromRow][toCol] instanceof Pawn) {
                Pawn adjacentPawn = (Pawn) board.board[fromRow][toCol];
                if (adjacentPawn.enPassantVulnerable && adjacentPawn.getColor() != piece.getColor()) {
                    // Capture the adjacent pawn
                    board.board[fromRow][toCol] = null;  
                    if (board.putsKingInCheck(fromRow, fromCol, toRow, toCol)) {
                        board.board[fromRow][toCol] = adjacentPawn; // Undo the move
                        return illegalMove(result);
                    }
                }
            }

            board.movePiece(piece, fromRow, fromCol, toRow, toCol);

            // If double jump, make the pawn en passant vulnerable
            if (pawn.isDoubleJump(fromRow, toRow, board.board)) {
                pawn.enPassantVulnerable = true;
            } else {
                pawn.enPassantVulnerable = false;
            }

            // Check if piece qualifies for promotion
            if ((piece.getColor() == Piece.PieceColor.white && toRow == 0) || 
                (piece.getColor() == Piece.PieceColor.black && toRow == 7)) {

                // What if you do like g7 g8 draw?
                // We assume the move will be carried out, the promotion defaults to a queen, then the draw is handled

                // Default to queen if no valid third argument is given
                // Default to queen if thirdArg is "draw?"
                String promotionPiece = PAWN_PROMOTION_ARGS.contains(thirdArg) ? thirdArg : "Q";
                pawn.promotePawn(promotionPiece, toRow, toCol, board.board);
            
            }
        } else {
            System.out.println("Invalid pawn movement");
            return illegalMove(result);
        }


    } else if (piece instanceof Rook) {

        Rook rook = (Rook)piece;
        boolean isLegalMove = rook.isLegalMove(fromRow, fromCol, toRow, toCol, board.board);

        if (isLegalMove) {

            // Does the move put the player's own king in check?
            boolean putsKingInCheck = board.putsKingInCheck(fromRow, fromCol, toRow, toCol);
            if (putsKingInCheck) {
                System.out.println("That move does not get your king out of check, or its puts your king into check");
                return illegalMove(result);
            }

            board.movePiece(piece, fromRow, fromCol, toRow, toCol);
            rook.hasMovedOnce = true;
            
        } else {
            return illegalMove(result);
        }

    } else if (piece instanceof Knight) {

        Knight knight = (Knight)piece;
        boolean isLegalMove = knight.isLegalMove(fromRow, fromCol, toRow, toCol, board.board);

        if (isLegalMove) {

            // Does the move put the player's own king in check?
            boolean putsKingInCheck = board.putsKingInCheck(fromRow, fromCol, toRow, toCol);
            if (putsKingInCheck) {
                System.out.println("That move does not get your king out of check, or its puts your king into check");
                return illegalMove(result);
            }

            board.movePiece(piece, fromRow, fromCol, toRow, toCol);
        } else {
            return illegalMove(result);
        }

    } else if (piece instanceof Bishop) {

        Bishop bishop = (Bishop)piece;
        boolean isLegalMove = bishop.isLegalMove(fromRow, fromCol, toRow, toCol, board.board);

        if (isLegalMove) {

            // Does the move put the player's own king in check?
            boolean putsKingInCheck = board.putsKingInCheck(fromRow, fromCol, toRow, toCol);
            if (putsKingInCheck) {
                System.out.println("That move does not get your king out of check, or its puts your king into check");
                return illegalMove(result);
            }

            board.movePiece(piece, fromRow, fromCol, toRow, toCol);
        } else {
            return illegalMove(result);
        }

    } else if (piece instanceof Queen) {

        Queen queen = (Queen)piece;
        boolean isLegalMove = queen.isLegalMove(fromRow, fromCol, toRow, toCol, board.board);

        if (isLegalMove) {

            // Does the move put the player's own king in check?
            boolean putsKingInCheck = board.putsKingInCheck(fromRow, fromCol, toRow, toCol);
            if (putsKingInCheck) {
                System.out.println("That move does not get your king out of check, or its puts your king into check");
                return illegalMove(result);
            }

            board.movePiece(piece, fromRow, fromCol, toRow, toCol);
        } else {
            return illegalMove(result);
        }

    } else if (piece instanceof King) {

        King king = (King)piece;

        // Check for castling
        if (fromCol == 4 && Math.abs(toCol - fromCol) == 2) { // Castling move (king moves 2 squares)
           // Check if the King has moved
            if (king.hasMovedOnce) {
                System.out.println("King has already moved");
                return illegalMove(result);
            }

            int rookCol = (toCol == 6) ? 7 : 0; // Rook's column
            Rook rook = (Rook)board.getPiece(fromRow, rookCol);

            if (rook == null || rook.hasMovedOnce) {
                System.out.println("Rook has already moved or doesn't exist.");
                return illegalMove(result);
            }

            // Check if the King is currently in check
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    Piece pieces = board.board[i][j];
                    if (pieces != null && pieces.getColor() != piece.getColor()) {
                        if (pieces.isLegalMove(i, j, fromRow, fromCol, board.board)) {
                            System.out.println("You cannot castle while your king is in check");
                            return illegalMove(result); // Return if a piece can attack the king
                        }
                    }
                }
            }

            // Check if the squares between the King and Rook are clear
            int direction = (toCol == 6) ? 1 : -1; // Rook direction (right for kingside, left for queenside)
            for (int i = fromCol + direction; i != rookCol; i += direction) {
                if (board.getPiece(fromRow, i) != null) {
                    System.out.println("There are pieces between the king and rook");
                    return illegalMove(result);
                }
            }

            // Check if the King moves through check
            direction = (toCol == 6) ? 1 : -1;
            for (int i = fromCol + direction; i != toCol + direction; i += direction) {
                if (board.putsKingInCheck(fromRow, fromCol, toRow, i)) {
                    System.out.println("King passes over (or lands on) a square that can be attacked");
                    return illegalMove(result);
                }
            }

            // Move the King and the Rook
            board.movePiece(king, fromRow, fromCol, toRow, toCol);
            board.movePiece(rook, fromRow, rookCol, toRow, toCol - direction);
            
            king.hasMovedOnce = true;
            rook.hasMovedOnce = true;
        } else {
            // Handle regular King move
            boolean isLegalMove = king.isLegalMove(fromRow, fromCol, toRow, toCol, board.board);

            if (isLegalMove) {

                // Does the move put the player's own king in check?
                boolean putsKingInCheck = board.putsKingInCheck(fromRow, fromCol, toRow, toCol);
                if (putsKingInCheck) {
                    System.out.println("That move does not get your king out of check, or its puts your king into check");
                    return illegalMove(result);
                }

                board.movePiece(piece, fromRow, fromCol, toRow, toCol);
                king.hasMovedOnce = true;
            } else {
                return illegalMove(result);
            }
        }
    }
    


    

    // Handle draw
    // Assuming draw should occur before a checkmate.
    if (thirdArg.equals("draw?")) {
        result.message = ReturnPlay.Message.DRAW;
        currentPlayer = (currentPlayer == Player.white) ? Player.black : Player.white;
        result.piecesOnBoard = updatePiecesOnBoard();
        return result;
    }


    Piece.PieceColor opposingPlayerColor = (currentPlayer == Player.white) ? Piece.PieceColor.black : Piece.PieceColor.white;
    if (board.isCheckmate(opposingPlayerColor, piece)) {
        result.message = (currentPlayer == Player.white) ? 
            ReturnPlay.Message.CHECKMATE_WHITE_WINS : 
            ReturnPlay.Message.CHECKMATE_BLACK_WINS;
        result.piecesOnBoard = updatePiecesOnBoard();
        return result;
    }


    // Check if the current player's move put the opponent's king in check
    if (board.putsOpponentInCheck( (currentPlayer == Player.white) ? Piece.PieceColor.black : Piece.PieceColor.white)) {
        result.message = ReturnPlay.Message.CHECK;
    }



    // 4. Switch turns
    currentPlayer = (currentPlayer == Player.white) ? Player.black : Player.white;

    // Return updated state of board
    result.piecesOnBoard = updatePiecesOnBoard();

    return result;
}


/**
 * This method should reset the game, and start from scratch.
 */
public static void start() {

    board = new Board(); // Initialize a new game board
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

}

}