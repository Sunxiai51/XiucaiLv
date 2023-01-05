package com.sunveee.xiucailv.web.context.asset.domain.asset.entity;

import com.sunveee.framework.common.exceptions.utils.BizAssertUtil;
import lombok.*;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
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
    private String username;

    public static AssetItem fromCsvRecord(CSVRecord csvRecord) {
        String labels = csvRecord.get("labels");
        return AssetItem.builder()
                .code(csvRecord.get("code"))
                .name(csvRecord.get("name"))
                .type(AssetItemType.fromCsvRecord(csvRecord))
                .labels(StringUtils.isNotBlank(labels) ? Arrays.asList(labels.split(",")) : new ArrayList<>())
                .description(csvRecord.get("description"))
                .username(csvRecord.get("username"))
                .build();
    }

    public void validate() {
        BizAssertUtil.hasText(code, "code cannot be null");
        BizAssertUtil.hasText(name, "name cannot be null");
        BizAssertUtil.notNull(type, "type cannot be null");
        BizAssertUtil.notNull(labels, "labels cannot be null");
        BizAssertUtil.hasText(username, "username cannot be null");
        BizAssertUtil.isTrue(null == description || description.length() <= 100, "max description length 100");
    }


    @Getter
    public enum AssetItemType {
        DEBT("负债"),
        INVEST("投资"),
        CASH("现金"),

        ;
        private final String name;

        AssetItemType(String name) {
            this.name = name;
        }

        public static AssetItemType fromName(String name) {
            for (AssetItemType type : AssetItemType.values()) {
                if (StringUtils.equals(name, type.getName())) {
                    return type;
                }
            }
            throw new IllegalArgumentException("unknown AssetItemType.name: " + name);
        }

        public static AssetItemType fromCsvRecord(CSVRecord csvRecord) {
            return fromName(csvRecord.get("type"));
        }
    }

}
