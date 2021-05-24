package com.gig.springcloud.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.gig.springcloud.model.Coupon;
import com.gig.springcloud.model.Product;
import com.gig.springcloud.repos.CouponRepo;

@RestController
@RequestMapping("/couponapi")
public class CouponController {

	@Autowired
	CouponRepo repo;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@RequestMapping(value="/coupons", method= RequestMethod.POST)
	public Coupon create(@RequestBody Coupon coupon) {
		Product product = restTemplate.getForObject("http://localhost:8082/productapi/product/Alexa", Product.class);
		System.out.println("Successfully called product service"+product.toString());
		if("Alexa".equalsIgnoreCase(product.getName())) {
			coupon.setCode(coupon.getCode()+"Alexa");
		}
		return repo.save(coupon);
	}
	
	@RequestMapping(value="/coupons/{code}", method= RequestMethod.GET)
	public Coupon getCoupon(@PathVariable("code") String code) {
		return repo.findByCode(code);
	}
}
