/* (C) TAMA Studios 2026 */
package com.code.tama.triggerapi.gui;

import java.util.*;

import javax.annotation.Nullable;

import com.code.tama.triggerapi.helpers.PlanetHelper;
import com.code.tama.triggerapi.universal.UniversalCommon;
import com.code.tama.tts.server.data.json.loaders.PlanetLoader;
import com.code.tama.tts.server.misc.containers.SpaceTimeCoordinate;
import com.mojang.blaze3d.systems.RenderSystem;

import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractSliderButton;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4f;

import static com.code.tama.tts.client.renderers.worlds.helper.CustomLevelRenderer.drawPlanet;

public class AstralMapScreen extends Screen {

	private static final int COLOR_BG = 0xFF05050C;
	private static final int COLOR_GRID = 0x2033335A;
	private static final int COLOR_STAR = 0xFFFFFFFF;
	private static final int COLOR_ORBIT = 0x40559FFF;
	private static final int COLOR_ORBIT_HOVER = 0xA0AACCFF;
	private static final int COLOR_PLANET = 0xFF6FB7FF;
	private static final int COLOR_PLANET_ROOT = 0xFFFFD35C;
	private static final int COLOR_PLANET_HOVER_RING = 0x80FFFFFF;
	private static final int COLOR_TARDIS = 0xFF57F1C4;
	private static final int COLOR_DESTINATION = 0xFFFF5A5A;
	private static final int COLOR_TRAJECTORY = 0xFFFFFFFF;
	private static final int COLOR_TEXT = 0xFFE0E0E0;
	private static final int COLOR_TEXT_DIM = 0xFFA0A0A0;
	private static final int COLOR_PANEL = 0xC0101018;

	@Nullable
	private final ResourceKey<Level> currentDimension;
	@Nullable
	private final SpaceTimeCoordinate currentSpacePosition;
	private final SpaceTimeCoordinate destination;


	private static final Map<String, VertexBuffer> planetVBOs = new HashMap<>();
	private double scale = 0.05;
	private double panX = 0;
	private double panZ = 0;
	private long timeOffsetTicks = 0;

	private boolean dragging = false;
	private double lastMouseX, lastMouseY;

	private TimeSliderWidget timeSlider;

	private final List<StarDot> stars = new ArrayList<>();

	private final List<PlanetScreenPoint> lastPlanetPoints = new ArrayList<>();
	@Nullable
	private PlanetLoader.Planet hoveredPlanet;

	private static final int MAP_MARGIN_TOP = 28;
	private static final int MAP_MARGIN_BOTTOM = 54;
	private static final int MAP_MARGIN_SIDE = 8;

	public AstralMapScreen(@Nullable ResourceKey<Level> currentDimension,
	                       @Nullable SpaceTimeCoordinate currentSpacePosition, SpaceTimeCoordinate destination) {
		super(Component.literal("Astral Map"));
		this.currentDimension = currentDimension;
		this.currentSpacePosition = currentSpacePosition;
		this.destination = destination;
	}

	@Override
	protected void init() {
		super.init();

		stars.clear();
		Random rand = new Random(12345L);
		for (int i = 0; i < 220; i++) {
			stars.add(new StarDot(rand.nextFloat(), rand.nextFloat(), rand.nextInt(2) + 1));
		}

		autoFitView();

		int sliderWidth = Math.min(360, this.width - 140);
		int sliderX = (this.width - sliderWidth) / 2;
		int bottomY = this.height - 34;

		timeSlider = new TimeSliderWidget(sliderX, bottomY, sliderWidth, 20);
		addRenderableWidget(timeSlider);

		addRenderableWidget(Button.builder(Component.literal("Now"), b -> {
			timeSlider.resetToNow();
		}).bounds(sliderX + sliderWidth + 8, bottomY, 50, 20).build());

		addRenderableWidget(Button.builder(Component.literal("Recenter"), b -> autoFitView())
				.bounds(sliderX - 66, bottomY, 58, 20).build());
	}

	@Override
	public boolean isPauseScreen() {
		return false;
	}

	private void autoFitView() {
		long time = currentGameTime();

		double minX = Double.MAX_VALUE, maxX = -Double.MAX_VALUE;
		double minZ = Double.MAX_VALUE, maxZ = -Double.MAX_VALUE;
		boolean any = false;

		for (PlanetLoader.Planet p : PlanetLoader.list()) {
			Vec3 pos = PlanetHelper.getPosition(p, time);
			minX = Math.min(minX, pos.x);
			maxX = Math.max(maxX, pos.x);
			minZ = Math.min(minZ, pos.z);
			maxZ = Math.max(maxZ, pos.z);
			any = true;
		}

		Vec3 tardisPos = resolveTardisPosition(time);
		Vec3 destPos = new Vec3(destination.GetX(), destination.GetY(), destination.GetZ());
		for (Vec3 v : new Vec3[] { tardisPos, destPos }) {
			minX = Math.min(minX, v.x);
			maxX = Math.max(maxX, v.x);
			minZ = Math.min(minZ, v.z);
			maxZ = Math.max(maxZ, v.z);
			any = true;
		}

		if (!any) {
			panX = 0;
			panZ = 0;
			scale = 0.05;
			return;
		}

		double spanX = Math.max(maxX - minX, 16);
		double spanZ = Math.max(maxZ - minZ, 16);

		int mapW = Math.max(1, this.width - MAP_MARGIN_SIDE * 2);
		int mapH = Math.max(1, this.height - MAP_MARGIN_TOP - MAP_MARGIN_BOTTOM);

		double fitScale = Math.min(mapW / spanX, mapH / spanZ) * 0.85;
		scale = Math.max(0.0005, Math.min(fitScale, 4.0));

		panX = (minX + maxX) / 2.0;
		panZ = (minZ + maxZ) / 2.0;
	}

	private long currentGameTime() {
		long liveTime = 0;
		if (this.minecraft != null && this.minecraft.level != null) {
			liveTime = this.minecraft.level.getGameTime();
		}
		return liveTime + timeOffsetTicks;
	}

	private Vec3 resolveTardisPosition(long time) {
		if (currentSpacePosition != null) {
			return new Vec3(currentSpacePosition.GetX(), currentSpacePosition.GetY(), currentSpacePosition.GetZ());
		}

		if (currentDimension != null) {
			PlanetLoader.Planet planet = PlanetLoader.strList.get(currentDimension.location().toString());
			if (planet != null) {
				return PlanetHelper.getPosition(planet, time);
			}
		}

		return Vec3.ZERO;
	}

	private boolean tardisPositionIsKnown() {
		return currentSpacePosition != null || (currentDimension != null
				&& PlanetLoader.strList.containsKey(currentDimension.location().toString()));
	}

	@Override
	public void render(GuiGraphics gg, int mouseX, int mouseY, float partialTick) {
		gg.fill(0, 0, this.width, this.height, COLOR_BG);
		renderStarfield(gg);
		renderGrid(gg);

		long time = currentGameTime();

		lastPlanetPoints.clear();
		hoveredPlanet = null;

		for (PlanetLoader.Planet p : PlanetLoader.list()) {
			if (p.getOrbit() == null)
				continue;
			PlanetLoader.Planet parent = PlanetLoader.strList.get(p.getOrbit().parent());
			if (parent == null)
				continue;
			Vec3 parentPos = PlanetHelper.getPosition(parent, time);
			renderOrbitPath(gg, p.getOrbit(), parentPos);
		}

		PoseStack stack = gg.pose();
		stack.pushPose();
		for (PlanetLoader.Planet p : PlanetLoader.list()) {
			Vec3 worldPos = PlanetHelper.getPosition(p, time);
			int[] screen = worldToScreen(worldPos.x, worldPos.z);
			int radius = planetPixelRadius(p.getSize());

			lastPlanetPoints.add(new PlanetScreenPoint(p, screen[0], screen[1], radius));

			boolean isHovered = distSq(mouseX, mouseY, screen[0], screen[1]) <= (radius + 3) * (radius + 3);
			if (isHovered) {
				hoveredPlanet = p;
				fillCircle(gg, screen[0], screen[1], radius + 3, COLOR_PLANET_HOVER_RING);
			}
			stack.pushPose();
			renderPlanet(stack, RenderSystem.getProjectionMatrix(), new Vec3(screen[0], screen[1], 0), radius,
					p.getId(), UniversalCommon.parse(p.getTexture()));
			stack.popPose();
		}
		stack.popPose();

		Vec3 tardisWorld = resolveTardisPosition(time);
		Vec3 destWorld = new Vec3(destination.GetX(), destination.GetY(), destination.GetZ());
		int[] tardisScreen = worldToScreen(tardisWorld.x, tardisWorld.z);
		int[] destScreen = worldToScreen(destWorld.x, destWorld.z);

		drawDashedLine(gg, tardisScreen[0], tardisScreen[1], destScreen[0], destScreen[1], COLOR_TRAJECTORY, 5, 4);

		drawDiamond(gg, tardisScreen[0], tardisScreen[1], 6, tardisPositionIsKnown() ? COLOR_TARDIS : 0xFF555555);
		drawTarget(gg, destScreen[0], destScreen[1], 6, COLOR_DESTINATION);

		renderHud(gg, mouseX, mouseY, time, tardisWorld, destWorld);

		super.render(gg, mouseX, mouseY, partialTick);

		if (hoveredPlanet != null) {
			renderPlanetTooltip(gg, mouseX, mouseY, hoveredPlanet, time);
		}
	}

	private void renderStarfield(GuiGraphics gg) {
		for (StarDot star : stars) {
			int sx = (int) (star.u * this.width);
			int sy = MAP_MARGIN_TOP + (int) (star.v * (this.height - MAP_MARGIN_TOP - MAP_MARGIN_BOTTOM));
			gg.fill(sx, sy, sx + star.size, sy + star.size, COLOR_STAR);
		}
	}

	private void renderGrid(GuiGraphics gg) {
		int step = 48;
		for (int x = MAP_MARGIN_SIDE; x < this.width - MAP_MARGIN_SIDE; x += step) {
			gg.fill(x, MAP_MARGIN_TOP, x + 1, this.height - MAP_MARGIN_BOTTOM, COLOR_GRID);
		}
		for (int y = MAP_MARGIN_TOP; y < this.height - MAP_MARGIN_BOTTOM; y += step) {
			gg.fill(MAP_MARGIN_SIDE, y, this.width - MAP_MARGIN_SIDE, y + 1, COLOR_GRID);
		}
	}

	private void renderOrbitPath(GuiGraphics gg, PlanetLoader.Orbit orbit, Vec3 parentPos) {
		final int SAMPLES = 72;
		int[] prev = null;
		for (int i = 0; i <= SAMPLES; i++) {
			double progress = i / (double) SAMPLES;
			Vec3 point = computeOrbitPoint(orbit, progress, parentPos);
			int[] screen = worldToScreen(point.x, point.z);
			if (prev != null) {
				drawLine(gg, prev[0], prev[1], screen[0], screen[1], COLOR_ORBIT);
			}
			prev = screen;
		}
	}

	private static Vec3 computeOrbitPoint(PlanetLoader.Orbit orbit, double progress, Vec3 parentPos) {
		double angle = (progress * Math.PI * 2.0) + Math.toRadians(orbit.phase());

		double a = orbit.distance();
		double b = a * (1.0 - orbit.eccentricity());
		double focusOffset = Math.sqrt(Math.max(0, a * a - b * b));

		double x = a * Math.cos(angle) - focusOffset;
		double z = b * Math.sin(angle);

		Vec3 normal = new Vec3(Math.sin(Math.toRadians(orbit.inclination())),
				Math.cos(Math.toRadians(orbit.inclination())), 0).normalize();
		Vec3 arbitrary = Math.abs(normal.y) > 0.99 ? new Vec3(1, 0, 0) : new Vec3(0, 1, 0);
		Vec3 right = normal.cross(arbitrary).normalize();
		Vec3 forward = right.cross(normal).normalize();

		return parentPos.add(right.scale(x)).add(forward.scale(z));
	}

	private void renderHud(GuiGraphics gg, int mouseX, int mouseY, long time, Vec3 tardisWorld, Vec3 destWorld) {
		gg.drawCenteredString(this.font, "Astral Map", this.width / 2, 8, COLOR_TEXT);

		gg.fill(6, 20, 190, 78, COLOR_PANEL);
		int ly = 24;
		gg.drawString(this.font, legendDot(COLOR_PLANET_ROOT) + " Root body", 10, ly, COLOR_TEXT, false);
		ly += 11;
		gg.drawString(this.font, legendDot(COLOR_PLANET) + " Planet", 10, ly, COLOR_TEXT, false);
		ly += 11;
		gg.drawString(this.font, legendDot(COLOR_TARDIS) + " TARDIS", 10, ly, COLOR_TEXT, false);
		ly += 11;
		gg.drawString(this.font, legendDot(COLOR_DESTINATION) + " Destination", 10, ly, COLOR_TEXT, false);

		double distance = Math.sqrt(tardisWorld.distanceToSqr(destWorld));
		String distStr = String.format(Locale.ROOT, "Distance to destination: %,d blocks", Math.round(distance));
		String timeStr = "Game time: " + time + (timeOffsetTicks != 0
				? String.format(Locale.ROOT, " (%s%.1f days)", timeOffsetTicks >= 0 ? "+" : "", timeOffsetTicks / 24000.0)
				: "");

		int panelW = Math.max(this.font.width(distStr), this.font.width(timeStr)) + 12;
		int panelX = this.width - panelW - 6;
		gg.fill(panelX, 20, panelX + panelW, 54, COLOR_PANEL);
		gg.drawString(this.font, distStr, panelX + 6, 25, COLOR_TEXT, false);
		gg.drawString(this.font, timeStr, panelX + 6, 37, COLOR_TEXT_DIM, false);

		if (!tardisPositionIsKnown()) {
			gg.drawCenteredString(this.font, "WARNING: TARDIS position could not be resolved", this.width / 2,
					this.height - MAP_MARGIN_BOTTOM - 12, 0xFFFF5555);
		}
	}

	private String legendDot(int color) {
		return "\u25CF";
	}

	private void renderPlanetTooltip(GuiGraphics gg, int mouseX, int mouseY, PlanetLoader.Planet planet, long time) {
		List<Component> lines = new ArrayList<>();
		lines.add(Component.literal(planet.getName()));
		lines.add(Component.literal("id: " + planet.getId()).withStyle(s -> s.withColor(0xAAAAAA)));
		if (planet.getOrbit() != null) {
			lines.add(Component.literal(String.format(Locale.ROOT, "orbiting: %s", planet.getOrbit().parent()))
					.withStyle(s -> s.withColor(0xAAAAAA)));
		}
		gg.renderComponentTooltip(this.font, lines, mouseX, mouseY);
	}

	private int[] worldToScreen(double worldX, double worldZ) {
		int sx = (int) Math.round(this.width / 2.0 + (worldX - panX) * scale);
		int sy = (int) Math.round(
				MAP_MARGIN_TOP + (this.height - MAP_MARGIN_TOP - MAP_MARGIN_BOTTOM) / 2.0 + (worldZ - panZ) * scale);
		return new int[] { sx, sy };
	}

	private int planetPixelRadius(int worldSize) {
		int radius = (int) Math.round(2 + Math.sqrt(Math.max(1, worldSize)));
		return Math.max(2, Math.min(radius, 14));
	}

	private static double distSq(double x1, double y1, double x2, double y2) {
		double dx = x1 - x2, dy = y1 - y2;
		return dx * dx + dy * dy;
	}

	private void fillCircle(GuiGraphics gg, int cx, int cy, int radius, int color) {
		for (int dy = -radius; dy <= radius; dy++) {
			int dx = (int) Math.sqrt(Math.max(0, radius * radius - dy * dy));
			gg.fill(cx - dx, cy + dy, cx + dx + 1, cy + dy + 1, color);
		}
	}

	private void drawDiamond(GuiGraphics gg, int cx, int cy, int radius, int color) {
		for (int dy = -radius; dy <= radius; dy++) {
			int dx = radius - Math.abs(dy);
			gg.fill(cx - dx, cy + dy, cx + dx + 1, cy + dy + 1, color);
		}
	}

	private void drawTarget(GuiGraphics gg, int cx, int cy, int radius, int color) {
		gg.fill(cx - radius, cy - 1, cx + radius + 1, cy + 1, color);
		gg.fill(cx - 1, cy - radius, cx + 1, cy + radius + 1, color);
		fillCircleOutline(gg, cx, cy, radius, color);
	}

	private void fillCircleOutline(GuiGraphics gg, int cx, int cy, int radius, int color) {
		int steps = Math.max(16, radius * 4);
		for (int i = 0; i < steps; i++) {
			double angle = (i / (double) steps) * Math.PI * 2;
			int x = cx + (int) Math.round(Math.cos(angle) * radius);
			int y = cy + (int) Math.round(Math.sin(angle) * radius);
			gg.fill(x, y, x + 1, y + 1, color);
		}
	}

	private void drawLine(GuiGraphics gg, double x1, double y1, double x2, double y2, int color) {
		int steps = (int) Math.max(Math.abs(x2 - x1), Math.abs(y2 - y1));
		steps = Math.max(steps, 1);
		for (int i = 0; i <= steps; i++) {
			double t = i / (double) steps;
			int x = (int) Math.round(x1 + (x2 - x1) * t);
			int y = (int) Math.round(y1 + (y2 - y1) * t);
			gg.fill(x, y, x + 1, y + 1, color);
		}
	}

	private void drawDashedLine(GuiGraphics gg, double x1, double y1, double x2, double y2, int color, int dash,
	                            int gap) {
		double dx = x2 - x1, dy = y2 - y1;
		double dist = Math.sqrt(dx * dx + dy * dy);
		if (dist < 0.001)
			return;
		double ux = dx / dist, uy = dy / dist;

		double pos = 0;
		boolean draw = true;
		while (pos < dist) {
			double segLen = draw ? dash : gap;
			double next = Math.min(pos + segLen, dist);
			if (draw) {
				drawLine(gg, x1 + ux * pos, y1 + uy * pos, x1 + ux * next, y1 + uy * next, color);
			}
			pos = next;
			draw = !draw;
		}
	}

	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int button) {
		if (super.mouseClicked(mouseX, mouseY, button)) {
			return true;
		}
		if (button == 0 && mouseY > MAP_MARGIN_TOP && mouseY < this.height - MAP_MARGIN_BOTTOM) {
			dragging = true;
			lastMouseX = mouseX;
			lastMouseY = mouseY;
			return true;
		}
		return false;
	}

	@Override
	public boolean mouseReleased(double mouseX, double mouseY, int button) {
		if (button == 0) {
			dragging = false;
		}
		return super.mouseReleased(mouseX, mouseY, button);
	}

	@Override
	public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
		if (dragging && button == 0) {
			panX -= dragX / scale;
			panZ -= dragY / scale;
			lastMouseX = mouseX;
			lastMouseY = mouseY;
			return true;
		}
		return super.mouseDragged(mouseX, mouseY, button, dragX, dragY);
	}

	@Override
	public boolean mouseScrolled(double mouseX, double mouseY, double delta) {
		double factor = delta > 0 ? 1.15 : 1 / 1.15;
		scale = Math.max(0.0002, Math.min(scale * factor, 5.0));
		return true;
	}

	private static final class StarDot {
		final float u, v;
		final int size;

		StarDot(float u, float v, int size) {
			this.u = u;
			this.v = v;
			this.size = size;
		}
	}

	private static final class PlanetScreenPoint {
		final PlanetLoader.Planet planet;
		final int x, y, radius;

		PlanetScreenPoint(PlanetLoader.Planet planet, int x, int y, int radius) {
			this.planet = planet;
			this.x = x;
			this.y = y;
			this.radius = radius;
		}
	}

	private final class TimeSliderWidget extends AbstractSliderButton {

		private static final long MAX_OFFSET_TICKS = 24000L * 30;

		TimeSliderWidget(int x, int y, int width, int height) {
			super(x, y, width, height, Component.empty(), 0.5);
			updateMessage();
		}

		@Override
		protected void updateMessage() {
			long offset = offsetFromValue();
			double days = offset / 24000.0;
			setMessage(Component.literal(String.format(Locale.ROOT, "Time offset: %s%.1f days",
					offset >= 0 ? "+" : "", days)));
		}

		@Override
		protected void applyValue() {
			timeOffsetTicks = offsetFromValue();
		}

		private long offsetFromValue() {
			return Math.round((this.value - 0.5) * 2.0 * MAX_OFFSET_TICKS);
		}

		void resetToNow() {
			this.value = 0.5;
			timeOffsetTicks = 0;
			updateMessage();
		}
	}

	public static void renderPlanet(@NotNull PoseStack poseStack, Matrix4f projectionMatrix,
	                                @NotNull Vec3 screenPosition, float size, @NotNull String planetId, @NotNull ResourceLocation texture) {

		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
		RenderSystem.setShaderTexture(0, texture);

		RenderSystem.disableDepthTest();
		RenderSystem.depthMask(false);
		RenderSystem.disableBlend();

		poseStack.pushPose();
		poseStack.translate(screenPosition.x, screenPosition.y, screenPosition.z);

		VertexBuffer vbo = planetVBOs.get(planetId);
		if (vbo == null || vbo.isInvalid()) {
			BufferBuilder buffer = Tesselator.getInstance().getBuilder();
			buffer.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
			vbo = new VertexBuffer(VertexBuffer.Usage.STATIC);
			vbo.bind();
			vbo.upload(drawPlanet(buffer, size));
			VertexBuffer.unbind();
			planetVBOs.put(planetId, vbo);
		}

		if (!vbo.isInvalid()) {
			vbo.bind();
			vbo.drawWithShader(poseStack.last().pose(), projectionMatrix,
					Objects.requireNonNull(RenderSystem.getShader()));
			VertexBuffer.unbind();
		}

		poseStack.popPose();

		RenderSystem.enableDepthTest();
		RenderSystem.depthMask(true);
		RenderSystem.enableBlend();
	}
}