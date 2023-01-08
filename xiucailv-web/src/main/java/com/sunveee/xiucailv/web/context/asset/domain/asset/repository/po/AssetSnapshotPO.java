package com.sunveee.xiucailv.web.context.asset.domain.asset.repository.po;

import com.sunveee.framework.common.utils.bean.BeanTransferUtils;
import com.sunveee.xiucailv.web.common.jpa.BaseJpaEntity;
import com.sunveee.xiucailv.web.context.asset.domain.asset.entity.AssetSnapshot;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import java.util.Currency;

/**
 * AssetSnapshotPO
 *
 * @author SunVeee
 * @date 2023/1/6 18:12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "asset_snapshot", indexes = {@Index(name = "uk_date_item", columnList = "date,assetItemCode", unique = true)
})
public class AssetSnapshotPO extends BaseJpaEntity {
    @Column(columnDefinition = "varchar(32) COMMENT '资产快照日期'", nullable = false, updatable = false)
    private String date;
    @Column(columnDefinition = "varchar(32) COMMENT '资产项编号'", nullable = false, updatable = false)
    private String assetItemCode;
    @Column(columnDefinition = "decimal(20,2) COMMENT '资产金额'", nullable = false, updatable = false)
    private Double balance;
    @Column(columnDefinition = "varchar(6) COMMENT '资产金额币种'", nullable = false, updatable = false)
    private String currencyCode;
    @Column(columnDefinition = "text COMMENT '扩展字段'", updatable = false)
    private String ext;
    @Column(columnDefinition = "varchar(32) COMMENT '资产项类型'", nullable = false, updatable = false)
    private String assetItemType;
    @Column(columnDefinition = "varchar(32) COMMENT '所属用户'", nullable = false)
    private String username;


    public static AssetSnapshotPO fromDomain(AssetSnapshot domain) {
        if (null == domain) {
            return null;
        }
        AssetSnapshotPO result = BeanTransferUtils.transferBean(domain, AssetSnapshotPO.class);
        result.setCurrencyCode(domain.getCurrency().getCurrencyCode());
        return result;
    }

    public static AssetSnapshot toDomain(AssetSnapshotPO po) {
        if (null == po) {
            return null;
        }
        AssetSnapshot result = BeanTransferUtils.transferBean(po, AssetSnapshot.class);
        result.setCurrency(Currency.getInstance(po.getCurrencyCode()));
        return result;
    }
}
