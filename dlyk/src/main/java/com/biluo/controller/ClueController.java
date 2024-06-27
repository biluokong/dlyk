package com.biluo.controller;

import com.biluo.model.po.TClue;
import com.biluo.model.po.TClueRemark;
import com.biluo.model.result.R;
import com.biluo.service.ClueService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static com.biluo.utils.Constants.DEFAULT_PAGE_SIZE;

@RestController
@RequestMapping("clue")
@RequiredArgsConstructor
public class ClueController {
	private final ClueService clueService;

	/**
	 * 根据分页参数获取线索列表
	 */
	@GetMapping("list")
	@PreAuthorize("hasAuthority('clue:list')")
	public R list(Integer pageNum, Integer pageSize) {
		return R.OK(clueService.list(pageNum, pageSize));
	}

	/**
	 * 上传excel表格
	 */
	@PostMapping("upload")
	@PreAuthorize("hasAuthority('clue:import')")
	public R upload(MultipartFile file) {
		clueService.upload(file);
		return R.OK();
	}

	/**
	 * 检查手机号是否已经录入，如果已经录入则返回false
	 */
	@GetMapping("check/phone/{phone}")
	public R checkPhone(@PathVariable String phone) {
		return R.OK(clueService.checkPhone(phone));
	}

	/**
	 * 录入线索
	 */
	@PostMapping
	@PreAuthorize("hasAuthority('clue:add')")
	public R addClue(@RequestBody TClue clue) {
		return clueService.addClue(clue) ? R.OK() : R.FAIL();
	}

	/**
	 * 获取线索详情
	 */
	@GetMapping("{id}")
	@PreAuthorize("hasAuthority('clue:view')")
	public R getClueDetail(@PathVariable Integer id) {
		return R.OK(clueService.getClueDetail(id));
	}

	/**
	 * 修改线索信息
	 */
	@PutMapping
	@PreAuthorize("hasAuthority('clue:edit')")
	public R updateClue(@RequestBody TClue clue) {
		return clueService.updateClue(clue) ? R.OK() : R.FAIL();
	}

	/**
	 * 删除线索
	 */
	@DeleteMapping("{id}")
	@PreAuthorize("hasAuthority('clue:delete')")
	public R deleteClue(@PathVariable Integer id) {
		return clueService.deleteClue(id) ? R.OK() : R.FAIL();
	}

	/**
	 * 新增线索备注
	 */
	@PostMapping("remark")
	public R addClueRemark(@RequestBody TClueRemark remark) {
		return clueService.addClueRemark(remark) ? R.OK() : R.FAIL();
	}

	/**
	 * 根据线索id获取该线索的备注列表
	 */
	@GetMapping("remarks/{clueId}")
	public R getClueRemarks(@PathVariable Integer clueId,
							@RequestParam(defaultValue = "1") Integer pageNum,
							@RequestParam(defaultValue = DEFAULT_PAGE_SIZE) Integer pageSize) {
		return R.OK(clueService.getClueRemarks(clueId, pageNum, pageSize));
	}
}
