package org.yde.ydeapp.interfacerefi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.step.skip.SkipPolicy;
import org.springframework.batch.item.file.FlatFileParseException;

import java.io.FileNotFoundException;

public class FileVerificationSkipper implements SkipPolicy {

    private static final Logger log = LoggerFactory.getLogger("badRecordLogger");

    @Override
    public boolean shouldSkip(Throwable exception, int skipCount)  {
        if (exception instanceof FileNotFoundException) {
            return false;
        } else if (exception instanceof FlatFileParseException && skipCount <= 5) {
            FlatFileParseException ffpe = (FlatFileParseException) exception;
            String errorMessage = "An error occured while processing the " + ffpe.getLineNumber() + " line of the file. Below was the faulty " + "input.\n" +
                ffpe.getInput() + "\n";
            log.error("{}", errorMessage);
            return true;
        } else {
            return false;
        }
    }
}