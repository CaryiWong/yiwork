package cn.yi.gather.v11.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import cn.yi.gather.v11.entity.Resetpassword;
@Component("resetpasswordRepositoryV2")
public interface ResetpasswordRepository extends JpaRepository<Resetpassword, String> {

}
