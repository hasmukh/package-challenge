package com.hasu.packagechallenge.constants;

import lombok.experimental.UtilityClass;

@UtilityClass
public class PackageChallangeConstants {

	public final String INDEX = "index";
	public final String WEIGHT = "weight";
	public final String COST = "cost";

	public final int MAX_ITEMS_IN_PACKAGE = 15;
	public final int MAX_PACKAGE_WEIGHT = 100;
	public final int MAX_COST = 100;
	public final float MAX_ITEM_WEIGHT = 100f;

	// Pattern to match item in the line.
	public final String ITEM_REGEX = "(?<index>\\d+),(?<weight>\\d+\\.\\d+),€(?<cost>\\d+)";

}
