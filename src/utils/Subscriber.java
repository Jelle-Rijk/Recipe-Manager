package utils;

import enums.ObserverEvent;

public interface Subscriber {
	default void update() {
		update(ObserverEvent.ALL);
	}

	public void update(ObserverEvent eventType);
}
