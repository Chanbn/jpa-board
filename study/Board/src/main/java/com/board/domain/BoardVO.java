package com.board.domain;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class BoardVO {

	private Long idx;
	private String title;
	private String content;
	private String writer;
	private LocalDateTime regTime;
	private LocalDateTime updateTime;
	
}
