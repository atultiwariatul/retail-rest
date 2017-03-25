package com.atul.retail.configs;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.GregorianCalendar;

/**
 * An Aspect which will update lastModifiedDate automatically whenever we are going to save an entity
 * in Collection.
 */
@Aspect
public class MongoAuditAspect {

	@Before(value = "(execution(public * org.springframework.data.mongodb.core.MongoOperations.update*(..))  && args(query,update,.. ))")
	public void setAuditFields(Query query, Update update) {
		if (update != null) {
			update.set("lastModifiedDate",  new GregorianCalendar().getTime());
		}
	}
}