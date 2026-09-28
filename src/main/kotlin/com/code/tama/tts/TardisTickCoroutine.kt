package com.code.tama.tts


import com.code.tama.tts.server.capabilities.interfaces.ITARDISLevel
import com.code.tama.tts.server.data.tardis.EnergyMode
import kotlinx.coroutines.*

class TardisTickCoroutine(val tardis: ITARDISLevel) {

    private var job: Job? = null
    private var oTick: Long = 0;

    @OptIn(DelicateCoroutinesApi::class)
    fun start() {
        job = GlobalScope.launch(Dispatchers.Default) {
            while (isActive) {
                try {
                    val data = tardis.GetData()
                    val subSystems = data.subSystemsData
                    val flightData = tardis.GetFlightData()
                    if (tardis.ticks % 20 == 0L) {

                        if (subSystems.DynamorphicController.isActivated
                            && subSystems.DynamorphicGeneratorStacks.isNotEmpty()
                            && data.isRefueling
                            && !flightData.isInFlight
                        ) {
                            tardis.getEnergy().receivePower(EnergyMode.ARTRON, 1, false)
                        }
                    }

                    if (tardis.ticks != oTick) {
                        if (flightData.isInFlight)
                            tardis.FlightTick()
                        oTick = tardis.ticks;
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

    fun stop() {
        job?.cancel()
    }
}
