/* (C) TAMA Studios 2026 */
package com.code.tama.triggerapi.helpers;

import com.code.tama.tts.server.data.json.loaders.PlanetLoader;

import net.minecraft.world.phys.Vec3;

public class PlanetHelper {
	public static Vec3 orbitPosition(Vec3 focusPosition, // Sun, planet, etc.
			double semiMajorAxis, // Width of orbit
			double semiMinorAxis, // Height of orbit
			double orbitProgress, // 0.0 -> 1.0
			Vec3 right, // Local X axis of orbital plane
			Vec3 forward // Local Z axis of orbital plane
	) {

		// Convert progress to angle
		double angle = orbitProgress * (Math.PI * 2.0);

		// Distance from center of ellipse to focus
		double focusOffset = Math.sqrt(semiMajorAxis * semiMajorAxis - semiMinorAxis * semiMinorAxis);

		// Position relative to ellipse center
		double localX = semiMajorAxis * Math.cos(angle);
		double localZ = semiMinorAxis * Math.sin(angle);

		// Shift ellipse so focus is at (0,0)
		localX -= focusOffset;

		return focusPosition.add(right.scale(localX)).add(forward.scale(localZ));
	}

	public static Vec3 getPosition(PlanetLoader.Planet planet, long gameTime) {
		if (planet == null)
			return Vec3.ZERO;

		// Root body, no orbit, just return its defined world position
		if (planet.getOrbit() == null) {
			return new Vec3(planet.getX(), planet.getY(), planet.getZ());
		}

		PlanetLoader.Orbit orbit = planet.getOrbit();
		PlanetLoader.Planet parent = PlanetLoader.strList.get(orbit.parent());
		Vec3 parentPos = getPosition(parent, gameTime);

		double progress = (gameTime % orbit.period()) / (double) orbit.period();
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

	public static double getLandingRadius(int size) {
		return size + (size * 0.3);
	}
}
