# Read #

```
	try {
		Select select = new Select("SELECT c.comp_id_company AS id, c.comp_name AS NAME " 
                                         +" FROM Company c "
                                         +" WHERE c.comp_id_company > ? "
                                         +" AND comp_id_company < ? "
                                         +" UNION ALL "
                                         +" SELECT emp_id_employee AS ID , emp_name AS NAME "
                                         +" WHERE "
                                         +" emp_name = ? " ,1,100,"Montse"
                                         ).setPagination(3, 4);

		final List<Item> result = new Crud().read(Item.class, select);
		
	} catch (Exception e) {
		log.error(e.getMessage(), e);
	}
```