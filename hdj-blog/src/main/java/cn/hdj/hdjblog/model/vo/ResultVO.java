package cn.hdj.hdjblog.model.vo;

import cn.hdj.hdjblog.constaint.enums.ResponseCodeEnum;
import lombok.Data;

import java.io.Serializable;

/**
 * @author h_dj
 * @Date: 2019/6/2 16:19
 * @Description: 响应结果json
 */
@SuppressWarnings("unchecked")
@Data
public class ResultVO<T> implements Serializable {

    private static final long serialVersionUID = 1348172152215944560L;

    /**
     * 返回状态码100为失败, 200为正确
     */
    private Integer code;

    /**
     * 返回处理信息，成功或者失败
     */
    private String msg;

    /**
     * 成功返回true，失败返回false
     */
    private Boolean success;

    /**
     * 返回给前端的数据
     */
    private T data;

    /**
     * 成功返回的json封装体
     *
     * @param value 原始数据
     * @return json格式
     */
    public static <T> ResultVO successJson(T value) {
        ResultVO results = new ResultVO();
        results.setCode(ResponseCodeEnum.SUCCESS.getCode());
        results.setMsg(ResponseCodeEnum.SUCCESS.getMsg());
        results.setSuccess(true);
        results.setData(value);
        return results;
    }

    public static ResultVO successJson() {
        return successJson(null);
    }

    /**
     * 失败返回的json封装体
     *
     * @return json格式
     */
    public static ResultVO errorJson() {
        ResultVO results = new ResultVO();
        results.setCode(ResponseCodeEnum.UNKNOWN.getCode());
        results.setSuccess(false);
        results.setMsg(ResponseCodeEnum.UNKNOWN.getMsg());
        return results;
    }


    /**
     * 失败返回的json封装体
     *
     * @return json格式
     */
    public static ResultVO errorJson(String msg, Integer code) {
        ResultVO results = new ResultVO();
        results.setCode(code);
        results.setSuccess(false);
        results.setMsg(msg);
        return results;
    }

    public static ResultVO errorJson(String msg) {
        return errorJson(msg, ResponseCodeEnum.UNKNOWN.getCode());
    }

    public static ResultVO errorJson(ResponseCodeEnum r) {
        ResultVO results = new ResultVO();
        results.setCode(r.getCode());
        results.setSuccess(false);
        results.setMsg(r.getMsg());
        return results;
    }
}
