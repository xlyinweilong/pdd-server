package com.yin.pddserver.apiusercenter.excel.template;

public enum TemplateUserImportEnum {

    USER;

    public String getFileName() {
        switch (this) {
            case USER:
                return "导入用户模板";
        }
        return null;
    }
}
