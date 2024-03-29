package org.gear.framework.core.engine.setting;

import org.gear.framework.core.log.annotation.GenerateCriticalFile;
import org.gear.framework.core.service.general.AssetLoader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@GenerateCriticalFile
public class SettingsFileLexer extends AssetLoader {

    private final List<Token> tokens;
    private String fileContent;
    private int start = 0;
    private int current = 0;
    private int line = 1;

    private static Map<String, TokenType> keywords = new HashMap<>();

    static {
        keywords.put("window", TokenType.WINDOW);
        keywords.put("gear", TokenType.GEAR);
        keywords.put("debug", TokenType.DEBUG);
        keywords.put("true", TokenType.BOOLEAN);
        keywords.put("false", TokenType.BOOLEAN);
    }

    public SettingsFileLexer() {
        this.tokens = new ArrayList<>();
    }

    public List<Token> scanFile(String filepath) {

        this.fileContent = getFileContent(filepath);

        while (!isAtEnd()) {
            start = current;
            scanToken();
        }

        makeToken(TokenType.END_OF_FILE, "\0");
        return this.tokens;
    }

    private void scanToken() {

        char c = advance();

        switch (c) {
            case ' ', '\t': {
                break;
            }
            case '\n': {
                line++;
                break;
            }
            case '(': {
                makeToken(TokenType.LEFT_PARENTHESIS, "(");
                break;
            }
            case ')': {
                makeToken(TokenType.RIGHT_PARENTHESIS, ")");
                break;
            }
            case '.': {
                makeToken(TokenType.DOT, ".");
                break;
            }
            case '"': {
                string();
                break;
            }
            case '=': {
                makeToken(TokenType.EQUAL, "=");
                break;
            }
            case ';': {
                makeToken(TokenType.SEMICOLON, ";");
                break;
            }
            case ':': {
                makeToken(TokenType.SEMICOLON, ":");
                break;
            }
            case ',': {
                makeToken(TokenType.COMMA, ",");
                break;
            }
            case '#': {
                comment();
                break;
            }

            default: {

                if (isNumeric(peek())) {
                    number();
                    break;
                }

                if (isAlphaNumeric(peek())) {
                    identifier();
                    break;
                }
            }
        }
    }

    private void comment() {
        while (peek() != '\n') {
            advance();
        }
    }

    private String getFileContent(String filepath) {

        StringBuilder builder = new StringBuilder();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(filepath));
            String line;

            while ((line = reader.readLine()) != null) {
                builder.append(line).append("\n");
            }

        } catch (IOException e) {
            critical("Error to parse config file.", new RuntimeException(e));
        }

        return builder.toString();
    }

    private char peek() {
        return fileContent.charAt(current);
    }

    private char advance() {
        if (!isAtEnd()) {
            return fileContent.charAt(current++);
        }

        return '\0';
    }

    private void back() {
        current--;
    }

    private boolean isAtEnd() {
        return current >= fileContent.length() - 1;
    }

    private void identifier() {
        while (isAlphaNumeric(peek()) || peek() == '_') {
            advance();
        }


        String word = fileContent.substring(start, current);

        makeToken(keywords.getOrDefault(word, TokenType.IDENTIFIER), word);
    }

    private void string() {
        char c = advance();
        while (c != '"' && c != '\n') {
            c = advance();
        }

        if(c == '\n') {
            line++;
            critical("Error: unterminated string at line" + (line - 1),
                    new RuntimeException());
        }

        String string = trim(fileContent.substring(start, current));
        makeToken(TokenType.STRING, string);
    }

    private String trim(String source) {
        return source.substring(1, source.length() - 1);
    }

    private void number() {

        boolean isFloat = false;

        while (!isAtEnd() && isNumeric(peek()) || peek() == '.') {
            if (peek() == '.') {
                if (isFloat) {
                    critical("Error to parse number in 'gconfig' file at line " + (line - 1),
                            new RuntimeException());
                } else {
                    isFloat = true;
                }
            }

            advance();

            if (isFloat && peek() == '.') {
                critical("Error to parse number in 'gconfig' file at line " + (line - 1),
                        new RuntimeException());
            }
        }

        String number = fileContent.substring(start, current);

        if(isFloat){
            makeToken(TokenType.FLOAT, number);
        } else {
            makeToken(TokenType.INTEGER, number);
        }
    }

    private boolean isNumeric(char c) {
        return c >= 48 && c <= 57;
    }

    private boolean isAlphaNumeric(char c) {
        return isNumeric(c) || isAlpha(c);
    }

    private boolean isAlpha(char c) {
        return (c >= 65 && c <= 90) || (c >= 97 && c <= 122);
    }

    private void makeToken(TokenType type, String lexeme) {
        this.tokens.add(new Token(type, lexeme, line));
    }
}
