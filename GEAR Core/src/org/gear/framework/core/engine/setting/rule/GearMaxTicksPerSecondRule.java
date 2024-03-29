package org.gear.framework.core.engine.setting.rule;

import org.gear.framework.core.engine.setting.SettingsFileParser;
import org.gear.framework.core.engine.setting.TokenType;

public class GearMaxTicksPerSecondRule extends SettingParseRule<Integer> {
    protected GearMaxTicksPerSecondRule(SettingsFileParser parser) {
        super(parser);
    }

    @Override
    public Integer parse() {
        consume(TokenType.EQUAL, "Expect '=' after 'max_ticks_per_second' identifier.");
        consume(TokenType.INTEGER, "Expect integer after '='.");

        return Integer.parseInt(previous().getLexeme());
    }
}
