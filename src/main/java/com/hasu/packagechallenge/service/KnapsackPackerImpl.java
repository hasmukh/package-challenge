
package com.hasu.packagechallenge.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.hasu.packagechallenge.model.Item;
/**
 * Service is responsible to provide knapsack packing methods
 * @author Hasmukh Maniya
 *
 */
public class KnapsackPackerImpl implements KnapsackPacker {

	@Override
	public String packItems(int maxWeight, List<Item> items) {

		int n = items.size() + 1;
		int w = maxWeight + 1;

		double[][] a = new double[n][w];

		// Setting up 0/1 Knapsack Matrix for Item Selection
		for (int i = 1; i < n; i++) {
			Item item = items.get(i - 1);
			for (int j = 1; j < w; j++) {
				if (item.getWeight() > j) {
					a[i][j] = a[i - 1][j];
				} else {
					a[i][j] = Math.max(a[i - 1][j], a[i - 1][(int) (j - item.getWeight())] + item.getCost());
				}

				// System.out.print(i + "," + j + "=>" + a[i][j] + " | ");
			}
			// System.out.println();
		}

		List<Integer> indexes = new ArrayList<>();
		int j = maxWeight;

		// Optimum cost can be carried
		double totalcost = a[n - 1][w - 1];

		// Reaching out to optimal weight can be carried
		for (; j > 0 && a[n - 1][j - 1] == totalcost; j--)
			;
		// Finding out the indexes of items which can be packed
		for (int i = n - 1; i > 0; i--) {
			if (a[i][j] != a[i - 1][j]) {
				indexes.add(items.get(i - 1).getIndex());
				j -= items.get(i - 1).getWeight();
			}
		}

		String strIndexes = indexes.stream().mapToInt(i -> i).sorted().mapToObj(Integer::toString)
				.collect(Collectors.joining(","));
		return strIndexes.isEmpty() ? "-" : strIndexes;

	}
}
