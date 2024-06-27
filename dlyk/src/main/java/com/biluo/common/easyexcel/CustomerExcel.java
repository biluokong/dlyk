package com.biluo.common.easyexcel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class CustomerExcel {

    /**
     * Excel表头的字段如下：
     *
     * 所属人	所属活动	客户姓名	客户称呼	客户手机	客户微信	客户QQ
     * 客户邮箱	客户年龄	客户职业	客户年收入
     * 客户住址	是否贷款	客户产品	客户来源	客户描述	下次联系时间
     */

    @ExcelProperty(value = "所属人")
    private String ownerName;

    @ExcelProperty(value = "所属活动")
    private String activityName;

    @ExcelProperty(value = "客户姓名")
    private String fullName;

    @ExcelProperty(value = "客户称呼")
    private String appellationName;

    @ExcelProperty(value = "客户手机")
    private String phone;

    @ExcelProperty(value = "客户微信")
    private String weixin;

    @ExcelProperty(value = "客户QQ")
    private String qq;

    @ExcelProperty(value = "客户邮箱")
    private String email;

    @ExcelProperty(value = "客户年龄")
    private int age;

    @ExcelProperty(value = "客户职业")
    private String job;

    @ExcelProperty(value = "客户年收入")
    private BigDecimal yearIncome;

    @ExcelProperty(value = "客户住址")
    private String address;

    @ExcelProperty(value = "是否贷款")
    private String needLoadName;

    @ExcelProperty(value = "客户产品")
    private String productName;

    @ExcelProperty(value = "客户来源")
    private String sourceName;

    @ExcelProperty(value = "客户描述")
    private String description;

    @ExcelProperty(value = "下次联系时间")
    private Date nextContactTime;

}
