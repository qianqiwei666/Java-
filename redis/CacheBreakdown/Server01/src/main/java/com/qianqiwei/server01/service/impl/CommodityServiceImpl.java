package com.qianqiwei.server01.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qianqiwei.server01.pojo.Commodity;
import com.qianqiwei.server01.service.CommodityService;
import com.qianqiwei.server01.mapper.CommodityMapper;
import com.qianqiwei.server01.vo.Shop;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.client.codec.StringCodec;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author 15915
 * @description 针对表【commodity】的数据库操作Service实现
 * @createDate 2022-08-02 16:20:11
 */
@Service
public class CommodityServiceImpl extends ServiceImpl<CommodityMapper, Commodity>
        implements CommodityService {

    @Autowired
    private RedissonClient redissonClient;

    //缓存获取shop_id
    public Shop findShop(String shop_id) {
        String shop = (String) redissonClient.getMap("shops", new StringCodec()).get(shop_id);
        //如果缓存中有数据直接返回。
        if (shop != null) return JSON.parseObject(shop, Shop.class);
        //如果没有数据从数据库中获取(lock)
        RLock lock = redissonClient.getLock(shop_id + "_lock");
        //锁的时间1000毫秒起步,每次叠加500毫秒
        long lockTime = 1000;
        while (true){
            try {
                lock.tryLock(lockTime, lockTime, TimeUnit.MILLISECONDS);
                //时间叠加
                lockTime+=500;
                //双重判断
                String shop01 = (String) redissonClient.getMap("shops", new StringCodec()).get(shop_id);
                if (shop01 != null) return JSON.parseObject(shop01, Shop.class);
                //从数据库中获取
                Shop shop1 = getShop(shop_id);
                //设置到数据库中
                redissonClient.getMap("shops",new StringCodec()).put(shop_id, JSON.toJSON(shop1));
                System.out.println("find to MySQL");
                return shop1;
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                if (lock.isHeldByCurrentThread()) lock.unlock();
            }
        }
    }

    public Shop getShop(String shop_id){
        QueryWrapper<Commodity> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("cid",shop_id);
        Commodity one = getOne(queryWrapper);
        Shop shop=new Shop();
        shop.setName(one.getCname());
        shop.setPrice(one.getCrice());
        return shop;
    }
}




