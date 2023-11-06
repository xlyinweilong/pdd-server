package com.yin.pddserver.common.exceptions;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * 异常信息
 *
 * @author yin
 */
@Getter
@Setter
public class ImportLineException extends BaseException {

    private Long line;

    private Integer column;

    public ImportLineException() {
    }

    public ImportLineException(String message) {
        super(message);
    }

    public ImportLineException(String message, Long line) {
        super(message);
        this.line = line;
    }

    public ImportLineException(String message, Long line, Integer column) {
        super(message);
        this.line = line;
        this.column = column;
    }

}
