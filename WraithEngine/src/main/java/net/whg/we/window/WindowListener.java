package net.whg.we.window;

/**
 * Listener interface that receives window event.
 */
public interface WindowListener
{
    /**
     * What to do when the window receives a resize event.
     * @param width The new width of the window
     * @param height The new width of the window
     */
	public void onWindowResized(int width, int height);
	
	 /**
     * What to do when key event happens
     * @param key The key on which the event is happening
     * @param KeyState What is happening with the key (pressed, released, repeatedly pressed)
     * @param mods A modifier for the key
     */
	public void onKey(int key, KeyState action, int mods);
	
	 /**
     * What to do when a key is pressed
     * @param key The key pressed
     * @param mods A modifier for the key
     */
	public void onType(int key, int mods);

	 /**
     * What to do when the mouse is moved
     * @param mouseX the new position of the mouse on the X axis
     * @param mouseY the new position of the mouse on the Y axis
     */
	public void onMouseMoved(float mouseX, float mouseY);
}
