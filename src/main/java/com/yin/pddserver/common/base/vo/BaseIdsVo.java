package com.yin.pddserver.common.base.vo;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BaseIdsVo extends BaseVo {

    private List<String> ids;
}
