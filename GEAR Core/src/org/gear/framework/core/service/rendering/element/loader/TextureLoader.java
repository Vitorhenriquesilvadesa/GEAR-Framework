package org.gear.framework.core.service.rendering.element.loader;

import org.gear.framework.core.log.annotation.LogAlias;
import org.gear.framework.core.service.general.AssetLoader;
import org.gear.framework.core.service.rendering.element.material.Texture;
import org.lwjgl.BufferUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL11.*;

@SuppressWarnings("ALL")
@LogAlias("Asset Loader")
public class TextureLoader extends AssetLoader {

    public Texture getTexture(String path, ImageFilter filter) {
        int width = 0;
        int height = 0;

        int[] pixels = null;
        try {
            BufferedImage image = ImageIO.read(new FileInputStream(path+".png"));
            width = image.getWidth();
            height = image.getHeight();
            pixels = new int[width * height];
            image.getRGB(0, 0, width, height, pixels, 0, width);
        } catch (IOException e) {
            critical("", new RuntimeException(e));
        }

        assert width != 0 && height != 0;

        int[] data = new int[width * height];
        for (int i = 0; i < width * height; i++) {
            int a = (pixels[i] & 0xff000000) >> 24;
            int r = (pixels[i] & 0xff0000) >> 16;
            int g = (pixels[i] & 0xff00) >> 8;
            int b = (pixels[i] & 0xff);

            data[i] = a << 24 | b << 16 | g << 8 | r;
        }

        int result = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, result);

        int _filter = GL_LINEAR;

        switch(filter) {
            case POINT -> _filter = GL_NEAREST;
            case LINEAR -> _filter = GL_LINEAR;
        }

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, _filter);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, _filter);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);

        IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
        buffer.put(data).flip();

        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA,
                GL_UNSIGNED_BYTE, buffer);
        glBindTexture(GL_TEXTURE_2D, 0);
        return new Texture(width, height, result);
    }

    public Texture getTexture(BufferedImage image, ImageFilter filter, int spriteWidth, int spriteHeight, int tileSize) {
        int width = 0;
        int height = 0;

        int[] pixels = null;
        width = tileSize;
        height = tileSize;

        pixels = new int[width * height];
        image.getRGB(spriteWidth, spriteHeight, width,height, pixels, 0, width);

        assert width != 0 && height != 0;

        int[] data = new int[width * height];
        for (int i = 0; i < width * height; i++) {
            int a = (pixels[i] & 0xff000000) >> 24;
            int r = (pixels[i] & 0xff0000) >> 16;
            int g = (pixels[i] & 0xff00) >> 8;
            int b = (pixels[i] & 0xff);

            data[i] = a << 24 | b << 16 | g << 8 | r;
        }

        int result = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, result);

        int _filter;

        switch(filter) {
            case POINT -> _filter = GL_NEAREST;
            default -> _filter = GL_LINEAR;
        }

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, _filter);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, _filter);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);

        IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
        buffer.put(data).flip();

        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA,
                GL_UNSIGNED_BYTE, buffer);
        glBindTexture(GL_TEXTURE_2D, 0);
        return new Texture(width, height, result);
    }
}
