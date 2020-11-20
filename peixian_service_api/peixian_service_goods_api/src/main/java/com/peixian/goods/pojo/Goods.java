package com.peixian.goods.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author TonyStark
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Goods implements Serializable {

    private Spu spu;

    private List<Sku> skuList;


}
