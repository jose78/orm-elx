# Create #

```
	TblAddress address = new TblAddress();
	address.setCity("CITY:madrid.");
	address.setName("NAME:PaLo BaJO.");
	address.setState("STATE:YO_misMO.");
	log.debug(address);

	try {	
		new Crud().insert(address);
		log.debug(address);
	} catch (final Throwable e) {
		SessionConncection.getManagerConncection().rollbackAll();
		log.error(e.getMessage(), e);
	}
	SessionConncection.getManagerConncection().commitAll();
```