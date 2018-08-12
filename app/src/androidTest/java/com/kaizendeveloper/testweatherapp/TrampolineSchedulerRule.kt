package com.kaizendeveloper.testweatherapp

import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

class TrampolineSchedulerRule : TestRule {

    private val scheduler by lazy { Schedulers.trampoline() }

    override fun apply(base: Statement, description: Description): Statement =
        object : Statement() {
            override fun evaluate() {
                try {
                    RxJavaPlugins.setComputationSchedulerHandler { scheduler }
                    RxJavaPlugins.setIoSchedulerHandler { scheduler }
                    RxJavaPlugins.setNewThreadSchedulerHandler { scheduler }
                    RxJavaPlugins.setSingleSchedulerHandler { scheduler }
                    base.evaluate()
                } finally {
                    RxJavaPlugins.reset()
                }
            }
        }
}