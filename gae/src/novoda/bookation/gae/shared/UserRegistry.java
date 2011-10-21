package novoda.bookation.gae.shared;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable
public class UserRegistry {

	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long id;

	@Persistent
	private String account;

	@Persistent
	private String xtifyId;

	public UserRegistry(String account, String xtifyId) {
		this.account = account;
		this.xtifyId = xtifyId;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getAccount() {
		return account;
	}

	public void setXtifyId(String xtifyId) {
		this.xtifyId = xtifyId;
	}

	public String getXtifyId() {
		return xtifyId;
	}

}
