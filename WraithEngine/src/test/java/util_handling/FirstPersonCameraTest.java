package util_handling;

import static org.junit.Assert.*;

import org.joml.Vector3f;
import org.junit.Before;
import org.junit.Test;

import net.whg.we.rendering.Camera;
import net.whg.we.utils.FirstPersonCamera;
import net.whg.we.utils.Input;
import net.whg.we.utils.MathUtils;
import net.whg.we.utils.Screen;
import net.whg.we.utils.Time;
import net.whg.we.window.KeyState;

public class FirstPersonCameraTest {
	
	@Test
	/**
	 * These tests test the setters and getters of the class FirstPersonCamera
	 */
	public void testInstantiation() {
		FirstPersonCamera fpc = new FirstPersonCamera(new Camera());
		assertTrue(fpc.getMouseSensitivity() == 3f);
		assertTrue(fpc.getMoveSpeed() == 7f);
		fpc.setMouseSensitivity(5f);
		fpc.setMoveSpeed(8f);
		assertTrue(fpc.getMouseSensitivity() == 5f);
		assertTrue(fpc.getMoveSpeed() == 8f);
		Vector3f v = new Vector3f();
		assertTrue(fpc.getBaseRotation().equals(v, 0));
		assertTrue(fpc.getExtraRotation().equals(v, 0));
	}
	
	@Test
	/**
	 * Check that the math methods send the expected value.
	 */
	public void testMathUtils() {
		Vector3f v = new Vector3f();
		assertTrue(MathUtils.isValid(v));
		assertTrue(MathUtils.clamp(5f, 4f, 6f) == 5f);
		assertTrue(MathUtils.clamp(4f, 5f, 6f) == 5f);
		assertTrue(MathUtils.clamp(6f, 4f, 5f) == 5f);
	}
	
	@Before
	public void setup() {
		Screen.setMouseLocked(true);
		Time.updateTime();
		try {	
			Thread.sleep(10);
		} catch(InterruptedException e) { }
		Time.updateTime();
	}
	
	@Test
	public void testUpdateCameraRotation() {
		float delta = 0.0001f;
		FirstPersonCamera fpc = new FirstPersonCamera(new Camera());
		
		Vector3f pre_base = fpc.getBaseRotation();
		Vector3f pre_extra = fpc.getExtraRotation();
		
		Input.setMousePosition(0, 0);
		Input.endFrame();
		Input.setMousePosition(0, 0);
		Input.endFrame();
		fpc.updateCameraRotation();
		
		Vector3f post_base = fpc.getBaseRotation();
		Vector3f post_extra = fpc.getExtraRotation();
		assertTrue(pre_base.equals(post_base, delta) && pre_extra.equals(post_extra, delta));
		
		Input.setMousePosition(10, 5);
		fpc.updateCameraRotation();
		
		post_base = fpc.getBaseRotation();
		post_extra = fpc.getExtraRotation();
		assertFalse(pre_base.equals(post_base, delta) && pre_extra.equals(post_extra, delta));
	}
	
	@Test
	public void testUpdateCameraPosition() {
		float delta = 0.0001f;		
		FirstPersonCamera fpc = new FirstPersonCamera(new Camera());
		Vector3f pre = fpc.getLocation().getPosition();
		
		fpc.updateCameraPosition();
		
		Vector3f post = fpc.getLocation().getPosition();
		assertTrue(pre.equals(post, delta));
		
		Input.setKeyPressed(Input.getKeyId("w"), KeyState.PRESSED, 0);
		fpc.updateCameraPosition();
		
		post = fpc.getLocation().getPosition();
		
		assertFalse(pre.equals(post, delta));
	}
	

}
