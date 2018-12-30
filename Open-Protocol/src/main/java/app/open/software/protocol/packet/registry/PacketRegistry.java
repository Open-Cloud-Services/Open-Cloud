/*
 * Copyright (c) 2018, Open-Software and contributors
 *
 * The code is licensed under the MIT License, which can be found in the root directory of the repository
 */

package app.open.software.protocol.packet.registry;

import app.open.software.protocol.packet.Packet;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import lombok.Getter;

/**
 * Registry of all {@link Packet}s
 *
 * @author Tammo0987
 * @version 1.0
 * @since 0.4
 */
public enum PacketRegistry {

	IN,
	OUT;

	/**
	 * {@link Map} of registered {@link Packet}s by id
	 */
	@Getter
	private final Map<Integer, Class<? extends Packet>> packets = new HashMap<>();

	/**
	 * Add a {@link Packet} to the {@link PacketRegistry}
	 *
	 * @param id Id of the {@link Packet}
	 * @param packet {@link Packet} class
	 */
	public void addPacket(final int id, final Class<? extends Packet> packet) {
		this.packets.put(id, packet);
	}

	/**
	 * @param id Id of the {@link Packet}
	 *
	 * @return {@link Packet} by id
	 *
	 * @throws NoSuchMethodException An error occurred
	 * @throws IllegalAccessException An error occurred
	 * @throws InvocationTargetException An error occurred
	 * @throws InstantiationException An error occurred
	 */
	public final Packet getPacketById(final int id) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
		return this.packets.get(id).getDeclaredConstructor().newInstance();
	}

	/**
	 * @param packet {@link Packet} of the id
	 *
	 * @return Id by {@link Packet}
	 */
	public final int getIdByPacket(final Packet packet) {
		return this.packets.entrySet().stream().filter(entry -> entry.getValue() == packet.getClass()).map(Map.Entry::getKey).findFirst().orElse(-1);
	}

}