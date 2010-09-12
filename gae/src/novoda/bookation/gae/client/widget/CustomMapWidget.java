package novoda.bookation.gae.client.widget;

import java.util.List;

import novoda.bookation.gae.shared.BookationMarker;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.maps.client.MapWidget;
import com.google.gwt.maps.client.control.MapTypeControl;
import com.google.gwt.maps.client.control.SmallMapControl;
import com.google.gwt.maps.client.event.MapDoubleClickHandler;
import com.google.gwt.maps.client.geom.LatLng;
import com.google.gwt.maps.client.geom.Point;
import com.google.gwt.maps.client.geom.Size;
import com.google.gwt.maps.client.overlay.Icon;
import com.google.gwt.maps.client.overlay.Marker;
import com.google.gwt.maps.client.overlay.MarkerOptions;
import com.google.gwt.user.client.ui.VerticalPanel;

public class CustomMapWidget extends VerticalPanel {

	private MapWidget map;

	public CustomMapWidget(final HandlerManager eventBus) {
		map = new MapWidget(LatLng.newInstance(51.500152,-0.126236), 13);
		map.setSize("500px", "300px");
		map.addControl(new SmallMapControl());
		map.addControl(new MapTypeControl());
		map.clearOverlays();
		map.addMapDoubleClickHandler(new MapDoubleClickHandler() {
			@Override
			public void onDoubleClick(MapDoubleClickEvent event) {
				eventBus.fireEvent(new CustomMapClickEvent(event.getLatLng()));
			}
		});
		add(map);
	}

	public void setBookations(List<BookationMarker> result) {
		map.clearOverlays();
	    Icon icon = Icon.newInstance(
	        "http://labs.google.com/ridefinder/images/mm_20_red.png");
	    icon.setShadowURL("http://labs.google.com/ridefinder/images/mm_20_shadow.png");
	    icon.setIconSize(Size.newInstance(12, 20));
	    icon.setShadowSize(Size.newInstance(22, 20));
	    icon.setIconAnchor(Point.newInstance(6, 20));
	    icon.setInfoWindowAnchor(Point.newInstance(5, 1));

	    MarkerOptions options = MarkerOptions.newInstance();
	    options.setIcon(icon);
	    for (BookationMarker bm : result) {
	      LatLng point = LatLng.newInstance(bm.getLatitude(), bm.getLongitude());
	      map.addOverlay(new Marker(point, options));
	    }
	}

}
