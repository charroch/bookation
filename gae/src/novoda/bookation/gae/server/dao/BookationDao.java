package novoda.bookation.gae.server.dao;

import java.util.List;

import novoda.bookation.gae.shared.Bookation;
import novoda.bookation.gae.shared.BookationMarker;

public interface BookationDao {

	Bookation get(Long id);
	
	Long persist(Bookation bookation);
	
	List<BookationMarker> get(String account);
	
}
