package top.koolhaas.tinybee.generator.generator.config.rules;

/**
 * 获取实体类字段属性类信息接口
 *
 * @author miemie
 * @since 2021/08/21
 */
public interface IColumnType {

    /**
     * 获取字段类型
     *
     * @return 字段类型
     */
    String getType();

    /**
     * 获取字段类型完整名
     *
     * @return 字段类型完整名
     */
    String getPkg();
}
