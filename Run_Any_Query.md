# Run any query #

If you need to run a massive **update** or **delete** you must work with the **ManagerConnection**, for example:

```
	Select query= new  Select("update Comppany SET comp_id_name = ?
				   WHERE comp_id_company < ?", 
				   "new_name" , 250);
	try {


		Result result = SessionConncection.getManagerConncection().execute(
					query , 
                                        "derby_CNX",
					"MYSQL_CNX");
		SessionConncection.getManagerConncection().commitAll();
	} catch (final Throwable e) {
		log.error(e.getMessage(), e);
		SessionConncection.getManagerConncection().rollbackAll();
	}

```
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>