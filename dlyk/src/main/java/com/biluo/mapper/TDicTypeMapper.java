package com.biluo.mapper;

import com.biluo.model.dto.DictDto;
import com.biluo.model.po.TDicType;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
* @author 797799421
* @description 针对表【t_dic_type(字典类型表)】的数据库操作Mapper
* @createDate 2024-06-14 18:11:10
* @Entity com.biluo.model.po.TDicType
*/
@Mapper
public interface TDicTypeMapper {

    int deleteByPrimaryKey(Long id);

    int insert(TDicType record);

    int insertSelective(TDicType record);

    TDicType selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TDicType record);

    int updateByPrimaryKey(TDicType record);

	List<DictDto> selectAll();
}
