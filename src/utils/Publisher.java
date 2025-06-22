package utils;

import enums.ObserverEvent;

public interface Publisher {
	/**
	 * Subscribes sub to all published events
	 * 
	 * @param sub
	 */
	default void subscribe(Subscriber sub) {
		subscribe(sub, ObserverEvent.ALL);
	}

	public void subscribe(Subscriber sub, ObserverEvent eventType);

	/**
	 * Unsubscribes sub from all published events
	 * 
	 * @param sub
	 */
	default void unsubscribe(Subscriber sub) {
		unsubscribe(sub, ObserverEvent.ALL);
	}

	public void unsubscribe(Subscriber sub, ObserverEvent eventType);

	default void notifySubscribers() {
		notifySubscribers(ObserverEvent.ALL);
	}

	public void notifySubscribers(ObserverEvent eventType);

}
