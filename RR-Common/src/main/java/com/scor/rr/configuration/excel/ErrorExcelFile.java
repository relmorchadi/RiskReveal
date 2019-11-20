package com.scor.rr.configuration.excel;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorExcelFile implements Serializable {

    private Integer row;
    private Integer col;
    private String message;
    private String value;

    public ErrorExcelFile(Integer row, Integer col, String message, String value) {
        this.row = row;
        this.col = col;
        this.message = message;
        this.value = value;
    }

    public ErrorExcelFile(Integer row, Integer col, String message) {
        this.row = row;
        this.col = col;
        this.message = message;
    }
    public ErrorExcelFile(Integer row, String message) {
        this.row = row;
        this.message = message;
    }
    public ErrorExcelFile(Integer row) {
        this.row = row;
    }

    public Integer getRow() {
        return row;
    }

    public void setRow(Integer row) {
        this.row = row;
    }

    public Integer getCol() {
        return col;
    }

    public void setCol(Integer col) {
        this.col = col;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
