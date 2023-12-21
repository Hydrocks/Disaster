package hydrocks.disaster.particles;

import hydrocks.disaster.Disaster;
import hydrocks.disaster.NMS.SendPacket;
import hydrocks.disaster.interfaces.NMSUtil;
import hydrocks.disaster.utils.VectorUtil;
import net.minecraft.core.particles.ParticleParam;
import net.minecraft.network.protocol.game.PacketPlayOutWorldParticles;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.craftbukkit.v1_20_R1.CraftParticle;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class ParticleShapes {

    /**
     * Sends an async packet particle
     *
     * @param loc Location
     * @param particle Particle e.g         ((CraftParticle.toNMS(Particle.FLAME)))
     * @param offX Offset X
     * @param offY Offset Y
     * @param offZ Offset Z
     * @param speed Speed of the particle
     * @param count Count of the particle
     */
    public static void sendParticle(Location loc, ParticleParam particle, float offX, float offY, float offZ, float speed, int count) {
        Bukkit.getScheduler().runTaskAsynchronously(Disaster.getPlugin(), () -> {
            NMSUtil nms = new SendPacket(new PacketPlayOutWorldParticles(
                    particle,
                    true,
                    loc.getX(),
                    loc.getY(),
                    loc.getZ(),
                    offX,
                    offY,
                    offZ,
                    speed,
                    count
            ));

            if (loc.getWorld() != null) {
                nms.sendPacket(loc.getWorld());
            }
        });
    }

    /**
     * Draws a 2D shape, such as a square, triangle, circle etc.
     *
     * @param particle Particle you want the thing to be idk
     * @param points points of the particle
     * @param rad radius of the particle
     * @param loc location of the particle
     * @param pitch pitch of the particle
     * @param yaw yaw of the particle
     */
    public static void draw2DShape(ParticleParam particle, int points, double rad, Location loc, float pitch, float yaw, boolean draw) {
        // Get the increment of the shape. type: 2d
        double increment = (2 * Math.PI) / points;

        // Calculate the correct pitch and yaw
        pitch = pitch * 0.017453292F;
        yaw = yaw * 0.017453292F;

        List<Location> locations = new ArrayList<>();
        float finalPitch = pitch;
        float finalYaw = yaw;

        for (int i = 0; i < points; i++) {
            // Get the locations necessary to draw the shape.
            Location p1 = getLocation(loc, getVector(rad, (i * increment)), finalPitch, finalYaw);
            Location p2 = getLocation(loc, getVector(rad, ((i + 1) * increment)), finalPitch, finalYaw);

            // Add the new locations to the List.
            locations.add(p1);
            if (draw) locations.addAll(doLine(p1, p2, 0.2));
        }

        locations.forEach(location -> {
            sendParticle(location, particle, 0, 0, 0, 0, 0);
        });
    }

    private static List<Location> doLine(Location from, Location to, double rad) {
        List<Location> locations = new ArrayList<>();
        if (rad > 0) {
            double distance = from.distance(to);

            Vector direction = to.toVector().subtract(from.toVector()).normalize();

            for (double length = 0; length < distance; length += rad) {
                Location pointOnLine = from.clone().add(direction.clone().multiply(length));
                locations.add(pointOnLine);
            }
        }
        return locations;
    }

    private static Location getLocation(Location loc, Vector v, float pitch, float yaw) {
        VectorUtil.rotateAroundAxisX(v, pitch);
        VectorUtil.rotateAroundAxisY(v, yaw);
        return loc.clone().add(v);
    }
    private static Vector getVector(double rad, double angle) {
        return new Vector(
                rad * Math.cos(angle),
                0,
                rad * Math.sin(angle)
        );
    }
}
