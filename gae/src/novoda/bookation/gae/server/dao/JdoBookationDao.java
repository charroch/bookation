package novoda.bookation.gae.server.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.jdo.JDOOptimisticVerificationException;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import novoda.bookation.gae.shared.Bookation;
import novoda.bookation.gae.shared.BookationMarker;

import org.appengine.commons.dao.PMF;


public class JdoBookationDao implements BookationDao {

	private static final Logger log = Logger.getLogger(JdoBookationDao.class.getName());

	protected PersistenceManager getPM() {
		return PMF.get().getPersistenceManager();
	}

	@Override
	public Bookation get(Long id) {
		if(id == null) {
            return null;
        }
        return getPM().getObjectById(Bookation.class, id);
	}

	@Override
	public Long persist(Bookation toPersist) {
		PersistenceManager pm = getPM();
        pm.currentTransaction().begin();
        try {
            Bookation bookation = get(toPersist.getId());
            if(bookation != null) {
                toPersist.setId(bookation.getId());
            } else {
            	toPersist.setCreated(new Date());
            }
            toPersist.setModified(new Date());
            bookation = pm.makePersistent(toPersist);
            pm.currentTransaction().commit();
            return bookation.getId();
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

	@Override
	@SuppressWarnings("unchecked")
	public List<BookationMarker> get(String account) {
		PersistenceManager pm = getPM();
		Query query = pm.newQuery(Bookation.class);
	    query.setFilter("email == accountParam");
	    query.declareParameters("String accountParam");
	    try {
	        List<Bookation> results = (List<Bookation>) query.execute(account);
	        if(results == null) {
	        	return new ArrayList<BookationMarker>();
	        }
	        List<BookationMarker> markers = new ArrayList<BookationMarker>();
	        for(Bookation bookation : results) {
	        	markers.add(new BookationMarker(bookation.getLatitude(), bookation.getLongitude()));
	        }
	        return markers;
	    } finally {
	        query.closeAll();
	    }
	}

}
