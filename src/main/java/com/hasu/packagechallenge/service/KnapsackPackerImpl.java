
package com.hasu.packagechallenge.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.hasu.packagechallenge.model.Item;

import lombok.Data;

@Data
public class KnapsackPackerImpl implements KnapsackPacker {

	@Override
	public String packItems(int maxWeight, List<Item> items) {
		int n = items.size() + 1;
		int w = maxWeight + 1;

		double[][] a = new double[n][w];

		for (int i = 1; i < n; i++) {
			Item item = items.get(i - 1);
			for (int j = 1; j < w; j++) {
				if (item.getWeight() > j) {
					a[i][j] = a[i - 1][j];
				} else {
					a[i][j] = Math.max(a[i - 1][j], a[i - 1][(int) (j - item.getWeight())] + item.getCost());
				}
			}
		}

		List<Integer> indexes = new ArrayList<>();
		int j = maxWeight;
		double totalcost = a[n - 1][w - 1];
		for (; j > 0 && a[n - 1][j - 1] == totalcost; j--)
			;

		for (int i = n - 1; i > 0; i--) {
			if (a[i][j] != a[i - 1][j]) {
				indexes.add(items.get(i - 1).getIndex());
				j -= items.get(i - 1).getWeight();
			}
		}

		// Get indexes of items which could fit in the package
		// List<Integer> indexes = getIndexOfSelectedItems(a, n - 1, maxWeight, items);

		String result = indexes.stream().mapToInt(i -> i).sorted().mapToObj(Integer::toString)
				.collect(Collectors.joining(","));
		return result.isEmpty() ? "-" : result;

	}

	private static List<Integer> getIndexOfSelectedItems(double[][] a, int n, int maxWeight, List<Item> items) {
		List<Integer> indexes = new ArrayList<>();
		while (n != 0) {
			if (a[n][maxWeight] != a[n - 1][maxWeight]) {
				indexes.add(items.get(n - 1).getIndex());
				maxWeight -= items.get(n - 1).getWeight();
			}
			n--;
		}

		return indexes;
	}

}
