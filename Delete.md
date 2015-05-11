# Delete #
```
        Crud crud = new Crud();
	int level= 0;
	try {

		List<TblAddress> lstResult = crud.find(TblAddress.class, level,new Criteria());

		TblAddress entity = lstResult.get(0);

		crud.delete(entity);

		SessionConncection.getManagerConncection().commitAll();
	} catch (final Throwable e) {
		log.error(e.getMessage(), e);
		SessionConncection.getManagerConncection().rollbackAll();
	}
```