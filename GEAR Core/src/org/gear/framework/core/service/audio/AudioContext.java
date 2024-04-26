package org.gear.framework.core.service.audio;

import org.lwjgl.openal.AL;
import org.lwjgl.openal.AL10;
import org.lwjgl.openal.AL10;
import org.lwjgl.openal.ALCCapabilities;


import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.openal.ALC10.*;

public class AudioContext {

    private List<Integer> buffers;

    public AudioContext() {

    }

    public void destroy() {
    }
}
