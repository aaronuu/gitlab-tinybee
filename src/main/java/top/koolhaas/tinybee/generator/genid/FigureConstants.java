package top.koolhaas.tinybee.generator.genid;

/**
 * @program: tinybee
 * @description:
 * @author: hackerdom
 * @created: 2021/08/21
 */
public class FigureConstants {

    /**
     * ==================  数值  ==================
     */
    public static final int TWO = 2;
    /**
     * 3
     */
    public static final int THREE = 3;
    /**
     * 18
     */
    public static final int EIGHTEEN = 18;
    /**
     * 40
     */
    public static final int FORTY = 40;
    /**
     * 1024
     */
    public static final int EXT_INFO_LENGTH = 1024;

    /** ==================  消息超时使用  ==================*/
    /**
     * 发送tbnotify消息的超时时间，单位为ms
     */
    public static final String NOTIFY_TIMEOUT = "1000";

    /** ==================  组装sequence使用  ==================*/
    /**
     * 系统版本
     */
    public static final String SYSTEM_VERSION = "0";

    /**
     * 数据版本
     */
    public static final String DATA_VERSION = "1";

    /**
     * 随机分表分库位
     */
    public static final String ZERO_ZERO = "00";

    /**
     * 暂时使用的10位业务扩展位
     */
    public static final String BIZ_EXT = "0000000000";

    /**
     * 回收扩展时间，单位分钟
     */
    public static final int RECYCLE_EXT_TIME = 30;

    /**
     * 不限制使用次数
     */
    public static final int UNLIMIT_USE_COUNT = -1;

    /**
     * 空字符串
     */
    public static final String EMPTY_STRING = "";

    /**
     * 空JSON对象
     */
    public static final String EMPTY_JSON_STRING = "{}";
}
