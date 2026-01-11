//Jonas Himawan
//4/14/2025
//CSE 123
//P0: CIPHERS
//TA: Cady Xia
// The CaesarShift class represents a CaesarShift cipher, which shifts characters in the encodable range
// by a fixed amount. This class extends the Substitution class.
import java.util.Queue;
import java.util.LinkedList;

public class CaesarShift extends Substitution{

    //Behavior:
    // - Constructor for the CaesarShift cipher.
    // - Shifts the encodable range to the left by the provided number of shifts.
    // - The first character of the encodable range will become the last if the shift is 1.
    //Exceptions:
    // - Throws IllegalArgumentException of the provided shift is a negative value or 0.
    //Returns: N/A
    //Parameters:
    // - int shift: an integer representing the shift of the CaesarShift cipher.
    public CaesarShift(int shift){
        if (shift <= 0){
            throw new IllegalArgumentException("shift cannot be 0 or any negative value");
        }
        String encodableBefore = "";
        String encodableAfter = "";
        for (int i = Cipher.MIN_CHAR; i <= Cipher.MAX_CHAR; i++){
            encodableBefore += (char)(i);
        }
        Queue<Character> shiftQueue = new LinkedList<>();
        for(int i = 0; i < encodableBefore.length(); i++){
            shiftQueue.add(encodableBefore.charAt(i));
        }
        for(int i = 0; i < shift; i++){
            shiftQueue.add(shiftQueue.remove());
        }
        int queueSize = shiftQueue.size();
        for(int i = 0; i < queueSize; i++){
            encodableAfter += shiftQueue.remove();
        }
        setEncoding(encodableAfter);
    }
}
