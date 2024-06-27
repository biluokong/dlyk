package com.biluo.common.easyexcel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.util.ListUtils;
import com.biluo.annotation.InsertAutoFill;
import com.biluo.mapper.TClueMapper;
import com.biluo.model.po.TClue;
import com.biluo.model.po.TUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
public class ClueListener implements ReadListener<TClue> {
	/**
	 * 每隔100条存储数据库，实际使用中可以300条，然后清理list ，方便内存回收
	 */
	public static final int BATCH_COUNT = 100;
	private List<TClue> cacheDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
	private final TClueMapper tClueMapper;

	// 每条数据解析都会来调用
	@Override
	@InsertAutoFill
	public void invoke(TClue clue, AnalysisContext analysisContext) {
		// 手动设置创建人、创建时间等信息
		TUser user = (TUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		clue.setCreateTime(new Date());
		clue.setEditTime(new Date());
		clue.setCreateBy(user.getId());
		clue.setEditBy(user.getId());

		cacheDataList.add(clue);
		if (cacheDataList.size() >= BATCH_COUNT) {
			saveData();
			cacheDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
		}
	}

	private void saveData() {
		tClueMapper.insertBatch(cacheDataList);
	}

	// 读取完成调用
	@Override
	public void doAfterAllAnalysed(AnalysisContext analysisContext) {
		saveData();
	}
}