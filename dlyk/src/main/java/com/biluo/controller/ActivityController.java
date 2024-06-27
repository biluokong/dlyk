package com.biluo.controller;

import com.biluo.model.po.TActivity;
import com.biluo.model.po.TActivityRemark;
import com.biluo.model.query.ActivityQuery;
import com.biluo.model.result.R;
import com.biluo.service.ActivityService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static com.biluo.utils.Constants.DEFAULT_PAGE_SIZE;

@RestController
@RequestMapping("activity")
@RequiredArgsConstructor
public class ActivityController {
	private final ActivityService activityService;

	/**
	 * 根据分页条件查询活动列表
	 */
	@PostMapping("list")
	@PreAuthorize("hasAuthority('activity:list')")
	public R list(@RequestParam(defaultValue = "1") Integer pageNum,
				  @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) Integer pageSize,
				  @RequestBody ActivityQuery query) {
		return R.OK(activityService.list(pageNum, pageSize, query));
	}

	/**
	 * 新增活动
	 */
	@PostMapping
	@PreAuthorize("hasAuthority('activity:add')")
	public R addActivity(@RequestBody TActivity activity) {
		return activityService.addActivity(activity) ? R.OK() : R.FAIL();
	}

	/**
	 * 根据id获取活动信息
	 */
	@GetMapping("{id}")
	@PreAuthorize("hasAuthority('activity:view')")
	public R getActivityInfo(@PathVariable Long id) {
		return R.OK(activityService.getActivityInfo(id));
	}

	/**
	 * 修改活动信息
	 */
	@PutMapping
	@PreAuthorize("hasAuthority('activity:edit')")
	public R updateActivity(@RequestBody TActivity activity) {
		return activityService.updateActivity(activity) ? R.OK() : R.FAIL();
	}

	/**
	 * 获取活动详情
	 */
	@GetMapping("detail/{id}")
	@PreAuthorize("hasAuthority('activity:view')")
	public R getActivityDetail(@PathVariable Long id) {
		return R.OK(activityService.getActivityDetail(id));
	}

	/**
	 * 获取正在进行的活动列表
	 */
	@GetMapping("ongoing/list")
	public R getOngoingActivityList() {
		return R.OK(activityService.getOngoingActivityList());
	}

	/**
	 * 添加活动备注
	 */
	@PostMapping("remark")
	public R addRemark(@RequestBody TActivityRemark remark) {
		return activityService.addRemark(remark) ? R.OK() : R.FAIL();
	}

	/**
	 * 获取活动备注列表
	 */
	@GetMapping("remarks/{activityId}")
	public R getRemarks(@PathVariable Long activityId,
						@RequestParam(defaultValue = "1") Integer pageNum,
						@RequestParam(defaultValue = DEFAULT_PAGE_SIZE) Integer pageSize) {
		return R.OK(activityService.getRemarkList(activityId, pageNum, pageSize));
	}

	/**
	 * 根据备注id删除活动备注（逻辑删除）
	 */
	@DeleteMapping("remark/{id}")
	public R deleteRemark(@PathVariable Integer id) {
		return activityService.deleteRemark(id) ? R.OK() : R.FAIL();
	}

	/**
	 * 修改活动备注信息
	 */
	@PutMapping("remark")
	public R updateRemark(@RequestBody TActivityRemark remark) {
		return activityService.updateRemark(remark) ? R.OK() : R.FAIL();
	}
}
