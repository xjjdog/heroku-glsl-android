package info.u250.c2d.tests.android;


import info.u250.c2d.engine.Engine;
import info.u250.c2d.engine.EngineDrive;
import info.u250.c2d.engine.resources.AliasResourceManager;

import java.io.File;

public class TheEngineInstance extends Engine {
	String fileName = null;
	File cacheDir;
	AndroidInterface androidInterface;
	public TheEngineInstance(AndroidInterface androidInterface,File cacheDir ,String name){
		fileName = name;
		this.cacheDir = cacheDir;
		this.androidInterface = androidInterface;
	}
	@Override
	protected EngineDrive onSetupEngineDrive() {
		return new EngineDrive() {
			
			@Override
			public EngineOptions onSetupEngine() {
				EngineOptions opt = new EngineOptions(new String[]{"view-source.png"}, 960, 540);
				opt.useGL20 = true;
				return opt;
			}
			
			@Override
			public void onResourcesRegister(AliasResourceManager<String> reg) {
				reg.texture("BBB", "view-source.png");
			}
			
			@Override
			public void onLoadedResourcesCompleted() {
				Engine.setMainScene(new AScene(cacheDir,fileName));
			}
			
			@Override
			public void dispose() {
				
			}
		};
	}
	public AndroidInterface getAndroidInterface() {
		return androidInterface;
	}

}
