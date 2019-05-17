package com.skm.exa.persistence.dto;

import com.skm.exa.domain.bean.ImageBean;
import lombok.Data;

@Data
public class ImageCorrelationDto extends ImageBean {
    private Long correlationId;
}
