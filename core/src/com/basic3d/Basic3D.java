package com.basic3d;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.loaders.ModelLoader;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g3d.*;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.loader.ObjLoader;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;

public class Basic3D implements ApplicationListener {
	public PerspectiveCamera cam;
	public ModelBatch modelBatch;
	public Model cubeModel;
	public Model sphereModel;
	public ModelInstance cubeInstance, sphereInstance;
	public Environment environment;
	public CameraInputController camController;
	public Texture sraczTexture, saulTexture;

	@Override
	public void create () {
		cam = new PerspectiveCamera(90, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		cam.position.set(20f, 20f, 20f);
		cam.lookAt(0, 0, 0);
		cam.near = 1f;
		cam.far = 300f;
		cam.update();

		sraczTexture = new Texture(Gdx.files.internal("sracz.png"));
		saulTexture = new Texture(Gdx.files.internal("saul.jpg"));

		environment = new Environment();
		environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
		environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, 1f, -0.8f, 0.2f));

		camController = new CameraInputController(cam);
		Gdx.input.setInputProcessor(camController);

		modelBatch = new ModelBatch();
		ModelBuilder modelBuilder = new ModelBuilder();

		cubeModel = modelBuilder.createBox(10f, 10f, 10f,
				new Material(TextureAttribute.createDiffuse(sraczTexture)),
				VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal | VertexAttributes.Usage.TextureCoordinates);
		cubeInstance = new ModelInstance(cubeModel);

		sphereModel = modelBuilder.createSphere(10f, 10f, 10f, 30, 30,
				new Material(TextureAttribute.createDiffuse(saulTexture)),
				VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal | VertexAttributes.Usage.TextureCoordinates);
		sphereInstance = new ModelInstance(sphereModel);
		sphereInstance.transform.translate(10f, 0, -10f);
	}

	@Override
	public void render () {
		Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

		modelBatch.begin(cam);
		modelBatch.render(cubeInstance,environment);
		modelBatch.render(sphereInstance,environment);
		modelBatch.end();

		camController.update();
	}

	@Override
	public void dispose () {
		modelBatch.dispose();
		cubeModel.dispose();
		sphereModel.dispose();
	}

	@Override
	public void resume () {
	}

	@Override
	public void resize (int width, int height) {
	}

	@Override
	public void pause () {
	}
}
