package cn.yi.gather.v11.service.serviceImpl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.yi.gather.v11.dao.ImglogRepository;
import cn.yi.gather.v11.entity.Imglog;
import cn.yi.gather.v11.service.IImglogServiceV2;

/**
 * 图片记录
 * @author Lee.J.Eric
 * @time 2014年5月29日下午5:25:02
 */
@Service("imglogServiceV2")
public class ImglogServiceV2 implements IImglogServiceV2{
	
	@Resource(name = "imglogRepositoryV2")
	private ImglogRepository repository;

	@Override
	public int createImgLog(String url, int type) {
		// TODO Auto-generated method stub
		Imglog imglog=new Imglog();
		try {
			imglog.setCreatedate(System.currentTimeMillis());
			imglog.setImgurl(url);
			imglog.setStarts(0);
			imglog.setType(type);
			imglog = repository.save(imglog);
		} catch (Exception e) {
			// TODO: handle exception
			imglog = null;
		}
		if(imglog!=null)
			return 0;
		return 1;
	}

	@Override
	public List<Imglog> getListByType(int type, int size) {
		// TODO Auto-generated method stub
		return repository.getListByType(type, size);
	}

	@Override
	public void updateImglog(Imglog imglog) {
		// TODO Auto-generated method stub
		repository.save(imglog);
	}

}
