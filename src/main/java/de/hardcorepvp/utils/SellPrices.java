package de.hardcorepvp.utils;

public enum SellPrices {

	SULPHUR(15), IRON_INGOT(50), GOLD_INGOT(200), DIAMOND(250);

	private final int price;

	SellPrices(int price) {

		this.price = price;

	}

	public int getPrice() {
		return price;
	}

}
