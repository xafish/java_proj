package datasrc.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service("dataProducerStringBlocked")
public class DataProducerStringBlocked implements DataProducer<StringValue> {
    private static final Logger log = LoggerFactory.getLogger(DataProducerStringBlocked.class);

    @Override
    public StringValue produce(long seed) {
        log.info("produce using seed:{}", seed);
        sleep();
        return new StringValue(String.format("someDataStr:%s", seed));
    }

    private void sleep() {
        try {
            Thread.sleep(TimeUnit.SECONDS.toMillis(10));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
