package accounts;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.json.JSONArray;
import org.json.JSONObject;
import achievements.Achievement;
import items.Item;
import net.dv8tion.jda.api.entities.User;

public class Account {
	public String id;
	public User user;

	public long trustFactorScore;
	public TrustFactor trustLevel;

	public List<Item> inventory;
	public List<Achievement> achievements;

	public boolean canDrop;
	public long money;

	public Account(String id, User user, long trustFactorScore, TrustFactor trustLevel, List<Item> inventory, boolean canDrop,
			long money) {
		this.id = id;
		this.user = user;
		this.trustFactorScore = trustFactorScore;
		this.trustLevel = trustLevel;
		this.inventory = inventory;
		this.achievements = new ArrayList<>();
		this.canDrop = canDrop;
		this.money = money;
	}

	/*
	 * Returns an item from inventory.
	 */
	public Optional<Item> getItemByFQName(String itemFQName) {
		return this.inventory.stream().filter(i -> i.getFQName().equals(itemFQName)).findFirst();
	}

	/*
	 * Converts the current user to a jsonObject
	 */
	public JSONObject toJson() {
		JSONObject result = new JSONObject();
		JSONArray items = new JSONArray();
		JSONArray achievements = new JSONArray();

		for (Item item : this.inventory)
			items.put(item.toJson());

		for (Achievement achievement : this.achievements)
			achievements.put(achievement);

		result.put("id", this.id);
		result.put("name", this.user.getName());
		result.put("created", this.user.getTimeCreated().toString());
		result.put("joined", this.user.getTimeCreated().toString());
		result.put("inventory", items);
		result.put("achievements", achievements);
		result.put("trustFactorScore", this.trustFactorScore);
		result.put("trustFactor", this.trustLevel);
		result.put("canDrop", this.canDrop);
		result.put("money", this.money);
		return result;
	}
}
