package me.emafire003.dev.particleanimationlib.util;

import net.minecraft.world.phys.Vec3;

public final class VectorUtils {

    public static Vec3 rotateVector(Vec3 vector, float angleX, float angleY, float angleZ) {
        vector = vector.xRot(angleX);
        vector = vector.yRot(angleY);
        vector = vector.zRot(angleZ);
        return vector;
    }

    /**
     * This handles non-unit vectors, with yaw and pitch instead of X,Y,Z angles.
     * <p>
     * Thanks to SexyToad!
     *
     * @param vector vector to rotate
     * @param yawDegrees yaw degrees
     * @param pitchDegrees pitch degrees
     * @return rotated vector
     */
    public static Vec3 rotateVector(Vec3 vector, float yawDegrees, float pitchDegrees) {
        double yaw = Math.toRadians(-1 * (yawDegrees + 90));
        double pitch = Math.toRadians(-pitchDegrees);

        double cosYaw = Math.cos(yaw);
        double cosPitch = Math.cos(pitch);
        double sinYaw = Math.sin(yaw);
        double sinPitch = Math.sin(pitch);

        double initialX, initialY, initialZ;
        double x, y, z;

        // Z_Axis rotation (Pitch)
        initialX = vector.x();
        initialY = vector.y();
        x = initialX * cosPitch - initialY * sinPitch;
        y = initialX * sinPitch + initialY * cosPitch;

        // Y_Axis rotation (Yaw)
        initialZ = vector.z();
        initialX = x;
        z = initialZ * cosYaw - initialX * sinYaw;
        x = initialZ * sinYaw + initialX * cosYaw;

        return new Vec3(x, y, z);
    }

    /*public static Vec3d rotateAroundAxisX(Vec3d vector, double angle) {
        double y, z, cos, sin;
        cos = Math.cos(angle);
        sin = Math.sin(angle);
        y = vector.getY() * cos - vector.getZ() * sin;
        z = vector.getY() * sin + vector.getZ() * cos;
        return new Vec3d(vector.getX(), y, z);
    }

    public static Vec3d rotateAroundAxisY(Vec3d vector, double angle) {
        double x, z, cos, sin;
        cos = Math.cos(angle);
        sin = Math.sin(angle);
        x = vector.getX() * cos + vector.getZ() * sin;
        z = vector.getX() * -sin + vector.getZ() * cos;
        return new Vec3d(x, vector.getY(), z);
    }

    public static Vec3d rotateAroundAxisZ(Vec3d vector, double angle) {
        double x, y, cos, sin;
        cos = Math.cos(angle);
        sin = Math.sin(angle);
        x = vector.getX() * cos - vector.getY() * sin;
        y = vector.getX() * sin + vector.getY() * cos;
        return new Vec3d(x, y, vector.getZ());
    }

    public static double angleToXAxis(Vec3d vector) {
        return Math.atan2(vector.getX(), vector.getY());
    }
*/
}
