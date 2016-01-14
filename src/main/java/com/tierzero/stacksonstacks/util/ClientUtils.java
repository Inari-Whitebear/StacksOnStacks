package com.tierzero.stacksonstacks.util;

import java.awt.Color;
import java.io.IOException;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import com.tierzero.stacksonstacks.pile.PileItem;
import com.tierzero.stacksonstacks.pile.PileItemRegistry;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.resources.IResource;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ClientUtils {
	private static WorldRenderer worldRenderer = Tessellator.getInstance().getWorldRenderer();

	public static void drawQuad(double Umin, double Vmin, double Umax, double Vmax, double scale) {
		worldRenderer.addVertexWithUV(0.0D, 0.0D, 1.0D * scale, (double) Umax, (double) Vmin);
		worldRenderer.addVertexWithUV(1.0D * scale, 0.0D, 1.0D * scale, (double) Umin, (double) Vmin);
		worldRenderer.addVertexWithUV(1.0D * scale, 0.0D, 0.0D, (double) Umin, (double) Vmax);
		worldRenderer.addVertexWithUV(0.0D, 0.0D, 0.0D, (double) Umax, (double) Vmax);
	}

	public static void drawItem(IIcon icon, double scale) {
		if(icon != null) {
			float Umin = icon.getMinU();
			float Vmax = icon.getMaxV();
			float Vmin = icon.getMinV();
			float Umax = icon.getMaxU();
			drawQuad(Umin, Vmin, Umax, Vmax, scale);
		}
	}

	public static void drawRectangularPrism(double x, double y, double z, double x1, double y1, double z1,
			double slantX, double slantZ, double Umin, double Vmin, double Umax, double Vmax, Color color) {

		worldRenderer.setColorOpaque(color.getRed(), color.getGreen(), color.getBlue());
		worldRenderer.addVertexWithUV(x1, y, z, Umin, Vmax);
		worldRenderer.addVertexWithUV(x1, y, z1, Umin, Vmin);
		worldRenderer.addVertexWithUV(x, y, z1, Umax, Vmin);
		worldRenderer.addVertexWithUV(x, y, z, Umax, Vmax);

		// Render side 1(up)

		worldRenderer.addVertexWithUV(x1 - slantX, y1, z1 - slantZ, Umax, Vmax);
		worldRenderer.addVertexWithUV(x1 - slantX, y1, z + slantZ, Umax, Vmin);
		worldRenderer.addVertexWithUV(x + slantX, y1, z + slantZ, Umin, Vmin);
		worldRenderer.addVertexWithUV(x + slantX, y1, z1 - slantZ, Umin, Vmax);

		// Render side 2 (north)

		worldRenderer.addVertexWithUV(x, y, z, Umin, Vmin);
		worldRenderer.addVertexWithUV(x + slantX, y1, z + slantZ, Umin, Vmax);
		worldRenderer.addVertexWithUV(x1 - slantX, y1, z + slantZ, Umax, Vmax);
		worldRenderer.addVertexWithUV(x1, y, z, Umax, Vmin);

		// Render side 3 (south)
		worldRenderer.addVertexWithUV(x1, y, z1, Umax, Vmin);
		worldRenderer.addVertexWithUV(x1 - slantX, y1, z1 - slantZ, Umax, Vmax);
		worldRenderer.addVertexWithUV(x + slantX, y1, z1 - slantZ, Umin, Vmax);
		worldRenderer.addVertexWithUV(x, y, z1, Umin, Vmin);

		// Render side 4 (west)

		worldRenderer.addVertexWithUV(x, y, z, Umin, Vmax);
		worldRenderer.addVertexWithUV(x, y, z1, Umax, Vmax);
		worldRenderer.addVertexWithUV(x + slantX, y1, z1 - slantZ, Umax, Vmin);
		worldRenderer.addVertexWithUV(x + slantX, y1, z + slantZ, Umin, Vmin);

		// Render side 5 (east)

		worldRenderer.addVertexWithUV(x1, y, z, Umax, Vmin);
		worldRenderer.addVertexWithUV(x1 - slantX, y1, z + slantZ, Umax, Vmax);
		worldRenderer.addVertexWithUV(x1 - slantX, y1, z1 - slantZ, Umin, Vmax);
		worldRenderer.addVertexWithUV(x1, y, z1, Umin, Vmin);
		// Render side 6 (Down)
	}

	public static void drawRectangularPrism(double width, double length, double height, double slantX, double slantZ,
			double Umin, double Vmin, double Umax, double Vmax, Color color) {
		drawRectangularPrism(0, 0, 0, width, height, length, slantX, slantZ, Umin, Vmin, Umax, Vmax, color);
	}

	public static void enableTexture2D() {
		enable(GL11.GL_TEXTURE_2D);
	}

	public static void disableTexture2D() {
		disable(GL11.GL_TEXTURE_2D);
	}

	public static void pushAttrib() {
		GL11.glPushAttrib(8256);
	}

	public static void popAttrib() {
		GL11.glPopAttrib();
	}

	public static void loadIdentity() {
		GL11.glLoadIdentity();
	}

	private static void disable(int cap) {
		GL11.glDisable(cap);
	}

	private static void enable(int cap) {
		GL11.glEnable(cap);
	}

	public static void disableAlpha() {
		disable(GL11.GL_ALPHA_TEST);
	}

	public static void enableAlpha() {
		enable(GL11.GL_ALPHA_TEST);
	}

	public static void alphaFunc(int func, float ref) {
		GL11.glAlphaFunc(func, ref);
	}

	public static void enableLighting() {
		enable(GL11.GL_LIGHTING);
	}

	public static void disableLighting() {
		disable(GL11.GL_LIGHTING);
	}

	public static void disableDepth() {
		disable(GL11.GL_DEPTH);
	}

	public static void enableDepth() {
		enable(GL11.GL_DEPTH);
	}

	public static void depthFunc(int func) {
		GL11.glDepthFunc(func);
	}

	public static void depthMask(boolean flag) {
		GL11.glDepthMask(flag);
	}

	public static void disableBlend() {
		disable(GL11.GL_BLEND);
	}

	public static void enableBlend() {
		enable(GL11.GL_BLEND);
	}

	public static void blendFunc(int sfactor, int dfactor) {
		GL11.glBlendFunc(sfactor, dfactor);
	}

	public static void enableCull() {
		enable(GL11.GL_CULL_FACE);
	}

	public static void disableCull() {
		disable(GL11.GL_CULL_FACE);
	}

	public static void enableRescaleNormal() {
		enable(GL12.GL_RESCALE_NORMAL);
	}

	public static void disableRescaleNormal() {
		disable(GL12.GL_RESCALE_NORMAL);
	}

	public static void matrixMode(int mode) {
		GL11.glMatrixMode(mode);
	}

	public static void pushMatrix() {
		GL11.glPushMatrix();
	}

	public static void popMatrix() {
		GL11.glPopMatrix();
	}

	public static void rotate(double angle, double x, double y, double z) {
		GL11.glRotated(angle, x, y, z);
	}

	public static void rotate(float angle, float x, float y, float z) {
		GL11.glRotatef(angle, x, y, z);
	}

	public static void scale(float x, float y, float z) {
		GL11.glScalef(x, y, z);
	}

	public static void scale(double x, double y, double z) {
		GL11.glScaled(x, y, z);
	}

	public static void translate(float x, float y, float z) {
		GL11.glTranslatef(x, y, z);
	}

	public static void translate(double x, double y, double z) {
		GL11.glTranslated(x, y, z);
	}

	public static void colorMask(boolean r, boolean g, boolean b, boolean a) {
		GL11.glColorMask(r, g, b, a);
	}

	public static void colour(float r, float g, float b, float a) {
		System.out.println(r + "," + g + "," + b);
		GL11.glColor4f(r, g, b, a);
	}

	public static void colour(float r, float g, float b) {
		// System.out.println(r + "," + g + "," + b);
		colour(r, g, b, 1.0F);
	}

	public static void colourOpaque(int colour) {
		colour(colour | 0xFF000000);
	}

	public static void colour(int colour) {
		float r = (colour >> 16 & 255) / 255F;
		float g = (colour >> 8 & 255) / 255F;
		float b = (colour & 255) / 255F;
		int a = colour >> 24 & 255;
		if (a <= 0)
			a = 255;

		colour(r, g, b, a / 255F);
	}

	public static void viewport(int x, int y, int x1, int height) {
		GL11.glViewport(x, y, x1, height);
	}

	public static void normal(float x, float y, float z) {
		GL11.glNormal3f(x, y, z);
	}

	public static long getCoordinateRandom(int x, int y, int z) {
		// MC 1.8 code...
		long l = (x * 3129871) ^ z * 116129781L ^ y;
		l = l * l * 42317861L + l * 11L;
		return l;
	}

	public static long getCoordinateRandom(double x, double y, double z) {

		return getCoordinateRandom((int) x, (int) y, (int) z);
	}
}
