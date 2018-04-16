package com.zlwon.server.service.impl;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zlwon.constant.StatusCode;
import com.zlwon.dto.exhibition.ExhibitionApplicationCaseDto;
import com.zlwon.exception.CommonException;
import com.zlwon.rdb.dao.ApplicationCaseMapper;
import com.zlwon.rdb.dao.ExhibitionCaseMapMapper;
import com.zlwon.rdb.dao.ExhibitionCaseMapper;
import com.zlwon.rdb.dao.ExhibitionMapper;
import com.zlwon.rdb.entity.ApplicationCase;
import com.zlwon.rdb.entity.Exhibition;
import com.zlwon.rdb.entity.ExhibitionCase;
import com.zlwon.rdb.entity.ExhibitionCaseMap;
import com.zlwon.server.config.UploadConfig;
import com.zlwon.server.config.WxApplicationConfig;
import com.zlwon.server.service.ExhibitionService;
import com.zlwon.server.service.RedisService;
import com.zlwon.utils.QRCodeUtil;
import com.zlwon.vo.customer.EngineerVo;
import com.zlwon.vo.exhibitionCaseMap.ExhibitionCaseMapVo;

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
	@Autowired
    private  WxApplicationConfig  applicationConfig;
	@Autowired
	private  UploadConfig   uploadConfig;
	@Autowired
	private  RedisService   redisService;
	@Value("${wx.token.redis.key}")
	private  String   ACCESS_TOKEN;
	
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
	 * 展会案例修改工程师，如果展会案例已关联工程师，需要把之前的标记为删除，如果之前关联的工程师id和该id一样，也是执行删除
	 * @param aid 案例id
	 * @param eid 展会id
	 * @param cid 工程师(知料师)id
	 * @return
	 */
	@Transactional
	@Override
	public int alterExhibitionApplicationCase(Integer  aid,Integer  eid,Integer  cid) {
		ExhibitionCaseMap exhibitionCaseMap = new  ExhibitionCaseMap();
		//查看该案例是否已属于该展会
		exhibitionCaseMap.setCaseId(aid);
		exhibitionCaseMap.setExhibitionId(eid);
		ExhibitionCaseMap map = exhibitionCaseMapMapper.selectByCaseIdAndEid(exhibitionCaseMap);
		//展会案例关联不存在
		if(map == null){
			throw  new  CommonException(StatusCode.DATA_NOT_EXIST);
		}else {
			//判断当前传入的工程师id和展会案例之前关联的工程师id是否一样，一样就删除，不一样需要把之前的删除，传入的工程师id添加
			ExhibitionCaseMap record = exhibitionCaseMapMapper.selectExhibitionCaseMapByAidAndEidMake(aid,eid);
			if(record.getExhibitionCaseId() != null){
				//通过exhibition_case_id去ExhibitionCase表中查看工程师id是否一样
				ExhibitionCase exhibitionCase = exhibitionCaseMapper.selectByPrimaryKey(record.getExhibitionCaseId());
				if(exhibitionCase.getUid() == cid){
					//执行删除标记
					exhibitionCase.setDel(-1);
					exhibitionCaseMapper.updateByPrimaryKeySelective(exhibitionCase);
					record.setExhibitionCaseId(null);
					return  exhibitionCaseMapMapper.updateByPrimaryKey(record);
				}else {
					//删除查询出来的数据，然后添加传递工程师id
					exhibitionCase.setDel(-1);
					exhibitionCaseMapper.updateByPrimaryKeySelective(exhibitionCase);
					//执行添加
					ExhibitionCase re = new  ExhibitionCase();
					re.setCid(aid);
					re.setDel(1);
					re.setUid(cid);
					exhibitionCaseMapper.insert(re);
					//更新展会案例表的展会案例id(其实就是工程和案例关联表的id)
					record.setExhibitionCaseId(re.getId());
					return  exhibitionCaseMapMapper.updateByPrimaryKeySelective(record);
				}
			}else {
				ExhibitionCase exhibitionCase = new  ExhibitionCase();
				exhibitionCase.setCid(aid);
				exhibitionCase.setDel(1);
				exhibitionCase.setUid(cid);
				//执行添加关联工程师
				exhibitionCaseMapper.insert(exhibitionCase);
				//更新展会案例表的展会案例id(其实就是工程和案例关联表的id)
				record.setExhibitionCaseId(exhibitionCase.getId());
				return  exhibitionCaseMapMapper.updateByPrimaryKeySelective(record);
			}
			
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
	 * 根据展会id，得到展会所有正常的案例(只显示已关联的)
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
	 * 根据展会id，得到展会下所有案例(案例都显示，已关联的有标记字段),模糊查询案例标题，筛选（材料生产商，应用行业，应用市场）
	 * @param pageIndex
	 * @param pageSize
	 * @param dto
	 * 			id 展会id，必传
	 * 			key 模糊查询案例标题，以下都是可选
	 * 			mid 材料生产商id
	 * 			industryId 应用行业id
	 * 			marketId  用户市场id
	 * @return
	 */
	@Override
	public PageInfo findAllExhibitionAppDetailsByIdMake(Integer pageIndex, Integer pageSize, ExhibitionApplicationCaseDto  dto) {
		//查看该展会是否存在
		Exhibition exhibition = exhibitionMapper.selectByPrimaryKey(dto.getId());
		if(exhibition == null  ||  exhibition.getDel() != 1){
			throw  new CommonException(StatusCode.DATA_NOT_EXIST);
		}
		
		PageHelper.startPage(pageIndex, pageSize);
		
		List<ExhibitionCaseMapVo>  list = applicationCaseMapper.selectApplicationCaseDetailsByExhibitionIdMake(dto);
		
		PageInfo<ExhibitionCaseMapVo>  info = new  PageInfo<>(list);
		return  info;
	}

	/**
	 * 展会添加案例
	 * 由于该表(zl_exhibition_case_map)删除的时候是delete操作，所以不用判断是否是虚拟的删除，但是要判断展会id和案例id是否存在，防止表单重复提交
	 */
	@Override
	@Transactional
	public int saveExhibitionApp(ExhibitionCaseMap exhibitionCaseMap,HttpServletRequest  request) {
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
		exhibitionCaseMapMapper.insertSelective(exhibitionCaseMap);
		//得到案例二维码，并更新案例的二维码路径
		String token = redisService.get(ACCESS_TOKEN);
		String codePath = null;
		codePath = QRCodeUtil.getWxacode(request, token, applicationConfig.getPath()+"?id="+exhibitionCaseMap.getCaseId()+"&cid="+exhibitionCaseMap.getExhibitionId(), exhibitionCaseMap.getCaseId()+"", uploadConfig);
		applicationCase.setCodePath(codePath);
		return  (int) applicationCaseMapper.updateByPrimaryKeySelective(applicationCase);
	}

	/**
	 * 删除展会案例，根据案例id和展会id
	 */
	@Override
	public int removeExhibitionAppByCaseIdAndEid(ExhibitionCaseMap  exhibitionCaseMap) {
		return exhibitionCaseMapMapper.deleteByCaseIdAndExhibitionId(exhibitionCaseMap);
	}

	
	/**
	 * 得到所有工程师(而且通过展会id和案例id，标识已关联的工程师)，后端查看展会案例关联的工程师
	 * @param aid 案例id
	 * @param eid 展会id
	 * @return
	 */
	public PageInfo findAllEngineer(Integer pageIndex,Integer pageSize,Integer aid, Integer eid) {
		PageHelper.startPage(pageIndex, pageSize);
		List<EngineerVo>  list = exhibitionCaseMapMapper.selectAllEngineer(aid,eid);
		return new  PageInfo<>(list);
	}

	

	

}
