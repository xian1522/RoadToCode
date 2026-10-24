package sourcecode.util;

import spoon.reflect.code.CtExpression;
import spoon.reflect.code.CtInvocation;
import spoon.reflect.code.CtReturn;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.declaration.CtExecutable;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.reference.CtExecutableReference;

public class SourceCodeUtil {

    public static boolean isRecursiveCall(CtInvocation ctInvocation) {

        CtMethod ctMethod = getParentCtMethod(ctInvocation);

        if(ctMethod != null) {
            CtMethod ctMethodFromInvocation = getCtMethodFromInvocation(ctInvocation);
            if(ctMethodFromInvocation.getSimpleName().equals(ctMethod.getSimpleName())
                    && ctMethodFromInvocation.getParameters().size() == ctMethod.getParameters().size()) {
                for(int i=0; i < ctMethod.getParameters().size(); i++) {
                    if(!ctMethodFromInvocation.getParameters().get(i).equals(ctMethod.getParameters().get(i))) {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }

    public static CtMethod getParentCtMethod(CtElement ctElement) {
        CtElement parent = ctElement.getParent();
        if(parent == null) {
            return null;
        }
        if(parent instanceof CtMethod<?>) {
            return (CtMethod) parent;
        }else {
            return getParentCtMethod(parent);
        }
    }
    public static CtMethod getCtMethodFromInvocation(CtInvocation ctInvocation) {
        CtExecutableReference executable = ctInvocation.getExecutable();
        CtExecutable declaration = executable.getDeclaration();
        if(declaration instanceof CtMethod) {
            return (CtMethod) declaration;
        }
        return null;
    }
}
