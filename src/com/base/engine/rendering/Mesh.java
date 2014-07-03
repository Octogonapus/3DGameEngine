package com.base.engine.rendering;

import com.base.engine.core.Util;
import com.base.engine.core.Vector3f;
import com.base.engine.rendering.meshLoading.IndexedModel;
import com.base.engine.rendering.meshLoading.OBJModel;
import com.base.engine.rendering.resourceManagement.MeshResource;
import org.lwjgl.opengl.GL15;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;

/**
 * @author Octogonapus
 */

public class Mesh
{
    private static HashMap<String, WeakReference<MeshResource>> loadedModels = new HashMap<String, WeakReference<MeshResource>>(0);
    private MeshResource resource;
    private String fileName;

    public Mesh(String fileName)
    {
        this.fileName = fileName;
        WeakReference<MeshResource> ref = loadedModels.get(fileName);

        if (ref != null && ref.get() != null)
        {
            resource = ref.get();
            resource.addReference();
        }
        else
        {
            loadMesh(fileName);
            loadedModels.put(fileName, new WeakReference<MeshResource>(resource));
        }
    }

    public Mesh(Vertex[] vertices, int[] indices)
    {
        this(vertices, indices, false);
    }

    public Mesh(Vertex[] vertices, int[] indices, boolean calcNormals)
    {
        fileName = "";
        addVertices(vertices, indices, calcNormals);
    }

    /**
     * Load a mesh into memory based on vertices, indices, and (possibly) normals.
     *
     * @param vertices      The vertices of the mesh
     * @param indices       The indices of the mesh
     * @param calcNormals   Whether or not to calculate normals
     */
	private void addVertices(Vertex[] vertices, int[] indices, boolean calcNormals)
	{
        if (calcNormals)
        {
            calcNormals(vertices, indices);
        }

		resource = new MeshResource(indices.length);
		
		glBindBuffer(GL_ARRAY_BUFFER, resource.getVbo());
		GL15.glBufferData(GL_ARRAY_BUFFER, Util.createFlippedBuffer(vertices), GL_STATIC_DRAW);
		
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, resource.getIbo());
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, Util.createFlippedBuffer(indices), GL_STATIC_DRAW);
	}

    /**
     * Render the mesh.
     */
	public void draw()
	{
		glEnableVertexAttribArray(0);
		glEnableVertexAttribArray(1);
        glEnableVertexAttribArray(2);
		
		glBindBuffer(GL_ARRAY_BUFFER, resource.getVbo());
		glVertexAttribPointer(0, 3, GL_FLOAT, false, Vertex.SIZE * 4, 0);
		glVertexAttribPointer(1, 2, GL_FLOAT, false, Vertex.SIZE * 4, 12);
        glVertexAttribPointer(2, 3, GL_FLOAT, false, Vertex.SIZE * 4, 20);
		
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, resource.getIbo());
		glDrawElements(GL_TRIANGLES, resource.getSize(), GL_UNSIGNED_INT, 0);
		
		glDisableVertexAttribArray(0);
		glDisableVertexAttribArray(1);
        glDisableVertexAttribArray(2);
	}

    /**
     * Calculates the normals of the mesh.
     *
     * @param vertices  The vertices of the mesh
     * @param indices   The indices of the mesh
     */
    private void calcNormals(Vertex[] vertices, int[] indices)
    {
        for (int i = 0; i < indices.length; i += 3)
        {
            int i0 = indices[i];
            int i1 = indices[i + 1];
            int i2 = indices[i + 2];

            Vector3f v1 = vertices[i1].getPos().sub(vertices[i0].getPos());
            Vector3f v2 = vertices[i2].getPos().sub(vertices[i0].getPos());

            Vector3f normal = v1.cross(v2).normalized();

            vertices[i0].setNormal(vertices[i0].getNormal().add(normal));
            vertices[i1].setNormal(vertices[i1].getNormal().add(normal));
            vertices[i2].setNormal(vertices[i2].getNormal().add(normal));
        }

        for (Vertex vert : vertices)
        {
            vert.setNormal(vert.getNormal().normalized());
        }
    }

    /**
     * Load a mesh by file name.
     *
     * @param fileName  The name of the mesh
     * @return          The loaded mesh
     */
    private Mesh loadMesh(String fileName)
    {
        String[] splitArray = fileName.split("\\.");
        String ext = splitArray[splitArray.length - 1];

        if(!ext.equals("obj"))
        {
            System.err.println("Error: File format not supported for mesh data: " + ext);
            new Exception().printStackTrace();
            System.exit(1);
        }

        OBJModel test = new OBJModel("./res/models/" + fileName);
        IndexedModel model = test.toIndexedModel();
        model.calcNormals();

        ArrayList<Vertex> vertices = new ArrayList<Vertex>(0);

        for (int i = 0; i < model.getPositions().size(); i++)
        {
            vertices.add(new Vertex(model.getPositions().get(i),
                                    model.getTexCoords().get(i),
                                    model.getNormals().get(i)));
        }

        Vertex[] vertexData = new Vertex[vertices.size()];
        vertices.toArray(vertexData);

        Integer[] indexData = new Integer[model.getIndices().size()];
        model.getIndices().toArray(indexData);

        addVertices(vertexData, Util.toIntArray(indexData), false);

        return this;
    }

    @Override
    protected void finalize() throws Throwable
    {
        if (resource.removeReference() && !fileName.isEmpty())
        {
            loadedModels.remove(fileName);
        }

        super.finalize();
    }
}
