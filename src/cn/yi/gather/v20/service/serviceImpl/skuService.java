package cn.yi.gather.v20.service.serviceImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.jfree.util.Log;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import cn.yi.gather.v20.dao.SKURepository;
import cn.yi.gather.v20.dao.SkuInventoryRepository;
import cn.yi.gather.v20.entity.ItemClass;
import cn.yi.gather.v20.entity.SKU;
import cn.yi.gather.v20.entity.SkuInventory;
import cn.yi.gather.v20.service.ISkuService;

import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * 个人展示
 * @author Lee.J.Eric
 *
 */
@Service("skuServiceV20")
public class skuService implements ISkuService{
	
	@Resource(name = "dataSourceV20")
	private ComboPooledDataSource dataSource; 
	
	@Resource(name = "sKURepositoryV20")
	private SKURepository repository;
	
	@Resource(name = "skuInventoryRepositoryV20")
	private SkuInventoryRepository skuInventoryRepository;
	
	@Override
	public List<SKU> getSKUListByItemClassId(Long item_class_id) {
		// TODO Auto-generated method stub
		//return sku_repository.findByItemClassId(item_class_id);
		return null;
	}

	@Override
	public SKU getSku(String sku_id) {
		// TODO Auto-generated method stub
		return repository.findById(sku_id);
		//return null;
	}
	
	@Override
	public Boolean skuExist(String sku_id) throws Exception {
		Connection conn = null;
		try {
			conn = dataSource.getConnection();
			boolean ret = skuExistInternal(conn, sku_id);
			return ret;
		}
		catch(Exception e) {
			Log.warn(
				String.format(
					"failed to check sku exists, expcetion:%s, sku_id=%s",
					e.toString(), sku_id));
			return null;
		}
		finally {
			if (conn != null) {
				conn.close();
			}
		}
	}
	
	@Override
	public SkuInventory getSkuInventory(String sku_id) {
		//return inventory_repository.findById(sku_id);
		return null;
	}
	
	@Override
	public Double getDefaultPriceById(String sku_id) throws Exception {
		Connection conn = null;
		PreparedStatement pstat = null;
		ResultSet result = null;
		try {
			conn = dataSource.getConnection();
			pstat = conn.prepareStatement("SELECT default_price FROM sku WHERE id=?");
			pstat.setString(1, sku_id);
			result = pstat.executeQuery();
			if (result.next()) {
				return result.getDouble("default_price");
			}
			return null;
		}
		catch(Exception e) {
			Log.warn(
					String.format(
						"failed to get default_price, expcetion:%s, sku_id=%s",
						e.toString(), sku_id));
				return null;
		}
		finally {
			if (result != null) {
				result.close();
			}
			if (pstat != null) {
				pstat.close();
			}
			if (conn != null) {
				conn.close();
			}
		}
	}
	
	@Override
	public Double getMemberPriceById(String sku_id) throws Exception {
		Connection conn = null;
		PreparedStatement pstat = null;
		ResultSet result = null;
		try {
			conn = dataSource.getConnection();
			pstat = conn.prepareStatement("SELECT member_price FROM sku WHERE id=?");
			pstat.setString(1, sku_id);
			result = pstat.executeQuery();
			if (result.next()) {
				return result.getDouble("member_price");
			}
			return null;
		}
		catch(Exception e) {
			Log.warn(
					String.format(
						"failed to get default_price, expcetion:%s, sku_id=%s",
						e.toString(), sku_id));
				return null;
		}
		finally {
			if (result != null) {
				result.close();
			}
			if (pstat != null) {
				pstat.close();
			}
			if (conn != null) {
				conn.close();
			}
		}
	}
	
	
	protected boolean skuExistInternal(Connection conn, String sku_id) throws Exception {
		PreparedStatement pstat = null;
		ResultSet result = null;
		try {
			pstat = conn.prepareStatement("SELECT id FROM sku WHERE id=?");
			pstat.setString(1, sku_id);
			result = pstat.executeQuery();
			if (result.next() && result.getString("id") != null) {
				return true;
			}
			return false;
		}
		catch(Exception e) {
			return false;
		}
		finally {
			if (result != null) {
				result.close();
			}
			if (pstat != null) {
				pstat.close();
			}
		}
	}

	@Override
	public SKU saveOrUpdate(SKU entity) {
		// TODO Auto-generated method stub
		repository.save(entity);
		return repository.save(entity);
	}

	@Override
	public SKU findById(String id) {
		// TODO Auto-generated method stub
		return repository.findOne(id);
	}

	@Override
	public List<SKU> findListByItemClass(final Long item_id) {
		// TODO Auto-generated method stub
		Specification<SKU> spec = new Specification<SKU>() {
			
			@Override
			public Predicate toPredicate(Root<SKU> root, CriteriaQuery<?> query,
					CriteriaBuilder cb) {
				// TODO Auto-generated method stub
				List<Predicate> ps = new ArrayList<Predicate>();
				ps.add(cb.equal(root.<ItemClass>get("itemClass").<Long>get("id"),item_id));
				query.where(ps.toArray(new Predicate[ps.size()]));
				return query.getRestriction();
			}
		};	
		return repository.findAll(spec);
	}
	
}
