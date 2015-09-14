package org.hisp.dhis.android.sdk.models.programstagedataelement;

import org.hisp.dhis.android.sdk.models.common.IStore;
import org.hisp.dhis.android.sdk.models.dataelement.DataElement;
import org.hisp.dhis.android.sdk.models.programstagesection.ProgramStageSection;
import org.hisp.dhis.android.sdk.models.programstage.ProgramStage;

import java.util.List;

public interface IProgramStageDataElementStore extends IStore<ProgramStageDataElement> {
    List<ProgramStageDataElement> query(ProgramStage programStage);
    List<ProgramStageDataElement> query(ProgramStageSection programStageSection);
    ProgramStageDataElement query(ProgramStage programStage, DataElement dataElement);
}