package i5.las2peer.services.cdService.data.util;

import java.util.Collection;

public class Formatter {

	///// Decimals /////

	public String decimals(String string, int i) {
		
		String format = "%." + i + "f";
		return decimals(string, format);
	}

	public String decimals(String string, String format) {

		String result;
		try {
			double decimal = Double.valueOf(string);
			result = String.format(format, decimal);
		} catch (Exception e) {
			return string;
		}
		return result;
	}

	///// Macros /////

	public String macros(String string, Collection<String> macros) {
		return macros(string, "\\", macros);
	}

	public String macros(String string, String prefix, Collection<String> macros) {

		for (String macro : macros) {
			string = string.replace("/" + macro + "/g", prefix + macro);
		}
		return string;
	}

}
