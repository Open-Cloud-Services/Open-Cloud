package app.open.software.master.container;

import java.util.UUID;
import lombok.Data;

@Data
public class ContainerMeta {

	private final UUID uuid;

	private final String host;

	private final String key;

}
