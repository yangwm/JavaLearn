
/*
 * Copyright (c) 1995 - 2008 Sun Microsystems, Inc.  All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Sun Microsystems nor the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */ 

package extra.reflect.types;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Member;
import java.util.List;
import java.util.ArrayList;
import static java.lang.System.out;

public class EnumSpy {
    private static final String fmt = "  %11s:  %s %s%n";

    public static void main(String... args) {
	try {
	    Class<?> c = Class.forName(args[0]);
	    if (!c.isEnum()) {
		out.format("%s is not an enum type%n", c);
		return;
	    }
	    out.format("Class:  %s%n", c);

	    Field[] flds = c.getDeclaredFields();
	    List<Field> cst = new ArrayList<Field>();  // enum constants
	    List<Field> mbr = new ArrayList<Field>();  // member fields
	    for (Field f : flds) {
		if (f.isEnumConstant())
		    cst.add(f);
		else
		    mbr.add(f);
	    }
	    if (!cst.isEmpty())
		print(cst, "Constant");
	    if (!mbr.isEmpty())
		print(mbr, "Field");

	    Constructor[] ctors = c.getDeclaredConstructors();
	    for (Constructor ctor : ctors) {
		out.format(fmt, "Constructor", ctor.toGenericString(),
			   synthetic(ctor));
	    }

	    Method[] mths = c.getDeclaredMethods();
	    for (Method m : mths) {
		out.format(fmt, "Method", m.toGenericString(),
			   synthetic(m));
	    }

        // production code should handle this exception more gracefully
	} catch (ClassNotFoundException x) {
	    x.printStackTrace();
	}
    }

    private static void print(List<Field> lst, String s) {
	for (Field f : lst) {
 	    out.format(fmt, s, f.toGenericString(), synthetic(f));
	}
    }

    private static String synthetic(Member m) {
	return (m.isSynthetic() ? "[ synthetic ]" : "");
    }
}
/*
args:{java reflect.types.EnumSpy java.lang.annotation.RetentionPolicy}
output:
Class:  class java.lang.annotation.RetentionPolicy
     Constant:  public static final java.lang.annotation.RetentionPolicy java.la
ng.annotation.RetentionPolicy.SOURCE
     Constant:  public static final java.lang.annotation.RetentionPolicy java.la
ng.annotation.RetentionPolicy.CLASS
     Constant:  public static final java.lang.annotation.RetentionPolicy java.la
ng.annotation.RetentionPolicy.RUNTIME
        Field:  private static final java.lang.annotation.RetentionPolicy[] java
.lang.annotation.RetentionPolicy.$VALUES [ synthetic ]
  Constructor:  private java.lang.annotation.RetentionPolicy()
       Method:  public static java.lang.annotation.RetentionPolicy java.lang.ann
otation.RetentionPolicy.valueOf(java.lang.String)
       Method:  public static java.lang.annotation.RetentionPolicy[] java.lang.a
nnotation.RetentionPolicy.values()

*///:
/*
args:{java reflect.types.EnumSpy reflect.types.Eon}
output:
Class:  class reflect.types.Eon
     Constant:  public static final reflect.types.Eon reflect.types.Eon.HADEAN
     Constant:  public static final reflect.types.Eon reflect.types.Eon.ARCHAEAN

     Constant:  public static final reflect.types.Eon reflect.types.Eon.PROTEROZ
OIC
     Constant:  public static final reflect.types.Eon reflect.types.Eon.PHANEROZ
OIC
        Field:  private static final reflect.types.Eon[] reflect.types.Eon.$VALU
ES [ synthetic ]
  Constructor:  private reflect.types.Eon()
       Method:  public static reflect.types.Eon reflect.types.Eon.valueOf(java.l
ang.String)
       Method:  public static reflect.types.Eon[] reflect.types.Eon.values()

*///:
/*
args:{java reflect.types.EnumSpy java.util.ArrayList}
output:
class java.util.ArrayList is not an enum type

*///:
/*
args:{java reflect.types.EnumSpy java.util.concurrent.TimeUnit}
output:
Class:  class java.util.concurrent.TimeUnit
     Constant:  public static final java.util.concurrent.TimeUnit java.util.conc
urrent.TimeUnit.NANOSECONDS
     Constant:  public static final java.util.concurrent.TimeUnit java.util.conc
urrent.TimeUnit.MICROSECONDS
     Constant:  public static final java.util.concurrent.TimeUnit java.util.conc
urrent.TimeUnit.MILLISECONDS
     Constant:  public static final java.util.concurrent.TimeUnit java.util.conc
urrent.TimeUnit.SECONDS
     Constant:  public static final java.util.concurrent.TimeUnit java.util.conc
urrent.TimeUnit.MINUTES
     Constant:  public static final java.util.concurrent.TimeUnit java.util.conc
urrent.TimeUnit.HOURS
     Constant:  public static final java.util.concurrent.TimeUnit java.util.conc
urrent.TimeUnit.DAYS
        Field:  static final long java.util.concurrent.TimeUnit.C0
        Field:  static final long java.util.concurrent.TimeUnit.C1
        Field:  static final long java.util.concurrent.TimeUnit.C2
        Field:  static final long java.util.concurrent.TimeUnit.C3
        Field:  static final long java.util.concurrent.TimeUnit.C4
        Field:  static final long java.util.concurrent.TimeUnit.C5
        Field:  static final long java.util.concurrent.TimeUnit.C6
        Field:  static final long java.util.concurrent.TimeUnit.MAX
        Field:  private static final java.util.concurrent.TimeUnit[] java.util.c
oncurrent.TimeUnit.$VALUES [ synthetic ]
  Constructor:  java.util.concurrent.TimeUnit(java.lang.String,int,java.util.con
current.TimeUnit$1) [ synthetic ]
  Constructor:  private java.util.concurrent.TimeUnit()
       Method:  abstract int java.util.concurrent.TimeUnit.excessNanos(long,long
)
       Method:  public void java.util.concurrent.TimeUnit.timedJoin(java.lang.Th
read,long) throws java.lang.InterruptedException
       Method:  public void java.util.concurrent.TimeUnit.timedWait(java.lang.Ob
ject,long) throws java.lang.InterruptedException
       Method:  public long java.util.concurrent.TimeUnit.toDays(long)
       Method:  public long java.util.concurrent.TimeUnit.toHours(long)
       Method:  public long java.util.concurrent.TimeUnit.toMicros(long)
       Method:  public long java.util.concurrent.TimeUnit.toMillis(long)
       Method:  public long java.util.concurrent.TimeUnit.toMinutes(long)
       Method:  public long java.util.concurrent.TimeUnit.toSeconds(long)
       Method:  public static java.util.concurrent.TimeUnit java.util.concurrent
.TimeUnit.valueOf(java.lang.String)
       Method:  public static java.util.concurrent.TimeUnit[] java.util.concurre
nt.TimeUnit.values()
       Method:  public void java.util.concurrent.TimeUnit.sleep(long) throws jav
a.lang.InterruptedException
       Method:  public long java.util.concurrent.TimeUnit.toNanos(long)
       Method:  static long java.util.concurrent.TimeUnit.x(long,long,long)
       Method:  public long java.util.concurrent.TimeUnit.convert(long,java.util
.concurrent.TimeUnit)

*///: