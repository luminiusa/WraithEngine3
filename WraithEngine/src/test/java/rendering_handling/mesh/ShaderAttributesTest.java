package rendering_handling.mesh;

import org.junit.Assert;
import org.junit.Test;
import net.whg.we.rendering.ShaderAttributes;

public class ShaderAttributesTest
{
	@Test
	public void defaultSize()
	{
		ShaderAttributes attrib = new ShaderAttributes();

		Assert.assertEquals(0, attrib.getCount());
		Assert.assertEquals(0, attrib.getVertexSize());
		Assert.assertEquals(0, attrib.getVertexByteSize());
	}

	@Test
	public void addAttrib()
	{
		ShaderAttributes attrib = new ShaderAttributes();

		attrib.addAttribute("pos", 3);
		attrib.addAttribute("normal", 3);
		attrib.addAttribute("uv", 2);

		Assert.assertEquals(3, attrib.getCount());
		Assert.assertEquals(8, attrib.getVertexSize());
		Assert.assertEquals(32, attrib.getVertexByteSize());
	}

	@Test
	public void addTooMany()
	{
		ShaderAttributes attrib = new ShaderAttributes(1);

		attrib.addAttribute("pos", 3);
		attrib.addAttribute("normal", 3);
		attrib.addAttribute("uv1", 2);
		attrib.addAttribute("uv2", 2);
		attrib.addAttribute("uv3", 2);
		attrib.addAttribute("uv4", 2);
		attrib.addAttribute("uv5", 2);
		attrib.addAttribute("uv6", 2);
		attrib.addAttribute("uv7", 2);
		attrib.addAttribute("uv8", 2);
	}

	@Test
	public void getSizeOf()
	{
		ShaderAttributes attrib = new ShaderAttributes();

		attrib.addAttribute("pos", 3);
		attrib.addAttribute("normal", 3);
		attrib.addAttribute("uv", 2);

		Assert.assertEquals(3, attrib.getSizeOf("pos"));
		Assert.assertEquals(3, attrib.getSizeOf("normal"));
		Assert.assertEquals(2, attrib.getSizeOf("uv"));
	}

	@Test
	public void getSizeOf_wrongAttrib()
	{
		ShaderAttributes attrib = new ShaderAttributes();

		Assert.assertEquals(-1, attrib.getSizeOf("pos"));
	}

	@Test(expected = IllegalArgumentException.class)
	public void addLargeAttrib()
	{
		ShaderAttributes attrib = new ShaderAttributes();
		attrib.addAttribute("pos", 5);
	}

	@Test(expected = IllegalArgumentException.class)
	public void addNegativeAttrib()
	{
		ShaderAttributes attrib = new ShaderAttributes();
		attrib.addAttribute("pos", -1);
	}

	@Test(expected = ArrayIndexOutOfBoundsException.class)
	public void getNegativeAttribName()
	{
		ShaderAttributes attrib = new ShaderAttributes();
		attrib.getAttributeName(-1);
	}

	@Test(expected = ArrayIndexOutOfBoundsException.class)
	public void getAttribName_OverCount_RoomInBuffer()
	{
		ShaderAttributes attrib = new ShaderAttributes(16);
		attrib.getAttributeName(1);
	}

	@Test(expected = ArrayIndexOutOfBoundsException.class)
	public void getNegativeAttribSize()
	{
		ShaderAttributes attrib = new ShaderAttributes();
		attrib.getAttributeSize(-1);
	}

	@Test
	public void removeAttribute()
	{
		ShaderAttributes attrib = new ShaderAttributes(4);

		attrib.addAttribute("pos", 3);
		attrib.addAttribute("normal", 3);
		attrib.addAttribute("uv1", 2);
		attrib.addAttribute("uv2", 2);

		attrib.removeAttribute(1);

		Assert.assertEquals(3, attrib.getCount());
		Assert.assertEquals(0, attrib.indexOf("pos"));
		Assert.assertEquals(1, attrib.indexOf("uv1"));
		Assert.assertEquals(2, attrib.indexOf("uv2"));
		Assert.assertEquals(-1, attrib.indexOf("normal"));
	}

	@Test
	public void getPositionInVertex()
	{
		ShaderAttributes attrib = new ShaderAttributes(4);

		attrib.addAttribute("pos", 3);
		attrib.addAttribute("normal", 3);
		attrib.addAttribute("uv1", 2);
		attrib.addAttribute("uv2", 2);

		Assert.assertEquals(0, attrib.getPositionInVertex(0));
		Assert.assertEquals(3, attrib.getPositionInVertex(1));
		Assert.assertEquals(6, attrib.getPositionInVertex(2));
		Assert.assertEquals(8, attrib.getPositionInVertex(3));
	}
}
