package regression.xgboost.utils;

import java.util.List;
import java.util.concurrent.AbstractExecutorService;
import java.util.concurrent.TimeUnit;

public class DummyExecutorService extends AbstractExecutorService {
	public boolean awaitTermination(long timeout, TimeUnit unit) {
		return false;
	}

	public boolean isShutdown() {
		return false;
	}

	@Override
	public void shutdown() {

	}

	@Override
	public List<Runnable> shutdownNow() {
		return null;
	}

	@Override
	public boolean isTerminated() {
		return false;
	}

	@Override
	public void execute(Runnable command) {
		command.run();
	}

}
