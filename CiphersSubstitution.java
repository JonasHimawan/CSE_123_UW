//Jonas Himawan
//4/14/2025
//CSE 123
//P0: CIPHERS
//TA: Cady Xia
//This class represents a Substitution cipher with encryption and decryption mechanisms. 
//This class extends the Cipher superclass.
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;

public class Substitution extends Cipher{
    private String encoding;
    
    //Behavior:
    // - A constructor for the Substitution cipher.
    // - Constructs a Substitution cipher with an empty encoding.
    //Exceptions: N/A
    //Returns: N/A
    //Parameters: N/A
    public Substitution(){
        this.encoding = null;
    }

    //Behavior:
    // - Another constructor for the Substitution class.
    // - Constructs a Substitituion cipher with the given encoding
    // - The provided encoding cannot be null, has to have the same number
    //   of characters as the encodable range, cannot contain duplicate characters,
    //   and each individual character cannot be outside the encodable range.
    //Exceptions:
    // - Throws IllegalArgumentException if encoding representation of the 
    // Substition cipher is null, the length of the encoding doesn't have 
    // the same number of characters as the Cipher's encodable range, contains
    // duplicate characters, or if any character is outside the Cipher's encodable range. 
    //Returns: N/A
    //Parameters: 
    // - String encoding: The encoding of the Substituion cipher.
    public Substitution (String encoding){
        setEncoding(encoding);
    }

    //Behavior:
    // -Helper method that returns the encodable range of the Substition cipher.
    //Exceptions: N/A
    //Returns: 
    // - Returns a String representation of the encodable range of the Substitution cipher.
    //Parameters: N/A
    private String getEncodableRange(){
        String encodableRange = "";
        for (int i = Cipher.MIN_CHAR; i <= Cipher.MAX_CHAR; i++){
            encodableRange += (char)(i);
        }
        return encodableRange;
    }

    //Behavior:
    // - This method updates the encoding for this Substitution Cipher.
    // - The provided encoding cannot be null, has to have the same number
    //   of characters as the encodable range, cannot contain duplicate characters,
    //   and each individual character cannot be outside the encodable range.
    //Exceptions:
    // - Throws IllegalArgumentException if encoding representation of the 
    // Substition cipher is null, the length of the encoding doesn't have 
    // the same number of characters as the Cipher's encodable range, contains
    // duplicate characters, or if any character is outside the Cipher's encodable range. 
    //Returns: N/A
    //Parameters: 
    // - String encoding: The encoding of the Substituion cipher.
    public void setEncoding(String encoding){
        if(encoding == null || encoding.length() != Cipher.TOTAL_CHARS){
            throw new IllegalArgumentException("The encoding is incorrect");
        }
        this.encoding = encoding;
        Set<Character> uniqueCheck = new HashSet<>();
        for(int i = 0; i < encoding.length(); i++){
            uniqueCheck.add(encoding.charAt(i));
            if (isCharInRange(encoding.charAt(i)) == false){
                throw new IllegalArgumentException("The encoding cannot contain characters "
                                                            + "outside the encodable range");
            }
        }
        if(uniqueCheck.size() != encoding.length()){
            throw new IllegalArgumentException("The encoding cannot contain "+ 
                                                "duplicate characters");
        }         
    }

    //Behavior:
    // - Encrypts the given input string based on the encoding of the substitution cipher.
    // - The given input cannot be null and each character should be within the encodable range.
    // - The encoding of the Substition cipher must be non-null.
    //Exceptions:
    // - Throws IllegalArgumentException if the provided input is null.
    // - Throws IllegalStateException if the encoding of the Substition cipher is null.
    // - Throws an IllegalArgumentException if a character in the input is outside the encodable range.
    //Returns:
    // - A String representing the encrypted version of the input
    //Parameters: 
    // -String input: the string that will be encrypted.
    public String encrypt(String input){
        if(input == null){
            throw new IllegalArgumentException("Input cannot be null");
        }
        if(this.encoding == null){
            throw new IllegalStateException("Encoding cannot be null");
        }
        for(int i = 0; i < input.length(); i++){
            if (isCharInRange(input.charAt(i)) == false){
                throw new IllegalArgumentException("The input cannot contain characters "
                                                            + "outside the encodable range");
            }
        }
        String encryptOut = "";
        int index = 0;
        char eachInput;
        for (int i = 0; i < input.length(); i++){
            eachInput = input.charAt(i);
            index = getEncodableRange().indexOf(eachInput);
            encryptOut += encoding.charAt(index);
        }
        return encryptOut;
    }

    //Behavior:
    // - Decrypts the given input string based on the encoding of the substitution cipher.
    // - Reverses a single round of encryption if previously appled.
    // - The given input cannot be null and each character should be within the encodable range.
    // - The encoding of the Substition cipher must be non-null.
    //Exceptions:
    // - Throws IllegalArgumentException if the provided input is null.
    // - Throws IllegalStateException if the encoding of the Substition cipher is null.
    // - Throws an IllegalArgumentException if a character in the input is outside the 
    //   encodable range.
    //Returns:
    // - A String representing the decrypted version of the input
    //Parameters: 
    // - String input: the string that will be decrypted.
    public String decrypt(String input){
        if(input == null){
            throw new IllegalArgumentException("Input cannot be null");
        }
        if(this.encoding == null){
            throw new IllegalStateException("Encoding cannot be null");
        }
        for(int i = 0; i < input.length(); i++){
            if (isCharInRange(input.charAt(i)) == false){
                throw new IllegalArgumentException("The input cannot contain characters "
                                                            + "outside the encodable range");
            }
        }
        String decryptOut = "";
        int index = 0;
        char eachInput;
        for (int i = 0; i < input.length(); i++){
            eachInput = input.charAt(i);
            index = encoding.indexOf(eachInput);
            decryptOut += getEncodableRange().charAt(index);
        }
        return decryptOut;
    }
}
