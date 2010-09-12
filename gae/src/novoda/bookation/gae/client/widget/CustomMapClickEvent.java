package novoda.bookation.gae.client.widget;

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.maps.client.geom.LatLng;

public class CustomMapClickEvent extends GwtEvent<CustomMapClickHandler> {

	public static final GwtEvent.Type<CustomMapClickHandler> TYPE = new GwtEvent.Type<CustomMapClickHandler>();

	private double longitude;
	
	private double latitude;

	public CustomMapClickEvent(LatLng latLng) {
		this.latitude = latLng.getLatitude();
		this.longitude = latLng.getLongitude();
	}

	@Override
	protected void dispatch(CustomMapClickHandler handler) {
		handler.create(this);
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<CustomMapClickHandler> getAssociatedType() {
		return TYPE;
	}
	
	public double getLatitude() {
		return latitude;
	}
	
	public double getLongitude() {
		return longitude;
	}

}
