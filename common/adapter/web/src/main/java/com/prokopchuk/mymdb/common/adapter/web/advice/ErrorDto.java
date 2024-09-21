package com.prokopchuk.mymdb.common.adapter.web.advice;


import java.util.List;

public record ErrorDto(String errorCode, List<String> messages) {
}
