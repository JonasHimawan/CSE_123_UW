//Jonas Himawan
//4/14/2025
//CSE 123
//P0: CIPHERS
//TA: Cady Xia
//The MultiCipher class represents a list of multiple ciphers applied in sequence. 
//It encrypts or decrypts text by running through each cipher one by one. 
//This class extends the Cipher superclass. 
import java.util.List;

public class MultiCipher extends Cipher{
    private List<Cipher> ciphers;
    
    //Behavior: 
    // - Constructor for the MultiCipher object.
    // - The provided list of ciphers cannot be null.
    //Exceptions:
    // - Throws IllegalArgumentException if the list of ciphers is null.
    //Returns: N/A
    //Parameters:
    //List<Cipher> ciphers - A list of Ciphers in the MultiCipher object.
    public MultiCipher(List<Cipher> ciphers){
        if(ciphers == null){
            throw new IllegalArgumentException("Cannot be null");
        }
        this.ciphers = ciphers;
    }

    //Behavior:
    // - Encrypts the input string by applying each cipher in the list one after the other.
    // - The result of each encryption is passed as input to the next cipher.
    // - Returns the final encrypted string after all ciphers have been applied.
    // - The given input cannot be null and each character should be within the encodable range.
    //Exceptions:
    // - Throws IllegalArgumentException if the provided input is null.
    // - Throws an IllegalArgumentException if a character in the input is outside the encodable range.
    //Returns:
    // - A String representing the encrypted version of the input that has undergone
    //   all of the different ciphers.
    //Parameters: 
    // -String input: the string that will be encrypted.
    public String encrypt(String input){
        if (input == null){
            throw new IllegalArgumentException("Cannot be null");
        }
        for(int i = 0; i < input.length(); i++){
            if (isCharInRange(input.charAt(i)) == false){
                throw new IllegalArgumentException("The input cannot contain characters "
                                                            + "outside the encodable range");
            }
        }
        String encryptResult = input;
        for (Cipher eachCipher : ciphers){
            encryptResult = eachCipher.encrypt(encryptResult);
        }
        return encryptResult;
    }

    //Behavior:
    // - Decrypts the input string by applying each cipher in the list one after the other.
    // - The result of each decryption is passed as input to the next cipher.
    // - Returns the final decrypted string after all ciphers have been applied.
    // - Reverses a round of encryption if previously appled.
    // - The given input cannot be null and each character should be within the encodable range.
    //Exceptions:
    // - Throws IllegalArgumentException if the provided input is null.
    // - Throws an IllegalArgumentException if a character in the input is outside the 
    //   encodable range.
    //Returns:
    // - A String representing the decrypted version of the input that has undergone
    //   all of the different ciphers.
    //Parameters: 
    // - String input: the string that will be decrypted.
    public String decrypt(String input){
        if (input == null){
            throw new IllegalArgumentException("Cannot be null");
        }
        for(int i = 0; i < input.length(); i++){
            if (isCharInRange(input.charAt(i)) == false){
                throw new IllegalArgumentException("The input cannot contain characters "
                                                            + "outside the encodable range");
            }
        }
        String decryptResult = input;
        for (int i = ciphers.size() - 1; i >= 0; i--) {
            decryptResult = ciphers.get(i).decrypt(decryptResult);
        }
        return decryptResult;
    }
}
