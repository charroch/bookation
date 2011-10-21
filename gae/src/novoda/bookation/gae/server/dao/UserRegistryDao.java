package novoda.bookation.gae.server.dao;

public interface UserRegistryDao {

	void register(String account, String xtifyId);

	void deregister(String account);

	String getXtifyId(String account);

}
