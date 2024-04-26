package org.gear.framework.core.service.rendering.shaderpipeline;

import org.gear.framework.core.log.Logger;
import org.gear.framework.core.log.annotation.GenerateCriticalFile;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import static org.lwjgl.opengl.GL45.*;

@GenerateCriticalFile
public class ShaderPipelineBuilder extends Logger {

//    private int loadShader(String file) {
//        StringBuilder builder = new StringBuilder();
//
//        try {
//            BufferedReader reader = new BufferedReader(new FileReader(file));
//            String line;
//
//            while((line = reader.readLine()) != null) {
//                if("@Vertex".startsWith(line.toLowerCase().replaceAll(" ", ""))) {
//                    return parseShader(reader, GL_VERTEX_SHADER);
//                } else if("@Fragment".startsWith(line.toLowerCase().replaceAll(" ", ""))) {
//                    return parseShader(reader, GL_FRAGMENT_SHADER);
//                }
//            }
//
//        } catch (IOException e) {
//            error("Error to parse shader file!", new RuntimeException(e));
//        }
//    }
//
//    private int parseShader(BufferedReader reader, int shaderType) throws IOException {
//
//        String line;
//        StringBuilder builder = new StringBuilder();
//
//        while((line = reader.readLine()) != null) {
//            if(line.toLowerCase().startsWith("@EndVertex")) {
//                return createShader(builder.toString(), shaderType);
//            }
//        }
//
//        return -1;
//    }
//
//    private int createShader(String source, int shaderType) {
//        int shaderID = glCreateShader(shaderType);
//        glShaderSource(shaderID, source);
//        glCompileShader(shaderID);
//
//        if(glGetShaderi(shaderID, GL_COMPILE_STATUS) == GL_FALSE) {
//            critical("Could not compile shader, error: " + glGetShaderInfoLog(shaderID), new RuntimeException());
//        }
//
//        return shaderID;
//    }

    public int loadShader(String file, int type) {
        StringBuilder shaderSource = new StringBuilder();
        String typeToString = "";

        switch(type) {
            case GL_VERTEX_SHADER -> typeToString = "Vertex Shader";
            case GL_FRAGMENT_SHADER -> typeToString = "Fragment Shader";
        }

        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;

            while((line = reader.readLine()) != null) {
                shaderSource.append(line).append("\n");
            }

            reader.close();

        } catch (IOException e) {
            critical("Could not load shader", new RuntimeException(e));
        }

        int shaderID = glCreateShader(type);
        glShaderSource(shaderID, shaderSource.toString());
        glCompileShader(shaderID);

        if(glGetShaderi(shaderID, GL_COMPILE_STATUS) == GL_FALSE) {
            critical("Could not compile " + typeToString + " : " + glGetShaderInfoLog(shaderID), new RuntimeException());
        }

        return shaderID;
    }
}
