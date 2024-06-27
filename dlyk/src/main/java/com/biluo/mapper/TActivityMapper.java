package com.biluo.mapper;

import com.biluo.annotation.DataScope;
import com.biluo.model.dto.ActivityDto;
import com.biluo.model.po.TActivity;
import com.biluo.model.query.ActivityQuery;
import com.biluo.model.query.BaseQuery;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
* @author 797799421
* @description 针对表【t_activity(市场活动表)】的数据库操作Mapper
* @createDate 2024-06-14 18:11:10
* @Entity com.biluo.model.po.TActivity
*/
@Mapper
public interface TActivityMapper {

    int deleteByPrimaryKey(Long id);

    int insert(TActivity record);

    int insertSelective(TActivity record);

    TActivity selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TActivity record);

    int updateByPrimaryKey(TActivity record);

    List<TActivity> listByUserId(Long userId);

	List<TActivity> listByUserIds(Long[] userIds);

	@DataScope(tableAlias = "ta", tableField = "owner_id")
	List<ActivityDto> list(ActivityQuery query);

	ActivityDto getActivityDetail(Long id);

	List<TActivity> getOngoingActivityList();

	int count();
}
