package sourcecode.processors.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
@Data
public class TreeNode {
    private String content;
    private List<TreeNode> children = new ArrayList<>();

    public void addChild(TreeNode treeNode) {
        children.add(treeNode);
    }

    @Override
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(content).append("\n");
        for (TreeNode treeNode : children) {
            stringBuffer.append(content + ".child:" + treeNode).append("\n");
        }
        return stringBuffer.toString();
    }
}
