package hydrocks.disaster.utils;

import org.bukkit.Location;
import org.bukkit.util.Vector;

import java.util.Random;

public class VectorUtil {

    public static Vector rotateAroundAxisX(Vector v, double angle) {
        double cos = Math.cos(angle);
        double sin = Math.sin(angle);
        double y = v.getY() * cos - v.getZ() * sin;
        double z = v.getY() * sin + v.getZ() * cos;
        return v.setY(y).setZ(z);
    }

    public static Vector rotateAroundAxisY(Vector v, double angle) {
        double cos = Math.cos(angle);
        double sin = Math.sin(angle);
        double x = v.getX() * cos + v.getZ() * sin;
        double z = v.getX() * -sin + v.getZ() * cos;
        return v.setX(x).setZ(z);
    }

    public static Vector rotateAroundAxisZ(Vector v, double angle) {
        angle = Math.toRadians(angle);
        double x, y, cos, sin;
        cos = Math.cos(angle);
        sin = Math.sin(angle);
        x = v.getX() * cos - v.getY() * sin;
        y = v.getX() * sin + v.getY() * cos;
        return v.setX(x).setY(y);
    }

    public static Vector getRandVector(double x, double y, double z) {
        Random rand = new Random();

        int xX = (int) x;
        int yY = (int) y;
        int zZ = (int) z;

        return new Vector(
                rand.nextInt(xX) - xX / 2,
                y > 0 ? rand.nextInt(yY) - yY / 2 : 0,
                rand.nextInt(zZ) - zZ / 2
        );
    }

    public static Vector getMin(Location loc, double x, double y, double z) {
        return new Vector(
                loc.getX() - x,
                loc.getY() - y,
                loc.getZ() - z
        );
    }

    public static Vector getMax(Location loc, double x, double y, double z) {
        return new Vector(
                loc.getX() + x,
                loc.getY() + y,
                loc.getZ() + z
        );
    }

}