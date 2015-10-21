package cn.yi.gather.v20.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cn.yi.gather.v20.entity.User;

/**
 * 用户
 * @author Lee.J.Eric
 * @time 2014年5月29日上午11:31:55
 */
@Component("userRepositoryV20")
public interface UserRepository extends JpaRepository<User,String>,JpaSpecificationExecutor<User> {
	
	@Query(value = "select o from User o where  password =:password and (nickname =:username or email =:username or telnum =:username)")
	public User findByusernameAndPWD(@Param("password")String password,@Param("username")String username);
	
	public User findByEmail(String email);
	
	public User findByTelnum(String telnum);
	
	public User findByUnum(String unum);
	
	public User findByIcnum(String icnum);
	
	public User findByNicknameOrEmailOrTelnum(String nickname,String email,String telnum);
	
	/**
	 * 首页展示
	 * @param pageable
	 * @param ifindex
	 * @return
	 * Lee.J.Eric
	 * 2014 2014年5月30日 下午3:57:11
	 */
	public Page<User> findPageByIfindex(Pageable pageable,Integer ifindex);
	
	public User findByIdAndPassword(String id, String password);
	
	//public Page<User> findByRootAndLabelIn(Pageable pageable,Integer root,Labels label) ;
	//public Page<User> findByLabelIn(Pageable pageable,Labels label) ;
	
	/**
	 * 空间会员迁出
	 * 
	 * Lee.J.Eric
	 * 2014年9月22日 下午4:01:19
	 */
	@Modifying
	@Query(value = "update User set ifspace = 0")
	public void checkoutalluser();
}
