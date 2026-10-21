package sourcecode.processors;

import spoon.Launcher;
import spoon.reflect.CtModel;
import spoon.reflect.code.CtAssignment;
import spoon.reflect.code.CtBlock;
import spoon.reflect.code.CtStatement;
import spoon.reflect.declaration.CtConstructor;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.declaration.CtParameter;
import spoon.reflect.declaration.CtTypeParameter;
import spoon.reflect.visitor.CtScanner;
import spoon.reflect.visitor.filter.TypeFilter;

import java.util.List;

public class Main {
    public static void main(String[] args) {
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
//            List<CtStatement> statements = constructor.getBody().getStatements();
//            for(CtStatement stmt : statements) {
//                System.out.println(stmt.getClass() + ":" + stmt);
//            }

            ctMethod.accept(codeScanner);
        }
        System.out.println(codeScanner.getRoot());
    }
}
