package info.jab.fp.async;

import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestLoggerExtension implements BeforeEachCallback {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestLoggerExtension.class);

    @Override
    public void beforeEach(ExtensionContext context) {
        String testName = context.getDisplayName();
        LOGGER.info("Running test: {}", testName);
    }
}

