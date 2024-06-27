package com.biluo.common.easyexcel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.biluo.DlykApllication;
import com.biluo.common.enums.ClueDictEnum;
import com.biluo.model.po.TDicValue;
import com.biluo.model.po.TProduct;
import org.springframework.util.ObjectUtils;

import java.util.List;

/**
 * 实体类TClue的用于easy-excel的转换器
 */
public class ClueConverter implements Converter<Integer> {
	/**
	 * 把Excel的值转换成实体属性
	 */
	@Override
	public Integer convertToJavaData(ReadCellData<?> cellData, ExcelContentProperty contentProperty,
									GlobalConfiguration globalConfiguration) {
		// 获取表头名称，根据表头名称获取枚举值
		ExcelProperty annotation = contentProperty.getField().getDeclaredAnnotation(ExcelProperty.class);
		String headName = annotation.value()[0];
		ClueDictEnum clueDictEnum = ClueDictEnum.getByName(headName);
		if (clueDictEnum == null) return -1;

		// 根据枚举值获取缓存的值列表，在从值列表中获取值id
		String stringValue = cellData.getStringValue();
		List<Object> valueList = DlykApllication.dictCacheMap.get(clueDictEnum.getCode());

		// 找到valueList中某item的value值为stringValue的item的id
		if (ObjectUtils.isEmpty(valueList)) return -1;
		if (valueList.get(0) instanceof TDicValue) {
			for (Object obj : valueList) {
				TDicValue dicValue = (TDicValue) obj;
				if (stringValue.equals(dicValue.getTypeValue())) {
					return dicValue.getId();
				}
			}
		} else if (valueList.get(0) instanceof TProduct) {
			for (Object obj : valueList) {
				TProduct product = (TProduct) obj;
				if (stringValue.equals(product.getName())) {
					return product.getId();
				}
			}
		}
		return -1;
	}
	/**
	 * 把实体属性的值转换成Excel的值
	 */

}
