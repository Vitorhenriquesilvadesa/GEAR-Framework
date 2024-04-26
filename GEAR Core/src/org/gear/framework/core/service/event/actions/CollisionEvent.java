package org.gear.framework.core.service.event.actions;

import org.gear.framework.core.entity.component.collider.Collider;
import org.gear.framework.core.service.event.reactive.ReactiveEvent;

public class CollisionEvent extends ReactiveEvent {

    public final Collider firstCollider;
    public final Collider secondCollider;

    public CollisionEvent(Collider firstCollider, Collider secondCollider) {
        this.firstCollider = firstCollider;
        this.secondCollider = secondCollider;
    }

    @Override
    public String toString() {
        return "Collision Event";
    }
}
