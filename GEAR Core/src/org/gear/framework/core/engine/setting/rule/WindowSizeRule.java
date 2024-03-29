package org.gear.framework.core.engine.setting.rule;

import org.gear.framework.core.engine.setting.SettingsFileParser;
import org.gear.framework.core.engine.setting.TokenType;
import org.gear.framework.core.math.Vector2;

public class WindowSizeRule extends SettingParseRule<Vector2> {

    protected WindowSizeRule(SettingsFileParser parser) {
        super(parser);
    }

    @Override
    public Vector2 parse() {
        consume(TokenType.EQUAL, "Expect '=' after size identifier.");
        consume(TokenType.LEFT_PARENTHESIS, "Expect '(' after size identifier.");
        consume(TokenType.INTEGER, "Expect integer after '(' to get setting for window width.");

        float windowWidth = Integer.parseInt(previous().getLexeme());

        consume(TokenType.COMMA, "Expect ',' after window width.");
        consume(TokenType.INTEGER, "Expect integer after ','.");

        float windowHeight = Integer.parseInt(previous().getLexeme());

        consume(TokenType.RIGHT_PARENTHESIS, "Expect ')' after window height.");

        return new Vector2(windowWidth, windowHeight);
    }
}
