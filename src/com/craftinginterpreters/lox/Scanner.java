package com.craftinginterpreters.lox;

import static com.craftinginterpreters.lox.TokenType.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Scanner {
    private final String source;
    private final List<Token> tokens = new ArrayList<>();

    private int start = 0;
    private int current = 0;
    private final int line = 1;

    Scanner(final String source) {
        this.source = source;
    }

    List<Token> scanTokens() {
        while (!isAtEnd()) {
            // We are the beginning of the next lexeme.
            start = current;
            scanToken();
        }

        tokens.add(new Token(EOF, "", null, line));
        return tokens;
    }

    private void scanToken() {
        final char c = advance();
        switch (c) {
            case '(':
                addToken(LEFT_PAREN);
                break;
            case ')':
                addToken(RIGHT_PAREN);
                break;
            case '{':
                addToken(LEFT_BRACE);
                break;
            case '}':
                addToken(RIGHT_BRACE);
                break;
            case ',':
                addToken(COMMA);
                break;
            case '.':
                addToken(DOT);
                break;
            case ';':
                addToken(SEMICOLON);
                break;
            case '*':
                addToken(STAR);
                break;
            case '!':
                if (match('=')) {
                    addToken(BANG_EQUAL);
                } else {
                    addToken(BANG);
                }
                break;
            case '=':
                if (match('=')) {
                    addToken(EQUAL_EQUAL);
                } else {
                    addToken(EQUAL);
                }
                break;
            case '<':
                if (match('=')) {
                    addToken(LESS_EQUAL);
                } else {
                    addToken(LESS);
                }
                break;
            case '>':
                if (match('=')) {
                    addToken(GREATER_EQUAL);
                } else {
                    addToken(GREATER);
                }
                break;
            case '-':
                if (match('=')) {
                    addToken(MINUS_EQUAL);
                } else if (match('-')) {
                    addToken(MINUS_MINUS);
                } else {
                    addToken(MINUS);
                }
                break;
            case '+':
                if (match('=')) {
                    addToken(PLUS_EQUAL);
                } else if (match('+')) {
                    addToken(PLUS_PLUS);
                } else {
                    addToken(MINUS);
                }
                addToken(PLUS);
                break;
            default:
                // Can we detect a series of invalid characters, rather than
                // each character individually?
                Lox.error(line, "Unexpected character.");
                break;
        }
    }

    private boolean match(final char expected) {
        if (isAtEnd()) {
            return false;
        }
        if (source.charAt(current) != expected) {
            return false;
        }

        current++;
        return true;
    }

    private boolean isAtEnd() {
        return current >= source.length();
    }

    private char advance() {
        current++;
        return source.charAt(current - 1);
    }

    private void addToken(final TokenType type) {
        addToken(type, null);
    }

    private void addToken(final TokenType type, final Object literal) {
        final String text = source.substring(start, current);
        tokens.add(new Token(type, text, literal, line));
    }
}