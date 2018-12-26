package app.open.software.master.container;

import app.open.software.protocol.packet.Packet;
import io.netty.channel.Channel;
import java.util.ArrayList;
import java.util.List;
import lombok.*;

@Getter
@RequiredArgsConstructor
public class ContainerEntity {

	private final ContainerMeta containerMeta;

	@Setter
	private Channel channel;

	private final List<Packet> packetQueue = new ArrayList<>();

	public void sendPacket(final Packet packet) {
		if (this.channel == null) {
			this.packetQueue.add(packet);
		} else {
			this.channel.writeAndFlush(packet);
		}
	}

	public void disconnect() throws InterruptedException {
		this.channel.close().sync();
	}

	public final boolean isConnected() {
		return this.channel != null && this.channel.isOpen();
	}

	public String toString() {
		return "Container @" + this.containerMeta.getHost() + " is " + (this.isConnected() ? "connected" : "not connected");
	}

}