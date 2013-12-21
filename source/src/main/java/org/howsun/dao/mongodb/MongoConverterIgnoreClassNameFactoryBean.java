/**
 *
 */
package org.howsun.dao.mongodb;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.convert.MongoTypeMapper;

/**
 * MongoDB存储文档时不需要存储类名
 *
 *
 *
 * @author howsun
 *
 */
public class MongoConverterIgnoreClassNameFactoryBean implements FactoryBean<MappingMongoConverter> {

	private MappingMongoConverter converter;

	public void setConverter(MappingMongoConverter converter) {
		this.converter = converter;
	}

	@Override
	public MappingMongoConverter getObject() throws Exception {
		MongoTypeMapper typeMapper = new DefaultMongoTypeMapper(null);
		converter.setTypeMapper(typeMapper);
		return converter;
	}

	@Override
	public Class<?> getObjectType() {
		return MappingMongoConverter.class;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

}
