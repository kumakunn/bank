package me.bank.service;

import java.util.HashMap;

public class BankObserver {

	private final HashMap<String, OnDataChangeListener> mDataChangeListeners = new HashMap<String, OnDataChangeListener>();

	// Private constructor prevents instantiation from other classes
	private BankObserver() {
	}

	/**
	 * SingletonHolder is loaded on the first execution of
	 * Singleton.getInstance() or the first access to SingletonHolder.INSTANCE,
	 * not before.
	 */
	private static class SingletonHolder {
		public static final BankObserver INSTANCE = new BankObserver();
	}

	public static BankObserver getInstance() {
		return SingletonHolder.INSTANCE;
	}

	public void notifyListeners(String session) {
		
		if(session != null && !"".equals(session)) {
			synchronized (mDataChangeListeners) {
				mDataChangeListeners.get(session).onDataChange();
			}
			return;
		}
		
		synchronized (mDataChangeListeners) {
			for (String key : mDataChangeListeners.keySet()) {
				mDataChangeListeners.get(key).onDataChange();
			}
		}
	}

	public void addDataChangeListener(String session, OnDataChangeListener listener) {
		if (listener == null) {
			return;
		}

		synchronized (mDataChangeListeners) {
			mDataChangeListeners.put(session, listener);
		}
	}

	public void removeDataChangeListener(String session) {
		if (session == null || "".equals(session)) {
			return;
		}
		synchronized (mDataChangeListeners) {
			mDataChangeListeners.remove(session);
		}
	}

}
