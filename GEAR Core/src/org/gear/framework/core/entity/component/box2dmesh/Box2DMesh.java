package org.gear.framework.core.entity.component.box2dmesh;

import org.gear.framework.core.engine.Engine;
import org.gear.framework.core.entity.GameObject;
import org.gear.framework.core.entity.component.Component;
import org.gear.framework.core.math.Vector2;
import org.gear.framework.core.service.rendering.GraphicsAPI;
import org.gear.framework.core.service.rendering.element.model.Mesh;

import java.util.ArrayList;
import java.util.List;

public class Box2DMesh extends Component {

    private Mesh mesh;
    public int width = 0;
    public int height = 0;
    public Vector2 origin = Vector2.zero();

    public Box2DMesh(GameObject parent) {
        super(parent);
        setProps(new Box2DSize(0, 0, Vector2.zero()));
    }

    public void setOrigin(Vector2 origin) {
        this.origin = origin;
    }

    public void setProps(Box2DSize props) {
        this.height = props.height;
        this.width = props.width;
        this.origin = props.origin == null ? (this.origin == null ? Vector2.zero() : this.origin) : props.origin;
        this.mesh = generateMesh();
    }

    private Mesh generateMesh() {
        List<Vertex> vertices = new ArrayList<>();
        float top, bottom, left, right;

        float halfWidth = width / 2f;
        float halfHeight = height / 2f;

        bottom = origin.y - halfHeight;
        top = origin.y + halfHeight;
        left = -origin.x - halfWidth;
        right = -origin.x + halfWidth;

        vertices.add(new Vertex(left, bottom));
        vertices.add(new Vertex(left, top));
        vertices.add(new Vertex(right, top));
        vertices.add(new Vertex(right, bottom));

        float[] positions = new float[vertices.size() * 3];

        for (int i = 0; i < vertices.size(); i++) {
            positions[i * 3] = vertices.get(i).x;
            positions[i * 3 + 1] = vertices.get(i).y;
            positions[i * 3 + 2] = 0f;
        }

        vertices.clear();

        int[] indices = {
                2, 1, 0,
                0, 3, 2
        };

        float[] textureCoordinates = {
                0.0f, 1.0f,
                0.0f, 0.0f,
                1.0f, 0.0f,
                1.0f, 1.0f
        };

        float[] normals = {0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f};

        return Engine.fromService(GraphicsAPI.class).loadModel(positions, textureCoordinates, normals, indices);
    }

    public Mesh getMesh() {
        return mesh;
    }
}
