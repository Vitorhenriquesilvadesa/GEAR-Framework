package org.gear.framework.core.engine.setting.rule;

import org.gear.framework.core.engine.setting.SettingsFileParser;
import org.gear.framework.core.engine.setting.TokenType;

public class WindowTitleRule extends SettingParseRule<String> {

    protected WindowTitleRule(SettingsFileParser parser) {
        super(parser);
    }

    @Override
    public String parse() {
        consume(TokenType.EQUAL, "Expect '=' after title identifier.");
        consume(TokenType.STRING, "Expect string after '='.");

        return previous().getLexeme();
    }
}
