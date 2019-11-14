package com.leyou.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum ExceptionEnum {
    PRICE_CANNOT_BE_NULL(HttpStatus.BAD_REQUEST,"价格不能为空"),
    CATEGORY_NOT_FOUND(HttpStatus.NOT_FOUND,"商品分类没有查到"),
    BRAND_NOT_FOUND(HttpStatus.NOT_FOUND, "品牌没查到"),
    BRAND_SAVE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "新增品牌失败"),
    UPLOAD_ERROR(500, "文件上传失败"), INVALID_FILE_TYPE(400, "无效文件上传"),
    SPEC_GROUP_NOT_FOUND(404, "商品规格组没有查到"),
    SPEC_PARAM_NOT_FOUND(404, "商品规格参数没查到"),
    GOODS_NOT_FOUND(404, "商品未查到"),
    GOODS_SAVE_ERROR(500, "新增商品失败"),
    GOODS_DETAIL_NOT_FOUND(404, "商品详情不存在"),
    GOODS_SKU_NOT_FOUND(404, "商品的sku不存在"),
    GOODS_STOCK_NOT_FOUND(404, "商品库存不存在"),
    GOODS_UPDATE_ERROR(500, "更新商品失败"),
    GOODS_ID_CANNOT_BE_NULL(400, "商品id不能为空"),
    INVALID_USER_DATA_TYPE(400, "用户的数据类型不正确"),
    INVALID_VERIFY_CODE(400, "无效验证码"),
    INVALID_USERNAME_PASSWORD(400, "无效的用户名和密码"),
    CREATE_TOKEN_ERROR(500, "生成token失败"),
    UNAUTHORIZED(403, "未授权"),
    CART_NOT_FOUND(404, "购物车为空"),
    CREATE_ORDER_ERROR(500, "创建订单失败"),
    STOCK_NOT_ENOUGH(500, "库存不足"),
    ORDER_NOT_FOUND(404, "订单不存在"),
    ORDER_DETAIL_NOT_FOUND(404, "订单详情不存在"),
    ORDER_STATUS_NOT_FOUND(404, "订单状态不存在"),
    WX_PAY_CONNECT_FAIL(500, "微信失败"),
    ORDER_STATUS_ERROR(400, "订单状态不正确"),
    INVALID_SIGN_ERROR(400, "无效的签名异常"),
    INVALID_ORDER_PARAM(400, "订单参数异常"),
    UPDATE_ORDER_STATUS_ERROR(500, "更新订单状态失败");
    ExceptionEnum(HttpStatus httpStatus, String msg) {
        this.httpStatus = httpStatus;
        this.msg = msg;
        this.code = httpStatus.value();
    }
    ExceptionEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
        this.httpStatus = HttpStatus.valueOf(code);
    }
    private int code;
    private HttpStatus httpStatus;
    private String msg;
}
