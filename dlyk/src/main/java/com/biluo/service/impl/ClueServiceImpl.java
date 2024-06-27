package com.biluo.service.impl;

import com.alibaba.excel.EasyExcel;
import com.biluo.annotation.InsertAutoFill;
import com.biluo.annotation.UpdateAutoFill;
import com.biluo.common.easyexcel.ClueListener;
import com.biluo.mapper.TClueMapper;
import com.biluo.mapper.TClueRemarkMapper;
import com.biluo.model.dto.ClueDto;
import com.biluo.model.dto.ClueRemarkDto;
import com.biluo.model.po.TClue;
import com.biluo.model.po.TClueRemark;
import com.biluo.model.query.BaseQuery;
import com.biluo.model.query.ClueRemarkQuery;
import com.biluo.service.ClueService;
import com.biluo.utils.GlobalException;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ClueServiceImpl implements ClueService {
	private final TClueMapper tClueMapper;
	private final TClueRemarkMapper tClueRemarkMapper;

	@Override
	public PageInfo<ClueDto> list(Integer pageNum, Integer pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<ClueDto> list = tClueMapper.list(new BaseQuery());
		return new PageInfo<>(list);
	}

	@Override
	public void upload(MultipartFile file) {
		try {
			EasyExcel.read(file.getInputStream(), TClue.class, new ClueListener(tClueMapper))
					.sheet()
					.doRead();
		} catch (IOException e) {
			throw new GlobalException("上传失败");
		}
	}

	@Override
	public boolean checkPhone(String phone) {
		int count = tClueMapper.countByPhone(phone);
		return count <= 0;
	}

	@Override
	@InsertAutoFill
	@Transactional(rollbackFor = Exception.class)
	public boolean addClue(TClue clue) {
		int count = tClueMapper.insertSelective(clue);
		return count > 0;
	}

	@Override
	public ClueDto getClueDetail(Integer id) {
		return tClueMapper.getClueDetail(id);
	}

	@Override
	@UpdateAutoFill
	@Transactional(rollbackFor = Exception.class)
	public boolean updateClue(TClue clue) {
		int count = tClueMapper.updateByPrimaryKeySelective(clue);
		return count > 0;
	}

	@Override
	@InsertAutoFill
	@Transactional(rollbackFor = Exception.class)
	public boolean addClueRemark(TClueRemark remark) {
		int count = tClueRemarkMapper.insertSelective(remark);
		return count > 0;
	}

	@Override
	public PageInfo<ClueRemarkDto> getClueRemarks(Integer clueId, Integer pageNum, Integer pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		ClueRemarkQuery query = new ClueRemarkQuery();
		query.setClueId(clueId);
		List<ClueRemarkDto> list = tClueRemarkMapper.listByClueId(query);
		return new PageInfo<>(list);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean deleteClue(Integer id) {
		int count = tClueMapper.deleteByPrimaryKey(Long.valueOf(id));
		return count > 0;
	}
}
