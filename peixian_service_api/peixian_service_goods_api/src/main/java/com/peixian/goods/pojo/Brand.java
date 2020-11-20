package com.peixian.goods.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author gzj
 * @description
 * @date 2020/11/5
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "tb_brand")
public class Brand implements Serializable {

    @Id
    private Integer id;
    //品牌id

    private String name;
    //品牌名称

    private String image;
    //品牌图片地址

    private String letter;
    //品牌的首字母

    private Integer seq;
    //排序

}
