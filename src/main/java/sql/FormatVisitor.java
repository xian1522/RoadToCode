package sql;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.select.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FormatVisitor {
    private String sql;

    public FormatVisitor(String sql) {
        this.sql = sql;
    }

    public List<SqlFormatter> visitorSql() throws JSQLParserException {
        Statement statement = CCJSqlParserUtil.parse(sql);
        return getSqlFormatter(statement, 0);
    }

    public List<SqlFormatter> getSqlFormatter(Statement statement, int level) {

        List<SqlFormatter> formatterList = new ArrayList<>();

        if(statement instanceof PlainSelect) {
            PlainSelect plainSelect = (PlainSelect) statement;

            formatterList.add(SqlFormatter.builder()
                    .text("SELECT " + Select.getStringList(plainSelect.getSelectItems()))
                    .level(level).build());

            FromItem fromItem = plainSelect.getFromItem();
            if(fromItem instanceof ParenthesedSelect) {
                formatterList.add(SqlFormatter.builder()
                        .text("FROM (")
                        .level(level).build());

                ParenthesedSelect parenthesedSelect = (ParenthesedSelect) fromItem;
                SetOperationList setOperationList = (parenthesedSelect).getSetOperationList();
                formatterList.addAll(getSqlFormatter(setOperationList, level + 1));

                formatterList.add(SqlFormatter.builder()
                        .text(")" + parenthesedSelect.getAlias().getName())
                        .level(level).build());

            }else if(fromItem instanceof Table) {
                Table table = (Table)fromItem;
                formatterList.add(SqlFormatter.builder()
                        .text(("FROM " + table.getName()) + " " + table.getAlias().getName())
                        .level(level).build());
            }

            List<Join> joins = plainSelect.getJoins();
            if(joins != null) {
                for(Join join : joins) {
                    formatterList.add(SqlFormatter.builder()
                            .text(join.toString())
                            .level(level + 1).build());
                }
            }
            if(plainSelect.getWhere() != null) {
                formatterList.add(SqlFormatter.builder()
                        .text("WHERE " + plainSelect.getWhere())
                        .level(level).build());
            }

        }else if(statement instanceof SetOperationList) {
            SetOperationList setOperationList = (SetOperationList) statement;
            List<Select> selects = setOperationList.getSelects();
            List<SetOperation> operations = setOperationList.getOperations();
            for (int i = 0; i < selects.size(); i++) {
                if (i != 0) {
                    formatterList.add(SqlFormatter.builder()
                                    .text(operations.get(i - 1).toString())
                                    .level(level).build());
                }
                Select select = selects.get(i);
                if(select instanceof PlainSelect) {
                    formatterList.addAll(getSqlFormatter(select, level));
                }
            }
            if (setOperationList.getOrderByElements() != null) {
                formatterList.add(SqlFormatter.builder()
                                .text(PlainSelect.orderByToString(setOperationList.getOrderByElements()))
                                .level(level).build());
            }
        }
        return formatterList;
    }

    public String getFormattedSql(List<SqlFormatter> formatterList) {
        return formatterList.stream()
                .map(SqlFormatter::toString)
                .collect(Collectors.joining());
    }
}
