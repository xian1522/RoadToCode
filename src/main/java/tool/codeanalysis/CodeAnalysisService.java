package tool.codeanalysis;

import javafx.scene.Node;
import sourcecode.processors.CodeScanner;
import sourcecode.processors.model.TreeNode;
import spoon.Launcher;
import spoon.reflect.CtModel;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.visitor.filter.TypeFilter;

import java.util.List;

public class CodeAnalysisService {

    public TreeNode codeAnalysis() {
        Launcher launcher = new Launcher();
        launcher.addInputResource("E:\\myProject\\spring-boot-2.6.x\\spring-boot-project\\spring-boot\\src\\main\\java\\org\\springframework\\boot\\SpringApplication.java");
        CtModel ctModel = launcher.buildModel();

        List<CtMethod> ctMethods = ctModel.getElements(new TypeFilter<>(CtMethod.class));

        CodeScanner codeScanner = new CodeScanner(launcher.getEnvironment());

        for(CtMethod ctMethod : ctMethods) {
            List parameters = ctMethod.getParameters();
            if(!ctMethod.getSimpleName().equals("getSpringFactoriesInstances")) {
                continue;
            }
            if(parameters.size() != 3) {
                continue;
            }
            ctMethod.accept(codeScanner);
        }
        return codeScanner.getRoot();
    }
}
