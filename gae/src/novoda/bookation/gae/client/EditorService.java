package novoda.bookation.gae.client;

import java.util.List;

import novoda.bookation.gae.shared.Bookation;
import novoda.bookation.gae.shared.BookationMarker;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("editorService")
public interface EditorService extends RemoteService {

	void save(Bookation page);

	Bookation get(Long id);
	
	List<BookationMarker> getAccountBookations();
	
}
