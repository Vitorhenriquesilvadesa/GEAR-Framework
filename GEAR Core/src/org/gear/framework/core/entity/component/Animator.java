package org.gear.framework.core.entity.component;

import org.gear.framework.core.entity.component.Sprite.Animation;
import org.gear.framework.core.entity.component.Sprite.Frame;
import org.gear.framework.core.engine.Time;
import org.gear.framework.core.entity.GameObject;
import org.gear.framework.core.log.annotation.GenerateCriticalFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@GenerateCriticalFile
public class Animator extends Component{
    private Map<String, Animation> animations = new HashMap<>();
    private Frame currentFrame;
    private String currentAnimationName;
    private int animationIndex = 0;
    private Animation animation;
    public Animator(GameObject parent){
        super(parent);
    }
    public void start(){animations = new HashMap<>();}
    public void addAnimation(Animation animation, String name){
        animations.put(name,animation);
        currentAnimationName = name;
    }
    @Override
    public void update() {
        if(animations.containsKey(currentAnimationName)){
            animation = animations.get(currentAnimationName);
            List<Frame> frames = animation.getFrames();
            currentFrame = frames.get(animationIndex);
            if(Time.deltaTime() >= currentFrame.getDuration()){
                animationIndex ++;
            }
            if(animationIndex >= frames.size()){
                animationIndex = 0;
            }
        }
    }
     public Frame getCurrentFrame(){
        return currentFrame;
     }
}
