package hydrocks.disaster.NMS;

import hydrocks.disaster.interfaces.NMSUtil;
import net.minecraft.network.protocol.Packet;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_20_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class SendPacket implements NMSUtil {
    private final Packet<?> packet;
    public SendPacket(Packet<?> packet) {
        this.packet = packet;
    }

    /**
     * Sends a packet to everyone currently on the server.
     *
     */
    public void sendPacket() {
        Bukkit.getOnlinePlayers().forEach(this::sendPacket);
    }

    /**
     * Sends a packet to a specified player
     *
     * @param p The player you want to send the particle to.
     */
    public void sendPacket(Player p) {
        ((CraftPlayer) p).getHandle().c.a(packet);
    }

    /**
     * Sends a packet to every player on the specified world
     *
     * @param w The world you want to send the packet in
     */
    public void sendPacket(World w) {
        w.getPlayers().forEach(this::sendPacket);
    }

}
