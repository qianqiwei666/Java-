package com.qianqiwei.server01.controller;

import com.qianqiwei.server01.service.impl.CommodityServiceImpl;
import com.qianqiwei.server01.vo.Shop;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebController {

    @Autowired
    private CommodityServiceImpl commodityServiceImpl;
    @GetMapping("/findShop")
    public ResponseEntity<Shop> index(@RequestParam("shop_id")String shop_id){
        return ResponseEntity.ok().body(commodityServiceImpl.findShop(shop_id));
    }


}
