package example.com.templateprogram.test.events;

/**
 * Created by beijixiong on 2018/12/1.
 * 消息事件类
 */

public class MessageEvent {

    private String message;

    public MessageEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
