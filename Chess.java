package chess;

// Contributors: Luke Fernandez - lmf232
//               Esha Tripathi - et522


import java.util.ArrayList;
import java.util.Arrays;

public class Chess {

    enum Player { white, black }

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
    System.out.println(Arrays.toString(move_parts));

    ReturnPlay result = new ReturnPlay();
    
    /* FOLLOWING LINE IS A PLACEHOLDER TO MAKE COMPILER HAPPY */
    /* WHEN YOU FILL IN THIS METHOD, YOU NEED TO RETURN A ReturnPlay OBJECT */
    return result;
}


/**
 * This method should reset the game, and start from scratch.
 */
public static void start() {
    /* FILL IN THIS METHOD */
}

}