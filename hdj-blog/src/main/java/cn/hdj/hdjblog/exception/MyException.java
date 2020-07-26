package cn.hdj.hdjblog.exception;

import cn.hdj.hdjblog.constaint.enums.ResponseCodeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author hdj
 * @Description: 系统错误
 * @Version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class MyException extends RuntimeException {

    private String msg;
    private int code = 500;


    public MyException(Throwable throwable) {
        super(throwable);
    }

    public MyException() {
        super(ResponseCodeEnum.UNKNOWN.getMsg());
        this.msg = ResponseCodeEnum.UNKNOWN.getMsg();
    }


    public MyException(ResponseCodeEnum eEnum, Throwable e) {
        super(eEnum.getMsg(), e);
        this.msg = eEnum.getMsg();
        this.code = eEnum.getCode();
    }

    public MyException(ResponseCodeEnum eEnum) {
        super(eEnum.getMsg());
        this.msg = eEnum.getMsg();
        this.code = eEnum.getCode();
    }

    public MyException(String exception) {
        super(exception);
        this.msg = exception;
    }
}
