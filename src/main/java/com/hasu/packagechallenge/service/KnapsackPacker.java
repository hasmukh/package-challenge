package com.hasu.packagechallenge.service;

import java.util.List;

import com.hasu.packagechallenge.model.Item;

public interface KnapsackPacker {

	String packItems(int maxWeight, List<Item> items);
}
