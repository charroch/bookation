package novoda.bookation.gae.server.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import novoda.bookation.gae.server.dao.JdoUserRegistryDao;

public class Register extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		execute(req);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		execute(req);
	}
	
	private void execute(HttpServletRequest req){
		String email = req.getHeader("account");
		//TODO only for testing
		if(email == null) {
			email = req.getParameter("account");
		}
		String xtifyId = req.getParameter("xtify_id");
		new JdoUserRegistryDao().register(email, xtifyId);
	}
	
}
