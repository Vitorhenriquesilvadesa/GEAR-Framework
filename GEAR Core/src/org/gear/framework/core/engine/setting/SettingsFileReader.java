package org.gear.framework.core.engine.setting;

import java.util.List;

public class SettingsFileReader {

    private static final SettingsFileLexer lexer = new SettingsFileLexer();
    private static final SettingsFileParser parser = new SettingsFileParser();

    public static SettingsClassBuilder readFile(String filepath) {
        List<Token> tokens = lexer.scanFile(filepath);
        return parser.parseTokens(tokens);
    }
}
