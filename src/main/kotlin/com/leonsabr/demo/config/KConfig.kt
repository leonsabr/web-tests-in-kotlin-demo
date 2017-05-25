package com.leonsabr.demo.config

object KConfig {
    val seleniumHub: String = System.getProperty("seleniumHub")

    val browserName: String = System.getProperty("browserName")

    private val hostname = System.getProperty("hostname")

    private val teamcityServerPort = System.getProperty("teamcityServerPort")

    val teamcityBaseURL = "http://${hostname}:${teamcityServerPort}"
}
