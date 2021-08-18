package com.hasu.packagechallenge.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Package {
	
	private int maxWeight;
	private List<Item> items;

}
