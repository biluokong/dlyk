package com.biluo.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ClueDictEnum {
	APPELLATION("称呼", "appellation"),
	NEED_LOAN("是否贷款", "needLoan"),
	INTENTION_STATE("意向状态", "intentionState"),
	INTENTION_PRODUCT("意向产品", "intentionProduct"),
	SOURCE("线索来源", "source"),
	CLUE_STATE("线索状态", "clueState"),
	NOTE_WAY("跟踪方式", "noteWay");

	public static ClueDictEnum getByName(String name) {
		for (ClueDictEnum clueDictEnum : ClueDictEnum.values()) {
			if (clueDictEnum.getName().equals(name)) {
				return clueDictEnum;
			}
		}
		return null;
	}

	private final String name;
	private final String code;

}
