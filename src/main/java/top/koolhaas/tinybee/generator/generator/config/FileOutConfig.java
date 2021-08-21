package top.koolhaas.tinybee.generator.generator.config;

import top.koolhaas.tinybee.generator.generator.config.po.TableInfo;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 输出文件配置
 *
 * @author hubin
 * @since 2021/08/21
 */
@Data
@Accessors(chain = true)
public abstract class FileOutConfig {

    /**
     * 模板
     */
    private String templatePath;

    public FileOutConfig() {
        // to do nothing
    }

    public FileOutConfig(String templatePath) {
        this.templatePath = templatePath;
    }

    /**
     * 输出文件
     */
    public abstract String outputFile(TableInfo tableInfo);

    public boolean shouldGen(TableInfo tableInfo) {

        return true;
    }
}
