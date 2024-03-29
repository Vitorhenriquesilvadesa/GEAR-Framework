package org.gear.framework.core.engine.setting.rule;

import org.gear.framework.core.application.Game;
import org.gear.framework.core.engine.setting.SettingsFileParser;
import org.gear.framework.core.engine.setting.TokenType;

public class GearGameClassRule extends SettingParseRule<Class<? extends Game>> {


    protected GearGameClassRule(SettingsFileParser parser) {
        super(parser);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Class<? extends Game> parse() {
        consume(TokenType.EQUAL, "Expect '=' after 'game_class' identifier.");
        consume(TokenType.STRING, "Expect class name in string format after '='");

        try {
            return (Class<? extends Game>) Class.forName(previous().getLexeme());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
