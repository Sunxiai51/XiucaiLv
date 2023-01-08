package com.sunveee.xiucailv.web.context.asset.domain.asset.repository.po;

import com.sunveee.framework.common.utils.bean.BeanTransferUtils;
import com.sunveee.framework.common.utils.json.JSONUtils;
import com.sunveee.xiucailv.web.common.jpa.BaseJpaEntity;
import com.sunveee.xiucailv.web.context.asset.domain.asset.entity.AssetItem;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

/**
 * AssetItemPO
 *
 * @author SunVeee
 * @date 2022/12/3 23:14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "asset_item", indexes = {@Index(name = "uk_code", columnList = "code", unique = true)
})
public class AssetItemPO extends BaseJpaEntity {
    @Column(columnDefinition = "varchar(32) COMMENT '资产项编号'", nullable = false, updatable = false)
    private String code;
    @Column(columnDefinition = "varchar(32) COMMENT '资产项名称'", nullable = false)
    private String name;
    @Column(columnDefinition = "varchar(32) COMMENT '资产项类型'", nullable = false)
    private String type;
    @Column(columnDefinition = "varchar(100) COMMENT '资产项标签'", nullable = false)
    private String labelsJson;
    @Column(columnDefinition = "varchar(100) COMMENT '资产项描述'")
    private String description;
    @Column(columnDefinition = "varchar(32) COMMENT '资产项所属用户'", nullable = false)
    private String username;


    public static AssetItemPO fromDomain(AssetItem domain) {
        if (null == domain) {
            return null;
        }
        AssetItemPO result = BeanTransferUtils.transferBean(domain, AssetItemPO.class);
        result.setLabelsJson(JSONUtils.toJSONString(domain.getLabels()));
        return result;
    }

    public static AssetItem toDomain(AssetItemPO po) {
        if (null == po) {
            return null;
        }
        AssetItem result = BeanTransferUtils.transferBean(po, AssetItem.class);
        result.setLabels(JSONUtils.parseArray(po.getLabelsJson(), String.class));
        return result;
    }
}
