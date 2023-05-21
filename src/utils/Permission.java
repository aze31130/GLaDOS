package utils;

import constants.Constants;
import constants.Constants.Permissions;
import constants.Constants.Roles;
import accounts.Account;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import java.util.List;

public class Permission {
	/* Returns the permission level of someone
	 * +-------+-------+-----+------+
	 * | OWNER | ADMIN | MOD | NONE |
	 * +-------+-------+-----+------+
	 * |   3   |   2   |  1  |   0  |
	 * +-------+-------+-----+------+
	 */
	public static Boolean permissionLevel(Account account, Member member, int requiredLevel) {
		List<Role> roles = member.getRoles();
		int permission = 0;
		if(member.getId().equals(Constants.OwnerId)) {
			permission = Permissions.OWNER.level;
		} else {
			for(Role role : roles){
				if(role.getId().equals(Roles.MOD.id)) {
					permission = Permissions.MOD.level;
					
				}
			}
			for(Role role : roles){
				if(role.getId().equals(Roles.ADMIN.id)) {
					permission = Permissions.ADMIN.level;
				}
			}
		}
		return permission >= requiredLevel;
	}
}
