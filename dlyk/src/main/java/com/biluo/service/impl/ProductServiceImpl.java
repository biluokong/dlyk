package com.biluo.service.impl;

import com.biluo.mapper.TProductMapper;
import com.biluo.model.po.TProduct;
import com.biluo.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
	private final TProductMapper tProductMapper;

	@Override
	public List<TProduct> getAllOnSaleProduct() {
		return tProductMapper.selectAllOnSaleProduct();
	}
}
