import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;

public class Testing {
    private Repository repo1;
    private Repository repo2;

    // Occurs before each of the individual test cases
    // (creates new repos and resets commit ids)
    @BeforeEach
    public void setUp() {
        repo1 = new Repository("repo1");
        repo2 = new Repository("repo2");
        Repository.Commit.resetIds();
    }

    @Test
    @DisplayName("Synchronize Empty Test")
    public void testSynchronizeEmpty() throws InterruptedException{
        commitAll(repo1, new String[]{});
        commitAll(repo2, new String[]{"One", "Two", "Three"});
        repo1.synchronize(repo2);
        assertEquals(3, repo1.getRepoSize());
        testHistory(repo1, 3, new String[]{"One", "Two", "Three"});
    }
    
    @Test
    @DisplayName("Synchronize Front Test")
    public void testSynchronizeFront() throws InterruptedException{
        // Initialize commit messages
        commitAll(repo1, new String[]{"One", "Two"});
        commitAll(repo2, new String[]{"Three", "Four"});
        assertEquals(2, repo1.getRepoSize());
        assertEquals(2, repo2.getRepoSize());
        // Synchronize repo1 into repo2
        repo2.synchronize(repo1);
        assertEquals(4, repo2.getRepoSize());
        assertEquals(0, repo1.getRepoSize());
        // Make sure the history of repo1 is correctly synchronized
        testHistory(repo2, 4, new String[]{"One", "Two", "Three", "Four"});
    }

    @Test
    @DisplayName("Synchronize Middle Test")
    public void testSynchronizeMiddle() throws InterruptedException{
        repo1 = new Repository("repo1");
        repo2 = new Repository("repo2");
        commitAll(repo1, new String[]{"One"});
        commitAll(repo2, new String[]{"Two"});
        commitAll(repo1, new String[]{"Three"});
        commitAll(repo2, new String[]{"Four"});

        repo1.synchronize(repo2);
        assertEquals(4, repo1.getRepoSize());
        
        testHistory(repo1, 4, new String[]{"One", "Two", "Three", "Four"});
    }

    @Test
    @DisplayName("Synchronize End Test")
    public void testSynchronizeEnd() throws InterruptedException{
        commitAll(repo1, new String[]{"One", "Two", "Three"});
        commitAll(repo2, new String[]{"Four", "Five", "Six"});

        repo1.synchronize(repo2);
        assertEquals(6, repo1.getRepoSize());
        testHistory(repo1, 6, new String[]{"One", "Two", "Three", "Four", "Five", "Six"});
    }

    public void commitAll(Repository repo, String[] messages) throws InterruptedException {
        // Commit all of the provided messages
        for (String message : messages) {
            int size = repo.getRepoSize();
            repo.commit(message);
            
            // Make sure exactly one commit was added to the repo
            assertEquals(size + 1, repo.getRepoSize(),
                         String.format("Size not correctly updated after commiting message [%s]",
                                       message));

            // Sleep to guarantee that all commits have different time stamps
            Thread.sleep(2);
        }
    }

    public void testHistory(Repository repo, int n, String[] allCommits) {
        int totalCommits = repo.getRepoSize();
        assertTrue(n <= totalCommits,
                   String.format("Provided n [%d] too big. Only [%d] commits",
                                 n, totalCommits));
        
        String[] nCommits = repo.getHistory(n).split("\n");
        
        assertTrue(nCommits.length <= n,
                   String.format("getHistory(n) returned more than n [%d] commits", n));
        assertTrue(nCommits.length <= allCommits.length,
                   String.format("Not enough expected commits to check against. " +
                                 "Expected at least [%d]. Actual [%d]",
                                 n, allCommits.length));
        
        for (int i = 0; i < n; i++) {
            String commit = nCommits[i];

            // Old commit messages/ids are on the left and the more recent commit messages/ids are
            // on the right so need to traverse from right to left
            int backwardsIndex = totalCommits - 1 - i;
            String commitMessage = allCommits[backwardsIndex];

            assertTrue(commit.contains(commitMessage),
                       String.format("Commit [%s] doesn't contain expected message [%s]",
                                     commit, commitMessage));
            assertTrue(commit.contains("" + backwardsIndex),
                       String.format("Commit [%s] doesn't contain expected id [%d]",
                                     commit, backwardsIndex));
        }
    }
}
