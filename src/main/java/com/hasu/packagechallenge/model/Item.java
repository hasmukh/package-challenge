package com.hasu.packagechallenge.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Item {
	private Integer index;
	private Float weight;
	private Integer cost;
}
