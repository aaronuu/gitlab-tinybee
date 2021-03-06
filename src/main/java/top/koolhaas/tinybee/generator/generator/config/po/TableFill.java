package top.koolhaas.tinybee.generator.generator.config.po;

import com.baomidou.mybatisplus.annotation.FieldFill;
import lombok.Data;

/**
 * 字段填充
 *
 * @author hubin
 * @since 2021/08/21
 */
@Data
public class TableFill {

    /**
     * 字段名称
     */
    private String fieldName;
    /**
     * 忽略类型
     */
    private FieldFill fieldFill;

    public TableFill(String fieldName, FieldFill ignore) {
        this.fieldName = fieldName;
        this.fieldFill = ignore;
    }
}
