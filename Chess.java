package chess;

// Contributors: Luke Fernandez - lmf232
//               Esha Tripathi - et522


import java.util.ArrayList;
import java.util.Arrays;

public class Chess {

    enum Player { white, black }

    private static Board board; // Shared across all methods in the Chess class. Keeps track of the game
    private static Player currentPlayer = Player.white; // Start with white's turn


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

// Invalid operation: return board unchanged
private static ReturnPlay invalidOperation(ReturnPlay result) {
    System.out.println("Error: Invalid operation");
    result.piecesOnBoard = updatePiecesOnBoard();
    return result;
}

// Trim whitespace from the input string
private static String trim(String s) {
    return s.trim();
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
    // Outsource to function?
    // Add check for more than 3 arguments
    // Add check for file numbers greater than 8

    move = trim(move); // Trim whitespace from the input string
    String[] moveParts = move.split(" "); // Split the move into parts

    if (moveParts.length < 2 || moveParts.length > 3) {
        return invalidOperation(result);
    }

    String from;
    String to;
    String action; // Not handled yet

    from = moveParts[0];
    to = moveParts[1];

    if (moveParts.length == 3) {    
        action = moveParts[2];
    }


    // Get the piece trying to be moved.
    Piece piece = board.getPiece(board.chessToArrayIndex(from));
    
    // Check if the player is moving the correct color piece
    if (piece.getColor() != currentPlayer) {
        System.out.println("You cannot move that piece.");
        return invalidOperation(result);
    }


    // 2. Validate the move

    if (piece instanceof Pawn) {
        Pawn pawn = (Pawn)piece;

        boolean isLegalMove = pawn.isLegalMove(from, to, board.board); // 

        System.exit(0);

        if (isLegalMove) {
            // Do something
            // board.board[fromRank][fromFile] = null;  // Remove the piece from the start
            // board.board[toRank][toFile] = piece;    // Place the piece at the destination
        } else {
            // illegal move
        }

    } else if (piece instanceof Rook) {

    } else if (piece instanceof Knight) {

    } else if (piece instanceof Bishop) {

    } else if (piece instanceof Queen) {

    } else if (piece instanceof King) {

    }

    
        // Send the move instructions to the pawn class
        

        // If not valid, return null


        
        


    // 3. Update the board




    // 4. Switch turns



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


    // ReturnPiece piece = new ReturnPiece();
    // piece.pieceType = pawn.pieceType;
    // piece.pieceFile = pawn.pieceFile;
    // piece.pieceRank = pawn.pieceRank;

    
    // ReturnPlay result = new ReturnPlay();
    // result.piecesOnBoard = new ArrayList<>(); // Initialize the list    

    // // Create a test piece (e.g., White Pawn at e2)
    // ReturnPiece testPiece = new ReturnPiece();
    // testPiece.pieceType = ReturnPiece.PieceType.WP; // White Pawn
    // testPiece.pieceFile = ReturnPiece.PieceFile.f;  // Column 'e'
    // testPiece.pieceRank = 4;                        // Row 2

    // result.piecesOnBoard.add(testPiece); // Add to list

    // System.out.println(result.piecesOnBoard);

    // PlayChess.printBoard(result.piecesOnBoard);
    

}

}