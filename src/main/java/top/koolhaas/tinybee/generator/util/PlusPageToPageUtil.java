package top.koolhaas.tinybee.generator.util;

import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * @program: tinybee
 * @description: 将 mybaits.plus 的 page 对象转成我们自己的 page 对象
 * @author: hackerdom
 * @created: 2021/08/21
 */
public final class PlusPageToPageUtil {

    public static <T> Page<T> convert(IPage<T> page) {

        Page<T> rs = new Page<>();

        rs.setData(page.getRecords());
        rs.setPageNo(Long.valueOf(page.getCurrent()).intValue());
        rs.setTotalSize(page.getTotal());
        rs.setPageSize(Long.valueOf(page.getSize()).intValue());

        return rs;
    }
}
