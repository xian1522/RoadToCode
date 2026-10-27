package app.tool.sqlformat;

import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.LineNumberFactory;
import org.fxmisc.richtext.model.StyleSpans;
import org.fxmisc.richtext.model.StyleSpansBuilder;

import java.util.Collection;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 带行号 + SQL 语法高亮的 CodeArea 工厂。
 */
public final class SqlCodeArea {

    private static final String KEYWORD =
            "\\b(SELECT|FROM|WHERE|JOIN|LEFT|RIGHT|INNER|OUTER|FULL|CROSS|ON|AND|OR|NOT|IN|IS|NULL|LIKE|BETWEEN|EXISTS"
                    + "|GROUP|BY|HAVING|ORDER|ASC|DESC|LIMIT|OFFSET|UNION|ALL|INTERSECT|EXCEPT"
                    + "|CASE|WHEN|THEN|ELSE|END|DISTINCT|AS|INSERT|INTO|VALUES|UPDATE|SET|DELETE|CREATE|TABLE|ALTER|DROP|WITH)\\b";
    private static final String STRING = "'([^'\\\\]|\\\\.)*'";
    private static final String NUMBER = "\\b\\d+(\\.\\d+)?\\b";
    private static final String COMMENT = "--[^\\n]*|/\\*[^*]*\\*+(?:[^/*][^*]*\\*+)*/";

    private static final Pattern PATTERN = Pattern.compile(
            "(?<KEYWORD>" + KEYWORD + ")"
                    + "|(?<STRING>" + STRING + ")"
                    + "|(?<NUMBER>" + NUMBER + ")"
                    + "|(?<COMMENT>" + COMMENT + ")");

    private SqlCodeArea() {
    }

    public static CodeArea create(boolean editable) {
        CodeArea area = new CodeArea();
        area.setParagraphGraphicFactory(LineNumberFactory.get(area));
        area.setEditable(editable);
        area.getStylesheets().add(
                SqlCodeArea.class.getResource("/tool/sqlformat/sql-highlight.css").toExternalForm());
        area.textProperty().addListener((obs, oldText, newText) ->
                area.setStyleSpans(0, computeHighlighting(newText)));
        return area;
    }

    private static StyleSpans<Collection<String>> computeHighlighting(String text) {
        Matcher matcher = PATTERN.matcher(text);
        int last = 0;
        StyleSpansBuilder<Collection<String>> builder = new StyleSpansBuilder<>();
        while (matcher.find()) {
            String styleClass = matcher.group("KEYWORD") != null ? "keyword"
                    : matcher.group("STRING") != null ? "string"
                    : matcher.group("NUMBER") != null ? "number"
                    : matcher.group("COMMENT") != null ? "comment"
                    : null;
            builder.add(Collections.emptyList(), matcher.start() - last);
            builder.add(Collections.singleton(styleClass), matcher.end() - matcher.start());
            last = matcher.end();
        }
        builder.add(Collections.emptyList(), text.length() - last);
        return builder.create();
    }
}
