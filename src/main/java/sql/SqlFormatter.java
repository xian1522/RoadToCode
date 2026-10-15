package sql;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SqlFormatter {
    //缩进
    private int level;
    //SQL每行内容
    private String text;
    @Override
    public String toString(){
        return "    ".repeat(level) + text + "\n";
    }
}
