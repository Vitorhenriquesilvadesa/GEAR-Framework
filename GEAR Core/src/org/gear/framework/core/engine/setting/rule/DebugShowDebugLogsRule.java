package org.gear.framework.core.engine.setting.rule;

import org.gear.framework.core.engine.setting.SettingsFileParser;
import org.gear.framework.core.engine.setting.TokenType;

public class DebugShowDebugLogsRule extends SettingParseRule<Boolean> {

    protected DebugShowDebugLogsRule(SettingsFileParser parser) {
        super(parser);
    }

    @Override
    public Boolean parse() {
        consume(TokenType.EQUAL, "Expect '=' after 'show_logs' identifier.");
        consume(TokenType.BOOLEAN, "Expect boolean after '='.");

        return Boolean.parseBoolean(previous().getLexeme());
    }
}
