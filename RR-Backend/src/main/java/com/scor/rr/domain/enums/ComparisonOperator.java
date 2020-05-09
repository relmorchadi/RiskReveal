package com.scor.rr.domain.enums;

public enum ComparisonOperator {
    //BOTH
    EQ("equals"),
    NOT_EQUAL("notEqual"),
    //Text
    CONTAINS("contains"),
    NOT_CONTAINS("notContains"),
    STARTS_WITH("startsWith"),
    ENDS_WITH("endsWith"),

    //Numbers
    LT("lessThan"),
    LT_OR_EQ("lessThanOrEqual"),
    GT("greaterThan"),
    GT_OR_EQ("greaterThanOrEqual"),

    //Boolean
    EQ_BOOL("equalsBoolean");


    private String value;

    ComparisonOperator(String v) {
        this.value = v;
    }

    public String getValue() {
        return this.value;
    }
}
