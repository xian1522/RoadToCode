package tool.codeanalysis;

import sourcecode.processors.CodeScanner;
import sourcecode.processors.model.TreeNode;
import spoon.Launcher;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.declaration.CtParameter;
import spoon.reflect.factory.Factory;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CodeAnalysisService {

    private final CodeAiService codeAiService = new CodeAiService();

    public TreeNode codeAnalysis(String projectPath, String className, String methodName, List<String> paramNames) throws IOException {
        Launcher launcher = new Launcher();
        launcher.addInputResource(projectPath.trim());
        launcher.buildModel();
        Factory factory = launcher.getFactory();
        //类名
        CtClass<Object> springBootClass = factory.Class().get("org.springframework.boot.SpringApplication");

        CodeScanner codeScanner = new CodeScanner(launcher.getEnvironment());

        for (CtMethod ctMethod : springBootClass.getMethods()) {
            if (!ctMethod.getSimpleName().equals(methodName.trim())) {
                continue;
            }
            if (matchesParams(ctMethod, paramNames)) {
                ctMethod.accept(codeScanner);
            }
        }
        TreeNode root = codeScanner.getRoot();
        return codeAiService.askAi(root);
//        return root;
    }

    private boolean matchesParams(CtMethod ctMethod, List<String> paramNames) {
        List<CtParameter> parameters = ctMethod.getParameters();
        if (paramNames.size() != parameters.size()) {
            return false;
        }
        for (int i = 0; i < parameters.size(); i++) {
            if (!parameters.get(i).getSimpleName().equals(paramNames.get(i))) {
                return false;
            }
        }
        return true;
    }

    public static List<String> parseParamNames(String text) {
        return Arrays.stream(text.split("[,，]"))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toList());
    }
}
