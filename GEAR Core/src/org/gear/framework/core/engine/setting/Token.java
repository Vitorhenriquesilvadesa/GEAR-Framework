package org.gear.framework.core.engine.setting;

public class Token {

    private final TokenType tokenType;
    private final String lexeme;
    private final int line;

    public Token(TokenType tokenType, String lexeme, int line) {
        this.tokenType = tokenType;
        this.lexeme = lexeme;
        this.line = line;
    }

    public TokenType getTokenType() {
        return tokenType;
    }

    public String getLexeme() {
        return lexeme;
    }

    public int getLine() {
        return line;
    }


    @Override
    public String toString() {
        return "Token { " + tokenType.name() + ": " + lexeme + " }";
    }
}
