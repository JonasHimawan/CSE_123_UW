//Jonas Himawan
//5/7/2025
//CSE 123
//P1: Mini Git
//TA: Cady Xia
//The Repository class represents a mini version control system that stores a 
//history of commits in reverse chronological order. It supports creating commits, 
//viewing history, removing commits, and merging with another repository while preserving order.
import java.util.*;
import java.text.SimpleDateFormat;

public class Repository {
    private Commit head;
    private String name;

    //Behavior:
    // - Constructor for the repository class.
    //Exceptions:
    // - Throws IllegalArgumentException if the name representing the repository object
    //   is null or if the name is an empty space.
    //Returns:
    // - None
    //Parameters:
    // - String name: name of the repository object.
    public Repository(String name){
        if(name == null || name.isEmpty()){
            throw new IllegalArgumentException("Repository name cannot be null");
        }
        this.name = name;
        this.head = null;
    }

    //Behavior:
    // - Returns the ID of the current head in the repository object.
    // - If the head is null, it returns null.
    //Exceptions:
    // - None
    //Returns:
    // - String representing the current head of this repository object.
    //Parameters:
    // - None
    public String getRepoHead(){
        if(head == null){
            return null;
        }
        return head.id;

    }

    //Behavior:
    // - Returns the number of commits in the repository object.
    //Exceptions:
    // - None
    //Returns:
    // - Integer representing the number of commits in the repository object.
    //Parameters:
    // - None
    public int getRepoSize(){
        int countSize = 0;
        Commit curr = head;
        while(curr != null){
            curr = curr.past;
            countSize++;
        }
        return countSize;
    }

    //Behavior:
    // - Returns a string representation of the repository object.
    // - Format if repository objct has at least one commit: <name> - Current head: <head>.
    // - Format if repository object has no commits: <name> - No commits
    //Exceptions:
    // - None
    //Returns:
    // - String representation of the repository object depending on the number of commits.
    //Parameters:
    // - None
    public String toString(){
        if(head == null){
            return this.name + " - No commits";
        }
        return this.name + " - Current head: " + head.toString();
    }

    //Behavior:
    // - Checks if the commit with intended ID(targetId) is in the repository object or not.
    //Exceptions:
    // - Throws an IllegalArgumentException if the intended ID(targetId) is null.
    //Returns:
    // - True if the commit with the intended ID(targetId) is in the repository object.
    // - False if the commit with the intended ID(targetId) is not in the repository object.
    //Parameters:
    // - String targetId: The intended ID that the user wants to see if its in the 
    //   repository object.
    public boolean contains(String targetId){
        if (targetId == null){
            throw new IllegalArgumentException("Target ID can't be null");
        }
        Commit curr = head;
        while(curr != null){
            if ((targetId.equals(curr.id))) {
                return true;
            }
            curr = curr.past;
        }
        return false;
    }
 
    //Behavior:
    // - Returns String representations of the most recent n commits in the repository object
    //   with the most recent first. 
    // - Commits are separated by a new line.
    // - The most recent n commits cannot be less than 1.
    // - If there are no commits in the repository object, this method returns an empty string.
    // - If there are fewer than n commits in the repository object, all commits are returned.
    //Exceptions:
    // - Throws a IllegalArgumentException if the amount of history the user wants to see is
    //   non-positive.
    //Returns:
    // - A string representation of the most recent n commits in the repository object. most
    //   recent is first.
    //Parameters:
    // - int n: Integer represeting the recent amount of commits the user wants to viewing
    //   in the repository object.
    public String getHistory(int n){
        if(n < 1){
            throw new IllegalArgumentException("History is too small");
        }
        String history = "";
        Commit curr = head;
        
        int count = 0;
        while (curr != null && count < n) {
            history += curr.toString() + "\n";
            curr = curr.past;
            count++;
        }
        return history;
    }

    //Behavior:
    // - Creates a new commit with the given message and adds it to the repository object.
    // - The new commit becomes the new head of the repository object, history is preserved.
    // - The ID of the new commit is returned.
    // - Commit message cannot be null.
    //Exceptions:
    // - Throws new IllegalArgumentException if the commit message is null.
    //Returns:
    // - A string representing the ID of the new commit.
    //Parameters:
    // - String message: A String representing the message for the new commit.
    public String commit(String message){
        if(message == null){
            throw new IllegalArgumentException("Message cannot be null");
        }
        head = new Commit(message, head);
        
        return head.id;
    }

    //Behavior:
    // - Removes the commit with ID targetId for the repository object.
    // - Rest of the history is maintained.
    // - Returns true if the commit was dropped successfully, false if otherwise.
    //Exceptions:
    // - Throws an IllegalArgumentException if targetId is null
    //Returns:
    // - Boolean representing if the commit with ID targetId was succesffully dropped.
    // - True if successful, false if otherwise.
    //Parameters:
    // - String targetId: A string representing the Id of the commit the user wants to remove
    //   from the repository obkect.
    public boolean drop(String targetId){
        if (targetId == null){
            throw new IllegalArgumentException();
        }
        if (head == null) {
            return false;
        }
        // If the head needs to be removed
        if (head.id.equals(targetId)) {
            head = head.past;
            return true;
        }
        // Traverse the list to find the node to remove
        Commit curr = head;
        while (curr.past != null) {
            if (curr.past.id.equals(targetId)) {
                curr.past = curr.past.past;
                return true;
            }
            curr = curr.past;
        }
        return false;
    }

    //Behavior:
    // - Combines the two repository object histories where the chronological order is preserved.
    // - Takes all the commits in the other repository object and moves them into this repository object.
    // - Ordered from most recent to least recent in this repository object.
    // - Other repository object can be empty, but it cannot be null.
    // - If other repository object is empty, this repository object should not change.
    // - If this repository object is empty, all the commits in the other repository obkect 
    //   will be moved into this repository.
    // - The other repository object will be empty after this method runs.
    //Exceptions:
    // - Throws an IllegalArgumentException if the other repository is null.
    //Returns:
    // - None
    //Parameters:
    // - Repository other: Another repository that will be synchronized with this repository.
    public void synchronize(Repository other){
       //Checks if other repository is null.
        if (other == null){
            throw new IllegalArgumentException("Other repository cannot be null");
        }
        //Empty Case
        if (this.head == null){
            this.head = other.head;
            other.head = null;
        } else if (other.head != null){

            //Front Case
            if (other.head.timeStamp > this.head.timeStamp){
                Commit temp = other.head.past;
                other.head.past =  this.head;
                this.head = other.head;
                other.head = temp;
            }
            Commit thisCurr = this.head;

            //Middle Case
            while(thisCurr.past != null && other.head != null){
                if(thisCurr.past.timeStamp >= other.head.timeStamp){
                    thisCurr = thisCurr.past;
                } else {
                    Commit temp = other.head.past;
                    other.head.past = thisCurr.past;
                    thisCurr.past = other.head;
                    other.head = temp;
                }
            }

            //End case
            //Everything left from other head goes into this head.
            if(other.head != null){
                Commit temp = thisCurr;
                while(temp.past != null){
                    temp = thisCurr.past;
                }
                temp.past = other.head;
            }
            other.head = null;
        }
    }


    /**
     * DO NOT MODIFY
     * A class that represents a single commit in the repository.
     * Commits are characterized by an identifier, a commit message,
     * and the time that the commit was made. A commit also stores
     * a reference to the immediately previous commit if it exists.
     *
     * Staff Note: You may notice that the comments in this 
     * class openly mention the fields of the class. This is fine 
     * because the fields of the Commit class are public. In general, 
     * be careful about revealing implementation details!
     */
    public static class Commit {

        private static int currentCommitID;

        /**
         * The time, in milliseconds, at which this commit was created.
         */
        public final long timeStamp;

        /**
         * A unique identifier for this commit.
         */
        public final String id;

        /**
         * A message describing the changes made in this commit.
         */
        public final String message;

        /**
         * A reference to the previous commit, if it exists. Otherwise, null.
         */
        public Commit past;

        /**
         * Constructs a commit object. The unique identifier and timestamp
         * are automatically generated.
         * @param message A message describing the changes made in this commit. Should be non-null.
         * @param past A reference to the commit made immediately before this
         *             commit.
         */
        public Commit(String message, Commit past) {
            this.id = "" + currentCommitID++;
            this.message = message;
            this.timeStamp = System.currentTimeMillis();
            this.past = past;
        }

        /**
         * Constructs a commit object with no previous commit. The unique
         * identifier and timestamp are automatically generated.
         * @param message A message describing the changes made in this commit. Should be non-null.
         */
        public Commit(String message) {
            this(message, null);
        }

        /**
         * Returns a string representation of this commit. The string
         * representation consists of this commit's unique identifier,
         * timestamp, and message, in the following form:
         *      "[identifier] at [timestamp]: [message]"
         * @return The string representation of this collection.
         */
        @Override
        public String toString() {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
            Date date = new Date(timeStamp);

            return id + " at " + formatter.format(date) + ": " + message;
        }

        /**
        * Resets the IDs of the commit nodes such that they reset to 0.
        * Primarily for testing purposes.
        */
        public static void resetIds() {
            Commit.currentCommitID = 0;
        }
    }
}
