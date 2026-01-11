//Jonas Himawan
//4/14/2025
//CSE 123
//P0: CIPHERS
//TA: Cady Xia
// The CaesarKey class represents a CaesarKey cipher, which updates the encodable range.
// The updates encodale range will start with the provided key, then the ascending order
// of the orignal encodable range. This class extends the Substitution class.
import java.util.Queue;
import java.util.LinkedList;
import java.util.Set;
import java.util.HashSet;

public class CaesarKey extends Substitution{

    //Behavior:
    // - Constructor for the CaesarKey cipher.
    // - Updates the encodable range by making it start with the given key.
    // - The rest of encodable range will be the characters in the original order 
    //   besides the charaters included in the provided key.
    // - The key cannot be null or an empty string.
    // - The key cannot also contain a duplicate character and each character cannot fall
    //   outside the encodable range.
    //Exceptions:
    // - Throws IllegalArgumentException of the provided key is null or an empty string.
    // - Throws IllegalArgumentException if key contains a duplicate character.
    // - Throws IllegalArgumentException if a character within the key is outside 
    //   the encodable range.
    //Returns: N/A
    //Parameters:
    // - String key: a String representing the key of the CaesarKey cipher.
    public CaesarKey(String key){
        if (key == null || key == ""){
            throw new IllegalArgumentException("Key is incorrect");
        }
        Set<Character> uniqueCheck = new HashSet<>();
        for(int i = 0; i < key.length(); i++){
            uniqueCheck.add(key.charAt(i));
            if (isCharInRange(key.charAt(i)) == false){
                throw new IllegalArgumentException("The key is incorrect, it cannot contain " 
                                               + "characters outside the encodable range");
            }
        }
        if(uniqueCheck.size() != key.length()){
            throw new IllegalArgumentException("The key is incorrect, it cannot contain "
                                                            +"duplicate characters");
        }         
        String encodableBefore = "";
        String encodableAfter = "";
        for (int i = Cipher.MIN_CHAR; i <= Cipher.MAX_CHAR; i++){
            encodableBefore += (char)(i);
        }
        Queue<Character> keyQueue = new LinkedList<>();
        Queue<Character> auxKeyQueue = new LinkedList<>();
        for(int i = 0; i < encodableBefore.length(); i++){
            keyQueue.add(encodableBefore.charAt(i));
        }
        int queueSize = 0;
        for(int j = 0; j < key.length(); j++){
            queueSize = keyQueue.size();
            for(int i = 0; i < queueSize; i++){
                char nextChar = keyQueue.remove();
                if(nextChar == key.charAt(j)){
                    auxKeyQueue.add(nextChar);
                } else {
                    keyQueue.add(nextChar);
                }
            }
        }
        queueSize = keyQueue.size();
        for(int i = 0; i < queueSize; i++){
            auxKeyQueue.add(keyQueue.remove());
        }
        queueSize = auxKeyQueue.size();
        for(int i = 0; i < queueSize; i++){
            encodableAfter += auxKeyQueue.remove();
        }
        setEncoding(encodableAfter);
    }
}
