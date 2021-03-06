package com.craftinginterpreters.lox;

final class Token {
    final TokenType type;
    final String lexeme;
    final Object literal;
    final int line;
    // final int column; ??

    Token(final TokenType type, final String lexeme, final Object literal, final int line) {
        this.type = type;
        this.lexeme = lexeme;
        this.literal = literal;
        this.line = line;
    }

    public String toString() {
        return type + " " + lexeme + " " + literal;
    }
}