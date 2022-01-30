package com.intpfyqa.gui.web.selenium.factories;

import com.google.common.collect.Collections2;
import com.google.common.collect.Sets;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Constructor;
import java.util.*;

/**
 * Common object factory
 */
public class CommonFactory {

    private final Class[] allowInstantiate;
    private final Class[] expandArguments;
    private final boolean useOnlySpecifiedCount;

    private CommonFactory(Class[] allowInstantiate, Class[] expandArguments, boolean useOnlySpecifiedCount) {
        this.allowInstantiate = allowInstantiate;
        this.expandArguments = expandArguments;
        this.useOnlySpecifiedCount = useOnlySpecifiedCount;
    }

    public Object createInstance(Class objectClass, Object... args) {

        boolean allowed = false;

        for (Class allowedClass : allowInstantiate) {
            if (allowedClass.isAssignableFrom(objectClass)) {
                allowed = true;
                break;
            }
        }

        if (!allowed) throw new RuntimeException("Can't create object of class '" + objectClass + "'." +
                " This class is not supported");

        ConstructorInfo constructorInfo = resolveConstructor(objectClass, args);

        if (constructorInfo == null) {
            throw new IllegalArgumentException("Can't create instance of class " + objectClass + ". " +
                    "No constructor found with types: " + StringUtils.join(listTypes(args).get(0), ", "));
        }

        try {
            return constructorInfo.constructor.newInstance(constructorInfo.args);
        } catch (Throwable t) {
            throw new RuntimeException("Couldn't create instance of class " + objectClass + " with arguments: " +
                    StringUtils.join(listTypes(constructorInfo.args).get(0), ", "), t);
        }
    }

    @SuppressWarnings("unchecked")
    private ConstructorInfo resolveConstructor(Class objectClass, Object... args) {

        ConstructorInfo constructorInfo = new ConstructorInfo();
        List<Object[]> argVariants = buildArgsOrderVariants(args);

        for (Object[] argsVariant : argVariants) {
            List<LinkedList<Class>> types = listTypes(argsVariant);
            for (List<Class> typesEntry : types) {
                Class[] newArgs = typesEntry.toArray(new Class<?>[typesEntry.size()]);
                try {
                    Constructor constructor = objectClass.getDeclaredConstructor(newArgs);
                    constructor.setAccessible(true);
                    constructorInfo.constructor = constructor;
                    constructorInfo.args = argsVariant;
                    return constructorInfo;
                } catch (NoSuchMethodException ignore) {

                }
            }
        }

        return null;
    }

    private List<Object[]> buildArgsOrderVariants(Object... args) {

        List<Object[]> variants = new LinkedList<>();

        if (args.length == 0) {
            variants.add(new Object[]{});
            return variants;
        }

        if (args.length == 1) {
            variants.add(args);
            return variants;
        }

        if (!useOnlySpecifiedCount) {
            Set<Set<Object>> arrangements = Sets.powerSet(new HashSet<>(Arrays.asList(args)));
            arrangements = sortSet(arrangements, args.length);

            for (Set<Object> arrangement : arrangements) {
                Collection<List<Object>> permutations =
                        Collections2.permutations(Arrays.asList(arrangement.toArray(new Object[arrangement.size()])));
                for (List<Object> permutation : permutations)
                    variants.add(permutation.toArray(new Object[permutation.size()]));
            }
        } else {
            Collection<List<Object>> permutations =
                    Collections2.permutations(Arrays.asList(args));
            for (List<Object> permutation : permutations)
                variants.add(permutation.toArray(new Object[permutation.size()]));
        }

        return variants;
    }

    private Set<Set<Object>> sortSet(Set<Set<Object>> initial, int maxCount) {
        LinkedList<Set<Object>> res = new LinkedList<>();
        for (int i = maxCount; i >= 0; i--) {
            for (Set<Object> set : initial) {
                if (set.size() == maxCount) res.add(set);
            }
        }

        return new HashSet<>(res);
    }


    private List<LinkedList<Class>> listTypes(Object... objects) {
        List<LinkedList<Class>> classes = new LinkedList<>();
        LinkedList<Class> base = new LinkedList<>();

        for (Object o : objects) {
            if (o != null) {
                base.add(o.getClass());
            } else {
                base.add(null);
            }
        }

        classes.add(base);
        int count = base.size();

        for (int i = 0; i < count; i++) {
            List<Class> subClasses = expandClass(base.get(i));
            if (!subClasses.isEmpty()) {
                for (Class subClass : subClasses) {
                    List<LinkedList<Class>> newOnes = new LinkedList<>();
                    for (LinkedList<Class> existing : classes) {
                        LinkedList<Class> newOne = new LinkedList<>();
                        newOne.addAll((List<Class>) existing.clone());
                        newOne.set(i, subClass);
                        newOnes.add(newOne);
                    }
                    classes.addAll(newOnes);
                }
            }
        }

        return classes;
    }

    private List<Class> expandClass(Class cl) {
        List<Class> subClasses = new ArrayList<>();
        Class tmp = cl;
        boolean isSuperClass = false;
        while (tmp != null) {
            boolean ok = false;
            for (Class toExpand : expandArguments) {
                if (toExpand.isAssignableFrom(tmp)) {
                    ok = true;
                    break;
                }
            }
            if (ok) {
                subClasses.add(tmp);
            } else {
                if (isSuperClass) break;
            }

            tmp = tmp.getSuperclass();

            isSuperClass = true;
        }

        for (Class toExpand : expandArguments) {
            if (toExpand.isAssignableFrom(cl) && toExpand.isInterface()) {
                subClasses.add(toExpand);
            }
        }

        return subClasses;
    }


    private static class ConstructorInfo {

        Constructor constructor;
        Object[] args;
    }

    public static class CommonFactoryBuilder {
        private boolean useOnlySpecifiedCount = false;
        private Class[] allowedClasses = new Class[]{};
        private Class[] expandArguments = new Class[]{};

        private CommonFactoryBuilder() {
        }

        public static CommonFactoryBuilder withAllowedClasses(Class... classes) {
            CommonFactoryBuilder builder = new CommonFactoryBuilder();
            builder.allowedClasses = classes;
            return builder;
        }

        public CommonFactoryBuilder withArgumentsExpand(Class... classes) {
            this.expandArguments = classes;
            return this;
        }

        public CommonFactoryBuilder useOnlySpecifiedArgumentsCount() {
            this.useOnlySpecifiedCount = true;
            return this;
        }

        public CommonFactory build() {
            return new CommonFactory(allowedClasses, expandArguments, useOnlySpecifiedCount);
        }
    }
}
