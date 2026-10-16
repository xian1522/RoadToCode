package app;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 工具注册表：注册 / 枚举工具，按分组取。
 * 后续如需自动发现，可在此基础上换成 SPI 扫描。
 */
public class ToolRegistry {

    private final List<Tool> tools = new ArrayList<>();

    public void register(Tool tool) {
        tools.add(tool);
    }

    public List<Tool> getTools() {
        return Collections.unmodifiableList(tools);
    }

    public List<String> getGroups() {
        return tools.stream().map(Tool::getGroup).distinct().collect(Collectors.toList());
    }

    public List<Tool> getByGroup(String group) {
        return tools.stream().filter(t -> group.equals(t.getGroup())).collect(Collectors.toList());
    }
}
