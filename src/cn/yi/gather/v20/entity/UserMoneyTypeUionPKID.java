package cn.yi.gather.v20.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class UserMoneyTypeUionPKID implements java.io.Serializable {

	private static final long serialVersionUID = 777178764653L;

	private String userId;

    private Integer moneyType;

    public UserMoneyTypeUionPKID() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Column(name="userId")
    public String getUserId() { 
        return userId; 
    }

    public void setUserId(String userId) { 
        this.userId = userId; 
    } 
    
    @Column(name="moneyType")
    public Integer getMoneyType() { 
        return moneyType; 
    }

    public void setMoneyType(Integer moneyType) { 
        this.moneyType = moneyType; 
    }
    
    @Override
    public boolean equals(Object obj) {
    	
    	if(this==obj)
    		return true;
    	
    	if (obj==null) 
			return false;
    	
    	if (getClass() != obj.getClass())
            return false;
    	
    	UserMoneyTypeUionPKID pk = (UserMoneyTypeUionPKID)obj;
    	if (this.userId.equals(pk.getUserId()) && this.moneyType.equals(pk.getMoneyType())) {
    		return true;
    	}
    	return false;
    }
    
    @Override
    public int hashCode() {
    	return userId.hashCode() + moneyType.hashCode();
    }
}

