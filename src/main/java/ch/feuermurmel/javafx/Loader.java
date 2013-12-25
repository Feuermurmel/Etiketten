package ch.feuermurmel.javafx;

import java.io.IOException;
import java.lang.reflect.*;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javafx.fxml.FXMLLoader;

public abstract class Loader {
	@SuppressWarnings("unchecked")
	public static <T> T load(Class<T> controllerType) throws IOException {
		Controller controllerAnnotation = controllerType.getAnnotation(Controller.class);

		if (controllerAnnotation == null)
			throw new IllegalArgumentException(String.format("Type %s is not annotated with %s.", controllerType.getCanonicalName(), Controller.class.getCanonicalName()));

		String resourceName = controllerAnnotation.value();
		URL url = controllerType.getResource(resourceName);
		
		if (url == null)
			throw new IllegalArgumentException(String.format("Could not find resource \"%s\" for type %s.", resourceName, controllerType.getCanonicalName()));
		
		FXMLLoader loader = new FXMLLoader(url);
		
		loader.load();

		final Map<Method, Object> nodes = new HashMap<>();
		
		for (Method i : controllerType.getDeclaredMethods()) {
			if (Modifier.isAbstract(i.getModifiers())) {
				if (i.getParameterTypes().length > 0)
					throw new IllegalArgumentException(String.format("Abstract method %s of type %s has parameters.", i.getName(), controllerType.getCanonicalName()));
				
				Class<?> nodeType = i.getReturnType();
				String nodeID = i.getName();
				Object node = loader.getNamespace().get(nodeID);
				
				if (node == null)
					throw new IllegalArgumentException(String.format("No node with fx:id \"%s\" found in file %s.", nodeID, url));
				
				if (controllerType.isAssignableFrom(node.getClass()))
					throw new IllegalArgumentException(String.format("Node fx:id \"%s\" does is not of type %s.", nodeID, nodeType.getCanonicalName()));
				
				nodes.put(i, node);
			}
		}
		
		return (T) Proxy.newProxyInstance(
			controllerType.getClassLoader(), new Class<?>[]{ controllerType }, new InvocationHandler() {
			@Override
			public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
				return nodes.get(method);
			}
		});
	}
}
