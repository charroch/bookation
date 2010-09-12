package novoda.bookation.gae.server.servlet;


import java.util.List;

import novoda.bookation.gae.client.EditorService;
import novoda.bookation.gae.server.dao.BookationDao;
import novoda.bookation.gae.server.dao.JdoBookationDao;
import novoda.bookation.gae.server.dao.JdoUserRegistryDao;
import novoda.bookation.gae.server.dao.UserRegistryDao;
import novoda.bookation.gae.server.xtify.XtifyService;
import novoda.bookation.gae.shared.Bookation;
import novoda.bookation.gae.shared.BookationMarker;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class EditorServiceImpl extends RemoteServiceServlet implements EditorService {
	
	private static final long serialVersionUID = 1L;

	private BookationDao bdao = new JdoBookationDao();
	
	private UserRegistryDao urdao = new JdoUserRegistryDao();
	
	private XtifyService service = new XtifyService();
	
	@Override
	public void save(Bookation bookation) {
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
		bookation.setEmail(user.getEmail());
		bdao.persist(bookation);
		String xtifyId = urdao.getXtifyId(user.getEmail());
		service.sendMessage(xtifyId);
	}

	@Override
	public Bookation get(Long id) {
		return bdao.get(id);
	}
	
	@Override
	public List<BookationMarker> getAccountBookations() {
        UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
		String account = user.getEmail();
		return bdao.get(account);
	}

}
