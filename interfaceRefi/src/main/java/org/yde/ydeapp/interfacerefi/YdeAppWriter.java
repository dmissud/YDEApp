package org.yde.ydeapp.interfacerefi;

import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;
import org.yde.ydeapp.application.in.application.CollectionApplicationCmd;
import org.yde.ydeapp.application.in.application.ReferenceApplicationUseCase.ReferenceApplicationCmd;
import org.yde.ydeapp.application.in.application.ReferenceCollectionOfApplicationUseCase;
import org.yde.ydeapp.domain.organization.OrganizationIdent;

import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class YdeAppWriter implements ItemWriter<ReferenceApplicationCmd> {

    private final ReferenceCollectionOfApplicationUseCase referenceCollectionOfApplicationUseCase;
    private String nameOfFile;
    private List<String> organizations;

    public YdeAppWriter(ReferenceCollectionOfApplicationUseCase referenceCollectionOfApplicationUseCase) {
        this.referenceCollectionOfApplicationUseCase = referenceCollectionOfApplicationUseCase;
    }

    @Override
    public void write(List<? extends ReferenceApplicationCmd> listOfApplicationCmd) {
        List<? extends ReferenceApplicationCmd> listOfFilteredWithOrganizationCmd = listOfApplicationCmd.stream().filter(cmd -> organizations.contains(cmd.getIdRefOrganizationMoe())).collect(Collectors.toList());
        if (!listOfFilteredWithOrganizationCmd.isEmpty()) {
            referenceCollectionOfApplicationUseCase.referenceOrUpdateCollectionOfApplication(new CollectionApplicationCmd(listOfFilteredWithOrganizationCmd, this.nameOfFile));
        }
    }

    public void workOn(Path pathRefiFileToImport, List<OrganizationIdent> organizations) {
        this.nameOfFile = pathRefiFileToImport.getFileName().toString();
        this.organizations = organizations.stream().map(OrganizationIdent::getIdRefog).collect(Collectors.toList());
    }
}
