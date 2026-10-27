package app.tool.sqlformat;

import net.sf.jsqlparser.JSQLParserException;
import app.tool.sqlformat.sql.FormatVisitor;

/**
 * 封装现有 SQL 格式化引擎。GUI 只依赖本类，不感知 JSqlParser 细节。
 */
public class FormatService {

    public String format(String sql) throws JSQLParserException {
        FormatVisitor visitor = new FormatVisitor(sql);
        return visitor.getFormattedSql(visitor.visitorSql());
    }
}
