package com.biluo.model.query;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ActivityRemarkQuery extends BaseQuery {
	private Long activityId;
}
