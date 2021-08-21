package top.koolhaas.tinybee.generator.generator.config.querys;

import com.baomidou.mybatisplus.annotation.DbType;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * MySql 表数据查询
 *
 * @author hubin
 * @since 2021/08/21
 */
public class MariadbQuery extends AbstractDbQuery {

    @Override
    public DbType dbType() {
        return DbType.MARIADB;
    }

    @Override
    public String tablesSql() {
        return "show table status";
    }

    @Override
    public String tableFieldsSql() {
        return "show full fields from `%s`";
    }

    @Override
    public String tableName() {
        return "NAME";
    }

    @Override
    public String tableComment() {
        return "COMMENT";
    }

    @Override
    public String fieldName() {
        return "FIELD";
    }

    @Override
    public String fieldType() {
        return "TYPE";
    }

    @Override
    public String fieldComment() {
        return "COMMENT";
    }

    @Override
    public String fieldKey() {
        return "KEY";
    }

    @Override
    public boolean isKeyIdentity(ResultSet results) throws SQLException {
        return "auto_increment".equals(results.getString("Extra"));
    }
}
