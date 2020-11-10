package studio.rockpile.mybase.constant;

import studio.rockpile.mybase.service.base.BaseServiceHandler;

public enum ServiceTypeEnum {
	FILE_ENCRYPT("file-encrypt", "studio.rockpile.mybase.service.FileEncryptor");

	private final String key;
	private final String className;

	ServiceTypeEnum(final String key, final String className) {
		this.key = key;
		this.className = className;
	}

	public static BaseServiceHandler buildHandler(ServiceTypeEnum type) throws Exception {
		Class<?> clazz = Class.forName(type.getClassName());
		if (BaseServiceHandler.class.isAssignableFrom(clazz)) {
			return (BaseServiceHandler) clazz.newInstance();
		} else {
			throw new Exception("对应的服务类(" + type.getClassName() + ")未继承BaseServiceHandler");
		}
	}

	public static ServiceTypeEnum getType(String key) {
		ServiceTypeEnum[] types = ServiceTypeEnum.values();
		for (ServiceTypeEnum type : types) {
			if (type.getKey().equals(key)) {
				return type;
			}
		}
		return null;
	}

	public String getKey() {
		return key;
	}

	public String getClassName() {
		return className;
	}

}
