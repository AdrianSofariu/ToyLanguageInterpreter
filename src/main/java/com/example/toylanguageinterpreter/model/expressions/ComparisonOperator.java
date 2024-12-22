package com.example.toylanguageinterpreter.model.expressions;

public enum ComparisonOperator {
    LESS {
        @Override
        public String toString() {
            return "<";
        }
    },
    LESS_OR_EQUAL{
        @Override
        public String toString() {
            return "<=";
        }
    },
    EQUAL{
        @Override
        public String toString() {
            return "==";
        }
    },
    NOT_EQUAL{
        @Override
        public String toString() {
            return "!=";
        }
    },
    GREATER{
        @Override
        public String toString() {
            return ">";
        }
    },
    GREATER_OR_EQUAL{
        @Override
        public String toString() {
            return ">=";
        }
    };
}
