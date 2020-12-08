package com.document.documentTranslator.util.query;

import com.querydsl.core.types.dsl.BooleanExpression;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class DslFiltering {

    private List<BooleanExpression> expressions;
    private Integer begin;
    private Integer length;
    private Map<String, Object> input;

    public DslFiltering() {

        expressions = new ArrayList<>();
    }

    public void addExpression(BooleanExpression expression) {

        this.expressions.add(expression);
    }

    public Integer getBegin() {

        return begin;
    }

    public void setBegin(Integer begin) {

        this.begin = begin;
    }

    public Integer getLength() {

        return length;
    }

    public void setLength(Integer length) {

        this.length = length;
    }

    public BooleanExpression aggregateExpressions() {

        BooleanExpression result = null;
        if (!expressions.isEmpty()) {
            result = expressions.get(0);
            if (expressions.size() > 1) {
                for (int i = 1; i < expressions.size(); i++) {
                    result = result.and(expressions.get(i));
                }
            }
        }
        return result;
    }

    public Map<String, Object> getInput() {

        return input;
    }

    public void setInput(Map<String, Object> input) {

        this.input = input;
    }

    public List<BooleanExpression> getExpressions() {

        return expressions;
    }

    public void setExpressions(List<BooleanExpression> expressions) {

        this.expressions = expressions;
    }

    @Override
    public String toString() {

        return "DslFiltering [begin=" + begin + ", length=" + length + "]";
    }
}

