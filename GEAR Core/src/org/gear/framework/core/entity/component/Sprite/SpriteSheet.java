package org.gear.framework.core.entity.component.Sprite;

import org.gear.framework.core.engine.Engine;
import org.gear.framework.core.service.rendering.GraphicsAPI;
import org.gear.framework.core.service.rendering.element.loader.ImageFilter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SpriteSheet {
    private List<Sprite> sprites;
    private int width,height;
    private boolean spriteSheetEnd = true;
    /**
     * @param row "The number of the line that the image need to be reading"
     * **/
    public SpriteSheet(String path, int sheetWidth, int sheetHeight, int row,int spriteWidth,int spriteHeight,int tileSize) {
        BufferedImage image = null;
        sprites = new ArrayList<>();
        try {
            image = ImageIO.read(new FileInputStream(path + ".png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        width = image.getWidth();
        height = image.getHeight();

        int spriteW = spriteWidth;
        int spriteH = spriteHeight;

        assert width % sheetWidth == 0;
        assert height % sheetHeight == 0;

        while(spriteSheetEnd){
            createSprite(spriteW,spriteH,tileSize,image);
            spriteW += tileSize;

            if(spriteW == width && spriteH + tileSize == height){
                spriteSheetEnd = false;
            }
            if(spriteW  >= width){
                if(spriteH + tileSize < height){
                    spriteH += tileSize;
                }
                spriteW = 0;
            }

        }
    }

    private void createSprite(int spriteWidth,int spriteHeight,int tileSize,BufferedImage image){
        if(isEmpty(image,spriteWidth, spriteHeight)){
            sprites.add(new Sprite(Engine.fromService(GraphicsAPI.class).loadTexture(image, ImageFilter.POINT, spriteWidth, spriteHeight, tileSize)));
        }else {
            spriteSheetEnd = false;
        }
    }
    /**
     * Verify if the image is transparent
     * **/
    public boolean isEmpty(BufferedImage image, int spriteWidth,int spriteHeight){
        for (int y = 0; y < spriteHeight; y++) {
            for (int x = 0; x < spriteWidth; x++) {
                int alpha = (image.getRGB(x, y) >> 24) & 0xFF;
                if (alpha != 0) {
                    return false;
                }
            }
        }
        return true;
    }
//
//    private void parseSprite(BufferedImage image, int row){
//        int y  = row * height;
//        int column = 0;
//
//        while(width * column < image.getWidth()){
//            BufferedImage subImage = image.getSubimage(width * column,y, width,height);
//            if(!isBoundingBoxEmpty(subImage)){
//                Sprite sprite = new Sprite(Engine.fromService(GraphicsAPI.class).loadTexture(subImage,ImageFilter.LINEAR));
//                sprites.add(sprite);
//            }
//            column ++;
//        }
//    }

    private boolean isBoundingBoxEmpty(BufferedImage image){
        for(int i = 0; i < image.getWidth(); i ++){
            for(int j = 0; j < image.getHeight(); j ++){
                if((image.getRGB(i,j) & 0xFF000000) != 0X00){
                    return  false;
                }
            }
        }
        return true;
    }

    public List<Sprite> getSprites() {
        return sprites;
    }
}
