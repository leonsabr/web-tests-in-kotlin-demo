<#-- Uses FreeMarker template syntax, template guide can be found at http://freemarker.org/docs/dgui.html -->

<#import "common.ftl" as common>

<#global link>${link.buildResultsLink}</#global>
<#global message><#if build.personal>Personal build<#else>Build</#if> ${buildType.fullName} <@common.short_build_info build/> is probably hanging ${var.buildShortStatusDescription} on ${agentName}</#global>
