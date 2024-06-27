package com.biluo.service.impl;

import com.biluo.mapper.TDicTypeMapper;
import com.biluo.model.dto.DictDto;
import com.biluo.service.DictService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DictServiceImpl implements DictService {
	private final TDicTypeMapper tDicTypeMapper;

	@Override
	public List<DictDto> loadAllDict() {
		return tDicTypeMapper.selectAll();
	}
}
