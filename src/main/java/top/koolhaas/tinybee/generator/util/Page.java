package top.koolhaas.tinybee.generator.util;

import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @program: tinybee
 * @description:
 * @author: hackerdom
 * @created: 2021/08/21
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Page<T> {

    private List<T> data;

    private Integer pageNo;

    private Integer pageSize;

    private Long totalSize;

    public static Page createBlack() {

        Page page = new Page();
        page.setData(Lists.newArrayListWithCapacity(0));
        page.setTotalSize(0L);
        page.setPageNo(1);
        page.setPageSize(1);

        return page;
    }
}

