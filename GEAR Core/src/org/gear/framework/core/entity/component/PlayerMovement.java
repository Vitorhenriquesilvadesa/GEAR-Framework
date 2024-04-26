package org.gear.framework.core.entity.component;

import org.gear.framework.core.design_patterns.injection.Inject;
import org.gear.framework.core.engine.Time;
import org.gear.framework.core.entity.GameObject;
import org.gear.framework.core.entity.component.movement.TopDownMovement;
import org.gear.framework.core.math.Vector2;
import org.gear.framework.core.service.input.InputManager;
import org.gear.framework.core.service.input.KeyCode;

public class PlayerMovement extends Component{

    @Inject
    private InputManager input;
    private TopDownMovement topDownMovement;
    private final float speed = 32f;

    public PlayerMovement(GameObject parent) {
        super(parent);
    }

    public void start() {
        topDownMovement = getComponent(TopDownMovement.class);
    }

    public void update() {
        Vector2 movement = Vector2.zero();

        if(input.isKeyPressed(KeyCode.SR_KEY_UP)) {
            movement.y += 1;
        }

        if(input.isKeyPressed(KeyCode.SR_KEY_DOWN)) {
            movement.y -= 1;
        }

        if(input.isKeyPressed(KeyCode.SR_KEY_LEFT)) {
            movement.x -= 1;
        }

        if(input.isKeyPressed(KeyCode.SR_KEY_RIGHT)) {
            movement.x += 1;
        }

        movement = Vector2.normalize(movement);
        movement = Vector2.multiply(movement, speed);

        topDownMovement.move(Vector2.multiply(movement, Time.deltaTime()));
    }
}
