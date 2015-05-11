# Create and configure your connections #

The definition of connections is very simple and easy, you only need to  extend the class `AbstractConncection` and implement their abstract methods, to end also is necessary to use the annotation `@ConnectionDef`. You haave here two examples:

## 1º Example: Connection definition with DataSource ##

```
@ConnectionDef(nameConnection = "MySQL_CNX", read = true, write = true)
public class MySQLConnection extends AbstractConncection {

	private static Logger log = Logger.getLogger(MySQLConnection.class);
	private DataSource ds;
        private Connection cnx;

	public MySQLConnection() {
		super();
	}

	@Override
	protected void init() {
		InitialContext ctx;
		try {
			ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:blabla/bla/jdbc/blabla_ds");
		} catch (NamingException e1) {
			e1.printStackTrace();
		}

		log.debug("Load Driver:" + getName() + " OK.");
	}

	@Override
	protected Connection get() {
		try {
			cnx = ds.getConnection();
		} catch (SQLException e) {
			....
		}
		return cnx;
	}
}
```

## 2º Example: Connection definition with Driver ##

```
@ConnectionDef(nameConnection = "MySQL_CNX", read = true, write = true)
public class MySQLConnection extends AbstractConncection {

	private static Logger log = Logger.getLogger(MySQLConnection.class);

	private String strUrl;
	private String strDriverName;
	private String strUsr;
	private String strPass;
	private boolean flagAutoCommit;
        private Connection cnx;

	public MySQLConnection() {
		super();
	}

	@Override
	protected void init() {
		flagAutoCommit = false;
		strUsr = "bla";
		strPass = "bla_bla";
		strUrl = "jdbc:mysql://localhost:3306/blablaBD";
		strDriverName = "com.mysql.jdbc.Driver";

		try {
			Class.forName(strDriverName).newInstance();
		} catch (final Exception e) {
        		...
		}
	}

	@Override
	protected Connection get() {
		try {
			cnx = DriverManager.getConnection(strUrl, strUsr, strPass);
		} catch (SQLException e) {
                      ...
		}
		return cnx;
	}	
}
```