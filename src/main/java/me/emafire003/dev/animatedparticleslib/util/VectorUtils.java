package me.emafire003.dev.animatedparticleslib.util;

import net.minecraft.util.math.Vec3d;

public final class VectorUtils {



    public static Vec3d rotateVector(Vec3d vector, float angleX, float angleY, float angleZ) {
        // double x = vector.getX(), y = vector.getY(), z = vector.getZ();
        // double cosX = Math.cos(angleX), sinX = Math.sin(angleX), cosY =
        // Math.cos(angleY), sinY = Math.sin(angleY), cosZ = Math.cos(angleZ),
        // sinZ = Math.sin(angleZ);
        // double nx, ny, nz;
        // nx = (x * cosY + z * sinY) * (x * cosZ - y * sinZ);
        // ny = (y * cosX - z * sinX) * (x * sinZ + y * cosZ);
        // nz = (y * sinX + z * cosX) * (-x * sinY + z * cosY);
        // return vector.setX(nx).setY(ny).setZ(nz);
        // Having some strange behavior up there.. Have to look in it later. TODO
        /*rotateAroundAxisX(vector, angleX);
        rotateAroundAxisY(vector, angleY);
        rotateAroundAxisZ(vector, angleZ);*/
        vector.rotateX(angleX);
        vector.rotateY(angleY);
        vector.rotateZ(angleZ);
        return vector;
    }

    /**
     * This handles non-unit vectors, with yaw and pitch instead of X,Y,Z angles.
     *
     * Thanks to SexyToad!
     *
     * @param vector vector to rotate
     * @param yawDegrees yaw degrees
     * @param pitchDegrees pitch degrees
     * @return rotated vector
     */
    public static Vec3d rotateVector(Vec3d vector, float yawDegrees, float pitchDegrees) {
        double yaw = Math.toRadians(-1 * (yawDegrees + 90));
        double pitch = Math.toRadians(-pitchDegrees);

        double cosYaw = Math.cos(yaw);
        double cosPitch = Math.cos(pitch);
        double sinYaw = Math.sin(yaw);
        double sinPitch = Math.sin(pitch);

        double initialX, initialY, initialZ;
        double x, y, z;

        // Z_Axis rotation (Pitch)
        initialX = vector.getX();
        initialY = vector.getY();
        x = initialX * cosPitch - initialY * sinPitch;
        y = initialX * sinPitch + initialY * cosPitch;

        // Y_Axis rotation (Yaw)
        initialZ = vector.getZ();
        initialX = x;
        z = initialZ * cosYaw - initialX * sinYaw;
        x = initialZ * sinYaw + initialX * cosYaw;

        return new Vec3d(x, y, z);
    }

    public static double angleToXAxis(Vec3d vector) {
        return Math.atan2(vector.getX(), vector.getY());
    }

}
