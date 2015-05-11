# ConnectionDef #

When Elx start to work, Elx scans the classpath to find all classes that they will use this annotation. With `@ConnectionDef` you can indicate:
  * The name of the connection.
  * If the connection is writable  and/or readable
  * By default, Elx use the class **org.elx.orm.db.SessionConncection** to  store the **ManagerConncection** in the thread, If you work with multi-  thread you must change the values of  _nameClassConnectionProvider_ , _nameStaticMethodConnectionProvider_ in the annotation and to use your own method to manage the **ManagerConncection**.


```

@ConnectionDef(nameConnection = "MySQL_CNX", read = true, write = true ,
nameClassConnectionProvider="..." , // <- Optional
nameStaticMethodConnectionProvider="...." // <- Optional
)
```
