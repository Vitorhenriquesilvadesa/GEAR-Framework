package org.gear.framework.core.engine.setting;

import org.gear.framework.core.engine.setting.rule.SettingRule;
import org.gear.framework.core.engine.setting.rule.SettingRuleFactory;
import org.gear.framework.core.application.Game;
import org.gear.framework.core.math.Vector2;

import java.util.List;

public class SettingsFileParser {

    private List<Token> tokens;
    private SettingsClassBuilder settingsClassBuilder;
    private int current;
    private final SettingRuleFactory ruleFactory;

    public SettingsFileParser() {
        this.ruleFactory = new SettingRuleFactory(this);
    }

    public SettingsClassBuilder parseTokens(List<Token> tokens) {
        this.tokens = tokens;
        this.settingsClassBuilder = new SettingsClassBuilder();

        while (!isAtEnd()) {
            parseToken();
            advance();
        }

        return settingsClassBuilder;
    }

    private boolean isAtEnd() {
        return current >= tokens.size();
    }

    private void parseToken() {

        if (peek().getTokenType() == TokenType.NEW_LINE) {
            return;
        }

        switch (peek().getTokenType()) {
            case TokenType.WINDOW: {
                windowSetting();
                break;
            }
            case TokenType.GEAR: {
                gearSetting();
                break;
            }
            case TokenType.DEBUG: {
                debugSetting();
            }
            default: {
                if (peek().getTokenType() == TokenType.SEMICOLON || peek().getTokenType() == TokenType.END_OF_FILE) {
                    break;
                }

                throw new SettingsFileParseException("Unexpected token '" + peek().getLexeme() + "' at line " + peek().getLine() + ".");
            }
        }
    }

    private void debugSetting() {
        advance();
        consume(TokenType.DOT, "Expect '.' after 'gear' identifer");
        advance();

        switch (previous().getLexeme()) {
            case "show_logs": {
                debugShowLogs();
                break;
            }

            case "generate_critical_files": {
                debugGenerateCriticalFiles();
                break;
            }

            case "enable_file_tracking": {
                debugEnableFileTracking();
            }
        }
    }

    private void debugGenerateCriticalFiles() {
        boolean generateCriticalFiles = ruleFactory.parseWithRule(SettingRule.DebugGenerateCriticalFiles, Boolean.class);
        settingsClassBuilder.generateCriticalFiles(generateCriticalFiles);
    }

    private void debugShowLogs() {
        boolean showLogs = ruleFactory.parseWithRule(SettingRule.DebugShowLogs, Boolean.class);
        settingsClassBuilder.showLogs(showLogs);
    }

    private void debugEnableFileTracking() {
        boolean enableFileTracking = ruleFactory.parseWithRule(SettingRule.DebugEnableFileTracking, Boolean.class);
        settingsClassBuilder.enableFileTracking(enableFileTracking);
    }

    private void gearSetting() {

        advance();
        consume(TokenType.DOT, "Expect '.' after 'gear' identifer.");
        advance();

        switch (previous().getLexeme()) {
            case "max_ticks_per_second": {
                maxTicksPerSecond();
                break;
            }

            case "use_cold_annotations": {
                useColdAnnotations();
                break;
            }

            case "root_directory": {
                rootDirectory();
                break;
            }

            case "game_class": {
                gameClass();
                break;
            }

            default: {
                throw new SettingsFileParseException("Unknown gear setting:" + previous().getLexeme());
            }
        }
    }

    @SuppressWarnings("unchecked")
    private void gameClass() {
        Class<? extends Game> gameClass = ruleFactory.parseWithRule(SettingRule.GearGameClass, Class.class);
        settingsClassBuilder.gameClass(gameClass);
    }

    private void rootDirectory() {
        String rootDirectory = ruleFactory.parseWithRule(SettingRule.GearRootDirectory, String.class);
        settingsClassBuilder.rootDirectory(rootDirectory);
    }

    private void useColdAnnotations() {
        boolean useColdAnnotations = ruleFactory.parseWithRule(SettingRule.GearUseColdAnnotations, Boolean.class);
        settingsClassBuilder.useColdAnnotations(useColdAnnotations);
    }

    private void maxTicksPerSecond() {
        int maxTicksPerSecond = ruleFactory.parseWithRule(SettingRule.GearMaxTicksPerSecond, Integer.class);
        settingsClassBuilder.maxTicksPerSecond(maxTicksPerSecond);
    }

    private void windowSetting() {

        advance();
        consume(TokenType.DOT, "Expect '.' after 'window' identifer");
        advance();

        switch (previous().getLexeme()) {
            case "size": {
                windowSize();
                break;
            }
            case "fullscreen": {
                windowFullscreen();
                break;
            }
            case "title": {
                windowTitle();
                break;
            }
            default: {
                throw new SettingsFileParseException("Unknown window setting:" + previous().getLexeme());
            }
        }
    }

    private void windowTitle() {
        String windowTitle = ruleFactory.parseWithRule(SettingRule.WindowTitle, String.class);
        settingsClassBuilder.windowTitle(windowTitle);
    }

    private void windowFullscreen() {
        boolean isFullscreen = ruleFactory.parseWithRule(SettingRule.WindowFullscreen, Boolean.class);
        settingsClassBuilder.isFullScreen(isFullscreen);
    }

    private void windowSize() {
        Vector2 windowSize = ruleFactory.parseWithRule(SettingRule.WindowSize, Vector2.class);
        settingsClassBuilder.windowSize(windowSize);
    }

    public void consume(TokenType expect, String message) {
        if (peek().getTokenType() != expect) {
            Token token = peek();
            throw new SettingsFileParseException(message + " [At line " + token.getLine() + "]");
        }

        advance();
    }

    public Token advance() {
        if (!isAtEnd()) current++;
        return previous();
    }

    private boolean match(TokenType... types) {
        for (TokenType type : types) {
            if (check(type)) {
                advance();
                return true;
            }
        }

        return false;
    }

    public Token previous() {
        return tokens.get(current - 1);
    }

    private boolean check(TokenType type) {
        if (isAtEnd()) return false;
        return peek().getTokenType() == type;
    }

    public Token peek() {
        return tokens.get(current);
    }

    public SettingsClassBuilder getClassBuilder() {
        return settingsClassBuilder;
    }
}
