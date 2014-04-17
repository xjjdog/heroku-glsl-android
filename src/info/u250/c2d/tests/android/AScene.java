package info.u250.c2d.tests.android;

import info.u250.c2d.engine.Engine;
import info.u250.c2d.engine.Scene;

import java.io.File;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Net.HttpMethods;
import com.badlogic.gdx.Net.HttpRequest;
import com.badlogic.gdx.Net.HttpResponse;
import com.badlogic.gdx.Net.HttpResponseListener;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector2;
import com.nostra13.universalimageloader.utils.StorageUtils;

public class AScene implements Scene {
	ShaderProgram shader ;
	FrameBuffer frameBuffer;
	Mesh mesh ;
	boolean success = false;
	String msg = "";
	boolean hasTime = true;
	boolean hasResolution = true;
	
	
	boolean loadOk = false;
	
	public void setup(String fragmentShader){
		String vertexShader = "attribute vec3 position; void main() { gl_Position = vec4( position, 1.0 ); }";

		ShaderProgram shader = new ShaderProgram(vertexShader, fragmentShader);
		if (shader.isCompiled() == false) {
			success = false;
			msg = shader.getLog();
		}else{
			hasTime = fragmentShader.contains("time");
			hasResolution = fragmentShader.contains("resolution");
			success = true;
		}
		AScene.this.shader = shader;
		
		
		AScene.this.mesh = new Mesh(true,4,6,new VertexAttribute(VertexAttributes.Usage.Position, 2, "position"));
		AScene.this.mesh.setVertices(new float[] { -1, -1,  -1 , 1,  1, -1 , 1 ,1});
		AScene.this.mesh.setIndices(new short[]{0, 1, 2, 2, 1, 3});

		
		AScene.this.frameBuffer = new FrameBuffer(Format.RGB565, (int)(Engine.getWidth()/4), (int)(Engine.getHeight()/4), false);
		AScene.this.resolution.set(frameBuffer.getWidth(), frameBuffer.getHeight());
		
		
		AScene.this.loadOk = true;
	}
	String cacheDir = "";
	public AScene(File cacheDir,String fileName){
		this.cacheDir = cacheDir.getAbsolutePath();
//		shader = createDefaultShader(fileName);
		final String localAimFile=cacheDir.getAbsolutePath()+"/"+fileName;
		final File localAimFileFile = new File(localAimFile);
		if(localAimFileFile.exists()){
			this.setup(Gdx.files.absolute(localAimFile).readString());
		}else{
			String httpAimFile = "https://raw.githubusercontent.com/yadongx/glsl/master/glsl/"+fileName;
			HttpRequest request = new HttpRequest(HttpMethods.GET);
			request.setUrl(httpAimFile);
			Gdx.net.sendHttpRequest(request, new HttpResponseListener() {
				@Override
				public void handleHttpResponse (HttpResponse httpResponse) {
					final byte[] bytes = httpResponse.getResult();
					Gdx.app.postRunnable(new Runnable() {
						@Override
						public void run () {
							Gdx.files.absolute(localAimFile).writeBytes(bytes, false);
							AScene.this.setup(new String(bytes));
						}
					});		
				}
				
				@Override
				public void failed (Throwable t) {
					Gdx.app.log("EmptyDownloadTest", "Failed", t);
				}
				
				@Override
				public void cancelled () {
					Gdx.app.log("EmptyDownloadTest", "Cancelled");
				}
			});

		}
	}
	float accum = 0;
	@Override
	public void update(float delta) {
		accum+=delta;
	}
	Vector2 resolution= new Vector2(Engine.getWidth(),Engine.getHeight());
	@Override
	public void render(float delta) {
		if(loadOk){
			GL20 gl = Gdx.gl20;
			gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			if(!success){
				Engine.getDefaultFont().setColor(Color.WHITE);
				Engine.debugInfo("Fail load the glsl file !!!\n"+msg);
				return;
			}
			try{
				frameBuffer.begin();
				Gdx.gl20.glViewport(0, 0, frameBuffer.getWidth(), frameBuffer.getHeight());
				Gdx.gl20.glClearColor(0f, 1f, 0f, 1);
				Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
				shader.begin();
				if(hasResolution)shader.setUniformf("resolution", resolution );
				if(hasTime)shader.setUniformf("time", accum);
				mesh.render(shader, GL20.GL_TRIANGLES);
				shader.end();
				frameBuffer.end();
			}catch(Exception ex){
				frameBuffer.end();
				Gdx.graphics.getGL20().glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
				Engine.getDefaultFont().setColor(Color.WHITE);
				Engine.debugInfo("Fail:"+ex.getMessage());
				return;
			}
			
			Gdx.graphics.getGL20().glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
			Engine.getSpriteBatch().begin();
			Engine.getSpriteBatch().draw(frameBuffer.getColorBufferTexture(), 0, 0,Engine.getWidth(),Engine.getHeight());
			Engine.getSpriteBatch().end();
			
			Engine.debugInfo("you can find the glsl file in:\n"+this.cacheDir);
		}else{
			Gdx.graphics.getGL20().glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
			Engine.debugInfo("\n\n\tDownloadding........");
		}
	}

	@Override
	public void show() {
		
	}

	@Override
	public void hide() {
		
	}

	@Override
	public InputProcessor getInputProcessor() {
		return null;
	}
//	public ShaderProgram createDefaultShader (String fileName) {
//		String vertexShader = "attribute vec3 position; void main() { gl_Position = vec4( position, 1.0 ); }";
//		String fragmentShader = Gdx.files.absolute(fileName).readString();
//
//		ShaderProgram shader = new ShaderProgram(vertexShader, fragmentShader);
//		if (shader.isCompiled() == false) {
//			success = false;
//			msg = shader.getLog();
//		}else{
//			hasTime = fragmentShader.contains("time");
//			hasResolution = fragmentShader.contains("resolution");
//			success = true;
//		}
//		return shader;
//	}

}
