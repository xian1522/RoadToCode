package sourcecode.processors.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
@Data
public class TreeNode {
    private String content;
    private String type;

    private String aiAnalysis;

    private List<TreeNode> children = new ArrayList<>();

    public void addChild(TreeNode treeNode) {
        children.add(treeNode);
    }

    public boolean hasChildren() {
        return children.size() > 0;
    }

    @Override
    public String toString() {
        return content;
    }
}
