package com.skm.exa.mybatis;

import org.apache.commons.lang3.StringUtils;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author dhc
 * 2019-03-08 12:21
 * Map<排序字段, 排序方式> 如map.put("name", "asc");
 */
public class Sort extends LinkedHashMap<String, String> {
    public static final String ASC = "ASC";
    public static final String DESC = "DESC";

    public boolean isNotEmpty() {
        return this.size() > 0;
    }

    public String toOrderBy() {
        if (this.isEmpty()) return "";
        List<String> list = this.keySet().stream().filter(StringUtils::isNotBlank).map(p -> String.format(" %s %s", p, DESC.equalsIgnoreCase(this.get(p)) ? DESC : ASC)).collect(Collectors.toList());
        return list.isEmpty() ? "" : " ORDER BY" + StringUtils.join(list, ',');
    }
}
