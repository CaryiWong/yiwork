package cn.yi.gather.v11.service.serviceImpl;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.yi.gather.v11.dao.SpaceshowRepository;
import cn.yi.gather.v11.entity.Spaceshow;
import cn.yi.gather.v11.service.ISpaceshowServiceV2;

/**
 * 空间展示
 * @author Lee.J.Eric
 *
 */
@Service("spaceshowServiceV2")
public class SpaceshowServiceV2 implements ISpaceshowServiceV2{
	
	@Resource(name = "spaceshowRepositoryV2")
	private SpaceshowRepository repository;

	@Override
	public Spaceshow spaceshowSaveOrUpdate(Spaceshow spaceshow) {
		// TODO Auto-generated method stub
		return repository.save(spaceshow);
	}

	@Override
	public Spaceshow updateSpaceshow(String id, String img, Integer flag) {
		// TODO Auto-generated method stub
		Spaceshow spaceshow = repository.findOne(id);
		if(spaceshow==null)
			return null;
		String imgs = spaceshow.getImage();
		String[] arr = imgs.split(",");
		Set<String> set = new LinkedHashSet<String>();
		Collections.addAll(set, arr);
		if(flag==0){//添加图片
			set.add(img);
		}else if(flag == 1) {//删除图片
			set.remove(img);
		}
		imgs = "";
		for (String string : set) {
			if(imgs.isEmpty()){
				imgs += string;
			}else {
				imgs += "," + string;
			}
		}
		spaceshow.setImage(imgs);	
		return spaceshowSaveOrUpdate(spaceshow);
	}

	@Override
	public List<Spaceshow> getAllSpaceshowList() {
		// TODO Auto-generated method stub
		return repository.findAll();
	}

	@Override
	public Spaceshow findById(String id) {
		// TODO Auto-generated method stub
		return repository.findOne(id);
	}
	
}
