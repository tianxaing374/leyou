package com.leyou.cart.service;

import com.leyou.auth.entity.UserInfo;
import com.leyou.cart.interceptor.UserInterceptor;
import com.leyou.cart.pojo.Cart;
import com.leyou.common.enums.ExceptionEnum;
import com.leyou.common.exception.LyException;
import com.leyou.common.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    private static final String KEY_PREFIX = "cart:user:id:";

    public void addCart(Cart cart) {
        String key = getUserKey();
        String hashKey = cart.getSkuId().toString();
        BoundHashOperations<String, Object, Object> operation = redisTemplate.boundHashOps(key);
        if(operation.hasKey(hashKey)){
            String json = operation.get(hashKey).toString();
            Cart cacheCart = JsonUtils.parse(json, Cart.class);
            cacheCart.setNum(cacheCart.getNum()+cart.getNum());
            operation.put(hashKey,JsonUtils.serialize(cacheCart));
        }else {
            operation.put(hashKey,JsonUtils.serialize(cart));
        }
    }

    public List<Cart> queryCartList() {
        //获取登录用户
        String key = getUserKey();
        if(!redisTemplate.hasKey(key)){
            throw new LyException(ExceptionEnum.CART_NOT_FOUND);
        }
        BoundHashOperations<String, Object, Object> operation = redisTemplate.boundHashOps(key);
        List<Cart> carts = operation.values().stream().map(o -> JsonUtils.parse(o.toString(), Cart.class)).collect(Collectors.toList());
        return carts;
    }

    public void updateCartNum(Long skuId, Integer num) {
        //获取登录用户
        String key = getUserKey();
        BoundHashOperations<String, Object, Object> operations = redisTemplate.boundHashOps(key);
        String hashKey = skuId.toString();
        if(!operations.hasKey(hashKey)){
            throw new LyException(ExceptionEnum.CART_NOT_FOUND);
        }
        Cart cart = JsonUtils.parse(operations.get(hashKey).toString(), Cart.class);
        cart.setNum(num);
        operations.put(hashKey,JsonUtils.serialize(cart));
    }

    public void deleteCart(Long skuId) {
        //获取登录用户
        String key = getUserKey();
        redisTemplate.opsForHash().delete(key,skuId.toString());
    }

    private String getUserKey() {
        //获取登录用户
        UserInfo user = UserInterceptor.getUserInfo();
        return KEY_PREFIX+user.getId();
    }

}
