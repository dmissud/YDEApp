package org.yde.ydeapp.interfacerefi;

import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import org.yde.ydeapp.application.in.CollectionApplicationCmd;
import org.yde.ydeapp.application.in.ReferenceApplicationUseCase;

import java.io.InputStreamReader;
import java.util.Iterator;

public class TransformerSourceToCmd implements CollectionApplicationCmd {
    private CsvToBean csvToBean;
    public TransformerSourceToCmd (InputStreamReader inputStreamReader) {

         //   HeaderColumnNameMappingStrategy<ApplicationSourcePosition> strategy
        //            = new HeaderColumnNameMappingStrategy<>();
        ColumnPositionMappingStrategy strategy = new  ColumnPositionMappingStrategy();
            strategy.setType(ApplicationSourcePosition.class);

            this.csvToBean = new CsvToBeanBuilder(inputStreamReader)
                    .withSeparator(';')
                    .withMappingStrategy(strategy)
                    .withType(ApplicationSourcePosition.class)
                    .build();



    }
    @Override
    public Iterator<ReferenceApplicationUseCase.ReferenceApplicationCmd> iterator() {
        return new IteratorRefiFile();
    }


    class IteratorRefiFile implements Iterator<ReferenceApplicationUseCase.ReferenceApplicationCmd> {

        private ApplicationSourcePosition applicationSourcePosition;
        private final Iterator<ApplicationSourcePosition> csvIterator;

        public IteratorRefiFile() {
            this.csvIterator = csvToBean.iterator();
            findNextValidApplicationSourcePosition();
        }

        private void findNextValidApplicationSourcePosition() {
            if (csvIterator.hasNext()) {
                applicationSourcePosition = csvIterator.next();
                if (applicationSourcePosition.getState().equals("Désactivée")) {
                    findNextValidApplicationSourcePosition();
                }
            } else {
                applicationSourcePosition = null;
            }
        }

        @Override
        public boolean hasNext() {
            return applicationSourcePosition != null;
        }

        @Override
        public ReferenceApplicationUseCase.ReferenceApplicationCmd next() {
            ReferenceApplicationUseCase.ReferenceApplicationCmd referenceApplicationCmd =
                    new ReferenceApplicationUseCase.ReferenceApplicationCmd(applicationSourcePosition.getCodeApp(), applicationSourcePosition.getShortLibelle(),
                            applicationSourcePosition.getLongLibelle(), applicationSourcePosition.getIdResponsableMOE(), applicationSourcePosition.getFirstNameResponsableMoe(),
                            applicationSourcePosition.getLastNameResponsableMoe());

            findNextValidApplicationSourcePosition();

            return referenceApplicationCmd;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }


}
