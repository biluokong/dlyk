package com.biluo.service.impl;

import com.biluo.annotation.InsertAutoFill;
import com.biluo.annotation.UpdateAutoFill;
import com.biluo.mapper.TActivityMapper;
import com.biluo.mapper.TActivityRemarkMapper;
import com.biluo.model.dto.ActivityDto;
import com.biluo.model.po.TActivity;
import com.biluo.model.po.TActivityRemark;
import com.biluo.model.query.ActivityQuery;
import com.biluo.model.query.ActivityRemarkQuery;
import com.biluo.service.ActivityService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ActivityServiceImpl implements ActivityService {
	private final TActivityMapper tActivityMapper;
	private final TActivityRemarkMapper tActivityRemarkMapper;

	@Override
	public PageInfo<ActivityDto> list(Integer pageNum, Integer pageSize, ActivityQuery query) {
		PageHelper.startPage(pageNum, pageSize);
		List<ActivityDto> list = tActivityMapper.list(query);
		return new PageInfo<>(list);
	}

	@Override
	@InsertAutoFill
	@Transactional(rollbackFor = Exception.class)
	public boolean addActivity(TActivity activity) {
		int count = tActivityMapper.insertSelective(activity);
		return count > 0;
	}

	@Override
	public TActivity getActivityInfo(Long id) {
		return tActivityMapper.selectByPrimaryKey(id);
	}

	@Override
	@UpdateAutoFill
	@Transactional(rollbackFor = Exception.class)
	public boolean updateActivity(TActivity activity) {
		int count = tActivityMapper.updateByPrimaryKeySelective(activity);
		return count > 0;
	}

	@Override
	public ActivityDto getActivityDetail(Long id) {
		return tActivityMapper.getActivityDetail(id);
	}

	@Override
	@InsertAutoFill
	@Transactional(rollbackFor = Exception.class)
	public boolean addRemark(TActivityRemark remark) {
		int count = tActivityRemarkMapper.insertSelective(remark);
		return count > 0;
	}

	@Override
	public PageInfo<TActivityRemark> getRemarkList(Long activityId, Integer pageNum, Integer pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		ActivityRemarkQuery query = new ActivityRemarkQuery();
		query.setActivityId(activityId);
		List<TActivityRemark> list = tActivityRemarkMapper.getRemarkList(query);
		return new PageInfo<>(list);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean deleteRemark(Integer id) {
		// 逻辑删除
		TActivityRemark remark = new TActivityRemark()
				.setId(id)
				.setDeleted(1);
		int count = tActivityRemarkMapper.updateByPrimaryKeySelective(remark);
		return count > 0;
	}

	@Override
	@UpdateAutoFill
	@Transactional(rollbackFor = Exception.class)
	public boolean updateRemark(TActivityRemark remark) {
		int count = tActivityRemarkMapper.updateByPrimaryKeySelective(remark);
		return count > 0;
	}

	@Override
	public List<TActivity> getOngoingActivityList() {
		return tActivityMapper.getOngoingActivityList();
	}
}
