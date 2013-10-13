package cap.mizzou.rmtrx.app.TestDbActivity;

/**
 * Created with IntelliJ IDEA.
 * User: Ben
 * Date: 9/28/13
 * Time: 9:34 PM
 * To change this template use File | Settings | File Templates.
 */
public class Comment {
    private long id;
    private String comment;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    private String status;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    // Will be used by the ArrayAdapter in the ListView
    @Override
    public String toString() {
        return comment;
    }
}
