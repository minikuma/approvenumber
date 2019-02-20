package me.spring5.mvc.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomException extends RuntimeException {
    private String ans_cd;
    private String ans_msg;
}
