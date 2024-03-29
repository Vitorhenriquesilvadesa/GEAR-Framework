package org.gear.framework.core.engine.setting.rule;

import org.gear.framework.core.engine.setting.SettingsFileParser;
import org.gear.framework.core.engine.setting.TokenType;

public class GearUseColdAnnotationsRule extends SettingParseRule<Boolean> {
    protected GearUseColdAnnotationsRule(SettingsFileParser parser) {
        super(parser);
    }

    @Override
    public Boolean parse() {
        consume(TokenType.EQUAL, "Expect '=' after 'cold' identifier.");
        consume(TokenType.BOOLEAN, "Expect boolean value after '='");

        return Boolean.parseBoolean(previous().getLexeme());
    }
}
