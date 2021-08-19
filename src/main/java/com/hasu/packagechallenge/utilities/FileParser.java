package com.hasu.packagechallenge.utilities;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.hasu.packagechallenge.constants.PackageChallangeConstants;
import com.hasu.packagechallenge.exceptions.FileParserException;
import com.hasu.packagechallenge.model.Item;
import com.hasu.packagechallenge.model.Package;
/**
 * Singleton class responsible to help parsing file
 * @author z003yjtu
 *
 */
public class FileParser {

	private static FileParser fileParser;

	private FileParser() {
	}

	public static FileParser getInstance() {
		if (fileParser == null) {
			fileParser = new FileParser();
		}
		return fileParser;
	}

	public List<Package> parseFile(String inputFilePath) throws FileParserException {
		List<Package> packages = new ArrayList<>();

		try (FileInputStream inputStream = new FileInputStream(inputFilePath)) {
			try (Scanner scanner = new Scanner(inputStream)) {
				for (int lineNumber = 1; scanner.hasNext(); lineNumber++) {
					String line = scanner.nextLine();
					validateLine(lineNumber, line);
					packages.add(parseLine(lineNumber, line));

				}
			}
		} catch (IOException e) {
			throw new FileParserException(e);
		}

		return packages;
	}

	/**
	 * This method parse the line coming from Input file
	 * 
	 * @param lineNumber
	 * @param line
	 * @return Package
	 * @throws FileParserException
	 */
	private static Package parseLine(int lineNumber, String line) throws FileParserException {
		String[] splittedLines = line.split(":");

		// Package Weight
		final Integer packageWeight = Integer.parseInt(splittedLines[0].trim());

		// Validating weight of package
		validatePackage(lineNumber, line, packageWeight);

		// Parsed Items for Packaging
		List<Item> items = parseItemsFromLine(lineNumber, splittedLines[1]);

		// Validate Package Items
		validatePackageItems(items, line, lineNumber);

		return new Package(packageWeight, items);
	}

	/**
	 * This method validates weight of Package
	 * 
	 * @param lineNumber
	 * @param line
	 * @param packageWeight
	 * @throws FileParserException
	 */
	private static void validatePackage(int lineNumber, String line, Integer packageWeight) throws FileParserException {
		if (packageWeight > PackageChallangeConstants.MAX_PACKAGE_WEIGHT) {
			throw new FileParserException(String.format("Weight of the package should not be greater than %d.",
					PackageChallangeConstants.MAX_PACKAGE_WEIGHT), line, lineNumber);
		}
	}

	/**
	 * This method validates line of input file
	 * 
	 * @param lineNumber
	 * @param line
	 * @throws FileParserException
	 */
	private static void validateLine(int lineNumber, String line) throws FileParserException {
		if (line.split(":").length != 2) {
			throw new FileParserException(String.format("Line Number %d should contain only one : ", lineNumber), line,
					lineNumber);
		}
	}

	/**
	 * This Method parses line and prepares list of item object
	 * 
	 * @param lineNumber
	 * @param itemLine
	 * @return items
	 */
	private static List<Item> parseItemsFromLine(int lineNumber, String itemLine) {
		Pattern itemPattern = Pattern.compile(PackageChallangeConstants.ITEM_REGEX);
		Matcher itemMatcher = itemPattern.matcher(itemLine);
		List<Item> items = new ArrayList<>();
		while (itemMatcher.find()) {
			Item item = new Item(Integer.parseInt(itemMatcher.group(PackageChallangeConstants.INDEX)),
					Float.parseFloat(itemMatcher.group(PackageChallangeConstants.WEIGHT)),
					Integer.parseInt(itemMatcher.group(PackageChallangeConstants.COST)));
			items.add(item);
		}
		return items;
	}

	/**
	 * This method validate each item on the package with its weight, cost and total
	 * number of items resides in package
	 * 
	 * @param items
	 * @param line
	 * @param lineNumber
	 * @throws FileParserException
	 */
	private static void validatePackageItems(List<Item> items, String line, int lineNumber) throws FileParserException {

		// validate total number of items package can hold
		validateMaxNumberOfItemsInPackage(items, line, lineNumber);

		for (Item item : items) {
			// Validate weight of item
			validateItemWeight(item, line, lineNumber);

			// Validate cost of Item
			validateItemCost(item, line, lineNumber);
		}
	}

	/**
	 * This method validates maximum number of items in package
	 * 
	 * @param items
	 * @param line
	 * @param lineNumber
	 * @throws FileParserException
	 */
	private static void validateMaxNumberOfItemsInPackage(List<Item> items, String line, int lineNumber)
			throws FileParserException {
		if (items.size() > PackageChallangeConstants.MAX_ITEMS_IN_PACKAGE) {
			throw new FileParserException(
					String.format("A line can have maximum %d items. Provided number of items are %d. %n",
							PackageChallangeConstants.MAX_ITEMS_IN_PACKAGE, items.size()),
					line, lineNumber);
		}
	}

	/**
	 * This method validates weight of an Item, should not be more than threshhold
	 * defined in constant file.
	 * 
	 * @param item
	 * @param line
	 * @param lineNumber
	 * @throws FileParserException
	 */
	private static void validateItemWeight(Item item, String line, int lineNumber) throws FileParserException {
		if (item.getWeight() > PackageChallangeConstants.MAX_PACKAGE_WEIGHT) {
			throw new FileParserException(
					String.format("Item cannot have weight more than %d. Item with Index %d having weight %2f.",
							PackageChallangeConstants.MAX_PACKAGE_WEIGHT, item.getIndex(), item.getWeight()),
					line, lineNumber);
		}
	}

	/**
	 * This method validates Cost of an Item, should not be more than threshhold
	 * defined in constant file.
	 * 
	 * @param item
	 * @param line
	 * @param lineNumber
	 * @throws FileParserException
	 */
	private static void validateItemCost(Item item, String line, int lineNumber) throws FileParserException {
		if (item.getCost() > PackageChallangeConstants.MAX_COST) {
			throw new FileParserException(
					String.format("Item cannot have cost more than %d. Item with Index %d having cost %d.",
							PackageChallangeConstants.MAX_COST, item.getIndex(), item.getCost()),
					line, lineNumber);
		}
	}
}
