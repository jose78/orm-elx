# Find #

You must work with this method if you need to get a collection of entities with their relations (One to One or One to Much). the variable level inficates the depth in the driagram that you want, for example:

  * level = 0 of Company -> you get a collection of **TblCompany** without their relations.
  * level = 1 of Company -> you get a collection of **TblCompany** with their relations (Address , Employee).
  * level = 2 of Company -> you get a collection of **TblCompany** with their relations (Address , Employee-> Address).

```
	Criteria criteria= new Criteria(" comp_id_company < ? " , 201).setPagination(1, 1)
	try {
		Integer level = 2;
		final Set<TblCompany> result = new Crud().find(TblCompany.class,
                                                                level,
                                                                criteria
                                                             );

	} catch (final Exception e) {
		log.error(e.getMessage(), e);
	}

```