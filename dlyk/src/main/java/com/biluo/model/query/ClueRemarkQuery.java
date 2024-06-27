package com.biluo.model.query;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ClueRemarkQuery extends BaseQuery {
	private Integer clueId;
}
