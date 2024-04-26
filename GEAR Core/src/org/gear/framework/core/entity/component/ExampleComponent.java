//package com.vgames.survivalreckoning.framework.entity.component;
//
//import com.vgames.survivalreckoning.framework.entity.GameObject;
//
//public class ExampleComponent extends Component {
//    public ExampleComponent(GameObject parent) {
//        super(parent);
//
//        EventAPI.subscribeListener(CollisionEvent.class, this::onCollisionEnter);
//    }
//
//    public void onCollisionEnter(CollisionEvent event) {
//
//        if(event.firstCollider.tag == "player" && event.secondCollider.tag == "enemy") {
//            if(event.firstCollider.parent.getUUID() == this.parent.getUUID()) {
//                HealthComponent healthComponent = parent.getComponent(HealthComponent.class);
//                healthComponent.subtractLife(3);
//            }
//        }
//
//        if(event.firstCollider.tag == "enemy" && event.secondCollider.tag == "player") {
//            if(event.secondCollider.parent.getUUID() == this.parent.getUUID()) {
//                HealthComponent healthComponent = parent.getComponent(HealthComponent.class);
//                healthComponent.subtractLife(3);
//            }
//        }
//    }
//}
