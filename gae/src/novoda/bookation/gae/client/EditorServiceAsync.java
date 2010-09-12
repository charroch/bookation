package novoda.bookation.gae.client;

import java.util.List;

import novoda.bookation.gae.shared.Bookation;
import novoda.bookation.gae.shared.BookationMarker;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface EditorServiceAsync {

	void save(Bookation page, AsyncCallback<Void> callback);

	void get(Long id, AsyncCallback<Bookation> callback);

	void getAccountBookations(AsyncCallback<List<BookationMarker>> callback);
}
