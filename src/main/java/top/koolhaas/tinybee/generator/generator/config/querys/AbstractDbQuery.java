package top.koolhaas.tinybee.generator.generator.config.querys;

import top.koolhaas.tinybee.generator.generator.config.IDbQuery;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 表数据查询抽象类
 *
 * @author hubin
 * @since 2021/08/21
 */
public abstract class AbstractDbQuery implements IDbQuery {

    @Override
    public boolean isKeyIdentity(ResultSet results) throws SQLException {
        return false;
    }

    @Override
    public String[] fieldCustom() {
        return null;
    }
}
