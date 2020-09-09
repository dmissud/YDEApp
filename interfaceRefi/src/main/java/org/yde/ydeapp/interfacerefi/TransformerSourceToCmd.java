package org.yde.ydeapp.interfacerefi;

import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.yde.ydeapp.application.in.CollectionApplicationCmd;
import org.yde.ydeapp.application.in.ReferenceApplicationUseCase;

import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class TransformerSourceToCmd implements CollectionApplicationCmd {
    private final CsvToBean csvToBean;
    private StatTraitementRefiFile statTraitementRefiFile = new StatTraitementRefiFile();

    public TransformerSourceToCmd(InputStreamReader inputStreamReader) {
        ColumnPositionMappingStrategy strategy = new ColumnPositionMappingStrategy();
        strategy.setType(ApplicationSourcePosition.class);

        this.csvToBean = new CsvToBeanBuilder(inputStreamReader)
                .withSeparator(';')
                .withMappingStrategy(strategy)
                .withIgnoreQuotations(true)
                .withStrictQuotes(false)
                .withSkipLines(1)
                .build();
    }

    public final StatTraitementRefiFile giveResult() {
        return new StatTraitementRefiFile(this.statTraitementRefiFile);
    }

    @Override
    public Iterator<ReferenceApplicationUseCase.ReferenceApplicationCmd> iterator() {
        statTraitementRefiFile = new StatTraitementRefiFile();
        return new IteratorRefiFile();
    }


    class IteratorRefiFile implements Iterator<ReferenceApplicationUseCase.ReferenceApplicationCmd> {

        private ApplicationSourcePosition applicationSourcePosition;
        private final Iterator<ApplicationSourcePosition> csvIterator;

        public IteratorRefiFile() {
            this.csvIterator = csvToBean.iterator();
            findNextValidApplicationSourcePosition();
        }

        private StatTraitementRefiFile findNextValidApplicationSourcePosition() {

            if (csvIterator.hasNext()) {
                applicationSourcePosition = csvIterator.next();
                statTraitementRefiFile.addReadLine();

                if (applicationSourcePosition.getState().equals("Désactivée") || applicationSourcePosition.getCodeOfTypeOfApplication().equals("SU")) {
                    statTraitementRefiFile.addRejetedLine();
                    findNextValidApplicationSourcePosition();
                }
            } else {
                applicationSourcePosition = null;
            }
            return statTraitementRefiFile;
        }

        @Override
        public boolean hasNext() {
            return applicationSourcePosition != null;
        }

        @Override
        public ReferenceApplicationUseCase.ReferenceApplicationCmd next() {
            if (applicationSourcePosition != null) {
                ReferenceApplicationUseCase.ReferenceApplicationCmd referenceApplicationCmd =
                        new ReferenceApplicationUseCase.ReferenceApplicationCmd(
                                applicationSourcePosition.getCodeApp(),
                                applicationSourcePosition.getShortLibelle(),
                                applicationSourcePosition.getLongLibelle(),
                                new ReferenceApplicationUseCase.ReferenceApplicationCmd.ResponsableCmd(
                                        applicationSourcePosition.getIdResponsableMOE(),
                                        applicationSourcePosition.getFirstNameResponsableMoe(),
                                        applicationSourcePosition.getLastNameResponsableMoe()),
                                applicationSourcePosition.getIdRefogEntityMoe(),
                                new ReferenceApplicationUseCase.ReferenceApplicationCmd.CycleLifeCmd(
                                    applicationSourcePosition.getState(),
                                    applicationSourcePosition.getDateOfCreation(),
                                    applicationSourcePosition.getDateOfLastUpdate(),
                                    applicationSourcePosition.getDateEndInReality()));

                findNextValidApplicationSourcePosition();
                return referenceApplicationCmd;
            } else {
                throw new NoSuchElementException("No more Cmd in the file");
            }

        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }


}