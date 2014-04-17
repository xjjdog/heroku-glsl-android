package info.u250.c2d.tests.android;


import info.u250.c2d.engine.Engine;
import info.u250.c2d.engine.EngineDrive;
import info.u250.c2d.engine.resources.AliasResourceManager;

import java.io.File;

public class TheEngineInstance extends Engine {
	String fileName = null;
	File cacheDir;
	public TheEngineInstance(File cacheDir ,String name){
		fileName = name;
		this.cacheDir = cacheDir;
	}
	@Override
	protected EngineDrive onSetupEngineDrive() {
		return new EngineDrive() {
			
			@Override
			public EngineOptions onSetupEngine() {
				EngineOptions opt = new EngineOptions(new String[]{}, 960, 540);
				opt.useGL20 = true;
				return opt;
			}
			
			@Override
			public void onResourcesRegister(AliasResourceManager<String> reg) {
				
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

}
