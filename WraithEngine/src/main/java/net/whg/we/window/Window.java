package net.whg.we.window;

interface Window
{
	void setQueuedWindow(QueuedWindow window);

	void setName(String name);

	void setResizable(boolean resizable);

	void setVSync(boolean vSync);

	void setSize(int width, int height);

	void buildWindow();

	void requestClose();

	void disposeWindow();

	boolean endFrame();

	void updateWindow();

	void linkToOpenGL();
}