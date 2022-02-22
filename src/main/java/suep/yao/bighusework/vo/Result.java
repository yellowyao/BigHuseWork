package suep.yao.bighusework.vo;

import java.io.Serializable;
import java.util.Date;

public class Result implements Serializable {
    private String message;
    private Object data;
    private Boolean flag;

    public Result() {
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Result( Boolean flag,String message, Object data) {
        this.message = message;
        this.data = data;
        this.flag = flag;
    }
    public Result( Boolean flag,String message) {
        this.message = message;

        this.flag = flag;
    }
}
