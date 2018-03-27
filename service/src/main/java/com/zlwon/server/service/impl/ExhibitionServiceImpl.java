package com.zlwon.server.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zlwon.constant.StatusCode;
import com.zlwon.exception.CommonException;
import com.zlwon.rdb.dao.ApplicationCaseMapper;
import com.zlwon.rdb.dao.ExhibitionCaseMapMapper;
import com.zlwon.rdb.dao.ExhibitionCaseMapper;
import com.zlwon.rdb.dao.ExhibitionMapper;
import com.zlwon.rdb.entity.ApplicationCase;
import com.zlwon.rdb.entity.Exhibition;
import com.zlwon.rdb.entity.ExhibitionCase;
import com.zlwon.rdb.entity.ExhibitionCaseMap;
import com.zlwon.server.service.ExhibitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 展会ServiceImpl
 * @author yangy
 *
 */

@Service
public class ExhibitionServiceImpl implements ExhibitionService {

	@Autowired
	private  ExhibitionMapper  exhibitionMapper;
	@Autowired
	private  ApplicationCaseMapper   applicationCaseMapper;
	@Autowired
	private  ExhibitionCaseMapper  exhibitionCaseMapper;
	@Autowired
	private  ExhibitionCaseMapMapper  exhibitionCaseMapMapper;

	/**
	 * 得到所有展会，分页查找
	 */
	@Override
	public PageInfo<Exhibition> findAllExhibitionPage(Integer pageIndex, Integer pageSize) {
		PageHelper.startPage(pageIndex, pageSize);
		List<Exhibition>  list = exhibitionMapper.selectAllExhibition();
		return new PageInfo<>(list);
	}

	/**
	 * 添加展会
	 */
	@Override
	public int saveExhibition(Exhibition exhibition) {
		Date  date = new  Date();
		exhibition.setCreateTime(date);
		exhibition.setDel(1);//标记删除状态，1正常，-1已删除
		// TODO 不晓得添加展会的时候会不会添加案例，这暂时不做添加案例功能，只添加展会普通信息，添加案例有中间表
		return  exhibitionMapper.insert(exhibition);
	}

	/**
	 * 根据展会id，得到展会所有信息(不判断标记状态)
	 * @param id
	 * @return
	 */
	@Override
	public Exhibition findExhibitionInfoById(Integer id) {
		return exhibitionMapper.selectByPrimaryKey(id);
	}
	
	/**
	 * 保存编辑后的展会信息（只操作普通信息）
	 */
	@Override
	public int alterExhibitionSelective(Exhibition exhibition) {
		//查看该展会是否存在
		Exhibition  record = exhibitionMapper.selectByPrimaryKey(exhibition.getId());
		if(record==null  ||  record.getDel()!=1){
			//抛出数据不存在异常
			throw  new  CommonException(StatusCode.DATA_NOT_EXIST);
		}
		//执行更新
		return exhibitionMapper.updateByPrimaryKeySelective(exhibition);
		
	}

	/**
	 * 根据展会id，删除展会
	 */
	@Override
	public int removeExhibitionById(Integer id) {
		Exhibition record = new  Exhibition();
		record.setId(id);
		record.setDel(-1);
		return exhibitionMapper.updateByPrimaryKeySelective(record);
	}

	/**
	 * 展会案例关联工程师,直接添加，不做判断
	 */
	@Override
	public int saveExhibitionApplicationCase(ExhibitionCase exhibitionCase,Integer  exhibitionId) {
		ExhibitionCaseMap exhibitionCaseMap = new  ExhibitionCaseMap();
		//查看该案例是否已属于该展会
		exhibitionCaseMap.setCaseId(exhibitionCase.getCid());
		exhibitionCaseMap.setExhibitionId(exhibitionId);
		ExhibitionCaseMap map = exhibitionCaseMapMapper.selectByCaseIdAndEid(exhibitionCaseMap);
		//展会案例关联不存在
		if(map == null){
			throw  new  CommonException(StatusCode.DATA_NOT_EXIST);
		}else {
			//判断展会案例表id(exhibition_case_id)是否为空,为空才可以添加，否则无法添加
			if(map.getExhibitionCaseId()!=null){
				throw  new  CommonException(StatusCode.DATA_IS_EXIST);
			}
		}
		
		//执行添加操作
		exhibitionCase.setDel(1);
		exhibitionCaseMapper.insert(exhibitionCase);
		
		//得到展会案例表主键id，然后更新到展会案例关联表的展会案例id上
		map.setExhibitionCaseId(exhibitionCase.getId());
		return  exhibitionCaseMapMapper.updateByPrimaryKeySelective(map);
		 
	}
	
	/**
	 * 取消展会案例关联工程师
	 */
	@Override
	public int cancelExhibitionApplicationCase(ExhibitionCase exhibitionCase, Integer exhibitionId) {
		ExhibitionCaseMap exhibitionCaseMap = new  ExhibitionCaseMap();
		//查看该案例是否已属于该展会
		exhibitionCaseMap.setCaseId(exhibitionCase.getCid());
		exhibitionCaseMap.setExhibitionId(exhibitionId);
		ExhibitionCaseMap map = exhibitionCaseMapMapper.selectByCaseIdAndEid(exhibitionCaseMap);
		//展会案例关联不存在
		if(map == null){
			throw  new  CommonException(StatusCode.DATA_NOT_EXIST);
		}else {
			//根据map的exhibitionCaseId(是展会案例表主键)得到展会案例表数据
			//判断工程师id是否一样，一样才可以取消，否则无法取消
			if(map.getExhibitionCaseId()!=null){
				ExhibitionCase exhibitionCase2 = exhibitionCaseMapper.selectByPrimaryKey(map.getExhibitionCaseId());
				if(exhibitionCase2!=null && exhibitionCase2.getUid()==exhibitionCase.getUid()){
					//执行取消操作
					exhibitionCase2.setDel(-1);
					exhibitionCaseMapper.updateByPrimaryKeySelective(exhibitionCase2);
					
					//修改展会案例关联表exhibition_case_id字段为null
					map.setExhibitionCaseId(null);
					return  exhibitionCaseMapMapper.updateByPrimaryKey(map);
				}else {
					throw  new  CommonException(StatusCode.DATA_NOT_EXIST);
				}
			}else {
				throw  new  CommonException(StatusCode.DATA_NOT_EXIST);
			}
		}
	}
	
	/**
	 * 判断该案例是否已属于该展会，属于执行修改操作
	 */
	@Override
	public int alterExhibitionApplicationCase(ExhibitionCase exhibitionCase, Integer exhibitionId) {
		ExhibitionCaseMap exhibitionCaseMap = new  ExhibitionCaseMap();
		//查看该案例是否已属于该展会
		exhibitionCaseMap.setCaseId(exhibitionCase.getCid());
		exhibitionCaseMap.setExhibitionId(exhibitionId);
		ExhibitionCaseMap map = exhibitionCaseMapMapper.selectByCaseIdAndEid(exhibitionCaseMap);
		//展会案例关联不存在
		if(map == null){
			throw  new  CommonException(StatusCode.DATA_NOT_EXIST);
		}else {
			ExhibitionCase record = new ExhibitionCase();
			//执行更新展会案例表工程师id
			record.setId(map.getExhibitionCaseId());
			record.setUid(exhibitionCase.getUid());
			return  exhibitionCaseMapper.updateByPrimaryKeySelective(record);
		}
	}
	
	
	/**
	 * 根据展会案例ID查询展会案例信息
	 * @param id
	 * @return
	 */
	@Override
	public ExhibitionCase selectExhibitionCaseById(Integer id){
		ExhibitionCase temp = exhibitionCaseMapper.selectByPrimaryKey(id);
		return temp;
	}

	/**
	 * 根据展会id，得到展会所有正常的案例
	 */
	@Override
	public PageInfo<ApplicationCase> findAllExhibitionAppByIdMake(Integer pageIndex, Integer pageSize, Integer id) {
		//查看该展会是否存在
		Exhibition exhibition = exhibitionMapper.selectByPrimaryKey(id);
		if(exhibition == null  ||  exhibition.getDel() != 1){
			throw  new CommonException(StatusCode.DATA_NOT_EXIST);
		}
		PageHelper.startPage(pageIndex, pageSize);
		
		List<ApplicationCase>  list = applicationCaseMapper.selectApplicationCaseByExhibitionIdMake(id);
		
		PageInfo<ApplicationCase>  info = new  PageInfo<>(list);
		return info;
	}

	/**
	 * 展会添加案例
	 * 由于该表(zl_exhibition_case_map)删除的时候是delete操作，所以不用判断是否是虚拟的删除，但是要判断展会id和案例id是否存在，防止表单重复提交
	 */
	@Override
	public int saveExhibitionApp(ExhibitionCaseMap exhibitionCaseMap) {
		//查看要添加案例是否存在(状态正常)
		ApplicationCase applicationCase = applicationCaseMapper.findAppCaseById(exhibitionCaseMap.getCaseId());
		if(applicationCase==null  || applicationCase.getDel()!=1){
			throw  new CommonException(StatusCode.DATA_NOT_EXIST);
		}
		
		//查看该展会是否存在
		Exhibition exhibition = exhibitionMapper.selectByPrimaryKey(exhibitionCaseMap.getExhibitionId());
		if(exhibition == null  ||  exhibition.getDel() != 1){
			throw  new CommonException(StatusCode.DATA_NOT_EXIST);
		}
		//查看要添加的案例是属于该展会(因为没有伪删除状态，所以不用判断伪删除)
		ExhibitionCaseMap record = exhibitionCaseMapMapper.selectByCaseIdAndEid(exhibitionCaseMap);
		if(record != null){
			throw  new  CommonException(StatusCode.DATA_IS_EXIST);
		}
		//执行添加操作
		return  exhibitionCaseMapMapper.insertSelective(exhibitionCaseMap);
	}

	/**
	 * 删除展会案例，根据案例id和展会id
	 */
	@Override
	public int removeExhibitionAppByCaseIdAndEid(ExhibitionCaseMap  exhibitionCaseMap) {
		return exhibitionCaseMapMapper.deleteByCaseIdAndExhibitionId(exhibitionCaseMap);
	}

	

}
