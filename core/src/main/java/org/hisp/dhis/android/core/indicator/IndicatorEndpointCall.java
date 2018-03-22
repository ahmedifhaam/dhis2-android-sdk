/*
 * Copyright (c) 2017, University of Oslo
 *
 * All rights reserved.
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 * Neither the name of the HISP project nor the names of its contributors may
 * be used to endorse or promote products derived from this software without
 * specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.hisp.dhis.android.core.indicator;

import org.hisp.dhis.android.core.common.GenericCallData;
import org.hisp.dhis.android.core.common.GenericEndpointCallImpl;
import org.hisp.dhis.android.core.common.GenericHandler;
import org.hisp.dhis.android.core.common.Payload;
import org.hisp.dhis.android.core.common.UidsQuery;
import org.hisp.dhis.android.core.resource.ResourceModel;

import java.io.IOException;
import java.util.Set;

public final class IndicatorEndpointCall extends GenericEndpointCallImpl<Indicator, IndicatorModel, UidsQuery> {
    private final IndicatorService indicatorService;

    private IndicatorEndpointCall(GenericCallData data, IndicatorService indicatorService,
                                  GenericHandler<Indicator, IndicatorModel> indicatorHandler, UidsQuery uidsQuery) {
        super(data, indicatorHandler, ResourceModel.Type.INDICATOR, new IndicatorModelBuilder(), uidsQuery);
        this.indicatorService = indicatorService;
    }

    @Override
    protected retrofit2.Call<Payload<Indicator>> getCall(UidsQuery query, String lastUpdated)
            throws IOException {
        return indicatorService.getIndicators(
                Indicator.allFields,
                Indicator.lastUpdated.gt(lastUpdated),
                Indicator.uid.in(query.uids()),
                Boolean.FALSE);
    }

    public interface Factory {
        IndicatorEndpointCall create(GenericCallData data, Set<String> uids);
    }

    public static final IndicatorEndpointCall.Factory FACTORY = new IndicatorEndpointCall.Factory() {
        @Override
        public IndicatorEndpointCall create(GenericCallData data, Set<String> uids) {
            return new IndicatorEndpointCall(data, data.retrofit().create(IndicatorService.class),
                    IndicatorHandler.create(data.databaseAdapter()), UidsQuery.create(uids, null));
        }
    };
}