package net.whg.we.window;

public class WindowBuilder
{
	public static final int WINDOW_ENGINE_GLFW = 0;

	private QueuedWindow _window;
	private boolean _built;

	public WindowBuilder(int engine)
	{
		Window window;

		switch (engine)
		{
			case WINDOW_ENGINE_GLFW:
				window = new GLFWWindow();
				break;
			default:
				throw new IllegalArgumentException("Unknown window engine type!");
		}

		_window = new QueuedWindow(window);
		Thread windowThread = new Thread(() ->
		{
			_window.waitForEvents();
		});
		windowThread.setName("Window");
		windowThread.setDaemon(false);
		windowThread.start();
	}

	public WindowBuilder setListener(WindowListener listener)
	{
		_window.setWindowListener(listener);
		return this;
	}

	public WindowBuilder setName(String name)
	{
		if (_built)
			throw new IllegalStateException("Window already built!");

		_window.setName(name);
		return this;
	}

	public WindowBuilder setResizable(boolean resizable)
	{
		if (_built)
			throw new IllegalStateException("Window already built!");

		_window.setResizable(resizable);
		return this;
	}

	public WindowBuilder setVSync(boolean vSync)
	{
		if (_built)
			throw new IllegalStateException("Window already built!");

		_window.setVSync(vSync);
		return this;
	}

	public WindowBuilder setSize(int width, int height)
	{
		if (_built)
			throw new IllegalStateException("Window already built!");

		_window.setSize(width, height);
		return this;
	}

	public QueuedWindow build()
	{
		if (_built)
			throw new IllegalStateException("Window already built!");

		_built = true;
		_window.buildWindow();
		_window.linkToOpenGL();
		return _window;
	}
}
