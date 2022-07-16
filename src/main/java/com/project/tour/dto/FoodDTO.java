package com.project.tour.dto;

import lombok.Data;

@Data
public class FoodDTO {
	
	private String seq;
	private String image;
	private String name;
	private String address;
	private String category;
	private String cseq;
	
	private String open;
	private String close;
	
	private String likeCnt;
	private String reviewCnt;
	private String reviewAvg;

}
