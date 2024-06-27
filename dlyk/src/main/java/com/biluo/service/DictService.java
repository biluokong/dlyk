package com.biluo.service;

import com.biluo.model.dto.DictDto;
import com.biluo.model.po.TDicType;
import com.biluo.model.po.TDicValue;

import java.util.List;

public interface DictService {
	List<DictDto> loadAllDict();
}
