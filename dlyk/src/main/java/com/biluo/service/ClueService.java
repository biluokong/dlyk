package com.biluo.service;

import com.biluo.model.dto.ClueDto;
import com.biluo.model.dto.ClueRemarkDto;
import com.biluo.model.po.TClue;
import com.biluo.model.po.TClueRemark;
import com.github.pagehelper.PageInfo;
import org.springframework.web.multipart.MultipartFile;

public interface ClueService {
	PageInfo<ClueDto> list(Integer pageNum, Integer pageSize);

	void upload(MultipartFile file);

	boolean checkPhone(String phone);

	boolean addClue(TClue clue);

	ClueDto getClueDetail(Integer id);

	boolean updateClue(TClue clue);

	boolean addClueRemark(TClueRemark remark);

	PageInfo<ClueRemarkDto> getClueRemarks(Integer clueId, Integer pageNum, Integer pageSize);

	boolean deleteClue(Integer id);
}
