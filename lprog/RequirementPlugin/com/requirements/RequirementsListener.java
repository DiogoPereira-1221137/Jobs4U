package com.requirements;

import org.antlr.v4.runtime.tree.ErrorNode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RequirementsListener extends RequirementsABaseListener{

    private List<String> listErrors = new ArrayList<>();

    @Override
    public void visitErrorNode(ErrorNode errorNode){
        addError(String.format("Error, %s in  line %s\n", errorNode.getText(), errorNode.getSymbol().getLine()));
    }

    private void addError(String error) {
        listErrors.add(error);
    }

    public List<String> getResult() {
        if (listErrors.size() == 0) return Collections.singletonList("All correct, file was validated!!");
        return listErrors;
    }
}
