package net.whg.we.main;

import java.io.File;
import org.lwjgl.Version;
import net.whg.we.resources.FileDatabase;
import net.whg.we.resources.ResourceDatabase;
import net.whg.we.resources.ResourceLoader;
import net.whg.we.resources.ResourceManager;
import net.whg.we.resources.SimpleFileDatabase;
import net.whg.we.scene.GameLoop;
import net.whg.we.scene.WindowedGameLoop;
import net.whg.we.utils.Log;

/**
 * The program entry class. This class is used for the purpose of initializing
 * the plugin loader, all core plugins, and hunting down and launching local
 * plugins.
 *
 * @author TheDudeFromCI
 */
public class WraithEngine
{
	/**
	 * The current version of WraithEngine being run.
	 */
	public static final String VERSION = "v0.1.0a";

	/**
	 * The program entry method.
	 *
	 * @param args
	 *            - Arguments from command line. Used for determining program
	 *            functions. Currently, there are no extra runtime flags that can be
	 *            used at this time.
	 */
	public static void main(String[] args)
	{
		// Set default log parameters
		Log.setLogLevel(Log.TRACE);

		// Log some automatic system info
		Log.trace("Starting WraithEngine.");
		Log.debugf("WraithEngine Version: %s", VERSION);
		Log.debugf("Operating System: %s", System.getProperty("os.name"));
		Log.debugf("Operating System Arch: %s", System.getProperty("os.arch"));
		Log.debugf("Java Version: %s", System.getProperty("java.version"));
		Log.debugf("Java Vendor: %s", System.getProperty("java.vendor"));
		Log.debugf("System User: %s", System.getProperty("user.name"));
		Log.debugf("Working Directory: %s", System.getProperty("user.dir"));
		Log.debugf("LWJGL Version: %s", Version.getVersion());

		ResourceManager resourceManager = buildResourceManager();
		GameLoop gameLoop = new WindowedGameLoop(resourceManager.getResourceLoader());
		new GameState(resourceManager, gameLoop).run();
	}

	private static ResourceManager buildResourceManager()
	{
		File baseFolder = new File(System.getProperty("user.dir"));
		FileDatabase fileDatabase = new SimpleFileDatabase(baseFolder);
		ResourceDatabase resourceDatabase = new ResourceDatabase();
		ResourceLoader resourceLoader = new ResourceLoader();
		return new ResourceManager(resourceDatabase, resourceLoader, fileDatabase);
	}
}
