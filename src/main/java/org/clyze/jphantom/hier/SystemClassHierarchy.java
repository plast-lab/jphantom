package org.clyze.jphantom.hier;

import java.util.*;
import org.objectweb.asm.Type;

public class SystemClassHierarchy extends AbstractClassHierarchy
{
    private static final Map<ClassLoader,SystemClassHierarchy> systemHier = 
        new HashMap<>();

    private SystemClassHierarchy(ClassLoader loader) {
        this.loader = loader;
    }

    private final ClassLoader loader;

    public static SystemClassHierarchy getInstance(ClassLoader loader)
    {
        if (!systemHier.containsKey(loader))
            systemHier.put(loader, new SystemClassHierarchy(loader));

        return systemHier.get(loader);
    }

    @Override
    public void addClass(Type clazz, Type superclass, Type[] interfaces)
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public void addInterface(Type iface, Type[] superInterfaces)
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isInterface(Type obj) {
        return asClass(obj).isInterface();
    }

    @Override
    public boolean contains(Type obj) {
        try {
            Class.forName(obj.getClassName(), false, loader);
        } catch (ClassNotFoundException exc) {
            return false;
        }
        return true;
    }

    @Override
    public Set<Type> getInterfaces(Type obj)
    {
        Set<Type> ifaces = new HashSet<>();

        for (Class<?> c : asClass(obj).getInterfaces())
            ifaces.add(asType(c));

        return ifaces;
    }

    @Override
    public Type getSuperclass(Type obj)
    {
        Class<?> superclass = 
            isInterface(obj) ? Object.class : asClass(obj).getSuperclass();

        return asType(superclass);
    }

    private Class<?> asClass(Type obj)
    {
        try {
            return Class.forName(obj.getClassName(), false, loader);
        } catch (ClassNotFoundException exc) {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public Iterator<Type> iterator() {
        throw new UnsupportedOperationException();
    }

    public static final Type asType(Class<?> clazz) {
        return Type.getType(clazz);
    }
}
