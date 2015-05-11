# Update #

```
        int level = 0;
	try {
		List<TblAddress> lstResult = new Crud().find(TblAddress.class, level,
					new Criteria());

		TblAddress entity = lstResult.get(0);
		entity.setCity("MOD CITY");
		entity.setName("MOD NAME");

		new Crud().update(entity);
		SessionConncection.getManagerConncection().commitAll();

	} catch (final Throwable e) {
		log.error(e.getMessage(), e);
		SessionConncection.getManagerConncection().rollbackAll();
	}
```