package org.devgraft.member.service;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.devgraft.member.domain.GenderEnum;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class MemberGetResponse {
    private final String id;
    private final String nickName;
    private final GenderEnum gender;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime createAt;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime updateAt;
}
