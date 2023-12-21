package hydrocks.disaster.interfaces;

import net.minecraft.network.protocol.Packet;
import org.bukkit.World;
import org.bukkit.entity.Player;

public interface NMSUtil {

    /**
     * Sends a packet to everyone currently on the server.
     *
     */

    void sendPacket();

    /**
     * Sends a packet to a specified player
     *
     * @param p The player you want to send the particle to.
     */

    void sendPacket(Player p);

    /**
     * Sends a packet to every player on the specified world
     *
     * @param w The world you want to send the packet in
     */

    void sendPacket(World w);
}
