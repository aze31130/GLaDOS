package accounts;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
import achievements.Achievement;
import items.Item;
import net.dv8tion.jda.api.entities.Member;

public class Account {
	public String id;
	public Member member;

	public int level;
	public long experience;
	public long totalExperience;

	public TrustFactor trustLevel;
	public Permission permission;

	public List<Item> inventory;
	public List<Achievement> achievements;

	public boolean canDrop;
	public long money;

	public Account(String id, Member member, int level, long experience, long totalExperience,
			TrustFactor trustLevel, Permission permission, List<Item> inventory, boolean canDrop,
			long money) {
		this.id = id;
		this.member = member;
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
		result.put("name", this.member.getUser().getName());
		result.put("created", this.member.getUser().getTimeCreated().toString());
		result.put("joined", this.member.getTimeJoined().toString());
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
