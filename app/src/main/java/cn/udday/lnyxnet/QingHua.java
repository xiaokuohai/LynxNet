package cn.udday.lnyxnet;

import java.io.Serializable;

public class QingHua implements Serializable {

    /**
     * code : 200
     * msg : success
     * data : {"content":"我的手被划了一道口子，你也划一道，这样我们就是两口子了。"}
     * time : 1619921231
     * log_id : 247301328623116288
     */

    private int code;
    private String msg;
    private DataBean data;
    private int time;
    private long log_id;

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public DataBean getData() {
        return data;
    }

    public int getTime() {
        return time;
    }

    public long getLog_id() {
        return log_id;
    }

    public static class DataBean implements Serializable {
        /**
         * content : 我的手被划了一道口子，你也划一道，这样我们就是两口子了。
         */

        private String content;

        public String getContent() {
            return content;
        }
    }
}
