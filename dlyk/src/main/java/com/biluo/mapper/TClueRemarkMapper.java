package com.biluo.mapper;

import com.biluo.model.dto.ClueRemarkDto;
import com.biluo.model.po.TClueRemark;
import com.biluo.model.query.ClueRemarkQuery;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
* @author 797799421
* @description 针对表【t_clue_remark(线索跟踪记录表)】的数据库操作Mapper
* @createDate 2024-06-14 18:11:10
* @Entity com.biluo.model.po.TClueRemark
*/
@Mapper
public interface TClueRemarkMapper {

    int deleteByPrimaryKey(Long id);

    int insert(TClueRemark record);

    int insertSelective(TClueRemark record);

    TClueRemark selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TClueRemark record);

    int updateByPrimaryKey(TClueRemark record);

	List<ClueRemarkDto> listByClueId(ClueRemarkQuery query);
}
