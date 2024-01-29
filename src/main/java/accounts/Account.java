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

	public int level;
	public long experience;
	public long totalExperience;

	public TrustFactor trustLevel;
	public Permission permission;

	public List<Item> inventory;
	public List<Achievement> achievements;

	public boolean canDrop;
	public long money;

	public Account(String id, User user, int level, long experience, long totalExperience, TrustFactor trustLevel,
			Permission permission,
			List<Item> inventory, boolean canDrop, long money) {
		this.id = id;
		this.user = user;
		this.level = level;
		this.experience = experience;
		this.totalExperience = totalExperience;
		this.trustLevel = trustLevel;
		this.permission = permission;
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
		result.put("level", this.level);
		result.put("experience", this.experience);
		result.put("totalExperience", this.totalExperience);
		result.put("trustFactor", this.trustLevel);
		result.put("permission", this.permission);
		result.put("canDrop", this.canDrop);
		result.put("money", this.money);
		result.put("version", 1);
		return result;
	}
}
