package com.biluo.mapper;

import com.biluo.annotation.DataScope;
import com.biluo.model.po.TActivityRemark;
import com.biluo.model.query.ActivityRemarkQuery;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
* @author 797799421
* @description 针对表【t_activity_remark(市场活动备注表)】的数据库操作Mapper
* @createDate 2024-06-14 18:11:10
* @Entity com.biluo.model.po.TActivityRemark
*/
@Mapper
public interface TActivityRemarkMapper {

    int deleteByPrimaryKey(Long id);

    int insert(TActivityRemark record);

    int insertSelective(TActivityRemark record);

    TActivityRemark selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TActivityRemark record);

    int updateByPrimaryKey(TActivityRemark record);

    @DataScope(tableAlias = "tar", tableField = "create_by")
    List<TActivityRemark> getRemarkList(ActivityRemarkQuery query);
}
