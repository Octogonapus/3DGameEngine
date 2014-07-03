3DGameEngine
============

3DGameEngine is a Java based engine for 3D games.

Rendering
---------
Everything is done with multi-pass rendering.

The engine currently has support for,

- Meshes
    - Vertex + Index based mesh creation
    - Mesh loading from an OBJ file
- Materials
    - Multiple textures
    - Uniform floats
- Lighting
    - GLSL shaders
    - Phong or Blinn-Phong lighting model
    - Generic lights
        - Ambient lights
        - Directional lights
        - Point lights
        - Spot lights

Game Support
-----------
All rendering is done using a scene graph. The scene graph contains game objects. Each game object can have children game objects. Each game object can also have game components. An example scene graph is shown below:

- Scene Graph
    - Game Object
        - Game Object
        - Game Component
    - Game Object
        - Game Component
        - Game Component

>A game object will inherit its parent's translation, rotation, and scale. It will not inherit its parent's material.

Full JavaDocs can be found in the folder named "docs".

Libraries Used
-----------
* [LWJGL] - OpenGL bind for Java 

[LWJGL]:http://lwjgl.org/