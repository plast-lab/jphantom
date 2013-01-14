package jphantom;

import java.util.*;
import org.objectweb.asm.Type;

public interface Types {

    public static final Type OBJECT = Type.getType(Object.class);
    public static final Type THROWABLE = Type.getType(Throwable.class);
    public static final Type NULL_TYPE = Type.getObjectType("null");

    // Array Interfaces

    public static final Set<Type> ARRAY_INTERFACES = 
        new HashSet<>(Arrays.asList(Type.getType(Cloneable.class), 
                             Type.getType(java.io.Serializable.class)));
}