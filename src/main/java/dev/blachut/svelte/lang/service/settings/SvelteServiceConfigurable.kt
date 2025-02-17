// Copyright 2000-2023 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license.
package dev.blachut.svelte.lang.service.settings

import com.intellij.javascript.nodejs.interpreter.NodeJsInterpreterManager
import com.intellij.javascript.nodejs.util.NodePackageField
import com.intellij.lang.typescript.lsp.JSExternalDefinitionsNodeDescriptor
import com.intellij.lang.typescript.lsp.JSExternalDefinitionsPackageResolver
import com.intellij.openapi.options.Configurable
import com.intellij.openapi.options.UiDslUnnamedConfigurable
import com.intellij.openapi.project.Project
import com.intellij.ui.dsl.builder.AlignX
import com.intellij.ui.dsl.builder.Panel
import com.intellij.ui.dsl.builder.bind
import com.intellij.ui.dsl.builder.toMutableProperty
import dev.blachut.svelte.lang.SvelteBundle
import dev.blachut.svelte.lang.service.svelteLspServerPackageDescriptor

class SvelteServiceConfigurable(val project: Project) : UiDslUnnamedConfigurable.Simple(), Configurable {
  private val settings = getSvelteServiceSettings(project)

  override fun Panel.createContent() {
    group(SvelteBundle.message("svelte.service.configurable.service.group")) {
      row(SvelteBundle.message("svelte.service.configurable.service.package")) {
        val serverDescriptor = JSExternalDefinitionsNodeDescriptor(svelteLspServerPackageDescriptor.serverPackage)
        val packageField = NodePackageField(project,
                                            serverDescriptor,
                                            { NodeJsInterpreterManager.getInstance(project).interpreter },
                                            JSExternalDefinitionsPackageResolver(project, serverDescriptor))

        cell(packageField)
          .align(AlignX.FILL)
          .bind({ it.selectedRef },
                { nodePackageField, nodePackageRef -> nodePackageField.selectedRef = nodePackageRef },
                settings::packageRef.toMutableProperty())
      }


      buttonsGroup {
        row {
          radioButton(SvelteBundle.message("svelte.service.configurable.service.disabled"), SvelteServiceMode.DISABLED)
            .comment(SvelteBundle.message("svelte.service.configurable.service.disabled.help"))
        }
        row {
          radioButton(SvelteBundle.message("svelte.service.configurable.service.lsp"), SvelteServiceMode.ENABLED)
            .comment(SvelteBundle.message("svelte.service.configurable.service.lsp.help"))
        }
      }.apply {
        bind(settings::serviceMode)
      }
    }
  }

  override fun getDisplayName() = SvelteBundle.message("svelte.service.configurable.title")

  override fun getHelpTopic() = "settings.svelteservice"
}