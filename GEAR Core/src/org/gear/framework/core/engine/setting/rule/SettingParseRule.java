package org.gear.framework.core.engine.setting.rule;

import org.gear.framework.core.engine.setting.SettingsFileParser;
import org.gear.framework.core.engine.setting.Token;
import org.gear.framework.core.engine.setting.TokenType;

public abstract class SettingParseRule<T> {

    protected SettingsFileParser parser;

    protected SettingParseRule(SettingsFileParser parser) {
        this.parser = parser;
    }

    public abstract T parse();

    protected Token advance() {
        return parser.advance();
    }

    protected Token peek() {
        return parser.peek();
    }

    protected void consume(TokenType exptect, String message) {
        parser.consume(exptect, message);
    }

    protected Token previous() {
        return parser.previous();
    }
}
