package cn.yi.gather.v11.service.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.yi.gather.v11.dao.LabelsRepository;
import cn.yi.gather.v11.entity.Labels;
import cn.yi.gather.v11.service.ILabelsServiceV2;

/**
 * 标签业务接口
 * @author Lee.J.Eric
 * @time 2014年5月29日上午10:36:47
 */
@Service("labelsServiceV2")
public class LabelsServiceV2 implements ILabelsServiceV2 {
	
	@Resource(name = "labelsRepositoryV2")
	private LabelsRepository labelsRepository;

	@Override
	public Labels getByID(Long id) {
		// TODO Auto-generated method stub
		return labelsRepository.findOne(id);
	}

	@Override
	public List<Labels> getAllLabels() {
		// TODO Auto-generated method stub
		return labelsRepository.findAll();
	}

	@Override
	public List<Labels> getLabelsByids(String ids) {
		// TODO Auto-generated method stub
		String[] idStrings = ids.split(",");
		List<Long> idList = new ArrayList<Long>();
		for (int i = 0; i < idStrings.length; i++) {
			idList.add(Long.valueOf(idStrings[i]));
		}
		return labelsRepository.findByIdIn(idList);
	}

	@Override
	public List<Labels> getLabelsByList(List<Long> ids) {
		// TODO Auto-generated method stub
		return labelsRepository.findByIdIn(ids);
	}

	@Override
	public List<Labels> getLabelsByType(Integer type) {
		// TODO Auto-generated method stub
		return labelsRepository.findByLabeltype(type);
	}

	@Override
	public Labels getLabelsByLabeltypeAndZname(Integer labeltype, String zname) {
		// TODO Auto-generated method stub
		return labelsRepository.findByLabeltypeAndZname(labeltype, zname);
	}

	
}
