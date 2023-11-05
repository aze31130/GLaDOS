package utils;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import java.util.List;

import glados.GLaDOS;

public class PermissionsUtils {
	/*
	 * Returns the permission level of someone +-------+-------+-----+------+ | OWNER | ADMIN | MOD
	 * | NONE | +-------+-------+-----+------+ | 3 | 2 | 1 | 0 | +-------+-------+-----+------+
	 */
	public static Boolean permissionLevel(Member member, int requiredLevel) {
		List<Role> roles = member.getRoles();
		GLaDOS g = GLaDOS.getInstance();
		int permission = 0;
		if (member.getId().equals(g.ownerId)) {
			permission = 3;
		} else {
			for (Role role : roles) {
				if (role.getId().equals(g.roleModerator)) {
					permission = 1;
				}
			}
			for (Role role : roles) {
				if (role.getId().equals(g.roleAdministrator)) {
					permission = 2;
				}
			}
		}
		return permission >= requiredLevel;
	}
}
