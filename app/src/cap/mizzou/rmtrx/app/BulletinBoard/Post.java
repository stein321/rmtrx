package cap.mizzou.rmtrx.app.BulletinBoard;

/**
 * Created with IntelliJ IDEA.
 * User: Desi
 * Date: 11/12/13
 * Time: 12:55 AM
 * To change this template use File | Settings | File Templates.
 */
public class Post {

        private long id;
        private String post;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getPost() {
            return post;
        }

        public void setPost(String post) {
            this.post = post;
        }

        // Will be used by the ArrayAdapter in the ListView
        @Override
        public String toString() {
            return post;
        }
    }

