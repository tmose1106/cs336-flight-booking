package cs336.util;

import java.util.List;

public class QueryUtil {
	static public String getWhereClause(List<String> clauses) {
		StringBuffer buf = new StringBuffer();
		
		
		for (int i = 0; i < clauses.size(); i++) {
			if (i == 0) {
				buf.append("WHERE");				
			} else {
				buf.append("  AND");
			}
			
			buf.append(" " + clauses.get(i) + "\n");
		}
		
		return buf.toString();
	}
}
