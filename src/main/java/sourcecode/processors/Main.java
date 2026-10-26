package sourcecode.processors;

import sourcecode.processors.model.TreeNode;
import spoon.Launcher;
import spoon.reflect.CtModel;
import spoon.reflect.code.CtAssignment;
import spoon.reflect.code.CtBlock;
import spoon.reflect.code.CtStatement;
import spoon.reflect.declaration.*;
import spoon.reflect.factory.Factory;
import spoon.reflect.reference.CtArrayTypeReference;
import spoon.reflect.reference.CtTypeReference;
import spoon.reflect.visitor.CtScanner;
import spoon.reflect.visitor.filter.TypeFilter;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        Launcher launcher = new Launcher();
        launcher.addInputResource("E:\\myProject\\spring-boot-2.6.x\\spring-boot-project\\spring-boot\\src\\main\\java");
        CtModel ctModel = launcher.buildModel();
        Factory factory = launcher.getFactory();
        //类名
        CtClass<Object> springBootClass = factory.Class().get("org.springframework.boot.SpringApplication");
        //方法名
        String methodName = "run";
        //参数名
        List<String> arguments = Arrays.asList("primarySource, args".split(","));

        Set<CtMethod<?>> ctMethods = springBootClass.getMethods();

        CodeScanner codeScanner = new CodeScanner(launcher.getEnvironment());

        for(CtMethod ctMethod : ctMethods) {
            List parameters = ctMethod.getParameters();
            if(!ctMethod.getSimpleName().equals(methodName)) {
                continue;
            }
            if(parameters.size() != arguments.size()) {
                continue;
            }
            for(int i = 0; i < parameters.size(); i++) {
                CtParameter ctParameter = (CtParameter)parameters.get(i);
                if(!ctParameter.getSimpleName().equals(arguments.get(i))){
                    continue;
                }
            }
            ctMethod.accept(codeScanner);
            break;
        }

        TreeNode root = codeScanner.getRoot();
    }
}
