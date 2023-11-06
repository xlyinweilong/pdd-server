package com.yin.pddserver.common.config.mybatisplus;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.yin.pddserver.common.base.po.BasePo;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;


@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        if (metaObject.getOriginalObject() instanceof BasePo) {
            Date now = new Date();
            this.strictInsertFill(metaObject, "createDate", Date.class, now);
            this.strictUpdateFill(metaObject, "updateDate", Date.class, now);
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        if (metaObject.getOriginalObject() instanceof BasePo) {
            this.strictUpdateFill(metaObject, "updateDate", Date.class, new Date());
        }
    }
}
