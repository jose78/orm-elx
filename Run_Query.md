# Run Any Query #

```
        try {

                Result result = SessionConncection.getManagerConncection().execute(
                                        new Select("update Comppany SET comp_id_name = ? WHERE comp_id_company < ?", "new_name" , 250), 
                                        "derby_CNX",
                                        "MYSQL_CNX");
                SessionConncection.getManagerConncection().commitAll();
        } catch (final Throwable e) {
                log.error(e.getMessage(), e);
                SessionConncection.getManagerConncection().rollbackAll();
        }
```