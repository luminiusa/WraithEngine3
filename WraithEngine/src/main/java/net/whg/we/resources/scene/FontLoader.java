package net.whg.we.resources.scene;

import java.io.BufferedReader;
import java.io.FileReader;
import org.joml.Vector2f;
import org.joml.Vector4f;
import net.whg.we.resources.FileLoader;
import net.whg.we.resources.Resource;
import net.whg.we.resources.ResourceDatabase;
import net.whg.we.resources.ResourceFile;
import net.whg.we.resources.ResourceLoader;
import net.whg.we.ui.font.Font;
import net.whg.we.ui.font.Glyph;
import net.whg.we.utils.logging.Log;

public class FontLoader implements FileLoader
{
	private static final String[] FILE_TYPES =
	{
			"fnt"
	};

	@Override
	public String[] getTargetFileTypes()
	{
		return FILE_TYPES;
	}

	@Override
	public Resource loadFile(ResourceLoader resourceLoader, ResourceDatabase database,
			ResourceFile resourceFile)
	{
		try (BufferedReader in = new BufferedReader(new FileReader(resourceFile.getFile())))
		{
			String fontName = null;
			float imageWidth = 0f;
			float imageHeight = 0f;
			Glyph[] glyphs = null;
			float lineHeight = 0f;

			int glyphIndex = 0;
			String line;
			while ((line = in.readLine()) != null)
			{
				if (line.startsWith("info "))
				{
					String[] parts = line.split("\\s+(?=([^\\\"]*\\\"[^\\\"]*\\\")*[^\\\"]*$)");
					for (int i = 0; i < parts.length; i++)
					{
						if (parts[i].startsWith("face="))
							fontName = parts[i].substring(6, parts[i].length() - 1);

						if (parts[i].startsWith("size="))
							lineHeight = Float.parseFloat(parts[i].substring(5));
					}
				}
				else if (line.startsWith("common "))
				{
					String[] parts = line.split("\\s+(?=([^\\\"]*\\\"[^\\\"]*\\\")*[^\\\"]*$)");
					for (int i = 0; i < parts.length; i++)
					{
						if (parts[i].startsWith("scaleW="))
							imageWidth = Float.parseFloat(parts[i].substring(7));

						if (parts[i].startsWith("scaleH="))
							imageHeight = Float.parseFloat(parts[i].substring(7));
					}
				}
				else if (line.startsWith("chars "))
				{
					String[] parts = line.split("\\s+(?=([^\\\"]*\\\"[^\\\"]*\\\")*[^\\\"]*$)");
					for (int i = 0; i < parts.length; i++)
					{
						if (parts[i].startsWith("count="))
							glyphs = new Glyph[Integer.parseInt(parts[i].substring(6))];
					}
				}
				else if (line.startsWith("char "))
				{
					char c = ' ';
					Vector4f pos = new Vector4f();
					Vector2f size = new Vector2f();
					Vector2f offset = new Vector2f();
					float width = 0f;

					String[] parts = line.split("\\s+(?=([^\\\"]*\\\"[^\\\"]*\\\")*[^\\\"]*$)");
					for (int i = 0; i < parts.length; i++)
					{
						if (parts[i].startsWith("id="))
							c = (char) Integer.parseInt(parts[i].substring(3));

						if (parts[i].startsWith("x="))
							pos.x = Integer.parseInt(parts[i].substring(2)) / imageWidth;

						if (parts[i].startsWith("y="))
							pos.y = Integer.parseInt(parts[i].substring(2)) / imageHeight;

						if (parts[i].startsWith("width="))
							pos.z = Integer.parseInt(parts[i].substring(6)) / imageWidth;

						if (parts[i].startsWith("height="))
							pos.w = Integer.parseInt(parts[i].substring(7)) / imageHeight;

						if (parts[i].startsWith("xoffset="))
							offset.x = Integer.parseInt(parts[i].substring(8));

						if (parts[i].startsWith("yoffset="))
							offset.y = Integer.parseInt(parts[i].substring(8));

						if (parts[i].startsWith("xadvance="))
							width = Integer.parseInt(parts[i].substring(9)) / lineHeight;
					}

					size.x = pos.z * imageWidth / lineHeight;
					size.y = pos.w * imageHeight / lineHeight;
					offset.x /= lineHeight;
					offset.y = 1f - (offset.y / lineHeight + size.y);

					glyphs[glyphIndex++] = new Glyph(c, pos, size, offset, width);
				}
			}

			return new FontResource(resourceFile, fontName, new Font(glyphs));
		}
		catch (Exception exception)
		{
			Log.errorf("Failed to load font %s!", exception, resourceFile);
			return null;
		}
	}

	@Override
	public int getPriority()
	{
		return 0;
	}
}
