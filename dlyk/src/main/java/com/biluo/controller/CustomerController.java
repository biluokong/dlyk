package com.biluo.controller;

import com.alibaba.excel.EasyExcel;
import com.biluo.common.easyexcel.CustomerExcel;
import com.biluo.model.po.TCustomer;
import com.biluo.model.result.R;
import com.biluo.service.CustomerService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

import static com.biluo.utils.Constants.DEFAULT_PAGE_SIZE;

@RestController
@RequestMapping("customer")
@RequiredArgsConstructor
public class CustomerController {
	private final CustomerService customerService;

	/**
	 * 新增客户
	 */
	@PostMapping
	public R addCustomer(@RequestBody TCustomer customer) {
		return customerService.addCustomer(customer) ? R.OK() : R.FAIL();
	}

	/**
	 * 根据分页参数获取客户列表
	 */
	@GetMapping("list")
	public R getCustomerList(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
							 @RequestParam(value = "pageSize", defaultValue = DEFAULT_PAGE_SIZE) Integer pageSize) {
		return R.OK(customerService.getCustomerList(pageNum, pageSize));
	}

	/**
	 * 导出所有数据到excel
	 */
	@GetMapping("export")
	public void export(HttpServletResponse response,
					   @RequestParam(required = false) Integer[] ids) throws IOException {
		response.setHeader("Content-Type", MediaType.APPLICATION_OCTET_STREAM_VALUE);
		response.setHeader("Content-disposition", "attachment;filename=customer.xlsx");

		List<CustomerExcel> data = customerService.getCustomerExcelData(ids);

		EasyExcel.write(response.getOutputStream(), CustomerExcel.class)
				.sheet()
				.doWrite(data);
	}
}
