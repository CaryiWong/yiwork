package cn.yi.gather.v20.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import cn.yi.gather.v20.entity.Resetpassword;
@Component("resetpasswordRepositoryV20")
public interface ResetpasswordRepository extends JpaRepository<Resetpassword, String> {

}
