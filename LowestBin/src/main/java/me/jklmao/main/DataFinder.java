package me.jklmao.main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import net.hypixel.api.reply.skyblock.SkyBlockAuctionsReply;

public class DataFinder {

	// Phantom removed
	private List<String> itemNames = new ArrayList<>();
	private List<Integer> itemPrices = new ArrayList<>();
	private final String caduceus = "Caduceus Mender Skin";
	private final String primalFear = "Primal Fear Rune III";
	private final String redBack = "Redback Skin";
	private final String huskyWolf = "Husky Wolf Skin";
	private final String enderKnight = "Ender Knight Skin";
	private final String midNight = "Midnight Baby Yeti";
	private final String lightSasq = "Light Sasquatch Baby Yeti Skin";
	private final String darkSasq = "Dark Sasquatch Baby Yeti Skin";
	private final String candyBat = "Candy Bat Skin";
	private final String boneBat = "Bone Bat Skin";
	private final String gemStone = "Gemstone Divan Helmet Skin";
	private final String jesters = "Jester Bonzo's Mask Skin";
	private final String talisMan = "Talisman of the Century";
	private final String oni = "Oni Reaper Mask";
	private final String redOni = "Red Oni Reaper Mask";
	private final String blueOni = "Blue Oni Reaper Mask";
	private final String bloom = "Bloom Skin";
	private final String leaf = "Leaf Skin";
	private final String babyEnder = "Baby Ender Dragon Skin";
	private final String babyBlue = "Baby Blue Ender Dragon Skin";
	private final String pirateBomb = "Pirate Bomb Power Orb Skin";
	private final String securaty = "SecuRaty Guard Rat Skin";
	private final String ninjaRat = "Ninja Rat Skin";
	private final String pirateRat = "PiRate Rat Skin";
	private final String gymRat = "Gym Rat Rat Skin";
	private final String junkRat = "Junk Rat Rat Skin";
	private final String karate = "KaRate Rat Skin";
	private final String mrClaws = "Mr Claws Rat Skin";
	private final String squeak = "Squeakheart Rat Skin";

	public DataFinder() {
		initializeLists();
	}

	public void makeCall() throws InterruptedException, ExecutionException {

		JsonArray allAuctions = new JsonArray();
		List<CompletableFuture<SkyBlockAuctionsReply>> futureList = new ArrayList<>();
		int totalPages = APIManager.API.getSkyBlockAuctions(0).get().getTotalPages();

		for (int i = 0; i < totalPages; i++) {
			futureList.add(APIManager.API.getSkyBlockAuctions(i).whenComplete((page, throwable) -> allAuctions.add(page.getAuctions())));
		}

		CompletableFuture.allOf(futureList.toArray(new CompletableFuture[0])).get();

		for (int i = 0; i < totalPages; i++) {
			for (JsonElement element : allAuctions.get(i).getAsJsonArray()) {
				if (element.getAsJsonObject().get("bin").getAsBoolean() == false) {
					continue;
				}
				compareAuctionItem(element.getAsJsonObject().get("item_name").getAsString(),
					element.getAsJsonObject().get("starting_bid").getAsInt());
			}

		}
	}

	private void compareAuctionItem(String auctionItemName, int auctionItemPrice) {

		int index = 0;
		for (String name : itemNames) {

			if (auctionItemName.contains(name)) {
				if (auctionItemPrice < itemPrices.get(index)) {
					itemPrices.set(index, auctionItemPrice);
					return;
				}

			}

			index += 1;
		}

	}

	public List<String> getItemNames() {
		return itemNames;
	}

	public List<Integer> getItemPrices() {
		return itemPrices;
	}

	private void initializeLists() {
		allNames();
		allPrices();
	}

	private void allNames() {
		itemNames.add(babyBlue);
		itemNames.add(babyEnder);
		itemNames.add(blueOni);
		itemNames.add(boneBat);
		itemNames.add(caduceus);
		itemNames.add(enderKnight);
		itemNames.add(gemStone);
		itemNames.add(jesters);
		itemNames.add(karate);
		itemNames.add(leaf);
		itemNames.add(pirateBomb);
		itemNames.add(pirateRat);
		itemNames.add(primalFear);
		itemNames.add(redBack);
		itemNames.add(redOni);
		itemNames.add(securaty);
		itemNames.add(squeak);
		itemNames.add(bloom);
		itemNames.add(candyBat);
		itemNames.add(darkSasq);
		itemNames.add(gymRat);
		itemNames.add(huskyWolf);
		itemNames.add(junkRat);
		itemNames.add(lightSasq);
		itemNames.add(midNight);
		itemNames.add(mrClaws);
		itemNames.add(ninjaRat);
		itemNames.add(oni);
		itemNames.add(talisMan);

		Collections.sort(itemNames);
	}

	private void allPrices() {

		for (int i = 0; i < 29; i++) {
			itemPrices.add(Integer.MAX_VALUE);
		}

	}

}

