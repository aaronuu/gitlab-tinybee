package top.koolhaas.tinybee.core.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * @program: tinybee
 * @description:
 * @author: hackerdom
 * @created: 2021/08/21
 */
@Getter
@Setter
public abstract class ApiServiceException extends Exception {

    /**
     * 错误编码
     */
    private Integer code;

    /**
     * 错误的提示信息
     */
    private String errorMessage;

    /**
     * 默认用于dubbo反序列化
     */
    public ApiServiceException() {

    }

    public ApiServiceException(AbstractBaseExceptionEnum exception) {
        super(exception.getMessage());
        this.code = exception.getCode();
        this.errorMessage = exception.getMessage();
    }

    /**
     * 获取异常的类的具体名称
     */
    public String getExceptionClassName() {
        return this.getClass().getName();
    }
}
