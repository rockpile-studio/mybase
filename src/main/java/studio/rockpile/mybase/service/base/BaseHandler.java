package studio.rockpile.mybase.service.base;

public abstract class BaseHandler {

	public abstract void perform(String[] args) throws Exception;

	public abstract String usage();
}
