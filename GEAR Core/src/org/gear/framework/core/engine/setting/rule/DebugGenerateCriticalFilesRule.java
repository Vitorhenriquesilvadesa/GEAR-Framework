package org.gear.framework.core.engine.setting.rule;

import org.gear.framework.core.engine.setting.SettingsFileParser;
import org.gear.framework.core.engine.setting.TokenType;

public class DebugGenerateCriticalFilesRule extends SettingParseRule<Boolean> {

    protected DebugGenerateCriticalFilesRule(SettingsFileParser parser) {
        super(parser);
    }

    @Override
    public Boolean parse() {
        consume(TokenType.EQUAL, "Expect '=' after 'generate_critical_files' identifier.");
        consume(TokenType.BOOLEAN, "Expect boolean after '='.");

        return Boolean.parseBoolean(previous().getLexeme());
    }
}
