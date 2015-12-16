package jus.poc.prodcons.options;

import java.io.IOException;
import java.util.HashMap;
import java.util.InvalidPropertiesFormatException;
import java.util.Map;
import java.util.Properties;

public class XmlReader {

	public static HashMap<String, Integer> readFromXml()
			throws InvalidPropertiesFormatException, IOException {
		{

			HashMap<String, Integer> map = new HashMap<>();
			Properties properties = new Properties();
			properties
					.loadFromXML(ClassLoader
							.getSystemResourceAsStream("jus/poc/prodcons/options/options.xml"));
			String key;
			int value;

			for (Map.Entry<Object, Object> entry : properties.entrySet()) {
				key = (String) entry.getKey();
				value = Integer.parseInt((String) entry.getValue());
				map.put(key, value);
			}

			return map;
		}
	}
}
