package com.sunveee.xiucailv.web.context.asset.domain.asset.entity;

import lombok.*;

import java.util.List;

/**
 * AssetItem
 *
 * @author SunVeee
 * @date 2022/12/3 23:12
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AssetItem {
    private String code;
    private String name;
    private AssetItemType type;
    private List<String> labels;
    private String description;
    private String userid;


    @Getter
    public enum AssetItemType {
        DEBT("负债"),
        INVEST("投资"),
        CASH("现金"),

        ;
        private String name;

        AssetItemType(String name) {
            this.name = name;
        }
    }

}
