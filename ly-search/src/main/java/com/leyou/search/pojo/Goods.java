package com.leyou.search.pojo;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;
import java.util.Map;
import java.util.Set;

@Document(indexName = "goods",type = "docs",shards = 1,replicas = 0)
@Data
public class Goods {
    @Id
    private Long id; //spuId
    @Field(type = FieldType.Text,analyzer = "ik_max_word")
    private String all;//所有需要被搜索的信息，包含标题、分类、甚至品牌
    @Field(type = FieldType.Keyword,index=false)
    private String subTittle; //卖点
    private Long brandId;
    private Long cid1;
    private Long cid2;
    private Long cid3;
    private Date createTime;
    private Set<Long> price; //sku的价格
    @Field(type = FieldType.Keyword,index=false)
    private String skus; //sku信息的json结构
    private Map<String,Object> specs; //可搜索的规格参数，key参数名，值是参数值
}
