// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MediaFrameRateSliderModel.java

package br.srv.full.roadsurvey.models;

import java.io.Serializable;
import java.util.EventListener;

import javax.swing.BoundedRangeModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.EventListenerList;

public class MediaFrameRateSliderModel implements BoundedRangeModel,
		Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4105816739608712156L;

	public MediaFrameRateSliderModel() {
		changeEvent = null;
		listenerList = new EventListenerList();
		value = 21;
		extent = 0;
		min = 5;
		max = 50;
		isAdjusting = false;
	}

	public MediaFrameRateSliderModel(int value, int extent, int min, int max) {
		changeEvent = null;
		listenerList = new EventListenerList();
		this.value = 21;
		this.extent = 0;
		this.min = 5;
		this.max = 50;
		isAdjusting = false;
		if (max >= min && value >= min && value + extent >= value
				&& value + extent <= max) {
			this.value = value;
			this.extent = extent;
			this.min = min;
			this.max = max;
		} else {
			throw new IllegalArgumentException("invalid range properties");
		}
	}

	public int getValue() {
		return value;
	}

	public int getExtent() {
		return extent;
	}

	public int getMinimum() {
		return min;
	}

	public int getMaximum() {
		return max;
	}

	public void setValue(int n) {
		n = Math.min(n, 0x7fffffff - extent);
		int newValue = Math.max(n, min);
		if (newValue + extent > max)
			newValue = max - extent;
		setRangeProperties(newValue, extent, min, max, isAdjusting);
	}

	public void setExtent(int n) {
		int newExtent = Math.max(0, n);
		if (value + newExtent > max)
			newExtent = max - value;
		setRangeProperties(value, newExtent, min, max, isAdjusting);
	}

	public void setMinimum(int n) {
		int newMax = Math.max(n, max);
		int newValue = Math.max(n, value);
		int newExtent = Math.min(newMax - newValue, extent);
		setRangeProperties(newValue, newExtent, n, newMax, isAdjusting);
	}

	public void setMaximum(int n) {
		int newMin = Math.min(n, min);
		int newExtent = Math.min(n - newMin, extent);
		int newValue = Math.min(n - newExtent, value);
		setRangeProperties(newValue, newExtent, newMin, n, isAdjusting);
	}

	public void setValueIsAdjusting(boolean b) {
		setRangeProperties(value, extent, min, max, b);
	}

	public boolean getValueIsAdjusting() {
		return isAdjusting;
	}

	public void setRangeProperties(int newValue, int newExtent, int newMin,
			int newMax, boolean adjusting) {
		if (newMin > newMax)
			newMin = newMax;
		if (newValue > newMax)
			newMax = newValue;
		if (newValue < newMin)
			newMin = newValue;
		if ((long) newExtent + (long) newValue > (long) newMax)
			newExtent = newMax - newValue;
		if (newExtent < 0)
			newExtent = 0;
		boolean isChange = newValue != value || newExtent != extent
				|| newMin != min || newMax != max || adjusting != isAdjusting;
		if (isChange) {
			value = newValue;
			extent = newExtent;
			min = newMin;
			max = newMax;
			isAdjusting = adjusting;
			fireStateChanged();
		}
	}

	public void addChangeListener(ChangeListener l) {
		listenerList.add(javax.swing.event.ChangeListener.class, l);
	}

	public void removeChangeListener(ChangeListener l) {
		listenerList.remove(javax.swing.event.ChangeListener.class, l);
	}

	public ChangeListener[] getChangeListeners() {
		return (ChangeListener[]) listenerList
				.getListeners(javax.swing.event.ChangeListener.class);
	}

	protected void fireStateChanged() {
		Object listeners[] = listenerList.getListenerList();
		for (int i = listeners.length - 2; i >= 0; i -= 2)
			if (listeners[i] == javax.swing.event.ChangeListener.class) {
				if (changeEvent == null)
					changeEvent = new ChangeEvent(this);
				((ChangeListener) listeners[i + 1]).stateChanged(changeEvent);
			}

	}

	public String toString() {
		String modelString = (new StringBuilder("value=")).append(getValue())
				.append(", ").append("extent=").append(getExtent())
				.append(", ").append("min=").append(getMinimum()).append(", ")
				.append("max=").append(getMaximum()).append(", ")
				.append("adj=").append(getValueIsAdjusting()).toString();
		return (new StringBuilder(String.valueOf(getClass().getName())))
				.append("[").append(modelString).append("]").toString();
	}

	@SuppressWarnings("unchecked")
	public EventListener[] getListeners(
			@SuppressWarnings("rawtypes") Class listenerType) {
		return listenerList.getListeners(listenerType);
	}

	protected transient ChangeEvent changeEvent;
	protected EventListenerList listenerList;
	private int value;
	private int extent;
	private int min;
	private int max;
	private boolean isAdjusting;
}
