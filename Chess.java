package chess;

// Contributors: Luke Fernandez - lmf232
//               Esha Tripathi - et522


import java.util.ArrayList;
import java.util.Arrays;

public class Chess {

    enum Player { white, black }

    private static Board board; // Shared across all methods in the Chess class. This keeps track of the game

/**
 * Plays the next move for whichever player has the turn.
 * 
 * @param move String for next move, e.g. "a2 a3"
 * 
 * @return A ReturnPlay instance that contains the result of the move.
 *         See the section "The Chess class" in the assignment description for details of
 *         the contents of the returned ReturnPlay instance.
 */

// Trim whitespace from the input string
private static String trim(String s) {
    return s.trim();
}

public static ReturnPlay play(String move) {

    move = trim(move); // Trim whitespace from the input string
    
    // Split the move into parts
    String[] move_parts = move.split(" ");
    // System.out.println(Arrays.toString(move_parts));
    
    ReturnPlay result = new ReturnPlay();
    // So the piecesOnBoard is just a field of the ReturnPlay object
    
    // But we need to declare and initialize an ArrayList<ReturnPiece> type in Chess.java, 
    // then can add pieces to it, then set it to the piecesOnBoard field of a ReturnPlay object.


    /* WHEN YOU FILL IN THIS METHOD, YOU NEED TO RETURN A ReturnPlay OBJECT */
    return result;
}


/**
 * This method should reset the game, and start from scratch.
 */
public static void start() {

    board = new Board(); // Initialize a new game board

    Pawn pawn = new Pawn(ReturnPiece.PieceType.WP, ReturnPiece.PieceFile.a, 2);

    board.addPiece(pawn);

    ReturnPiece piece = new ReturnPiece();
    piece.pieceType = pawn.pieceType;
    piece.pieceFile = pawn.pieceFile;
    piece.pieceRank = pawn.pieceRank;

    
    ReturnPlay result = new ReturnPlay();
    result.piecesOnBoard = new ArrayList<>(); // Initialize the list
    result.piecesOnBoard.add(piece);
    // 
    

    // Create a test piece (e.g., White Pawn at e2)
    // ReturnPiece testPiece = new ReturnPiece();
    // testPiece.pieceType = ReturnPiece.PieceType.WP; // White Pawn
    // testPiece.pieceFile = ReturnPiece.PieceFile.e;  // Column 'e'
    // testPiece.pieceRank = 2;                        // Row 2

    // result.piecesOnBoard.add(testPiece); // Add to list

    PlayChess.printBoard(result.piecesOnBoard);
    

}

}