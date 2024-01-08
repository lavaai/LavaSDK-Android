package ai.lava.demoapp.android.consent

import ai.lava.demoapp.android.common.AppSession
import com.lava.lavasdk.ConsentListener
import com.lava.lavasdk.Lava
import com.lava.lavasdk.LavaPIConsentFlag

enum class ConsentFlag(val flag: String) {
    StrictlyNecessary("Strictly Necessary"),
    PerformanceAndLogging("Performance And Logging"),
    Functional("Functional"),
    Targeting("Targeting"),
}

object ConsentUtils {

    fun getStoredConsentFlags(): Set<LavaPIConsentFlag> {
        val consentFlags = AppSession.instance.getConsentFlags()
        return mapToLavaConsentFlags(consentFlags)
    }

    fun mapToLavaConsentFlags(consentFlags: Set<ConsentFlag>): Set<LavaPIConsentFlag> {
        return consentFlags.map {
            when (it) {
                ConsentFlag.PerformanceAndLogging -> LavaPIConsentFlag.PerformanceAndLogging
                ConsentFlag.Functional -> LavaPIConsentFlag.Functional
                ConsentFlag.Targeting -> LavaPIConsentFlag.Targeting
                ConsentFlag.StrictlyNecessary -> LavaPIConsentFlag.StrictlyNecessary
            }
        }.toSet()
    }

    fun fromLavaConsentFlags(consentFlags: Set<LavaPIConsentFlag>): Set<ConsentFlag> {
        return consentFlags.map {
            when (it) {
                LavaPIConsentFlag.PerformanceAndLogging -> ConsentFlag.PerformanceAndLogging
                LavaPIConsentFlag.Functional -> ConsentFlag.Functional
                LavaPIConsentFlag.Targeting -> ConsentFlag.Targeting
                LavaPIConsentFlag.StrictlyNecessary -> ConsentFlag.StrictlyNecessary
            }
        }.toSet()
    }

    fun applyConsentFlags(
        consentFlags: Set<ConsentFlag>,
        listener: ConsentListener?
    ) {
        val itemsToUpdate = mapToLavaConsentFlags(consentFlags)
        Lava.instance.setPIConsentFlags(
            itemsToUpdate,
            listener
        )
    }

    fun parseLavaPIConsentFlags(input: List<String>): Set<LavaPIConsentFlag> {
        return input.map {
            LavaPIConsentFlag.valueOf(it)
        }.toSet()
    }
}