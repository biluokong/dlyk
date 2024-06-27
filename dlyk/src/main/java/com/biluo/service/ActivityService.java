package com.biluo.service;

import com.biluo.model.dto.ActivityDto;
import com.biluo.model.po.TActivity;
import com.biluo.model.po.TActivityRemark;
import com.biluo.model.query.ActivityQuery;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface ActivityService {
	PageInfo<ActivityDto> list(Integer pageNum, Integer pageSize, ActivityQuery query);

	boolean addActivity(TActivity activity);

	TActivity getActivityInfo(Long id);

	boolean updateActivity(TActivity activity);

	ActivityDto getActivityDetail(Long id);

	boolean addRemark(TActivityRemark remark);

	PageInfo<TActivityRemark> getRemarkList(Long activityId, Integer pageNum, Integer pageSize);

	boolean deleteRemark(Integer id);

	boolean updateRemark(TActivityRemark remark);

	List<TActivity> getOngoingActivityList();
}
