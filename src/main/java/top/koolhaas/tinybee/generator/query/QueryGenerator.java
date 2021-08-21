package top.koolhaas.tinybee.generator.query;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.util.Map;

/**
 * @program: tinybee
 * @description:
 * @author: hackerdom
 * @created: 2021/08/21
 */
@Slf4j
public class QueryGenerator {

    public static final String SQL_RULES_COLUMN = "SQL_RULES_COLUMN";

    private static final String BEGIN = "_begin";
    private static final String END = "_end";
    private static final String STAR = "*";
    private static final String COMMA = ",";
    private static final String NOT_EQUAL = "!";
    /**
     * 页面带有规则值查询，空格作为分隔符
     */
    private static final String QUERY_SEPARATE_KEYWORD = " ";
    /**
     * 高级查询前端传来的参数名
     */
    private static final String SUPER_QUERY_PARAMS = "superQueryParams";

    /**
     * 排序列
     */
    private static final String ORDER_COLUMN = "column";
    /**
     * 排序方式
     */
    private static final String ORDER_TYPE = "order";
    private static final String ORDER_TYPE_ASC = "ASC";

    /**
     * 时间格式化
     */
    private static final ThreadLocal<SimpleDateFormat> local = new ThreadLocal<SimpleDateFormat>();

    private static SimpleDateFormat getTime() {
        SimpleDateFormat time = local.get();
        if (time == null) {
            time = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            local.set(time);
        }
        return time;
    }

    /**
     * 获取查询条件构造器QueryWrapper实例 通用查询条件已被封装完成
     *
     * @param searchObj    查询实体
     * @param parameterMap request.getParameterMap()
     * @return QueryWrapper实例
     */
    public static <T> QueryWrapper<T> initQueryWrapper(T searchObj, Map<String, String[]> parameterMap) {
        long start = System.currentTimeMillis();
        QueryWrapper<T> queryWrapper = new QueryWrapper<T>(searchObj);
        log.debug("---查询条件构造器初始化完成,耗时:" + (System.currentTimeMillis() - start) + "毫秒----");
        return queryWrapper;
    }
}
