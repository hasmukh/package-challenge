package com.hasu.packagechallenge.app;

import java.util.List;
import java.util.stream.Collectors;

import com.hasu.packagechallenge.exceptions.FileParserException;
import com.hasu.packagechallenge.model.Package;
import com.hasu.packagechallenge.service.KnapsackPacker;
import com.hasu.packagechallenge.service.KnapsackPackerImpl;
import com.hasu.packagechallenge.utilities.FileParser;

/**
 * 
 * @author Hasmukh Maniya
 * 
 */
public class PackageChallengeMain {
	public static void main(String[] args) throws FileParserException {
		if (args.length == 1) {

			KnapsackPacker knapsackPacker = new KnapsackPackerImpl();
			
			FileParser fileParser = FileParser.getInstance();

			// Parse file and convert intor Package Model
			List<Package> packages = fileParser.parseFile(args[0]);

			// Run Knapsack Dynamic program for each package
			String selectedItemIndexes = packages.stream()
					.map(itemPackage -> knapsackPacker.packItems(itemPackage.getMaxWeight(), itemPackage.getItems()))
					.collect(Collectors.joining("\n"));

			System.out.println(selectedItemIndexes);
		} else {
			System.err.println("input file path must provider as args[0], extra args is forbidden");
			System.exit(1);
		}
	}
}
