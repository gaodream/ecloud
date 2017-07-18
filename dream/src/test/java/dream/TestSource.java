package dream;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.core.io.UrlResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.util.StringUtils;

public class TestSource {
	public static String FACTORIES_RESOURCE_LOCATION = "F:/a.txt";
	public static void main(String[] args) throws IOException {
		Enumeration<URL> urls = ClassLoader.getSystemResources(FACTORIES_RESOURCE_LOCATION);
		List<String> result = new ArrayList<String>();
		while (urls.hasMoreElements()) {
			URL url = urls.nextElement();
			Properties properties = PropertiesLoaderUtils.loadProperties(new UrlResource(url));
			String factoryClassNames = properties.getProperty(ApplicationContextInitializer.class.getName());
			System.out.println(factoryClassNames);
			result.addAll(Arrays.asList(StringUtils.commaDelimitedListToStringArray(factoryClassNames)));
		}
	}

}
