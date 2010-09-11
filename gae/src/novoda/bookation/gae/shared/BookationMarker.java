package novoda.bookation.gae.shared;

import java.io.Serializable;

public class BookationMarker implements Serializable {

	private static final long serialVersionUID = 1L;

    private Double latitude;
    
    private Double longitude;
    
    public BookationMarker() {
    	
    }
    
    public BookationMarker(Double latitude, Double longitude) {
    	this.latitude = latitude;
    	this.longitude = longitude;
    }

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Double getLongitude() {
		return longitude;
	}

}
