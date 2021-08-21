package top.koolhaas.tinybee.core.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @program: tinybee
 * @description:
 * @author: hackerdom
 * @created: 2021/08/21
 */
@Getter
@Setter
@ToString
@ApiModel(value = "API统一返回封装", description = "API统一返回封装")
public class ResponseData<T> {

    public static final String DEFAULT_SUCCESS_MESSAGE = "请求成功";

    public static final String DEFAULT_ERROR_MESSAGE = "网络异常";

    public static final Integer DEFAULT_SUCCESS_CODE = 200;

    public static final Integer DEFAULT_ERROR_CODE = 500;

    /**
     * 请求是否成功
     */
    @ApiModelProperty(value = "是否成功响应")
    private Boolean success;

    /**
     * 响应状态码
     */
    @ApiModelProperty(value = "状态码")
    private Integer code;

    /**
     * 响应信息
     */
    @ApiModelProperty(value = "描述信息")
    private String message;

    /**
     * 响应对象
     */
    @ApiModelProperty(value = "数据")
    private T data;

    public ResponseData() {
    }

    public ResponseData(Boolean success, Integer code, String message, T data) {
        this.success = success;
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static <T> ResponseData<T> success() {
        return new ResponseData(true, DEFAULT_SUCCESS_CODE, DEFAULT_SUCCESS_MESSAGE, null);
    }

    public static <T> ResponseData<T> success(Object object) {
        return new ResponseData(true, DEFAULT_SUCCESS_CODE, DEFAULT_SUCCESS_MESSAGE, object);
    }

    public static <T> ResponseData<T> success(Integer code, String message, Object object) {
        return new ResponseData(true, code, message, object);
    }

    public static <T> ErrorResponseData<T> error(String message) {
        return new ErrorResponseData(message);
    }

    public static <T> ErrorResponseData<T> error(Integer code, String message) {
        return new ErrorResponseData(code, message);
    }

    public static <T> ErrorResponseData<T> error(Integer code, String message, Object object) {
        return new ErrorResponseData(code, message, object);
    }
}
