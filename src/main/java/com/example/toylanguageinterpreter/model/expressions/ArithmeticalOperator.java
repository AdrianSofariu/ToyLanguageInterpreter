package com.example.toylanguageinterpreter.model.expressions;

public enum ArithmeticalOperator {
    PLUS {
        @Override
        public String toString() {
            return "+";
        }
    },
    MINUS{
        @Override
        public String toString() {
            return "-";
        }
    },
    MULTIPLY{
        @Override
        public String toString() {
            return "*";
        }
    },
    DIVIDE{
        @Override
        public String toString() {
            return "/";
        }
    };
}



