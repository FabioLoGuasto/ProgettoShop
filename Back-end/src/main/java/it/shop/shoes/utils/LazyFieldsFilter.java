package it.shop.shoes.utils;

import jakarta.persistence.Persistence;

public class LazyFieldsFilter {
	
	/**
	 * Since there is negation, this method return true if the field associated with the table is not loaded 
	 * because in the field where there is the annotation we have a fetchType.LAZY, i want the field of that 
	 * table not to be loaded.
	 * 
	 * This field is the foreign key(obj)
	 * 
	 * .getPersistenceUtil() fetches the instance and thanks to this i can use the .isLoaded(obj) 
	 * method which check if that field associated with the entity is loaded or not.
	 * 
	 * If there weren't negation it would return true if the table was loaded.
	 */
	@Override
    public boolean equals(Object obj) {
        return !Persistence.getPersistenceUtil().isLoaded(obj);
    }

	
}
