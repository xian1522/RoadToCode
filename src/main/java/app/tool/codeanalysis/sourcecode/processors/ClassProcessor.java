package app.tool.codeanalysis.sourcecode.processors;

import spoon.processing.AbstractProcessor;
import spoon.reflect.code.CtBlock;
import spoon.reflect.code.CtStatement;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtMethod;

import java.util.List;
import java.util.Set;

public class ClassProcessor extends AbstractProcessor<CtClass<?>> {

    @Override
    public void process(CtClass<?> element) {
        List<CtMethod<?>> toString = element.getMethodsByName("toString");
        for(CtMethod<?> method : toString) {
            CtBlock<?> body = method.getBody();
            List<CtStatement> statements = body.getStatements();
            for(CtStatement statement : statements) {
                System.out.println(statement);
            }
        }
    }
}
