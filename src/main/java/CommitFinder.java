import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.revwalk.RevCommit;

import java.io.File;

public class CommitFinder {
    private static final String USER = "thegabrielesa";
    private static final String PROJ = "TestCommit";
    private static final String FILTER = "added";

    public static void main(String [] args) {
        File dir = new File("./tmp/" + PROJ);
        try {
            Git git = Git.cloneRepository()
                    .setURI( String.format("https://github.com/%s/%s.git", USER, PROJ))
                    .setDirectory(dir)
                    .call();
            //get commit by using cloned repo
            Iterable<RevCommit> logs = git.log().call();
            for (RevCommit rev : logs) {
                String msg = rev.getFullMessage();
                String id = rev.getId().getName();
                // check matching messages
                if (msg.toLowerCase().contains(FILTER))
                    System.out.println(id);
            }
        } catch (GitAPIException e) {
            System.out.println("Exception: " + e);
        }
    }
}