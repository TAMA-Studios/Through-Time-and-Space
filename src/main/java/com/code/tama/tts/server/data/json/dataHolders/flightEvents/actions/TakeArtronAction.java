package com.code.tama.tts.server.data.json.dataHolders.flightEvents.actions;

import com.code.tama.triggerapi.helpers.MathUtils;

public class TakeArtronAction extends FlightEventFailureAction {
    public TakeArtronAction(int amountToTake) {
        super((cap) -> cap.GetData().getSubSystemsData().setArtron(MathUtils.clamp(cap.GetData().getSubSystemsData().getArtron(), 0, Double.MAX_VALUE)), "take_artron");
    }
}
