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
        private String userid;
        private String postTitle;
        private String postDetails;


        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

         public String getUserId() {
            return userid;
        }

        public void setUserId(String userid) {
            this.userid = userid;
        }

        public String getPostTitle() {
            return postTitle;
        }

        public void setPostTitle(String title) {
            this.postTitle = title;
        }

        public String getPostDetails() {
            return postDetails;
        }

        public void setPostDetails(String details) {
            this.postDetails = details;
        }

        // Will be used by the ArrayAdapter in the ListView
        @Override
        public String toString() {
            return postTitle;
        }
    }

