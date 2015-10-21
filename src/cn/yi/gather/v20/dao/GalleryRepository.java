package cn.yi.gather.v20.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Component;

import cn.yi.gather.v20.entity.Gallery;
@Component("galleryRepositoryV20")
public interface GalleryRepository extends JpaRepository<Gallery, String>,JpaSpecificationExecutor<Gallery>{

}
