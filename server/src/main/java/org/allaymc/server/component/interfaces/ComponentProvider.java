package org.allaymc.server.component.interfaces;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.SneakyThrows;
import org.allaymc.api.component.interfaces.Component;
import org.allaymc.api.component.interfaces.ComponentInitInfo;
import org.allaymc.api.utils.Identifier;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

import static java.lang.reflect.Modifier.isStatic;

/**
 * @author daoge_cmd
 */
public interface ComponentProvider<T extends Component> {
    static <T extends Component> ComponentProvider<T> of(Supplier<T> provider, Class<?> componentClass) {
        return new SimpleComponentProvider<>((info) -> provider.get(), componentClass);
    }

    static <T extends Component> ComponentProvider<T> of(Function<ComponentInitInfo, T> provider, Class<?> componentClass) {
        return new SimpleComponentProvider<>(provider, componentClass);
    }

    static <T extends Component> ComponentProvider<T> ofSingleton(T singleton) {
        return of((info) -> singleton, singleton.getClass());
    }

    static <P extends Component> Map<Identifier, ComponentProvider<? extends P>> toMap(List<ComponentProvider<? extends P>> componentProviders) {
        var map = new HashMap<Identifier, ComponentProvider<? extends P>>();
        componentProviders.forEach(componentProvider -> {
            var id = componentProvider.findComponentIdentifier();
            if (map.containsKey(id)) {
                throw new IllegalArgumentException("Duplicate component: " + id);
            }

            map.put(id, componentProvider);
        });
        return map;
    }

    @SneakyThrows
    static Identifier findComponentIdentifier(Class<?> clazz) {
        Identifier identifier = null;
        while (identifier == null) {
            identifier = findComponentIdentifierInCertainClass(clazz);
            if (identifier == null) clazz = clazz.getSuperclass();
            if (clazz == null) break;
        }
        return identifier;
    }

    @SneakyThrows
    static Identifier findComponentIdentifierInCertainClass(Class<?> clazz) {
        for (var field : clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(Identifier.Component.class) && Identifier.class == field.getType() && isStatic(field.getModifiers())) {
                field.setAccessible(true);
                return (Identifier) field.get(null);
            }
        }
        return null;
    }

    T provide(ComponentInitInfo info);

    Class<?> getComponentClass();

    @SneakyThrows
    default Identifier findComponentIdentifier() {
        return findComponentIdentifier(getComponentClass());
    }

    @Getter
    @AllArgsConstructor
    class SimpleComponentProvider<T extends Component, I extends ComponentInitInfo> implements ComponentProvider<T> {
        private Function<I, T> provider;
        private Class<?> componentClass;

        @Override
        public T provide(ComponentInitInfo info) {
            return provider.apply((I) info);
        }
    }
}
