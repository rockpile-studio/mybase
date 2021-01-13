package studio.rockpile.mybase.service.base;

public abstract class BaseServiceHandler {

	public abstract void perform(String[] args) throws Exception;

	public abstract String usage();
}
