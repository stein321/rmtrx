package Models;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Ben
 * Date: 11/10/13
 * Time: 11:21 AM
 * To change this template use File | Settings | File Templates.
 */
public class ChatLog {
    private List<Message> messages;
    private String residenceId;

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public String getResidenceId() {
        return residenceId;
    }

    public void setResidenceId(String residenceId) {
        this.residenceId = residenceId;
    }
}
