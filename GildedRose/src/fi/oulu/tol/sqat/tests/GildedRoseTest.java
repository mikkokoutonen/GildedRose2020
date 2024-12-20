package fi.oulu.tol.sqat.tests;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import fi.oulu.tol.sqat.GildedRose;
import fi.oulu.tol.sqat.Item;

public class GildedRoseTest {

	@Test
	public void testTheTruth() {
		assertTrue(true);
	}
	@Test
	public void exampleTest() {
		//create an inn, add an item, and simulate one day
		GildedRose inn = new GildedRose();
		inn.setItem(new Item("+5 Dexterity Vest", 10, 20));
		inn.oneDay();
		
		//access a list of items, get the quality of the one set
		List<Item> items = inn.getItems();
		int quality = items.get(0).getQuality();
		
		//assert quality has decreased by one
		assertEquals("Failed quality for Dexterity Vest", 19, quality);
	}
	
	@Test
	public void testMain() {
		GildedRose inn = new GildedRose();
		String[] args = {""};
		GildedRose.main(args);
		List<Item> items = inn.getItems();
		assertEquals("Adding items in main method failed", 6, items.size());
		assertEquals("Failed quality for +5 Dexterity Vest", 19, items.get(0).getQuality());
	}
	
	@Test
	public void testBackstagePasses() {
		GildedRose inn = new GildedRose();
		inn.setItem(new Item("Backstage passes to a TAFKAL80ETC concert", 11, 10));
		inn.oneDay(); // 11 days to concert
		List<Item> items = inn.getItems();
		assertEquals("Failed quality for backstage passes", 11, items.get(0).getQuality());
		inn.oneDay(); // 10 days to concert
		assertEquals("Failed quality for backstage passes", 13, items.get(0).getQuality());
		inn.oneDay(); // 9 days to concert
		assertEquals("Failed quality for backstage passes", 15, items.get(0).getQuality());
		inn.oneDay(); // 8 days to concert
		inn.oneDay(); // 7 days to concert
		inn.oneDay(); // 6 days to concert
		assertEquals("Failed quality for backstage passes", 21, items.get(0).getQuality());
		inn.oneDay(); // 5 days to concert
		assertEquals("Failed quality for backstage passes", 24, items.get(0).getQuality());
		inn.oneDay(); // 4 days to concert
		assertEquals("Failed quality for backstage passes", 27, items.get(0).getQuality());
		inn.oneDay(); // 3 days to concert
		inn.oneDay(); // 2 days to concert
		inn.oneDay(); // 1 day to concert
		assertEquals("Failed quality for backstage passes", 36, items.get(0).getQuality());
		inn.oneDay(); // concert day
		assertEquals("Failed quality for backstage passes", 0, items.get(0).getQuality());
	}
	
	@Test
	public void testAgedBrie() {
		GildedRose inn = new GildedRose();
		inn.setItem(new Item("Aged Brie", 3, 40));
		inn.oneDay(); // sellIn 3 --> 2
		List<Item> items = inn.getItems();
		assertEquals("Failed quality for Aged Brie", 41, items.get(0).getQuality());
		inn.oneDay(); // sellIn 2 --> 1
		inn.oneDay(); // sellIn 1 --> 0
		assertEquals("Failed quality for Aged Brie", 43, items.get(0).getQuality());
		inn.oneDay(); // sellIn 0 --> -1
		assertEquals("Failed quality for Aged Brie", 45, items.get(0).getQuality());
		inn.oneDay();
		inn.oneDay();
		assertEquals("Failed quality for Aged Brie", 49, items.get(0).getQuality());
		inn.oneDay();
		assertEquals("Upper limit for quality exceeded", 50, items.get(0).getQuality());
		inn.oneDay();
		assertEquals("Upper limit for quality exceeded", 50, items.get(0).getQuality());
	}
	
	@Test
	public void testSulfuras() {
		GildedRose inn = new GildedRose();
		inn.setItem(new Item("Sulfuras, Hand of Ragnaros", 0, 80));
		inn.oneDay();
		List<Item> items = inn.getItems();
		assertEquals("Failed quality for Sulfuras", 80, items.get(0).getQuality());
		assertEquals("Failed sellIn for Sulfuras", 0, items.get(0).getSellIn());
	}
	
	@Test
	public void testPassedSellInDate() {
		GildedRose inn = new GildedRose();
		inn.setItem(new Item("Conjured Mana Cake", 2, 10));
		inn.oneDay(); // sellIn 2 --> 1
		List<Item> items = inn.getItems();
		assertEquals("Failed quality for Conjured Mana Cake", 9, items.get(0).getQuality());
		inn.oneDay(); // sellIn 1 --> 0
		assertEquals("Failed quality for Conjured Mana Cake", 8, items.get(0).getQuality());
		inn.oneDay(); // sellIn 0 --> -1
		assertEquals("Failed quality for Conjured Mana Cake", 6, items.get(0).getQuality());
		assertEquals("Failed sellIn for Conjured Mana Cake", -1, items.get(0).getSellIn());
	}
	
	@Test
	public void testZeroQuality() {
		GildedRose inn = new GildedRose();
		inn.setItem(new Item("Elixir of the Mongoose", 1, 0));
		inn.oneDay(); // sellIn 1 --> 0
		List<Item> items = inn.getItems();
		assertEquals("Failed quality for Elixir of the Mongoose", 0, items.get(0).getQuality());
		inn.oneDay(); // sellIn 0 --> -1
		assertEquals("Failed quality for Elixir of the Mongoose", 0, items.get(0).getQuality());
	}
	
	/* Loop testing: Test skipping the for-loop. */
	
	@Test
	public void testEmptyList() {
		GildedRose inn = new GildedRose();
		inn.oneDay();
		List<Item> items = inn.getItems();
		assertEquals("Not empty", 0, items.size());
	}
	
	/* Loop testing: Test running the for-loop several times. */
	
	@Test
	public void testSeveralItems() {
		GildedRose inn = new GildedRose();
		inn.setItem(new Item("+5 Dexterity Vest", 10, 20));
		inn.setItem(new Item("Elixir of the Mongoose", 5, 7));
		inn.setItem(new Item("Conjured Mana Cake", 3, 6));
		inn.oneDay();
		List<Item> items = inn.getItems();
		assertEquals("Failed quality for +5 Dexterity Vest", 19, items.get(0).getQuality());
		assertEquals("Failed quality for Elixir of the Mongoose", 6, items.get(1).getQuality());
		assertEquals("Failed quality for Conjured Mana Cake", 5, items.get(2).getQuality());
	}
	
}
