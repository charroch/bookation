package novoda.bookation.gae.server.dao;

import java.util.List;
import java.util.logging.Logger;

import javax.jdo.JDOOptimisticVerificationException;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import novoda.bookation.gae.shared.UserRegistry;

import org.appengine.commons.dao.PMF;

public class JdoUserRegistryDao implements UserRegistryDao {
	
	private static final Logger log = Logger.getLogger(JdoUserRegistryDao.class.getName());

	protected PersistenceManager getPM() {
		return PMF.get().getPersistenceManager();
	}
	
	@Override
	public void deregister(String account) {
		PersistenceManager pm = getPM();
        pm.currentTransaction().begin();
        try {
        	UserRegistry user = get(account, pm);
        	if(user == null) {
        		return;
        	}
            pm.deletePersistent(user);
            pm.currentTransaction().commit();
        } catch(JDOOptimisticVerificationException e) {
            log.severe("JDOOptimisticVerificationException " + e.getMessage());
            throw new RuntimeException("JDOOptimisticVerificationException", e);
        } finally {      
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            pm.close();
        }
	}

	@SuppressWarnings("unchecked")
	private UserRegistry get(String account, PersistenceManager pm) {
		Query query = pm.newQuery(UserRegistry.class);
	    query.setFilter("account == accountParam");
	    query.declareParameters("String accountParam");
	    try {
	        List<UserRegistry> results = (List<UserRegistry>) query.execute(account);
	        if (results.iterator().hasNext()) {
	            return results.get(0);
	        }
	    } finally {
	        query.closeAll();
	    }
		return null;
	}

	@Override
	public String getXtifyId(String account) {
		PersistenceManager pm = getPM();
		UserRegistry user = get(account, pm);
		if(user == null) {
			return null;
		}
		return user.getXtifyId();
	}

	@Override
	public void register(String account, String xtifyId) {
		PersistenceManager pm = getPM();
        pm.currentTransaction().begin();
        try {
            pm.makePersistent(new UserRegistry(account, xtifyId));
            pm.currentTransaction().commit();
        } catch(JDOOptimisticVerificationException e) {
            log.severe("JDOOptimisticVerificationException " + e.getMessage());
            throw new RuntimeException("JDOOptimisticVerificationException", e);
        } finally {      
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            pm.close();
        }
	}

}
