package com.kmware.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexpUtils {

	public static List<String> getElExpressions(String input) {
		Pattern p = Pattern.compile("\\#\\{[A-Za-z0-9\\.]+\\}");
		Matcher m = p.matcher(input);
		List<String> result = new ArrayList<String>();
		while (m.find()) {
			String group = m.group();
			if (group != null && !"".equals(group)) {
				result.add(group.substring(2, group.length() - 1));
			}

		}
		return result;
	}
}
