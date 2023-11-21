package me.jklmao.main;

import java.util.UUID;
import java.util.function.BiConsumer;

import net.hypixel.api.HypixelAPI;
import net.hypixel.api.apache.ApacheHttpClient;
import net.hypixel.api.reply.AbstractReply;

public class APIManager {

	public static final HypixelAPI API;

	static {
		String key = System.getProperty("apiKey", "HIDDENAPIKEY");
		API = new HypixelAPI(new ApacheHttpClient(UUID.fromString(key)));
	}

	public static final UUID HYPIXEL = UUID.fromString("f7c77d99-9f15-4a66-a87d-c4a51ef30d19");
	public static final String GUILD_ID = "53bd67d7ed503e868873eceb";

	public static void await() {
		while (!Thread.interrupted()) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public static <T extends AbstractReply> BiConsumer<T, Throwable> getTestConsumer() {
		return (result, throwable) -> {
			if (throwable != null) {
				throwable.printStackTrace();
				System.exit(0);
				return;
			}

			System.out.println(result);

			System.exit(0);
		};
	}

}