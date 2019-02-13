package net.whg.we.rendering;

public interface VMesh
{
	public void render();

	public void dispose();

	public void rebuild(VertexData vertexData);

	public boolean isDisposed();
}
